package com.sh.lynn.hz.lehe.module.photos;

import dagger.Subcomponent;

/**
 * Created by hyz84 on 16/9/28.
 */
@Subcomponent(modules=PhotoModule.class)
public interface PhotosCompoment {
    PhotosFragment inject(PhotosFragment photosFragment);

}
