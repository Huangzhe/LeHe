package com.sh.lynn.hz.lehe.module.brainSharp;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sh.lynn.hz.lehe.R;

import java.util.List;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class MyBrainSharpRecyclerViewAdapter extends RecyclerView.Adapter<MyBrainSharpRecyclerViewAdapter.ViewHolder> {

    private final List<BrainSharp.NewslistBean> mValues;

    public MyBrainSharpRecyclerViewAdapter(List<BrainSharp.NewslistBean> items) {
        mValues = items;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_brainsharp, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        String q = mValues.get(position).getQuest();
        String r = mValues.get(position).getResult();
        SpannableString spannableString = new SpannableString(q);
        spannableString.setSpan(new RelativeSizeSpan(1.0f), 0, q.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.GRAY), 0, q.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
       spannableString.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), 0, q.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);



        holder.mContentView.setText(spannableString);
       holder.mResultView.setText(r);


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public final TextView mResultView;
        public final ImageView mImageView;
        public BrainSharp.NewslistBean mItem;
        private boolean isShow=false;
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.tv_question);
            mResultView = (TextView) view.findViewById(R.id.tv_result);
            mImageView = (ImageView) view.findViewById(R.id.iv_showResult);
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isShow){
                        mResultView.setVisibility(View.GONE);
                        mImageView.setImageResource(R.mipmap.icon_expend);
                        isShow=false;
                    }else{
                        mResultView.setVisibility(View.VISIBLE);
                        mImageView.setImageResource(R.mipmap.incon_col);
                        isShow= true;
                    }
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
