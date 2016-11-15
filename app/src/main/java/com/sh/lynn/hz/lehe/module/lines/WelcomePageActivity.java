package com.sh.lynn.hz.lehe.module.lines;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.sh.lynn.hz.lehe.LeHeApp;
import com.sh.lynn.hz.lehe.R;
import com.sh.lynn.hz.lehe.module.main.MainActivity;
import com.sh.lynn.hz.lehe.net.APIManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

//import butterknife.BindView;
//import butterknife.ButterKnife;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class WelcomePageActivity extends Activity implements WelcPageView {


    private static final int AUTO_JUMP_DELAY_MILLIS = 5000;

    @Inject
    WelcPagePresenter mWelcPagePresenter;
    @Inject
    APIManager mAPIManager;

   @BindView(R.id.tv_welcomeWord)
    TextView tv_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome_page);

      ButterKnife.bind(this);
        //tv_text = (TextView) this.findViewById(R.id.tv_welcomeWord);
        LeHeApp.get(this).getAppComponent().plus(new WelcPageModule(this)).inject(this);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }


    @Override
    protected void onResume() {
        super.onResume();
        mWelcPagePresenter.getWelcWord();
//        try {
//            Thread.sleep(AUTO_JUMP_DELAY_MILLIS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        gotoMainView();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closedLoading() {

    }

    @Override
    public void setWelcWord(String text) {
        tv_text.setText(text);
    }

    @Override
    public void gotoMainView() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
