package com.sinhvien.lorenzo;

public class Account {
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String password,account;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Account() {
    }

    public Account(int id,String account, String password) {
        this.id=id;
        this.password = password;
        this.account = account;
    }
}
