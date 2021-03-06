package mcc.springbootboilerplate.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class MyUser implements Serializable {
    @Id
    @SequenceGenerator(name="MY_USER_SEQ", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "MY_USER_SEQ")
    private Long myUserId;
    private String username;
    @JsonIgnore
    private String encPassword;
    private int failedAttempts;
    private int isLocked;
    private int isDisabled;


    private int isPwExpired;
    private Date lastPasswordDate;

    public Long getMyUserId() {
        return myUserId;
    }

    public void setMyUserId(Long myUserId) {
        this.myUserId = myUserId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getEncPassword() {
        return encPassword;
    }

    public void setEncPassword(String encPassword) {
        this.encPassword = encPassword;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public int getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(int isLocked) {
        this.isLocked = isLocked;
    }

    public int getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(int isDisabled) {
        this.isDisabled = isDisabled;
    }

    public Date getLastPasswordDate() {
        return lastPasswordDate;
    }

    public void setLastPasswordDate(Date lastPasswordDate) {
        this.lastPasswordDate = lastPasswordDate;
    }

    public int getIsPwExpired() {
        return isPwExpired;
    }

    public void setIsPwExpired(int isPwExpired) {
        this.isPwExpired = isPwExpired;
    }
}
