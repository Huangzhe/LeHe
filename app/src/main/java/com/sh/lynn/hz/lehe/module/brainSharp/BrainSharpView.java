package com.sh.lynn.hz.lehe.module.brainSharp;

import java.util.List;

/**
 * Created by hyz84 on 16/9/19.
 */
public interface BrainSharpView {

    void showList(List<BrainSharp.NewslistBean> list);

    void showLoading();

    void closedLoading();
}
