package com.sh.lynn.hz.lehe.module.joyimage;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

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
    private int type;
    @Generated(hash = 1323941550)
    public JoyImage(String id, String ct, String img, String title, int type) {
        this.id = id;
        this.ct = ct;
        this.img = img;
        this.title = title;
        this.type = type;
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
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
