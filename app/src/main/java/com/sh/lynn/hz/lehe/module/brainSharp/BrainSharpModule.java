package com.sh.lynn.hz.lehe.module.brainSharp;

import com.sh.lynn.hz.lehe.net.APIManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hyz84 on 16/9/19.
 */
@Module
public class BrainSharpModule {
    BrainSharpView mBrainSharpView;

    public BrainSharpModule(BrainSharpView brainSharpView) {
        mBrainSharpView = brainSharpView;
    }

    @Provides
    public BrainSharpPresenter providePresenter(APIManager apiManager) {
        return new BrainSharpPresenter(mBrainSharpView, apiManager);
    }

    @Provides
    public BrainSharpView provideView() {
        return mBrainSharpView;
    }
}
