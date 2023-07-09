package com.example.discaount.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegisterResponse {

    @SerializedName("data")
    List<Register> registerList;
    private Message message;
    private Boolean success;

    public RegisterResponse(List<Register> registerList, Message message, Boolean success) {
        this.registerList = registerList;
        this.message = message;
        this.success = success;
    }

    public List<Register> getRegisterList() {
        return registerList;
    }

    public void setRegisterList(List<Register> registerList) {
        this.registerList = registerList;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
