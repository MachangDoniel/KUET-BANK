package com.example.kuetbank;

public class Employee {

    public  String accountid,employeetype,name,mobileno,email,pass,gender,dateofbirthh,address;
    public Employee(){}
    public Employee(String email,String pass){
        this.email=email;
        this.pass=pass;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public String getEmployeetype() {
        return employeetype;
    }

    public void setEmployeetype(String employeetype) {
        this.employeetype = employeetype;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
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

    public String getDateofbirthh() {
        return dateofbirthh;
    }

    public void setDateofbirthh(String dateofbirthh) {
        this.dateofbirthh = dateofbirthh;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Employee(String accountid, String employeetype, String name, String mobileno, String email, String pass, String gender, String dateofbirthh, String address) {
        this.accountid = accountid;
        this.employeetype = employeetype;
        this.name = name;
        this.mobileno = mobileno;
        this.email = email;
        this.pass = pass;
        this.gender = gender;
        this.dateofbirthh = dateofbirthh;
        this.address = address;
    }
}
