package com.sh.lynn.hz.lehe.module.lines;

import dagger.Subcomponent;

/**
 * Created by hyz84 on 16/11/10.
 */
@Subcomponent(modules=WelcPageModule.class)
public interface WelcPageComponent {WelcomePageActivity inject(WelcomePageActivity activity);

}
