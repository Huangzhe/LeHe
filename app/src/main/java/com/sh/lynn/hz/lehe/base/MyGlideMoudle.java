package com.sh.lynn.hz.lehe.base;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.request.target.ViewTarget;
import com.sh.lynn.hz.lehe.R;

/**
 * Created by hyz84 on 16/9/28.
 */

public class MyGlideMoudle implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        ViewTarget.setTagId(R.id.glide_tag_id);
        //设置图片的显示格式ARGB_8888
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        //设置磁盘缓存目录
        String downloadDirectoryPath= Environment.getExternalStorageDirectory()+"/Lehephotos";

        int cacheSize100MegaBytes = 100000000;
        builder.setDiskCache(
                new DiskLruCacheFactory(downloadDirectoryPath, cacheSize100MegaBytes)
        );
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
