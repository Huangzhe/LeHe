package com.sh.lynn.hz.lehe.module.joker;

import java.util.List;

/**
 * Created by hyz84 on 16/11/11.
 */

public interface JokerContract {
    interface  View{
            void showLoading();
            void closeLoading();
            void showJokerList(List<Joker> list);
            void showEnd(String text);
            void showDetail(Joker joker);
    }
     interface UserActionsListener {

         void getJokers();

         void loadMoreJokers(int start);

    }
}
