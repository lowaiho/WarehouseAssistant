package com.otel.warehouseassistant.menu;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class MenuObject implements Parcelable {
    private String _name;
    private int _photo;
    private int _bgColor;
    private String _eventCode;
    private String _forOperations;
    private List<MenuObject> _listMenuObject;
    private Class<?> _gotoContext;

    public MenuObject(String name, int photo, int bgColor, String eventCode, String forOperations, List<MenuObject> listMenuObject) {
        super();
        this._name = name;
        this._photo = photo;
        this._bgColor = bgColor;
        this._eventCode = eventCode;
        this._forOperations = forOperations;
        this._listMenuObject = listMenuObject;
    }

    public MenuObject(String name, int photo, int bgColor, String eventCode, String forOperations, List<MenuObject> listMenuObject, Class<?> gotoContext) {
        super();
        this._name = name;
        this._photo = photo;
        this._bgColor = bgColor;
        this._eventCode = eventCode;
        this._forOperations = forOperations;
        this._listMenuObject = listMenuObject;
        this._gotoContext = gotoContext;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public int getPhoto() {
        return _photo;
    }

    public void setPhoto(int photo) {
        this._photo = photo;
    }

    public int getBgColor() {
        return _bgColor;
    }

    public void setBgColor(int bgColor) {
        this._bgColor = bgColor;
    }

    public Class<?> getGotoContext() {
        return _gotoContext;
    }

    public void setGotoContext(Class<?> gotoContext) {
        this._gotoContext = gotoContext;
    }

    public String getEventCode() {
        return _eventCode;
    }

    public void setEventCode(String eventCode) {
        this._eventCode = eventCode;
    }

    public String getForOperations() {
        return _forOperations;
    }

    public void setForOperations(String forOperations) {
        this._forOperations = forOperations;
    }

    public List<MenuObject> getListMenuObject() {
        return _listMenuObject;
    }

    public void setListMenuObject(List<MenuObject> listMenuObject) {
        this._listMenuObject = listMenuObject;
    }

    //----------------Parcel-------------------------
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._name);
        dest.writeInt(this._photo);
        dest.writeInt(this._bgColor);
        dest.writeString(this._eventCode);
        dest.writeString(this._forOperations);
        dest.writeTypedList(this._listMenuObject);
        dest.writeSerializable(this._gotoContext);
    }

    public MenuObject() {
    }

    protected MenuObject(Parcel in) {
        this._name = in.readString();
        this._photo = in.readInt();
        this._bgColor = in.readInt();
        this._eventCode = in.readString();
        this._forOperations = in.readString();

        this._listMenuObject = new ArrayList<>();
        in.readTypedList(this._listMenuObject, MenuObject.CREATOR)  ;

        this._gotoContext = (Class<?>) in.readSerializable();
    }

    public static final Parcelable.Creator<MenuObject> CREATOR = new Parcelable.Creator<MenuObject>() {
        @Override
        public MenuObject createFromParcel(Parcel source) {
            return new MenuObject(source);
        }

        @Override
        public MenuObject[] newArray(int size) {
            return new MenuObject[size];
        }
    };
    //-----------------------------------------------
}
