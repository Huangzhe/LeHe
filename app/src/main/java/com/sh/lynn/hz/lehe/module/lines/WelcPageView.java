package com.sh.lynn.hz.lehe.module.lines;

/**
 * Created by hyz84 on 16/11/10.
 */

public interface WelcPageView {
    void showLoading();

    void closedLoading();

    void setWelcWord(String text);

    void gotoMainView();
}
