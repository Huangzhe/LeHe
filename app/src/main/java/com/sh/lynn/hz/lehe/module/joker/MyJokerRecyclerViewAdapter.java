package com.sh.lynn.hz.lehe.module.joker;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.sh.lynn.hz.lehe.R;
import com.sh.lynn.hz.lehe.listener.MyUMShareListener;
import com.sh.lynn.hz.lehe.listener.OnImageClickListener;
import com.sh.lynn.hz.lehe.net.CommonUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyJokerRecyclerViewAdapter extends RecyclerView.Adapter<MyJokerRecyclerViewAdapter.ViewHolder> {

    private final List<Joker> mValues;
    private MyUMShareListener mListener;
    private OnImageClickListener mClickListener;
    private Activity activity;

    public MyJokerRecyclerViewAdapter(List<Joker> items, MyUMShareListener listener, OnImageClickListener clickListener) {
        mValues = items;
        mListener = listener;
        mClickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        activity = (Activity) parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_joker_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.tv_title.setText(mValues.get(position).getTitle());
        String type = holder.mItem.getType();
        if ("1".equals(type)) {
            holder.iv_share.setVisibility(View.VISIBLE);
            holder.mTextView.setVisibility(View.VISIBLE);
            holder.mTextView.setText(Html.fromHtml(mValues.get(position).getText()));
            holder.mImageView.setVisibility(View.GONE);
        } else {

            holder.mImageView.setVisibility(View.VISIBLE);
            holder.mTextView.setVisibility(View.GONE);
            final Uri uri = Uri.parse(mValues.get(position).getText());
            holder.mImageView.setTag(mValues.get(position).getText());
            if ("2".equals(type)) {
                holder.iv_share.setVisibility(View.VISIBLE);
                //if(mValues.get(position).getText().equals(holder.mImageView.getTag())){
                GenericDraweeHierarchy builder = GenericDraweeHierarchyBuilder
                        .newInstance(holder.mView.getResources())
                        .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                        .setPlaceholderImage(R.mipmap.loading)
                        .build();
                holder.mImageView.setHierarchy(builder);

                holder.mImageView.setImageURI(uri);
            }
            if ("3".equals(type)) {
                holder.iv_share.setVisibility(View.GONE);
                holder.tv_title.setText("(GIF)" + mValues.get(position).getTitle());
                // if(mValues.get(position).getText().equals(holder.mImageView.getTag())){
                final GenericDraweeHierarchy builder = GenericDraweeHierarchyBuilder
                        .newInstance(holder.mView.getResources())
                        .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                        .setPlaceholderImage(R.mipmap.loading)
                        .build();
                holder.mImageView.setHierarchy(builder);
                ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(
                            String id,
                            @Nullable
                                    ImageInfo imageInfo,
                            @Nullable
                                    Animatable anim) {
                        if (anim != null) {
                            // 其他控制逻辑
                            // builder.setOverlayImage(holder.mView.getResources().getDrawable(R.mipmap.default_gif));
                            // anim.start();
                        }
                        if(imageInfo!=null){

                        }

                    }
                };


                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setUri(uri)

                        .setAutoPlayAnimations(false)
                        .build();
                holder.mImageView.setController(controller);

            }

            holder.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onImageInteraction(mValues.get(position));

                }
            });

        }


        holder.iv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = mValues.get(position).getType();

                final ShareAction shareAction = new ShareAction(activity);
                shareAction.setPlatform(SHARE_MEDIA.WEIXIN);
                if ("1".equals(type)) {
                    shareAction.withText(CommonUtils.html2Text(mValues.get(position).getText()))
                            .withTitle("笑话")
                            .setCallback(mListener)
                            .share();
                } else if ("2".equals(type)) {


                    UMImage umImage = new UMImage(activity, CommonUtils.getBitmap(Uri.parse(mValues.get(position).getText())));
                    shareAction.withMedia(umImage)
                            .withTitle("笑话")
                            .setCallback(mListener)
                            .share();
                } else if ("3".equals(type)) {

//                    int index = mValues.get(position).getText().lastIndexOf("/");
//                    final String fileName = mValues.get(position).getText().substring(index);
//                    FrescoUtil.saveGif(mValues.get(position).getText(), activity, new BaseDataSubscriber<CloseableReference<PooledByteBuffer>>() {
//
//
//                        @Override
//                        protected void onNewResultImpl(DataSource<CloseableReference<PooledByteBuffer>> dataSource) {
//                            if (dataSource.isFinished()) {
//                                CloseableReference<PooledByteBuffer> ref = dataSource.getResult();
//                                if (ref != null) {
//                                    try {
//                                        PooledByteBuffer result = ref.get();
//                                        InputStream is = new PooledByteBufferInputStream(result);
//                                        try {
////
//                                            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
//                                            byte[] b = new byte[1000];
//                                            int n;
//                                            while ((n = is.read(b)) != -1) {
//                                                bos.write(b, 0, n);
//                                            }
//                                            is.close();
//                                            bos.close();
//                                            FrescoUtil.savePic(fileName, bos);//通过byte文件头，判断是否是gif，再做相应的命名处理
//
//
//                                        } catch (Exception e) {
//                                        } finally {
//                                            Closeables.closeQuietly(is);
//                                        }
//                                    } finally {
//                                        CloseableReference.closeSafely(ref);
//                                        ref = null;
//                                    }
//                                }
//                            }
//                        }
//
//                        @Override
//                        protected void onFailureImpl(DataSource dataSource) {
//
//                        }
//                    });
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//
//
//
//                    UMImage umImage = new UMImage(activity, new File(FrescoUtil.IMAGE_PIC_CACHE_DIR, fileName));
//                    shareAction.withMedia(umImage)
//                            .withTitle("笑话")
//                            .setCallback(mListener)
//                            .share();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        @BindView(R.id.tv_joker)
        TextView mTextView;
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.iv_share)
        ImageView iv_share;
        public Joker mItem;
//        @BindView(R.id.bottle_view)
//        GABottleLoadingView mLoadingView;

        @BindView(R.id.iv_photo)
        SimpleDraweeView mImageView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
//            mTextView = (TextView) view.findViewById(R.id.tv_joker);
//            tv_title =(TextView) view.findViewById(tv_title);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTextView.getText() + "'";
        }
    }


}
