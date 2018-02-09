package com.mad.survey.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.mad.survey.R;
import com.mad.survey.fragments.ProjectDetailsFragment;


public class SurveyActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        //AndroidBug5497Workaround.assistActivity(this);

        replaceFragment(BaseActivity.FRAGMENT_ID_PROJECT_DETAILS, "new_survey");
    }

}
