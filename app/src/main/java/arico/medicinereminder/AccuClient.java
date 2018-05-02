package arico.medicinereminder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by starb on 5/1/2018.
 */
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class AccuClient {




        public static final String BASE_URL = "http://dataservice.accuweather.com/";
        private static Retrofit retrofit = null;


        public static Retrofit getClient() {


            if (retrofit==null) {

                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);

                OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
// add your other interceptors …

// add logging as last interceptor
                httpClient.addInterceptor(logging);  // <-- this is the important line!
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build())
                        .build();
            }
            return retrofit;
        }


}
