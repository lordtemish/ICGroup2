package com.studio.dynamica.icgroup.Forms;

public class RadioForm {
    boolean status;
    String text;
    public RadioForm(boolean s, String n){
        status=s;text=n;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public boolean isStatus() {
        return status;
    }
}
