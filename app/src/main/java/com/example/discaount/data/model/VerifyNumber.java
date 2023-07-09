package com.example.discaount.data.model;

public class VerifyNumber {
    private String phone;
    private String otp_code;

    public VerifyNumber(String phone, String otp_code) {
        phone = phone;
        otp_code = otp_code;
    }

    public String getNumber() {
        return phone;
    }

    public void setNumber(String phone) {
        phone = phone;
    }

    public String getOtp() {
        return otp_code;
    }

    public void setOtp(String otp_code) {
        otp_code = otp_code;
    }
}
