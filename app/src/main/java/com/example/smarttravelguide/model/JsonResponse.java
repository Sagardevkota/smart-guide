package com.example.smarttravelguide.model;

public class JsonResponse {
    private String status;
    private String message;
    private String id;


    public JsonResponse(String status,String message){
        this.status = status;
        this.message = message;
    }


    public JsonResponse(String message,String status,String id) {
        this.status = status;
        this.message = message;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
