package com.mad.survey.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.mad.survey.R;
import com.mad.survey.fragments.ExistingSurveysFragment;


public class ExistingSurveysActivity extends BaseActivity{
    private Fragment currentFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        //AndroidBug5497Workaround.assistActivity(this);

        if (savedInstanceState == null) {
            currentFragment = ExistingSurveysFragment.newInstance();
            getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.fragmentContent, currentFragment, "existing_surveys").commit();
        } else {
            currentFragment = getSupportFragmentManager().findFragmentByTag("existing_surveys");
        }

    }

}
