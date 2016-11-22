package com.sh.lynn.hz.lehe.module.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sh.lynn.hz.lehe.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hyz84 on 16/11/22.
 */

public class TextDetailDialog extends DialogFragment {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_text)
    TextView tv_text;
    @BindView(R.id.iv_share)
    ImageView iv_share;


    public TextDetailDialog(){

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.dialog_text_detail,container);

        ButterKnife.bind(this,view);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        Bundle bundle = getArguments();

        String title = bundle.getString("title");
        final   String text = bundle.getString("text");

        tv_title.setText(title);
        tv_text.setText(Html.fromHtml(text));

        iv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.QQ)
                        .withText(text)
                        .withTitle("笑话")
                        
                        .setCallback(umShareListener)
                        .share();
            }
        });

        return view;
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat","platform"+platform);

            Toast.makeText(getActivity(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(getActivity(),platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if(t!=null){
                Log.d("throw","throw:"+t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(getActivity(),platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };
}
