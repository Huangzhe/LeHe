package com.sh.lynn.hz.lehe.module.joker;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.lang.reflect.Type;

/**
 * Created by hyz84 on 16/9/8.
 */
@Entity
public class Joker {

    /**
     * ct : 2015-08-13 13:10:26.149
     * text : 新人发帖求过…… 媳妇最近怀孕了…天天这也不想吃那也不想吃…有一天发脾气要我给他做想吃的，结果做了好多还是没有想吃的…最后着急了大喊:再做不出我想吃的我就去大街上要饭……我想说:你吃什么自己都不知道我怎么做啊…唉…想想男人女人都不容易啊…
     * title : 媳妇儿有了…
     * type : 1
     */


    @Id
    private String id;
    private String ct;
    private String text;
    private String title;
    private String type;

    private int readState;//true read  ,false unRead

    public static class JokerDeserializer implements JsonDeserializer<Joker> {

        @Override
        public Joker deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Joker joker = new Joker();
            JsonObject jsonObject = json.getAsJsonObject();

            joker.setTitle(jsonObject.get("title").getAsString());
            joker.setId(jsonObject.get("id").getAsString());
            if(jsonObject.has("img")){
                joker.setText(jsonObject.get("img").getAsString());

            }else{
                joker.setText(jsonObject.get("text").getAsString());
            }
            joker.setCt(jsonObject.get("ct").getAsString());
            joker.setType(jsonObject.get("type").getAsInt()+"");

            return joker;
        }
    }

    @Generated(hash = 332152038)
    public Joker(String id, String ct, String text, String title, String type, int readState) {
        this.id = id;
        this.ct = ct;
        this.text = text;
        this.title = title;
        this.type = type;
        this.readState = readState;
    }

    @Generated(hash = 1456579416)
    public Joker() {
    }

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {
        this.ct = ct;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
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

    public int getReadState() {
        return this.readState;
    }

    public void setReadState(int readState) {
        this.readState = readState;
    }

  
}
