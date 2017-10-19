package apps.kinmniekan_code.pedidosfirebase.MODEL;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Felix on 16/10/2017.
 */

public class Commentary implements Serializable{
    private String userId;
    private String userNameSender;
    private String description;
    private Boolean visible;
    private Date date;

    public Commentary() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNameSender() {
        return userNameSender;
    }

    public void setUserNameSender(String userNameSender) {
        this.userNameSender = userNameSender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
