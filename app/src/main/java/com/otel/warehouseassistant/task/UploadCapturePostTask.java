package com.otel.warehouseassistant.task;

import android.os.AsyncTask;

import com.otel.warehouseassistant.fragment.ImageService;
import com.otel.warehouseassistant.model.Capture;
import com.otel.warehouseassistant.tool.WebApiUtils;

import retrofit2.Call;
import retrofit2.Response;

public class UploadCapturePostTask extends AsyncTask<Void, Void, Boolean> {

    public interface OnSuccessListener {
        void OnSuccess();
    }

    public interface OnFailListener {
        void OnFail();
    }

    private Capture capture;
    private OnSuccessListener onSuccessListener;
    private OnFailListener onFailListener;

    public UploadCapturePostTask(Capture capture, OnSuccessListener onSuccessListener, OnFailListener onFailListener) {
        this.capture = capture;
        this.onSuccessListener = onSuccessListener;
        this.onFailListener = onFailListener;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        ImageService service = WebApiUtils.getCartonService();

        String ImageCode = capture.ListImage.get(0).ImageCode;
        String ImageName = capture.ListImage.get(0).ImageName;
        String ImageURL = capture.ListImage.get(0).ImageURL;

        Call<Void> call = service.uploadImage(capture.WarehouseCode, capture.Owner, capture.Type, capture.OrderNo, capture.SKU, capture.LotNo,
                capture.LPN, capture.Remarks, capture.CreateBy,
                ImageCode, ImageName, ImageURL);

        try {
            Response<Void> r = call.execute();
            return (r.code() == 200);

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result)
            this.onSuccessListener.OnSuccess();
        else
            this.onFailListener.OnFail();
    }
}
