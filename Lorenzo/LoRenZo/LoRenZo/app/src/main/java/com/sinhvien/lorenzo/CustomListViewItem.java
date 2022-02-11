package com.sinhvien.lorenzo;

import android.net.Uri;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;

public class CustomListViewItem {
    @SerializedName("image")
    String imgBtn;
    @SerializedName("name")
    String title;
    int price;
    int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getImgBtn() {
        return imgBtn;
    }

    public void setImgBtn(String imgBtn) {
        this.imgBtn = imgBtn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public CustomListViewItem(int id,String imgBtn, String txtT, int txtP)
    {
        this.id=id;
        this.imgBtn=imgBtn;
        this.price=txtP;
        this.title=txtT;
    }
    public CustomListViewItem(){}

}
