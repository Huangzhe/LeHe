package com.sh.lynn.hz.lehe.module.joker;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the
 * interface.
 */
public class JokerFragment extends Fragment implements JokerContract.View {

    private int mColumnCount = 1;

    private int index = 0;

    private JokerContract.UserActionsListener mActionsListener;
    RecyclerView recyclerView = null;
    //    SwipeRefreshLayout swipeRefreshLayout = null;
    List<Joker> mJokerList = new ArrayList<>();
    MyJokerRecyclerViewAdapter mJokerRecyclerViewAdapter;
    boolean isBottom = false;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public JokerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_joker_list, container, false);

        mActionsListener = LeHeApp.get(getActivity()).getAppComponent().plus(new JokerModule(this)).getJokerPresenter();

        Context context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }

        mActionsListener.getJokers();
//        swipeRefreshLayout =
//                (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
//        swipeRefreshLayout.setColorSchemeColors(
//                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
//                ContextCompat.getColor(getActivity(), R.color.colorAccent),
//                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                mActionsListener.getJoyImages();
//
//            }
//        });
        mJokerRecyclerViewAdapter = new MyJokerRecyclerViewAdapter(mJokerList);
        recyclerView.setAdapter(mJokerRecyclerViewAdapter);

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
                                        mActionsListener.getJokers();
                                    }
                                },2000);
                               }
                            break;
                        case RecyclerView.SCROLL_STATE_DRAGGING:
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
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

    }

    @Override
    public void showJokerList(List<Joker> list) {
        mJokerList.addAll(list);
        mJokerRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEnd(String text) {
        Snackbar.make(getView(), text, Snackbar.LENGTH_LONG).setAction("action",null).show();
        //  Toast.makeText(getActivity().getApplicationContext(),"没有更多了，请过会儿再来看~",Toast.LENGTH_LONG).show();
    }

//    @Override
//    public void loadMoreJoker() {
//        mActionsListener.loadMoreJokers(index);
//    }
}
