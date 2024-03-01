package org.example;

import java.util.Stack;

public class BankInfo {
    private static String Name;
    private static String AccountNo;
    private static String AccType;
    private static int Balance;

    private static String  Phone;
    private static int PIN;

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String  Phone) {
        this.Phone = Phone;
    }
    public int getPIN() {
        return PIN;
    }

    public void setPIN(int PIN) {
        this.PIN = PIN;
    }

    public int getBalance() {
        return Balance;
    }

    public void setBalance(int Balance) {
        this.Balance = Balance;
    }
    public String getAccType() {
        return AccType;
    }

    public void setAccType(String AccType) {
        this.AccType = AccType;
    }

    public String getAccountNo() {
        return AccountNo;
    }

    public void setAccountNo(String  AccountNo) {
        this.AccountNo = AccountNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }


}
