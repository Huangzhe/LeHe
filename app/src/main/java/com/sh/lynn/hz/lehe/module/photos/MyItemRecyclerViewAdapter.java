package com.sh.lynn.hz.lehe.module.photos;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sh.lynn.hz.lehe.R;

import java.util.List;

/**
 *
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<Photos> mValues;
    Context mCtx;

    public MyItemRecyclerViewAdapter(List<Photos> items, Context ctx) {
        mValues = items;
        mCtx = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_photos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tv_title.setText(mValues.get(position).getTitle());
        //  holder.mContentView.setText(mValues.get(position).content);
        // holder.
//        Glide.with(mCtx)
//                .load(mValues.get(position).getPicUrl())
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .placeholder(R.mipmap.icon_hun_normal)
//                .thumbnail(1f)
//
//                .into(holder.iv_photo);
        Uri uri = Uri.parse(mValues.get(position).getPicUrl());
        holder.iv_photo.setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tv_title;
        public final SimpleDraweeView iv_photo;
        public Photos mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            iv_photo = (SimpleDraweeView) view.findViewById(R.id.iv_photo);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tv_title.getText() + "'";
        }
    }
}
