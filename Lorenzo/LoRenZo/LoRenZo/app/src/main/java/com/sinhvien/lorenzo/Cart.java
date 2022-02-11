package com.sinhvien.lorenzo;

public class Cart {
    int id,soLuong,thanhTien;
    String hinhAnh, tenSP;

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public Cart(int id, String tenSP, String hinhAnh, int soLuong, int thanhTien) {
        this.id = id;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
        this.hinhAnh = hinhAnh;
        this.tenSP=tenSP;
    }

    public Cart() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(int thanhTien) {
        this.thanhTien = thanhTien;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
