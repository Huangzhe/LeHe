package com.sh.lynn.hz.lehe.module.photos;

import android.util.Log;

import com.sh.lynn.hz.lehe.net.APIManager;
import com.sh.lynn.hz.lehe.net.SimpleCallback;

import java.util.List;

/**
 * Created by hyz84 on 16/9/28.
 */

public class PhotoPresenter {
    PhotosView mPhotosView;
    APIManager mAPIManager;

    public PhotoPresenter(PhotosView view, APIManager manager) {
        mPhotosView = view;
        mAPIManager = manager;
    }

    public void getData() {
        mAPIManager.getPhotos("870400af7aa7368475528367c434c959", 10, new SimpleCallback<List<Photos>>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onNext(List<Photos> photoses) {
                if (photoses != null && photoses.size() > 0)
                    for (Photos photo : photoses) {
                        Log.i("Photos", photo.getTitle() + "\n" + photo.getPicUrl());
                    }

                mPhotosView.showList(photoses);
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
