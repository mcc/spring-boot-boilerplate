package mcc.springbootboilerplate.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class MyLoginAttempt {
    @Id
    @SequenceGenerator(name="MY_LOGIN_ATTEMPT_SEQ", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "MY_LOGIN_ATTEMPT_SEQ")
    private Long myLoginAttemptId;
    private String username;
    private Date recordDate;
    private String ipAddress;

    public Long getMyLoginAttemptId() {
        return myLoginAttemptId;
    }

    public void setMyLoginAttemptId(Long myLoginAttemptId) {
        this.myLoginAttemptId = myLoginAttemptId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
