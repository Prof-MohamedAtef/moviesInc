package mo.ed.movies.inc.common.network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;
import mo.ed.movies.inc.util.Constants;
import mo.ed.movies.inc.util.Utils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Http {
    private static final String TAG = "HTTP";

    static String authorizationToken;
    static String language;
    static Retrofit retrofit;



    public static void initialize() {
        Gson gson=new GsonBuilder()
                .setLenient()
                .create();

        String bUrl = Constants.BASE_URL;
        retrofit = new Retrofit.Builder()
                .baseUrl(bUrl)
                .client(getHeader())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }

    public static <T> T create(Class<T> object) {
        //initialize(App.getConfig());
        if (retrofit == null) {
            initialize();
        }
        return retrofit.create(object);
    }

    public static <T> T create(Class<T> object, String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(getHeader())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(object);
    }

    public static <T> T createWithCountry(Class<T> object, String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(object);
    }

    public static void setAuthorizationToken(String token) {
        authorizationToken = token;
        Log.d("Authorization: ", "Token: " + authorizationToken);
    }

    public static String getAuthorizationToken() {
        return authorizationToken;
    }

    public static String getLanguage() {
        return language;
    }

    public static void setLanguage(String language) {
        Http.language = language;
    }

    private static OkHttpClient getHeader() {
        final int timezoneOffset = Utils.getTimezoneOffset();
        HttpLoggingInterceptor logging=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e("InterceptorMessage :", message);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .addNetworkInterceptor(
                        chain -> {
                            Request request = chain.request();
                            Request.Builder requestBuilder = request.newBuilder();
                            requestBuilder.addHeader("Content-Type", Constants.MULTIPART);
//                            requestBuilder.addHeader("Content-Type", "application/json");
                            requestBuilder.addHeader("Accept", "application/json");
                            request = requestBuilder.build();
                            return chain.proceed(request);
                        })
                .build();
//        okClient.interceptors().add()
        return okClient;
    }
}

