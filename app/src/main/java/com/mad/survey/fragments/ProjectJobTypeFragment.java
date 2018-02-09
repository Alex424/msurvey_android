package com.mad.survey.fragments;

import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.mad.survey.MADSurveyApp;
import com.mad.survey.R;
import com.mad.survey.activities.BaseActivity;
import com.mad.survey.globals.GlobalConstant;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.ProjectData;

public class ProjectJobTypeFragment extends BaseFragment implements View.OnClickListener,OnFragmentResumedListener {

    private CheckBox chkService, chkMod;

    public static ProjectJobTypeFragment newInstance(){
        ProjectJobTypeFragment fragment = new ProjectJobTypeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_project_job_type, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        chkService = (CheckBox) parent.findViewById(R.id.chkService);
        chkMod = (CheckBox) parent.findViewById(R.id.chkMod);

        parent.findViewById(R.id.btnService).setOnClickListener(this);
        parent.findViewById(R.id.btnMod).setOnClickListener(this);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
    }

    private void updateUIs(){
        chkService.setChecked(false);
        chkMod.setChecked(false);

        ProjectData projectData = MADSurveyApp.getInstance().getProjectData();
        int jobType = projectData.getJobType();
        if(jobType == GlobalConstant.PROJECT_JOB_TYPE_SERVICE)
            chkService.setChecked(true);
        else if(jobType == GlobalConstant.PROJECT_JOB_TYPE_MOD)
            chkMod.setChecked(true);


    }

    private void goToNext(int jobType){
        MADSurveyApp.getInstance().getProjectData().setJobType(jobType);
        projectDataHandler.update(MADSurveyApp.getInstance().getProjectData());

        if(MADSurveyApp.getInstance().isEditMode()) {
            ((BaseActivity) getActivity()).backToSpecificFragment("edit_option");
        }
        else{
            ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_PROJECT_NUMBER_BANKS, "project_number_banks");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;

            case R.id.btnService:
                goToNext(GlobalConstant.PROJECT_JOB_TYPE_SERVICE);
                break;
            case R.id.btnMod:
                goToNext(GlobalConstant.PROJECT_JOB_TYPE_MOD);
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
