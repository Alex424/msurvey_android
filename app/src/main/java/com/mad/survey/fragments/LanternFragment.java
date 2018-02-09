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
import com.mad.survey.models.LanternData;
import com.mad.survey.models.ProjectData;

public class LanternFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener {

    private CheckBox chkUnique, chkSameAsLast, chkSameAsExisting;
    private TextView txtFloorIdentifier;
    private TextView txtSubTitle;
    private TextView txtLanternPINo;

    private View parent;

    public static LanternFragment newInstance(){
        LanternFragment fragment = new LanternFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_lantern, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        txtFloorIdentifier = (TextView) parent.findViewById(R.id.txtFloorIdentifier);
        txtLanternPINo = (TextView) parent.findViewById(R.id.txtLanternPINo);
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
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtFloorIdentifier.setText(getString(R.string.floor_n_identifier_edit_title, MADSurveyApp.getInstance().getFloorDescriptor()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title,MADSurveyApp.getInstance().getBankNum()+1,MADSurveyApp.getInstance().getBankData().getName()));
            txtFloorIdentifier.setText(getString(R.string.floor_n_identifier_title, MADSurveyApp.getInstance().getFloorNum()+1,projectData.getNumFloors() , MADSurveyApp.getInstance().getFloorDescriptor()));
            txtLanternPINo.setText(getString(R.string.lantern_pi_n_title, MADSurveyApp.getInstance().getLanternNum()+1, MADSurveyApp.getInstance().getLanternCnt()));
        }

        //Added by Alex - 2018/01/24
        if (!MADSurveyApp.getInstance().isEditMode() && MADSurveyApp.getInstance().getLanternNum() > 0){
            ((BaseActivity) getActivity()).setBackable(false);
            parent.findViewById(R.id.btnBack).setVisibility(View.INVISIBLE);
        }
    }

    private void goToLanternList(){
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_LANTERN_LIST, "lanterns_list");
    }

    private void goToLanternSameAsMeasurements(){
        LanternData lanternData = lanternDataHandler.insertNewLanternSameAs(-1,
                MADSurveyApp.getInstance().getProjectData().getId(),
                MADSurveyApp.getInstance().getBankNum(),
                MADSurveyApp.getInstance().getLanternNum(),
                MADSurveyApp.getInstance().getFloorDescriptor());

        MADSurveyApp.getInstance().setLanternData(lanternData);

        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_LANTERN_SAMEAS_MEASUREMENTS, "lantern_sameas_measurements");
    }

    private void goToLanternCapturing(){
        MADSurveyApp.getInstance().setLanternData(null);

        ProjectData projectData = MADSurveyApp.getInstance().getProjectData();
        LanternData lanternData = lanternDataHandler.get(projectData.getId(),
                MADSurveyApp.getInstance().getBankNum(),
                MADSurveyApp.getInstance().getLanternNum(),
                MADSurveyApp.getInstance().getFloorDescriptor());
        if (lanternData != null){
            lanternDataHandler.delete(lanternData);
        }

        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_LANTERN_DESCRIPTION, "lantern_desc");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.btnUnique:
                goToLanternCapturing();
                break;
            case R.id.btnSameAsLast:
                goToLanternSameAsMeasurements();
                break;
            case R.id.btnSameAsExisting:
                goToLanternList();
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
