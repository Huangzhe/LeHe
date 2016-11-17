package com.sh.lynn.hz.lehe.module.joyimage;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.sh.lynn.hz.lehe.R;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a and makes a call to the
 * specified
 * TODO: Replace the implementation with code for your data type.
 */
public class MyJoyImageRecyclerViewAdapter extends RecyclerView.Adapter<MyJoyImageRecyclerViewAdapter.ViewHolder> {

    private final List<JoyImage> mValues;


    public MyJoyImageRecyclerViewAdapter(List<JoyImage> items) {
        mValues = items;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_joyimage, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getTitle());
        //holder.mContentView.setText(mValues.get(position).content);
        Uri uri = Uri.parse(mValues.get(position).getImg());
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)

                .build();
       GenericDraweeHierarchy builder= GenericDraweeHierarchyBuilder
               .newInstance(holder.mView.getResources())
               .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
               .setPlaceholderImage(R.mipmap.icon_hun_normal)
               .build();
       // builder.s
        //builder.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);
        holder.mContentView.setHierarchy(builder);
        holder.mContentView.setController(controller);

      //  holder.mContentView.setImageURI(uri);

//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final SimpleDraweeView mContentView;
        public JoyImage mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.tv_title);
            mContentView = (SimpleDraweeView) view.findViewById(R.id.iv_photo);
        }

//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
    }
}
