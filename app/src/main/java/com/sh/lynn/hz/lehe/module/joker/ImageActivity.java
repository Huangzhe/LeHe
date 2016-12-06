package com.sh.lynn.hz.lehe.module.joker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.sh.lynn.hz.lehe.R;
import com.sh.lynn.hz.lehe.net.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ImageActivity extends Activity {
    @BindView(R.id.iv_photo)
    SimpleDraweeView mDraweeView;
    String  imageUrl ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);

        Intent intent = getIntent();

         imageUrl = intent.getStringExtra("ImageUrl");
        String imageType = intent.getStringExtra("ImageType");
        Uri uri = Uri.parse(imageUrl);

        if ("2".equals(imageType)) {

            GenericDraweeHierarchy builder = GenericDraweeHierarchyBuilder
                    .newInstance(getResources())
                    .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                    .setPlaceholderImage(R.mipmap.icon_hun_normal)
                    .build();

            mDraweeView.setHierarchy(builder);

            mDraweeView.setImageURI(uri);
        } else {
            GenericDraweeHierarchy builder = GenericDraweeHierarchyBuilder
                    .newInstance(getResources())
                    .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
//                    .setPlaceholderImage(R.mipmap.default_gif)
                    .setProgressBarImage(R.drawable.loading)
                    .build();

            ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
                @Override
                public void onFinalImageSet(
                        String id,
                        @Nullable
                                ImageInfo imageInfo,
                        @Nullable
                                Animatable anim) {
                    super.onFinalImageSet(id, imageInfo, anim);
                    Log.d("Fresco_Load", "Complete");
                    if (imageInfo == null) {
                        return;
                    }

                    QualityInfo qualityInfo = imageInfo.getQualityInfo();
                    Log.d("Fresco_Load", "Quality level %d, good enough: %s, full quality: %s");
//                            imageInfo.getWidth(),
//                            imageInfo.getHeight(),
//                            qualityInfo.getQuality(),
//                            qualityInfo.isOfGoodEnoughQuality(),
//                            qualityInfo.isOfFullQuality();

                    if (anim != null) {
                        anim.start();
                    }

                }

                @Override
                public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
                    FLog.d("Fresco_Load", "Intermediate image received");
                    super.onIntermediateImageSet(id, imageInfo);
                }

                @Override
                public void onFailure(String id, Throwable throwable) {
                    FLog.e("Fresco_Load", throwable, "Error loading %s", id);
                    super.onFailure(id, throwable);
                }
            };


            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setUri(uri)
                    .setControllerListener(controllerListener)
                    .build();
            mDraweeView.setHierarchy(builder);
            mDraweeView.setController(controller);
        }
    }

    @OnClick(R.id.iv_photo)
    public void onClick() {

        finish();
    }

    @OnClick(R.id.btn_save)
    public void onSave(){

        int index = imageUrl.lastIndexOf("/");
         String fileName = imageUrl.substring(index);
        CommonUtils.saveImage(Uri.parse(imageUrl),fileName);
    }


}
