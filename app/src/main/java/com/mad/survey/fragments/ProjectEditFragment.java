package com.mad.survey.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mad.survey.MADSurveyApp;
import com.mad.survey.R;
import com.mad.survey.activities.BaseActivity;
import com.mad.survey.adapters.SurveysListAdapter;
import com.mad.survey.dialogs.SurveyDeleteConfirmDialog;
import com.mad.survey.dialogs.SurveyDeleteDialog;
import com.mad.survey.dialogs.SurveyOperationDialog;
import com.mad.survey.models.ProjectData;

import java.util.ArrayList;
import java.util.List;

public class ProjectEditFragment extends BaseFragment implements View.OnClickListener {



    public static ProjectEditFragment newInstance(){
        ProjectEditFragment fragment = new ProjectEditFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_project_edit_menu, container, false);

        initView(parent, inflater);


        return parent;
    }

    private void initView(View parent, LayoutInflater inflater){


        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        parent.findViewById(R.id.projectInfo).setOnClickListener(this);
        parent.findViewById(R.id.lobby).setOnClickListener(this);
        parent.findViewById(R.id.elevatorBanks).setOnClickListener(this);
        parent.findViewById(R.id.elevatorCars).setOnClickListener(this);
        parent.findViewById(R.id.hallEntrance).setOnClickListener(this);
        parent.findViewById(R.id.hallStation).setOnClickListener(this);
        parent.findViewById(R.id.lantern).setOnClickListener(this);
        parent.findViewById(R.id.cabInterior).setOnClickListener(this);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnSubmit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.btnSubmit:
                ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_SUBMIT_PROJECT, "submit_project");
                break;
            case R.id.projectInfo:
                ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_PROJECT_DETAILS, "project_details");
                break;
            case R.id.lobby:
                ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_EDIT_LOBBY_LIST, "edit_lobby_list");
                break;
            case R.id.elevatorBanks:
                ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_EDIT_BANK_LIST, "edit_bank_list");
                break;
            case R.id.hallStation:
                ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_EDIT_HALLSTATION_LIST, "edit_hallStation_list");
                break;
            case R.id.lantern:
                ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_EDIT_LANTERN_LIST, "edit_lantern_list");
                break;
            case R.id.elevatorCars:
                ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_EDIT_CAR_LIST, "edit_car_list");
                break;
            case R.id.cabInterior:
                ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_EDIT_INTERIOR_CAR_LIST, "edit_interior_car_list");
                break;
            case R.id.hallEntrance:
                ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_EDIT_HALL_ENTRANCE_LIST, "edit_hall_entrance_list");
                break;

        }
    }
}
