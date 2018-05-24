package com.otel.warehouseassistant.fragment;

import com.otel.warehouseassistant.model.Capture;
import com.otel.warehouseassistant.model.ImageInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ImageService {
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
    })
    @GET("/api/image/list")
    Call<List<Capture>> getImageList();


    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
    })
    @FormUrlEncoded
    @POST("/api/image/upload")
    Call<Void> uploadImage(@Field("WarehouseCode") String WarehouseCode, @Field("Owner") String Owner, @Field("Type") String Type, @Field("OrderNo") String OrderNo, @Field("SKU") String SKU,
                           @Field("LotNo") String LotNo, @Field("LPN") String LPN, @Field("Remarks") String Remarks, @Field("CreateBy") String CreateBy,
                           @Field("ImageCode") String ImageCode, @Field("ImageName") String ImageName, @Field("ImageURL") String ImageURL);

}
