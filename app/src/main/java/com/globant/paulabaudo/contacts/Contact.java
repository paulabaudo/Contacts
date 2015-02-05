package com.globant.paulabaudo.contacts;

/**
 * Created by paula.baudo on 05/02/2015.
 */
public class Contact {

    public final static String FIRSTNAME = "firstname";
    public final static String LASTNAME = "lastname";
    public final static String NICKNAME = "nickname";
    public final static String URL = "url";

    private String mFirstName;
    private String mLastName;
    private String mNickname;
    private String mUrl;

    public Contact(String mFirstName, String mUrl, String mNickname, String mLastName) {
        this.mFirstName = mFirstName;
        this.mUrl = mUrl;
        this.mNickname = mNickname;
        this.mLastName = mLastName;
    }

    public Contact(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getNickname() {
        return mNickname;
    }

    public void setNickname(String mNickname) {
        this.mNickname = mNickname;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}
