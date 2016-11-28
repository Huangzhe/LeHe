package com.sh.lynn.hz.lehe.module.joker;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sh.lynn.hz.lehe.R;
import com.sh.lynn.hz.lehe.listener.MyUMShareListener;
import com.sh.lynn.hz.lehe.net.CommonUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyJokerRecyclerViewAdapter extends RecyclerView.Adapter<MyJokerRecyclerViewAdapter.ViewHolder> {

    private final List<Joker> mValues;
    private MyUMShareListener mListener;
private     Activity activity;
    public MyJokerRecyclerViewAdapter(List<Joker> items,MyUMShareListener listener) {
        mValues = items;
        mListener=listener;
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
        holder.mTextView.setText(Html.fromHtml(mValues.get(position).getText()));
        holder.tv_title.setText(mValues.get(position).getTitle());

//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mListener) {
//                    mListener.onListFragmentInteraction(holder.mItem);
//                }
//            }
//        });
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
