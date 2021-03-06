package com.sh.lynn.hz.lehe.module.joyimage;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by hyz84 on 16/11/17.
 */
@Entity
public class JoyImage {
    @Id
    private String id;
    private String ct;
    private String img;
    private String title;
    private String type;
    private boolean readState;//true read  ,false unRead


    @Generated(hash = 278686530)
    public JoyImage(String id, String ct, String img, String title, String type,
            boolean readState) {
        this.id = id;
        this.ct = ct;
        this.img = img;
        this.title = title;
        this.type = type;
        this.readState = readState;
    }
    @Generated(hash = 654327250)
    public JoyImage() {
    }


    public String getCt() {
        return this.ct;
    }
    public void setCt(String ct) {
        this.ct = ct;
    }
    public String getImg() {
        return this.img;
    }
    public void setImg(String img) {
        this.img = img;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public boolean getReadState() {
        return this.readState;
    }
    public void setReadState(boolean readState) {
        this.readState = readState;
    }
}
