package com.sh.lynn.hz.lehe.module.photos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sh.lynn.hz.lehe.LeHeApp;
import com.sh.lynn.hz.lehe.R;
import com.sh.lynn.hz.lehe.listener.OnImageClickListener;
import com.sh.lynn.hz.lehe.module.joker.ImageActivity;
import com.sh.lynn.hz.lehe.net.APIManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 *
 */
public class PhotosFragment extends Fragment implements PhotosView {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    RecyclerView recyclerView;
    @Inject
    PhotoPresenter mPresenter;
    @Inject
    APIManager mAPIManager;
    boolean isBottom = false;

    MyItemRecyclerViewAdapter mAdapter;

    private List<Photos> mPhotosList = new ArrayList<>();
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PhotosFragment() {
    }


    @SuppressWarnings("unused")
    public static PhotosFragment newInstance(int columnCount) {
        PhotosFragment fragment = new PhotosFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            //  recyclerView.setAdapter(new MyItemRecyclerViewAdapter(DummyContent.ITEMS, mListener));
        }
        LeHeApp.get(getActivity()).getAppComponent().plus(new PhotoModule(this)).inject(this);

        mAdapter = new MyItemRecyclerViewAdapter(mPhotosList, new OnImageClickListener() {
            @Override
            public void onImageInteraction(Object item) {

                if (item instanceof Photos) {

                    Intent intent = new Intent(getActivity(), ImageActivity.class);
                    intent.putExtra("ImageUrl", ((Photos) item).getPicUrl());
                    intent.putExtra("ImageType","2");
                    startActivity(intent);
                }
            }
        });
        recyclerView.setAdapter(mAdapter);
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    switch (newState) {
                        case RecyclerView.SCROLL_STATE_IDLE:

                            if (isBottom){
                                showEnd("马上加载更多，请稍后！");
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mPresenter.getData();
                                    }
                                },2000);
                            }
                            break;
                        case RecyclerView.SCROLL_STATE_DRAGGING:
                            // Log.i("Alex2", "开始拖了,现在margin是" + (mFooterView == null ? "" : mFooterView.getBottomMargin()));
                            break;
                        case RecyclerView.SCROLL_STATE_SETTLING:

                            break;
                    }

                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    int totalItemCount = linearLayoutManager.getItemCount();
                    int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                    if (totalItemCount == (lastVisibleItem + 1)) {
                        // Toast.makeText(getActivity().getApplicationContext(), "马上加载更多，请稍后！", Toast.LENGTH_SHORT).show();
                        Log.e("recyclerView", "totalItemCount: " + totalItemCount + "  lastVisibleItem: " + lastVisibleItem);
                        isBottom = true;
                        //mActionsListener.getJoyImages();


                    } else {
                        isBottom = false;
                    }
                }
            });
        }



        mPresenter.getData();

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void showList(List<Photos> list) {
        if (recyclerView != null){
            mPhotosList.addAll(list);
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closedLoading() {

    }


    @Override
    public void showEnd(String text) {
        Snackbar.make(getView(), text, Snackbar.LENGTH_LONG).setAction("action",null).show();
    }
}
