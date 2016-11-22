package com.sh.lynn.hz.lehe.module.joker;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sh.lynn.hz.lehe.R;

import java.util.List;

public class MyJokerRecyclerViewAdapter extends RecyclerView.Adapter<MyJokerRecyclerViewAdapter.ViewHolder> {

    private final List<Joker> mValues;
    private JokerFragment.OnListFragmentInteractionListener mListener;

    public MyJokerRecyclerViewAdapter(List<Joker> items,JokerFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener=listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_joker_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTextView.setText(Html.fromHtml(mValues.get(position).getText()));
        holder.tv_title.setText(mValues.get(position).getTitle());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
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
        public final TextView mTextView;
        public final TextView tv_title;

        public Joker mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTextView = (TextView) view.findViewById(R.id.tv_joker);
            tv_title =(TextView) view.findViewById(R.id.tv_title);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTextView.getText() + "'";
        }
    }
}
