package com.sh.lynn.hz.lehe.module.photos;

import java.util.List;

/**
 * Created by hyz84 on 16/9/28.
 */

public interface PhotosView {
    void showList(List<Photos> list);

    void showLoading();

    void closedLoading();
}
