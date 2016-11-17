package com.sh.lynn.hz.lehe.module.joyimage;

import dagger.Subcomponent;

/**
 * Created by hyz84 on 16/11/11.
 */
@Subcomponent(modules = JoyImageModule.class)
public interface JoyImageComponent {
    JoyImagePresenter getJoyImaagePresenter();
}
