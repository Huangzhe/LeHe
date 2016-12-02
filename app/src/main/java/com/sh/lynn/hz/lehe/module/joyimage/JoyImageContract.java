package com.sh.lynn.hz.lehe.module.joyimage;

import java.util.List;

/**
 * Created by hyz84 on 16/11/11.
 */

public interface JoyImageContract {
    interface View {
        void showLoading();

        void closeLoading();

        void showJoyImageList(List<JoyImage> list);

        void showEnd(String text);
    }

    interface UserActionsListener {

        void getJoyImages();

        void downLoadImage(String path);

    }
}
