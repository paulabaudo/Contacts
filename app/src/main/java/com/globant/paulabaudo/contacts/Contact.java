package com.globant.paulabaudo.contacts;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by paula.baudo on 05/02/2015.
 */
public class Contact {

    public final static String FIRSTNAME = "firstname";
    public final static String LASTNAME = "lastname";
    public final static String NICKNAME = "nickname";
    public final static String URL = "url";
    public final static String ID = "_id";

    @DatabaseField (generatedId = true, columnName = ID) private int id;
    @DatabaseField (columnName = FIRSTNAME) private String mFirstName;
    @DatabaseField (columnName = LASTNAME) private String mLastName;
    @DatabaseField (columnName = NICKNAME) private String mNickname;
    @DatabaseField (columnName = URL) private String mUrl;

    public Contact() {

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
