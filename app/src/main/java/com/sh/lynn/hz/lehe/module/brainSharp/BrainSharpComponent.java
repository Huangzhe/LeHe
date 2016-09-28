package com.sh.lynn.hz.lehe.module.brainSharp;

import dagger.Subcomponent;

/**
 * Created by hyz84 on 16/9/19.
 */
@Subcomponent(modules = BrainSharpModule.class)
public interface BrainSharpComponent {
    BrainSharpFragment inject(BrainSharpFragment fragment);

}
