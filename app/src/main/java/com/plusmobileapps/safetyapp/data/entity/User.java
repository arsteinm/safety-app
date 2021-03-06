package com.plusmobileapps.safetyapp.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by aaronmusengo on 1/23/18.
 * <p>
 * Updated by Robert Beerman on 2/19/18.
 * Updated by Bart Skoczylas 11/17/18
 */

@Entity(tableName = "user",
        foreignKeys = @ForeignKey(entity = School.class,
                parentColumns = "schoolId",
                childColumns = "schoolId"))
public class User {
    @PrimaryKey
    private int userId;

    @ColumnInfo(name = "userName")
    private String userName;

    @ColumnInfo(name = "schoolId")
    private int schoolId;

    @ColumnInfo(name = "emailAddress")
    private String emailAddress;

    @ColumnInfo(name = "role")
    private String role;

    @ColumnInfo(name = "remoteId")
    private int remoteId;

    @ColumnInfo(name = "lastLogin")
    private long lastLogin;


    // TODO Add isRegistered field

    public User(int userId, int schoolId, String emailAddress, String userName, String role) {
        this.userId = userId;
        this.schoolId = schoolId;
        this.emailAddress = emailAddress;
        this.role = role;
        this.userName = userName;
        this.remoteId = 0;
        setLastLogin(lastLogin);
    }

    //Getters
    public int getUserId() {
        return this.userId;
    }

    public int getSchoolId() {
        return this.schoolId;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public String getUserName() {
        return userName;
    }

    public String getRole() {
        return role;
    }

    public int getRemoteId() {
        return remoteId;
    }

    public long getLastLogin() { return lastLogin; }

    //Setters
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setRemoteId(int remoteId) {
        this.remoteId = remoteId;
    }
	


    public void setLastLogin(long lastLogin) { this.lastLogin = new Date().getTime(); }

    public boolean isRegistered() {
        return remoteId > 0;
    }
}
