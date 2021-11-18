package com.kurokiji.gss.models;

public class UserProfile {
    String username;
    int password;
    String email;
    String uriPath;
    String systemIpData;
    boolean requestPinData;
    boolean welcomeDoneData;

    public UserProfile(String username, int password, String email, String uriPath, String systemIpData, boolean requestPinData, boolean welcomeDoneData) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.uriPath = uriPath;
        this.systemIpData = systemIpData;
        this.requestPinData = requestPinData;
        this.welcomeDoneData = welcomeDoneData;
    }

    public String getUsername() {
        return username;
    }

    public int getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getUriPath() {
        return uriPath;
    }

    public String getUserIpData() {
        return systemIpData;
    }

    public boolean isRequestPinData() {
        return requestPinData;
    }

    public boolean getWelcomeDoneData() {
        return welcomeDoneData;
    }

    public void setWelcomeDoneData(boolean welcomeDoneData) {
        this.welcomeDoneData = welcomeDoneData;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUriPath(String uriPath) {
        this.uriPath = uriPath;
    }

    public void setUserIpData(String systemIpData) {
        this.systemIpData = systemIpData;
    }

    public void setRequestPinData(boolean requestPinData) {
        this.requestPinData = requestPinData;
    }
}
