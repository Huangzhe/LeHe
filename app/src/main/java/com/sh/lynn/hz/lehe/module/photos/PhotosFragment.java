package com.sh.lynn.hz.lehe.module.photos;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sh.lynn.hz.lehe.LeHeApp;
import com.sh.lynn.hz.lehe.R;
import com.sh.lynn.hz.lehe.net.APIManager;

import java.util.List;

import javax.inject.Inject;


/**
 *
 */
public class PhotosFragment extends Fragment  implements  PhotosView{

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    RecyclerView recyclerView;
    @Inject
    PhotoPresenter mPresenter;
    @Inject
    APIManager mAPIManager;
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
        if(     recyclerView!=null        )
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(list,getActivity().getApplicationContext()));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closedLoading() {

    }
}
