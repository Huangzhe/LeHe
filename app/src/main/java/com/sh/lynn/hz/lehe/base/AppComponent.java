package com.sh.lynn.hz.lehe.base;

import com.sh.lynn.hz.lehe.module.brainSharp.BrainSharpComponent;
import com.sh.lynn.hz.lehe.module.brainSharp.BrainSharpModule;
import com.sh.lynn.hz.lehe.module.photos.PhotoModule;
import com.sh.lynn.hz.lehe.module.photos.PhotosCompoment;
import com.sh.lynn.hz.lehe.net.ApiModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by hyz84 on 16/9/8.
 */
@Singleton
@Component(modules = {AppModule.class,ApiModule.class})
public interface AppComponent {
    BrainSharpComponent plus(BrainSharpModule module);
    PhotosCompoment plus(PhotoModule module);
}
