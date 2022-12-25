package com.example.kuetbank;

public class Loan {
    String Name,Accountno,Approve,Installment,Loan,Emi;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAccountno() {
        return Accountno;
    }

    public void setAccountno(String accountno) {
        Accountno = accountno;
    }

    public String getApprove() {
        return Approve;
    }

    public void setApprove(String approve) {
        Approve = approve;
    }

    public String getInstallment() {
        return Installment;
    }

    public void setInstallment(String installment) {
        Installment = installment;
    }

    public String getLoan() {
        return Loan;
    }

    public void setLoan(String loan) {
        Loan = loan;
    }

    public String getEmi() {
        return Emi;
    }

    public void setEmi(String emi) {
        Emi = emi;
    }

    public Loan(String name, String accountno, String approve, String installment, String loan, String emi) {
        Name = name;
        Accountno = accountno;
        Approve = approve;
        Installment = installment;
        Loan = loan;
        Emi = emi;
    }

    public Loan() {
    }
}
