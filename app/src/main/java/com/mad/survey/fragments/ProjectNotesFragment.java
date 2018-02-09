package com.mad.survey.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mad.survey.MADSurveyApp;
import com.mad.survey.R;
import com.mad.survey.activities.BaseActivity;
import com.mad.survey.globals.GlobalConstant;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.ProjectData;
import com.mad.survey.utils.Utils;

public class ProjectNotesFragment extends BaseFragment implements View.OnClickListener, OnFragmentResumedListener {

    private EditText edtNotes;

    public static ProjectNotesFragment newInstance(){
        ProjectNotesFragment fragment = new ProjectNotesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_project_notes, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        edtNotes = (EditText) parent.findViewById(R.id.edtNotes);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtNotes.requestFocus();
                Utils.showKeyboard(getActivity(), true, edtNotes);
            }
        });
    }

    private void updateUIs(){

        edtNotes.setText("");
        ProjectData projectData = MADSurveyApp.getInstance().getProjectData();
        edtNotes.setText(projectData.getNotes());
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        String notes = edtNotes.getText().toString();
        int projectId = MADSurveyApp.getInstance().getProjectData().getId();
        MADSurveyApp.getInstance().getProjectData().setNotes(notes);
        projectDataHandler.update(MADSurveyApp.getInstance().getProjectData());
        ((BaseActivity) getActivity()).replacePhotosFragment(projectId,GlobalConstant.PROJECT_PHOTO_TYPE_BUILDING, BaseActivity.FRAGMENT_ID_PROJECT_JOB_TYPE, "job_type");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.btnNext:
                goToNext();
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
        edtNotes.requestFocus();
        Utils.showKeyboard(getActivity(), true, edtNotes);
    }
}
