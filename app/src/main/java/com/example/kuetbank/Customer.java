package com.example.kuetbank;

public class Customer {
    public String accountno, accounttype, name, mobile, email, pass, gender, dob, address, balance;

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) { this.accountno = accountno; }

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public Customer(){ }

    public Customer(String accountno, String accounttype, String name, String mobile, String email, String pass, String gender, String dob, String address, String balance) {
        this.accountno = accountno;
        this.accounttype = accounttype;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.pass = pass;
        this.gender = gender;
        this.dob = dob;
        this.address = address;
        this.balance = balance;
    }
}