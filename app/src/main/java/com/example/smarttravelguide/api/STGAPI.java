package com.example.smarttravelguide.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class STGAPI {

        public static apiService apiService = null;
        public static String base_url = "http://10.0.2.2:8081";


        public static apiService getApiService() {
            if (apiService == null) {
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
                OkHttpClient okHttpClient = new OkHttpClient()
                        .newBuilder()
                        .addInterceptor(loggingInterceptor)
                        .build();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(base_url)
                        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient)
                        .build();

                apiService = retrofit.create(apiService.class);
            }
            return apiService;
        }

        public interface apiService {




        }

    }


