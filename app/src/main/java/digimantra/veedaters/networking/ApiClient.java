package digimantra.veedaters.networking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dmlabs on 28/8/17.
 */

public class ApiClient {
  //  private static final String ROOT_URL="http://googlex.in/veedater/api/web/v1/";
    private static final String ROOT_URL="http://veedater.dmlabs.in/api/web/v1/";
   // public static final String IMG_BASE="http://googlex.in/veedater/uploads/";
    public static final String IMG_BASE="http://veedater.dmlabs.in/uploads/";

    private static ApiService service=null;
    private static Retrofit retrofit=null;
    private static boolean canCache;
    public static ApiService getClient()
    {
        if (retrofit==null)
        {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(5, TimeUnit.MINUTES)
                    .connectTimeout(5,TimeUnit.MINUTES)
                    .cache(null)
                    .addInterceptor(interceptor).build();
            retrofit=new Retrofit.Builder().baseUrl(ROOT_URL).addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client).build();

        }
        return retrofit.create(ApiService.class);
    }
}
