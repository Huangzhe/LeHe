package com.sh.lynn.hz.lehe.module.brainSharp;

import java.util.List;

/**
 * Created by hyz84 on 16/9/8.
 */
public class BrainSharp {


    /**
     * code : 200
     * msg : success
     * newslist : [{"id":"6","quest":"两只狗赛跑，甲狗跑得快，乙狗跑得慢，跑到终点时，哪只狗出汗多?","result":"狗不会出汗"}]
     */

    private int code;
    private String msg;
    /**
     * id : 6
     * quest : 两只狗赛跑，甲狗跑得快，乙狗跑得慢，跑到终点时，哪只狗出汗多?
     * result : 狗不会出汗
     */

    private List<NewslistBean> newslist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<NewslistBean> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<NewslistBean> newslist) {
        this.newslist = newslist;
    }

    public static class NewslistBean {
        private String id;
        private String quest;
        private String result;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getQuest() {
            return quest;
        }

        public void setQuest(String quest) {
            this.quest = quest;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }
}
