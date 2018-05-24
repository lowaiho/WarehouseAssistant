package com.otel.warehouseassistant.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class ImageInfo implements Parcelable {

    public int CaptureId;
    public int ImageId;
    public String ImageCode;
    public String ImageName;
    public String ImageURL;
    public Date ImageDate;

    public ImageInfo() {

    }

    public ImageInfo(int CaptureId, int ImageId, String ImageCode, String name, String url, Date ImageDate) {
        this.CaptureId = CaptureId;
        this.ImageId = ImageId;
        this.ImageCode = ImageCode;
        this.ImageName = name;
        this.ImageURL = url;
        this.ImageDate = ImageDate;
    }

    protected ImageInfo(Parcel in) {
        CaptureId = in.readInt();
        ImageId = in.readInt();
        ImageCode = in.readString();
        ImageName = in.readString();
        ImageURL = in.readString();
        ImageDate = new Date(in.readLong());
    }

    public static final Creator<ImageInfo> CREATOR = new Creator<ImageInfo>() {
        @Override
        public ImageInfo createFromParcel(Parcel in) {
            return new ImageInfo(in);
        }

        @Override
        public ImageInfo[] newArray(int size) {
            return new ImageInfo[size];
        }
    };

    public String getImageName() {
        return ImageName;
    }

    public String getImageURL() {
        return ImageURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(CaptureId);
        dest.writeInt(ImageId);
        dest.writeString(ImageCode);
        dest.writeString(ImageName);
        dest.writeString(ImageURL);
        dest.writeLong(ImageDate.getTime());
    }
}
