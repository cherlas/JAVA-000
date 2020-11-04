package gateway.outbound.okhttp;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import okhttp3.*;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class OkhttpOutboundHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(OkhttpOutboundHandler.class);

    private final String proxyServer;
    private static OkHttpClient okHttpClient;

    public OkhttpOutboundHandler(String proxyServer) {
        this.proxyServer = proxyServer.endsWith("/") ? proxyServer.substring(0, proxyServer.length() - 1) : proxyServer;
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient();
        }
    }

    public void handle(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        String url = proxyServer + fullRequest.uri();
        doRequest(fullRequest, ctx, url);
    }

    private static void doRequest(FullHttpRequest fullRequest, ChannelHandlerContext ctx, String url) {
        Request request = new Request.Builder().url(url).get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("error, e:" + e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                if (fullRequest != null) {
                    if (!HttpUtil.isKeepAlive(fullRequest)) {
                        ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                    } else {
                        ctx.write(response);
                    }
                    ctx.flush();
                }
            }
        });
    }
}
