package com.sinhvien.lorenzo;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("numProduct")
    int soLuong;
    @SerializedName("price")
    int gia;
    @SerializedName("name")
    String tenSP;
    @SerializedName("image")
   String hinhAnh;
    @SerializedName("type")
    String loai;
    @SerializedName("description")
    String moTa;
    @SerializedName("id")
    int idSP;

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getGia() {
        return gia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public int getIdSP() {
        return idSP;
    }

    public String getLoai() {
        return loai;
    }

    public String getMoTa() {
        return moTa;
    }

    public String getTenSP() {
        return tenSP;
    }


    public void setIdSP(int idSP) {
        this.idSP = idSP;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }
    public Product(){}
    public Product(int id,String name,String hinhAnh,String type,String moTa,int gia,int soLuong)
    {
        this.gia=gia;
        this.idSP=id;
        this.hinhAnh=hinhAnh;
        this.loai=type;
        this.tenSP=name;
        this.soLuong=soLuong;
        this.moTa=moTa;
    }
}
