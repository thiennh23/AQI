package com.example.iaq;

import com.google.gson.annotations.SerializedName;

public class Status {
    @SerializedName("version")
    public String version;

    @SerializedName("authServerUrl")
    public String authServerUrl;
}
