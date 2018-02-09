package com.mad.survey.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mad.survey.MADSurveyApp;
import com.mad.survey.R;
import com.mad.survey.activities.BaseActivity;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.BankData;
import com.mad.survey.models.HallStationData;
import com.mad.survey.models.ProjectData;

public class HallStationFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private CheckBox chkUnique, chkSameAsLast, chkSameAsExisting;
    private TextView txtFloorIdentifier, txtHallStationDescriptor;
    private TextView txtSubTitle;

    private View parent;

    public static HallStationFragment newInstance(){
        HallStationFragment fragment = new HallStationFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_hallstation, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        txtFloorIdentifier = (TextView) parent.findViewById(R.id.txtFloorIdentifier);
        txtHallStationDescriptor = (TextView) parent.findViewById(R.id.txtHallStationDescriptor);
        chkUnique = (CheckBox) parent.findViewById(R.id.chkUnique);
        chkSameAsLast = (CheckBox) parent.findViewById(R.id.chkSameAsLast);
        chkSameAsExisting = (CheckBox) parent.findViewById(R.id.chkSameAsExisting);

        parent.findViewById(R.id.btnUnique).setOnClickListener(this);
        parent.findViewById(R.id.btnSameAsLast).setOnClickListener(this);
        parent.findViewById(R.id.btnSameAsExisting).setOnClickListener(this);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
    }

    private void updateUIs(){
        ProjectData projectData = MADSurveyApp.getInstance().getProjectData();
        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtFloorIdentifier.setText(getString(R.string.floor_n_identifier_edit_title, MADSurveyApp.getInstance().getFloorDescriptor()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtFloorIdentifier.setText(getString(R.string.floor_n_identifier_title, MADSurveyApp.getInstance().getFloorNum() + 1, projectData.getNumFloors(), MADSurveyApp.getInstance().getFloorDescriptor()));
            txtHallStationDescriptor.setText(getString(R.string.hall_station_descriptor_n_title,  MADSurveyApp.getInstance().getHallStationNum()+1, bankData.getNumOfRiser()));
        }

        //Added by Alex - 2018/01/24
        if (!MADSurveyApp.getInstance().isEditMode() && MADSurveyApp.getInstance().getHallStationNum() > 0){
            ((BaseActivity) getActivity()).setBackable(false);
            parent.findViewById(R.id.btnBack).setVisibility(View.INVISIBLE);
        }

    }

    private void goToHallStationsList(){
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_HALLSTATION_LIST, "hall_stations_list");
    }

    private void goToAFFMeasurement(){

        HallStationData hallStationData = hallStationDataHandler.insertNewHallStationSameAs(-1,
                MADSurveyApp.getInstance().getProjectData().getId(),
                MADSurveyApp.getInstance().getBankNum(),
                MADSurveyApp.getInstance().getHallStationNum(),
                MADSurveyApp.getInstance().getFloorDescriptor(),
                MADSurveyApp.getInstance().getFloorNum());

        MADSurveyApp.getInstance().setHallStationData(hallStationData);
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_HALLSTATION_SAMEAS_MEASUREMENTS, "hallstation_sameas_measurements");
    }

    private void goToHallStationCapturing(){
        MADSurveyApp.getInstance().setHallStationData(null);

        ProjectData projectData = MADSurveyApp.getInstance().getProjectData();
        HallStationData hallStationData = hallStationDataHandler.get(projectData.getId(),
                MADSurveyApp.getInstance().getBankNum(),
                MADSurveyApp.getInstance().getHallStationNum(),
                MADSurveyApp.getInstance().getFloorDescriptor());
        if (hallStationData != null){
            hallStationDataHandler.delete(hallStationData);
        }

        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_HALLSTATION_DESCRIPTION, "hallstation_description");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.btnUnique:
                goToHallStationCapturing();
                break;
            case R.id.btnSameAsLast:
                goToAFFMeasurement();
                break;
            case R.id.btnSameAsExisting:
                goToHallStationsList();
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
