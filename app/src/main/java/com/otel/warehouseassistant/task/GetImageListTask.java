package com.otel.warehouseassistant.task;

import android.os.AsyncTask;

import com.otel.warehouseassistant.fragment.ImageService;
import com.otel.warehouseassistant.model.Capture;
import com.otel.warehouseassistant.model.ImageInfo;
import com.otel.warehouseassistant.tool.WebApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class GetImageListTask extends AsyncTask<Void, Void, Boolean> {
    public interface OnSuccessListener {
        void OnSuccess(List<Capture> result_list);
    }

    private List<Capture> result_list = null;
    private OnSuccessListener onSuccessListener;

    public GetImageListTask(OnSuccessListener onSuccessListener) {
        this.onSuccessListener = onSuccessListener;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        ImageService service = WebApiUtils.getCartonService();

        Call<List<Capture>> call = service.getImageList();

        try {
            Response<List<Capture>> r = call.execute();
            result_list = r.body();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result)
            this.onSuccessListener.OnSuccess(result_list);
        else
            this.onSuccessListener.OnSuccess(null);
    }
}
