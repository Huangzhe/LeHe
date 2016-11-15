package com.sh.lynn.hz.lehe.module.joker;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hyz84 on 16/11/11.
 */
@Module
public class JokerModule {
    private final JokerContract.View mView;


    public JokerModule(JokerContract.View view) {
        mView = view;
    }

    @Provides
    JokerContract.View provideTasksContractView() {
        return mView;
    }


}
