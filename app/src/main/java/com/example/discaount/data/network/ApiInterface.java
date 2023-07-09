package com.example.discaount.data.network;

import com.example.discaount.data.model.ChangePassModel;
import com.example.discaount.data.model.CompanyResponse;
import com.example.discaount.data.model.DepartmentResponse;
import com.example.discaount.data.model.DrugsResponse;
import com.example.discaount.data.model.ImageResponse;
import com.example.discaount.data.model.ImageSliderOffResponse;
import com.example.discaount.data.model.MedicineResponse;
import com.example.discaount.data.model.OfferRequest;
import com.example.discaount.data.model.OfferResponse;
import com.example.discaount.data.model.OrderRequest;
import com.example.discaount.data.model.OrderResponse;
import com.example.discaount.data.model.OrderAllResponse;
import com.example.discaount.data.model.ProductResponse;
import com.example.discaount.data.model.SearchRespons;
import com.example.discaount.data.model.SetPasswordModel;
import com.example.discaount.data.model.UserProfile;
import com.example.discaount.data.model.UserResponse;
import com.example.discaount.data.model.UserResponse2;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("departments/{id}/products")
    Call<ProductResponse> getProducts(@Path("id") String id);

    @GET("products")
    Call<SearchRespons> searchProducts(@Query("query") String name);

    @GET("drugs")
    Call<DrugsResponse> searchDrugs(@Query("query") String name);

    @GET("medicinesection/{id}/drugs")
    Call<DrugsResponse> getDrugs(@Path("id") String id);

    @GET("companies/{id}/drugs")
    Call<DrugsResponse> getCompanyDrugs(@Path("id") int id);

    @GET("drugs/{id}/instances")
    Call<DrugsResponse> getInstance(@Path("id") String id);

    @GET("companies/{id}/drugs")
    Call<DrugsResponse> getMDrugs(@Path("id") String id);

    @GET("categories")
    Call<DepartmentResponse> getCategories();

    @GET("companies")
    Call<CompanyResponse> getCompany();

    @GET("categories/{id}/departments")
    Call<DepartmentResponse> getSub(@Path("id") String id);

    @GET("medicinesection")
    Call<MedicineResponse> getMedicine();

    @POST("register")
    @FormUrlEncoded
    Call<UserResponse> register(
            @Field("password") String password,
            @Field("phone") String phone,
            @Field("address") String address,
            @Field("name") String name,
            @Field("age") String age);

    @POST("verify")
    @FormUrlEncoded
    Call<UserResponse> confirmMobile(@Field("otp_code") String otp_code,
                                     @Field("token_firebase") String token_firebase,
                                     @Field("phone") String phone);

    @GET("offers")
    Call<OfferResponse> getOffer();

    @POST("login")
    @FormUrlEncoded
    Call<UserResponse2> getLogin(
            @Field("password") String password,
            @Field("phone") String phone,
            @Field("token_firebase") String token_firebase);

    @GET("orders")
    Call<OrderResponse> getMyOrder(@Header("ana") String token);

    @POST("orders")
    Call<OrderAllResponse> sendOrder(@Header("ana") String token,
                                     @Body OrderRequest order);
    @POST("bookings")
    Call<OfferRequest> sendOffer(@Header("ana") String token,
                                 @Body OfferRequest offer);

    @GET("profile")
    Call<UserProfile> getMyData(@Header("ana") String token);

    @Multipart
    @POST("orders_uploads")
    Call<ImageResponse> upLoadImage(@Header("ana") String token,
                                    @Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("resend")
    Call<UserResponse2> resendOtp(@Field("phone") String mobile);


    @PUT("password")
    Call<ChangePassModel> changePassword(@Header("ana") String token,
                                         @Body ChangePassModel changePassword);

    @POST("password")
    Call<SetPasswordModel> setPassword(@Header("ana") String token,
                                       @Body SetPasswordModel setPasswordModel);

    @GET("images?query=add")
    Call<ImageSliderOffResponse> getAddImage();

    @GET("images?query=offer")
    Call<ImageSliderOffResponse> getOfferImage();


}