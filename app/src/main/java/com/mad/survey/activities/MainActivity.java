package com.mad.survey.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;

import com.mad.survey.MADSurveyApp;
import com.mad.survey.R;
import com.mad.survey.preferences.AppPreference;
import com.mad.survey.utils.Utils;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private RelativeLayout layoutInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutInfo = (RelativeLayout) findViewById(R.id.layoutInfo);

        findViewById(R.id.btnNewSurvey).setOnClickListener(this);
        findViewById(R.id.btnExistingSurveys).setOnClickListener(this);
        findViewById(R.id.btnSettings).setOnClickListener(this);
        findViewById(R.id.btnInfo).setOnClickListener(this);
        findViewById(R.id.layoutInfo).setOnClickListener(this);
        findViewById(R.id.txtAddress).setOnClickListener(this);
        findViewById(R.id.txtPhone).setOnClickListener(this);
        findViewById(R.id.txtWebsite).setOnClickListener(this);
        findViewById(R.id.txtEmail).setOnClickListener(this);

        if (!AppPreference.getBooleanPrefValue(this, AppPreference.PREF_KEY_FIRST_TIME)){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showInfoLayout();
                }
            }, 500);
        }
    }

    private void showInfoLayout(){
        final boolean isShown = layoutInfo.getVisibility() == View.VISIBLE;
        AlphaAnimation anim = new AlphaAnimation(isShown? 1.0f:0.0f, isShown? 0.0f:1.0f);
        anim.setDuration(300);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        layoutInfo.startAnimation(anim);

        layoutInfo.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (isShown && !AppPreference.getBooleanPrefValue(MainActivity.this, AppPreference.PREF_KEY_FIRST_TIME)) {
                    AppPreference.setSharedPrefValue(MainActivity.this, AppPreference.PREF_KEY_FIRST_TIME, true);
                    goToSettings();
                }

                layoutInfo.setVisibility(isShown ? View.INVISIBLE : View.VISIBLE);

            }
        }, 300);
    }

    private void goToNewSurvey(){
        MADSurveyApp.getInstance().setIsEditMode(false);
        MADSurveyApp.getInstance().setProjectData(null);
        Intent intent = new Intent(this, SurveyActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.appear_from_right, R.anim.disappear_to_left);
    }

    private void goToExistingSurveys(){
        Intent intent = new Intent(this, ExistingSurveysActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.appear_from_right, R.anim.disappear_to_left);
    }

    private void goToSettings(){
        Intent intent = new Intent(this, SetupActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.appear_from_right, R.anim.disappear_to_left);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnNewSurvey:
                goToNewSurvey();
                break;
            case R.id.btnExistingSurveys:
                goToExistingSurveys();
                break;
            case R.id.btnSettings:
                goToSettings();
                break;
            case R.id.btnInfo:case R.id.layoutInfo:
                showInfoLayout();
                break;
            case R.id.txtAddress:
                Utils.goToGoogleMap(this, "http://maps.google.com/maps?q=115 City View Dr. Etobicoke, Ontario, M9W 5A8, Canada");
                break;
            case R.id.txtPhone:
                Utils.goToPhoneCall(this, getString(R.string.main_info_content_phone));
                break;
            case R.id.txtWebsite:
                Utils.goToBrowser(this, "http://" + getString(R.string.main_info_content_website));
                break;
            case R.id.txtEmail:
                Utils.goToEmail(this, getString(R.string.main_info_content_email));
                break;
        }
    }
}
