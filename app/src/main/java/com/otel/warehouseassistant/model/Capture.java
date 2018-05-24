package com.otel.warehouseassistant.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

public class Capture implements Parcelable {

    public int CaptureId;
    public String WarehouseCode;
    public String Owner;
    public String Type;
    public String OrderNo;
    public String SKU;
    public String LotNo;
    public String LPN;
    public String Remarks;
    public boolean IsActive;
    public String CreateBy;
    public Date CreateOn;
    public String UpdateBy;
    public Date UpdateOn;
    public ArrayList<ImageInfo> ListImage;

    public Capture() {

    }

    public Capture(int CaptureId, String WarehouseCode, String Owner, String Type, String OrderNo, String SKU, String LotNo, String LPN, String Remarks, boolean IsActive, String CreateBy, Date CreateOn, String UpdateBy, Date UpdateOn, ArrayList<ImageInfo> ListImage) {
        this.CaptureId = CaptureId;
        this.WarehouseCode = WarehouseCode;
        this.Owner = Owner;
        this.Type = Type;
        this.OrderNo = OrderNo;
        this.SKU = SKU;
        this.LotNo = LotNo;
        this.LPN = LPN;
        this.Remarks = Remarks;
        this.IsActive = IsActive;
        this.CreateBy = CreateBy;
        this.CreateOn = CreateOn;
        this.UpdateBy = UpdateBy;
        this.UpdateOn = UpdateOn;
        this.ListImage = ListImage;
    }

    protected Capture(Parcel in) {
        CaptureId = in.readInt();
        WarehouseCode = in.readString();
        Owner = in.readString();
        Type = in.readString();
        OrderNo = in.readString();
        SKU = in.readString();
        LotNo = in.readString();
        LPN = in.readString();
        Remarks = in.readString();
        IsActive = in.readByte() != 0;
        CreateBy = in.readString();
        CreateOn = new Date(in.readLong());
        UpdateBy = in.readString();
        UpdateOn = new Date(in.readLong());
        in.readTypedList(ListImage, ImageInfo.CREATOR);
    }

    public static final Creator<Capture> CREATOR = new Creator<Capture>() {
        @Override
        public Capture createFromParcel(Parcel in) {
            return new Capture(in);
        }

        @Override
        public Capture[] newArray(int size) {
            return new Capture[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(CaptureId);
        parcel.writeString(WarehouseCode);
        parcel.writeString(Owner);
        parcel.writeString(Type);
        parcel.writeString(OrderNo);
        parcel.writeString(SKU);
        parcel.writeString(LotNo);
        parcel.writeString(LPN);
        parcel.writeString(Remarks);
        parcel.writeByte((byte) (IsActive ? 1 : 0));
        parcel.writeString(CreateBy);
        parcel.writeLong(CreateOn.getTime());
        parcel.writeString(UpdateBy);
        parcel.writeLong(UpdateOn.getTime());
        parcel.writeTypedList(ListImage);

    }
}
