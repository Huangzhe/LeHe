package com.sh.lynn.hz.lehe.module.joyimage;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hyz84 on 16/11/11.
 */
@Module
public class JoyImageModule {
    private final JoyImageContract.View mView;


    public JoyImageModule(JoyImageContract.View view) {
        mView = view;
    }

    @Provides
    JoyImageContract.View provideTasksContractView() {
        return mView;
    }


}
