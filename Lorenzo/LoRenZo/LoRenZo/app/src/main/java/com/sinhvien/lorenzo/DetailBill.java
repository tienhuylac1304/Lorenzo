package com.sinhvien.lorenzo;

public class DetailBill {
    int id,productId,billId,num;
    double totalPrice;

    public DetailBill(){}

    public DetailBill(int id, int productId, int billId, int num, double totalPrice) {
        this.id = id;
        this.productId = productId;
        this.billId = billId;
        this.num = num;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
