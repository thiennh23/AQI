package com.example.iaq.Models;

import com.google.gson.annotations.SerializedName;

public class Token {
    @SerializedName("access_token")
    public String access_token;
    @SerializedName("expires_in")
    public String expires_in;

    @SerializedName("refresh_expires_in")
    public Integer refresh_expires_in;

    @SerializedName("refresh_token")
    public String refresh_token;

    @SerializedName("token_type")
    public String token_type;

    @SerializedName("not-before-policy")
    public Integer not_before_policy;

    @SerializedName("session_state")
    public String session_state;

    @SerializedName("scope")
    public String scope;


    //GETTERS AND SETTERS
    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public Integer getRefresh_expires_in() {
        return refresh_expires_in;
    }

    public void setRefresh_expires_in(Integer refresh_expires_in) {
        this.refresh_expires_in = refresh_expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public Integer getNot_before_policy() {
        return not_before_policy;
    }

    public void setNot_before_policy(Integer not_before_policy) {
        this.not_before_policy = not_before_policy;
    }

    public String getSession_state() {
        return session_state;
    }

    public void setSession_state(String session_state) {
        this.session_state = session_state;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
