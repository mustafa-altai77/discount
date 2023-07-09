package com.example.discaount.data.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://shayoub.com/discount/public/api/";
  private ApiInterface apiInterface;
  private static ApiClient INSTANCE;
  public static Retrofit retrofit;

  public static Retrofit getRetrofitClient() {
    //If condition to ensure we don't create multiple retrofit instances in a single application
    if (retrofit == null) {
      //Defining the Retrofit using Builder
      Gson gson = new GsonBuilder().setLenient().create();
      retrofit = new Retrofit.Builder()
              .baseUrl(BASE_URL) //This is the only mandatory call on Builder object.
              //.addConverterFactory(GsonConverterFactory.create()) // Convertor library used to convert response into POJO
              .addConverterFactory(GsonConverterFactory.create(gson))
              .build();
    }
    return retrofit;
  }
}