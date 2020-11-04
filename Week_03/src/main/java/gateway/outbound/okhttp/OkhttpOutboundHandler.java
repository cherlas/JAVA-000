package gateway.outbound.okhttp;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import kotlin.Pair;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static io.netty.handler.codec.rtsp.RtspResponseStatuses.OK;

public class OkhttpOutboundHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(OkhttpOutboundHandler.class);
    private static OkHttpClient okHttpClient;
    private final String proxyServer;

    public OkhttpOutboundHandler(String proxyServer) {
        this.proxyServer = proxyServer.endsWith("/") ? proxyServer.substring(0, proxyServer.length() - 1) : proxyServer;
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient();
        }
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
                FullHttpResponse response1 = null;
                try {
                    response1 = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(Objects.requireNonNull(response.body()).bytes()));
                    for (Pair<? extends String, ? extends String> header : response.headers()) {
                        response1.headers().add(header.component1(), header.component2());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    response1 = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
                    ctx.close();
                } finally {
                    if (fullRequest != null) {
                        if (!HttpUtil.isKeepAlive(fullRequest)) {
                            ctx.write(response1).addListener(ChannelFutureListener.CLOSE);
                        } else {
                            ctx.write(response1);
                        }
                    }
                    ctx.flush();
                }
            }
        });
    }

    public void handle(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        String url = proxyServer + fullRequest.uri();
        doRequest(fullRequest, ctx, url);
    }
}
