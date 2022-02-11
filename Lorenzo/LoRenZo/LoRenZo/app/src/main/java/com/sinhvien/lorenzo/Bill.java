package com.sinhvien.lorenzo;

public class Bill {

    int billId,detailBillId;
    String buyDate;
    public Bill(){}

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getDetailBillId() {
        return detailBillId;
    }

    public void setDetailBillId(int detailBillId) {
        this.detailBillId = detailBillId;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public Bill(int billId, int detailBillId, String buyDate) {
        this.billId = billId;
        this.detailBillId = detailBillId;
        this.buyDate = buyDate;
    }
}
