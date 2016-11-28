package com.sh.lynn.hz.lehe.listener;

import android.content.Context;
import android.widget.Toast;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by hyz84 on 16/11/28.
 */

public class MyUMShareListener implements UMShareListener {

    private Context mCtx;

    public MyUMShareListener(Context ctx){
        mCtx = ctx;
    }

    @Override
    public void onResult(SHARE_MEDIA platform) {
        Toast.makeText(mCtx, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(SHARE_MEDIA platform, Throwable throwable) {
            Toast.makeText(mCtx, platform + " 分享失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel(SHARE_MEDIA platform) {
        Toast.makeText(mCtx, platform + " 分享取消", Toast.LENGTH_SHORT).show();
    }
}
