import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class OkHttp {

    public static final String REQUEST_URL = "http://localhost:8081";
    private static OkHttpClient okHttpClient;

    public static void main(String[] args) {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient();
        }
        doRequest();
    }

    private static void doRequest() {
        Request request = new Request.Builder().url(REQUEST_URL).get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("error, e:" + e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                System.out.println(response.body());
            }
        });
    }
}
