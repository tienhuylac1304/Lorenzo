package com.sinhvien.lorenzo;

public class CustomListViewItemCart {
    String imgBtn;
    String title;
    int soLuong;
    int price;
    int id;

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

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

    public CustomListViewItemCart(int id, String imgBtn, String txtT, int txtP,int txtSL)
    {
        this.id=id;
        this.imgBtn=imgBtn;
        this.price=txtP;
        this.title=txtT;
        this.soLuong=txtSL;
    }
    public CustomListViewItemCart(){}
}
