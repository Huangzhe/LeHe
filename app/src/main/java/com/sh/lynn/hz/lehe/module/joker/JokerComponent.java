package com.sh.lynn.hz.lehe.module.joker;

import dagger.Subcomponent;

/**
 * Created by hyz84 on 16/11/11.
 */
@Subcomponent(modules = JokerModule.class)
public interface JokerComponent {
    JokerPresenter getJokerPresenter();
}
