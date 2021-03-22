package com.example.smarttravelguide.model;

public class JsonResponse {
    private String status;
    private String message;


    public JsonResponse(String message,String status) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
