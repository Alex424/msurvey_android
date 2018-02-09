package com.mad.survey.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mad.survey.R;
import com.mad.survey.globals.GlobalConstant;
import com.mad.survey.utils.Utils;

public class SplashActivity extends BaseActivity {

    ImageView imgMainLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        InitStorage();
        updateUIs();
        doLogoAnimation();
    }

    /** Initializing local storage folder **/
    private void InitStorage() {
        Utils.CreateWorkDirectories(GlobalConstant.getHomeDirPath(), false);
        Utils.CreateWorkDirectories(GlobalConstant.getPhotoDirPath(), false);
    }

    // Set the Main Logo's image's height same as width
    private void updateUIs(){
        imgMainLogo = (ImageView) findViewById(R.id.imgMainLogo);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imgMainLogo.getLayoutParams();
        params.height = displayWidth - getResources().getDimensionPixelSize(R.dimen.splash_image_margin) * 2;
        imgMainLogo.setLayoutParams(params);
    }

    private void doLogoAnimation(){
        AlphaAnimation anim = new AlphaAnimation(0, 1);
        anim.setDuration(1000);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        imgMainLogo.startAnimation(anim);

        imgMainLogo.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.appear_from_right, R.anim.disappear_to_left);
            }
        }, 1500);
    }
}
