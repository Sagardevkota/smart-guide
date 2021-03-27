package com.example.smarttravelguide.api;

import com.example.smarttravelguide.model.Hotel;
import com.example.smarttravelguide.model.JsonResponse;
import com.example.smarttravelguide.model.Place;
import com.example.smarttravelguide.model.Room;
import com.example.smarttravelguide.model.User;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class STGAPI {

        public static apiService apiService = null;
        public static String base_url = "http://10.0.2.2/";


        public static apiService getApiService() {
            if (apiService == null) {
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
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


            @POST("smart-travel-guide/login.php")
            Observable<JsonResponse> login(@Body User user);

            @POST("smart-travel-guide/register.php")
            Observable<JsonResponse> register(@Body User user);

            @GET("smart-travel-guide/user-info.php")
            Observable<User> getUserInfo(@Query("id") Integer userId);

            @POST("smart-travel-guide/updateUserInfo.php")
            Observable<JsonResponse> updateUser(@Body User user);

            @GET("smart-travel-guide/hotels.php")
            Observable<List<Hotel>> getHotels();


            @GET("smart-travel-guide/rooms.php")
            Observable<List<Room>> getRooms(@Query("hotelId")int hotelId);

            @GET("smart-travel-guide/places.php")
            Observable<List<Place>> getPlaces(@Query("type")String type);


        }

    }


