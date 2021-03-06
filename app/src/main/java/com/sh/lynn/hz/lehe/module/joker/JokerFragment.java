package com.sh.lynn.hz.lehe.module.joker;

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
import com.sh.lynn.hz.lehe.listener.MyUMShareListener;
import com.sh.lynn.hz.lehe.listener.OnImageClickListener;

import java.util.ArrayList;
import java.util.Collections;
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
    List<Joker> mJokerList = new ArrayList<>();
    MyJokerRecyclerViewAdapter mJokerRecyclerViewAdapter;
    boolean isBottom = false;
    private OnListFragmentInteractionListener mListener;
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

        //mActionsListener.getJokers();
        mActionsListener.loadMoreJokers();
        mJokerRecyclerViewAdapter = new MyJokerRecyclerViewAdapter(mJokerList, new MyUMShareListener(getActivity().getApplicationContext()),new OnImageClickListener() {
            @Override
            public void onImageInteraction(Object item) {
                //  mPresenter.downLoadImage(item.getImg());

                if(item instanceof Joker){

                    Intent intent = new Intent(getActivity(),ImageActivity.class);
                    intent.putExtra("ImageUrl",((Joker)item).getText());
                    intent.putExtra("ImageType",((Joker)item).getType());
                    startActivity(intent);
                }
            }
        });
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
                                isBottom = false;
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mActionsListener.loadMoreJokers();
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
                        Log.e("recyclerView", "totalItemCount: " + totalItemCount + "  lastVisibleItem: " + lastVisibleItem);
                        isBottom = true;


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
        Collections.shuffle(list);
        mJokerList.addAll(list);
//        for(int x=0;x<list.size();x++){
//            Log.d("showJokerList",list.get(x).getTitle()+" type="+list.get(x).getType()+"  text="+list.get(x).getText());
//        }
        mJokerRecyclerViewAdapter.notifyDataSetChanged();
        Log.d("showJokerList","list size="+list.size()+" allSize="+mJokerList.size());
    }

    @Override
    public void showEnd(String text) {
        Snackbar.make(getView(), text, Snackbar.LENGTH_LONG).setAction("action",null).show();
        //  Toast.makeText(getActivity().getApplicationContext(),"没有更多了，请过会儿再来看~",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showDetail(Joker joker) {
//        Dialog dialog = new Dialog(getActivity());
//        dialog.setContentView();
//        Bundle bundle = new Bundle();
//        bundle.putString("title",joker.getTitle());
//        bundle.putString("text",joker.getText());
//        TextDetailDialog textDetailDialog = new TextDetailDialog();
//        textDetailDialog.setArguments(bundle);
//        textDetailDialog.show(getFragmentManager(),"textDialog");

    }

//    @Override
//    public void loadMoreJoker() {
//        mActionsListener.loadMoreJokers(index);
//    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Joker item);
    }
}
