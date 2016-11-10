package com.sh.lynn.hz.lehe.module.lines;

import com.sh.lynn.hz.lehe.net.APIManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hyz84 on 16/11/10.
 */
@Module
public class WelcPageModule {

    WelcPageView mWelcPageView;

    public WelcPageModule(WelcPageView view) {
        mWelcPageView = view;
    }

    @Provides
    public WelcPagePresenter providePresenter(APIManager apiManager) {
        return new WelcPagePresenter(mWelcPageView, apiManager);
    }

    @Provides
    public WelcPageView provideView() {
        return mWelcPageView;
    }
}
