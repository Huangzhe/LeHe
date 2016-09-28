package com.sh.lynn.hz.lehe.module.photos;

import com.sh.lynn.hz.lehe.net.APIManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hyz84 on 16/9/28.
 */
@Module
public class PhotoModule {

    PhotosView mPhotosView;

    public PhotoModule(PhotosView view) {
        mPhotosView = view;
    }

    @Provides
    public PhotoPresenter providePresenter(APIManager apiManager) {
        return new PhotoPresenter(mPhotosView, apiManager);
    }

    @Provides
    public PhotosView provideView() {
        return mPhotosView;
    }
}
