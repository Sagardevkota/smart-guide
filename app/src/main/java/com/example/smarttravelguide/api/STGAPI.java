package com.example.smarttravelguide.api;

import com.example.smarttravelguide.model.BookedPlaceDto;
import com.example.smarttravelguide.model.BookedRoomDto;
import com.example.smarttravelguide.model.DestinationBook;
import com.example.smarttravelguide.model.Hotel;
import com.example.smarttravelguide.model.JsonResponse;
import com.example.smarttravelguide.model.Place;
import com.example.smarttravelguide.model.Restaurant;
import com.example.smarttravelguide.model.RestaurantBook;
import com.example.smarttravelguide.model.RestaurantDto;
import com.example.smarttravelguide.model.Room;
import com.example.smarttravelguide.model.RoomBook;
import com.example.smarttravelguide.model.User;

import java.util.List;

import io.reactivex.Completable;
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

            @GET("smart-travel-guide/restaurants.php")
            Observable<List<Restaurant>> getRestaurants();

            @GET("smart-travel-guide/rooms.php")
            Observable<List<Room>> getRooms(@Query("hotelId")int hotelId);

            @GET("smart-travel-guide/places.php")
            Observable<List<Place>> getPlaces(@Query("type")String type);


            @POST("smart-travel-guide/book-destinations.php")
            Observable<JsonResponse> bookDestination(@Body DestinationBook destinationBook);

            @POST("smart-travel-guide/book-room.php")
            Observable<JsonResponse> bookRoom(@Body RoomBook roomBook);

            @POST("smart-travel-guide/check-for-room.php")
            Observable<JsonResponse> checkForRoom(@Query("roomId")int roomId,@Query("checkInDate")String checkInDate,@Query("checkOutDate")String checkOutDate);

            @GET("smart-travel-guide/booked-rooms.php")
            Observable<List<BookedRoomDto>> getBookedRooms(@Query("userId")int userId);

            @GET("smart-travel-guide/booked-places.php")
            Observable<List<BookedPlaceDto>> getBookedPlaces(@Query("userId")int userId, @Query("type")String type);

            @GET("smart-travel-guide/booked-restaurants.php")
            Observable<List<RestaurantDto>> getBookedRestaurants(@Query("userId") int userId);

            @GET("smart-travel-guide/check-for-restaurant.php")
            Observable<JsonResponse> checkForTable(@Query("id")int id,@Query("date")String date,@Query("time")String time);

            @POST("smart-travel-guide/book-restaurant.php")
            Observable<JsonResponse> bookRestaurant(@Body RestaurantBook restaurantBook);
        }

    }


