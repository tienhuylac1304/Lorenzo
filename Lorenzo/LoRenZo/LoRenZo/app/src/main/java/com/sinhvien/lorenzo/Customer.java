package com.sinhvien.lorenzo;

import com.google.gson.annotations.SerializedName;

public class Customer {

    int id;
    int sex;
    String phone;
    String name;
    String adress;
    String date;
    String email,account;
    @SerializedName("pass")
    String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Customer() { }

    public Customer(int id, int sex, String phone, String name, String adress, String date, String email, String account, String password) {
        this.id = id;
        this.sex = sex;
        this.phone = phone;
        this.name = name;
        this.adress = adress;
        this.date = date;
        this.email = email;
        this.account = account;
        this.password = password;
    }
}
