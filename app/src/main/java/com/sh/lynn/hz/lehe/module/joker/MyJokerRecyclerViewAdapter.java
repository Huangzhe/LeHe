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

import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.sh.lynn.hz.lehe.R;
import com.sh.lynn.hz.lehe.listener.MyUMShareListener;
import com.sh.lynn.hz.lehe.listener.OnImageClickListener;
import com.sh.lynn.hz.lehe.net.CommonUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyJokerRecyclerViewAdapter extends RecyclerView.Adapter<MyJokerRecyclerViewAdapter.ViewHolder> {

    private final List<Joker> mValues;
    private MyUMShareListener mListener;
    private OnImageClickListener mClickListener;
    private     Activity activity;
    public MyJokerRecyclerViewAdapter(List<Joker> items,MyUMShareListener listener,OnImageClickListener clickListener) {
        mValues = items;
        mListener=listener;
        mClickListener=clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        activity = (Activity)parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_joker_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.tv_title.setText(mValues.get(position).getTitle());
        String type =  holder.mItem.getType();
        if("1".equals(type)) {
            holder.mTextView.setVisibility(View.VISIBLE);
            holder.mTextView.setText(Html.fromHtml(mValues.get(position).getText()));
            holder.mImageView.setVisibility(View.GONE);
        }else {

            holder.mImageView.setVisibility(View.VISIBLE);
            holder.mTextView.setVisibility(View.GONE);
            final Uri uri = Uri.parse(mValues.get(position).getText());

            if("2".equals(type)) {


                GenericDraweeHierarchy builder = GenericDraweeHierarchyBuilder
                        .newInstance(holder.mView.getResources())
                        .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                        .setPlaceholderImage(R.mipmap.icon_hun_normal)
                        .build();
                holder.mImageView.setHierarchy(builder);
                holder.mImageView.setImageURI(uri);
            }
            if("3".equals(type)) {

                GenericDraweeHierarchy builder = GenericDraweeHierarchyBuilder
                        .newInstance(holder.mView.getResources())
                        .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                        .setPlaceholderImage(R.mipmap.default_gif)
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
                            anim.start();
                        }
                    }
                };


//                DraweeController controller = Fresco.newDraweeControllerBuilder()
//                .setUri(uri)
//               .setControllerListener(controllerListener)
//                .build();
//                holder.mImageView.setController(controller);

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
                new ShareAction(activity).setPlatform(SHARE_MEDIA.WEIXIN)
                        .withText(CommonUtils.html2Text(mValues.get(position).getText()))
                        .withTitle("笑话")
                        .setCallback(mListener)
                        .share();
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
        @BindView(R.id.iv_photo)
        SimpleDraweeView mImageView;
        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this,view);
//            mTextView = (TextView) view.findViewById(R.id.tv_joker);
//            tv_title =(TextView) view.findViewById(tv_title);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTextView.getText() + "'";
        }
    }


}
