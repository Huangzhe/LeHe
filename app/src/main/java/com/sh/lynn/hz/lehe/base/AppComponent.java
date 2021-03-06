package com.sh.lynn.hz.lehe.base;

import com.sh.lynn.hz.lehe.module.brainSharp.BrainSharpComponent;
import com.sh.lynn.hz.lehe.module.brainSharp.BrainSharpModule;
import com.sh.lynn.hz.lehe.module.joker.JokerComponent;
import com.sh.lynn.hz.lehe.module.joker.JokerModule;
import com.sh.lynn.hz.lehe.module.joyimage.JoyImageComponent;
import com.sh.lynn.hz.lehe.module.joyimage.JoyImageModule;
import com.sh.lynn.hz.lehe.module.lines.WelcPageComponent;
import com.sh.lynn.hz.lehe.module.lines.WelcPageModule;
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
    WelcPageComponent plus(WelcPageModule module);
    JokerComponent plus(JokerModule module);
    JoyImageComponent plus(JoyImageModule module);
}
