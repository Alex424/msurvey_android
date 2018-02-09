package com.mad.survey.fragments;

import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.mad.survey.MADSurveyApp;
import com.mad.survey.R;
import com.mad.survey.activities.BaseActivity;
import com.mad.survey.globals.GlobalConstant;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.BankData;
import com.mad.survey.models.HallStationData;
import com.mad.survey.models.LanternData;
import com.mad.survey.models.ProjectData;

public class LanternDescriptionFragment extends BaseFragment implements View.OnClickListener ,OnFragmentResumedListener{

    private TextView txtFloorIdentifier, txtLanternPINo;
    private TextView txtSubTitle;
    private CheckBox chkPositionIndicator, chkLantern, chkPILanternCombo;
    private LanternData lanternData;
    private static boolean flag = false;
    public static LanternDescriptionFragment newInstance(){
        LanternDescriptionFragment fragment = new LanternDescriptionFragment();
        return fragment;
    }

    public static LanternDescriptionFragment newInstance(boolean tag){
        LanternDescriptionFragment fragment = new LanternDescriptionFragment();
        flag = tag;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_lantern_description, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        txtFloorIdentifier = (TextView) parent.findViewById(R.id.txtFloorIdentifier);
        txtLanternPINo = (TextView) parent.findViewById(R.id.txtLanternPINo);

        chkPositionIndicator = (CheckBox) parent.findViewById(R.id.chkPositionIndicator);
        chkPILanternCombo = (CheckBox) parent.findViewById(R.id.chkPILanternCombo);
        chkLantern = (CheckBox) parent.findViewById(R.id.chkLantern);

        parent.findViewById(R.id.btnPositionIndicator).setOnClickListener(this);
        parent.findViewById(R.id.btnLantern).setOnClickListener(this);
        parent.findViewById(R.id.btnPILanternCombo).setOnClickListener(this);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
    }

    private void updateUIs(){

        chkLantern.setChecked(false);
        chkPILanternCombo.setChecked(false);
        chkPositionIndicator.setChecked(false);
        ProjectData projectData = MADSurveyApp.getInstance().getProjectData();

        lanternData = lanternDataHandler.get(projectData.getId(),
                MADSurveyApp.getInstance().getBankNum(),
                MADSurveyApp.getInstance().getLanternNum(),
                MADSurveyApp.getInstance().getFloorDescriptor());

        MADSurveyApp.getInstance().setLanternData(lanternData);

        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtFloorIdentifier.setText(getString(R.string.floor_n_identifier_edit_title, MADSurveyApp.getInstance().getFloorDescriptor()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title,MADSurveyApp.getInstance().getBankNum()+1,MADSurveyApp.getInstance().getBankData().getName()));
            txtFloorIdentifier.setText(getString(R.string.floor_n_identifier_title, MADSurveyApp.getInstance().getFloorNum()+1,projectData.getNumFloors() , MADSurveyApp.getInstance().getFloorDescriptor()));
            txtLanternPINo.setText(getString(R.string.lantern_pi_n_title, MADSurveyApp.getInstance().getLanternNum()+1, MADSurveyApp.getInstance().getLanternCnt()));
        }

        if(lanternData!=null){
            String descriptor = lanternData.getDescriptor();
            switch(descriptor){
                case GlobalConstant.LANTERN_LANTERN:
                    chkLantern.setChecked(true);
                    break;
                case GlobalConstant.LANTERN_PI_LANTERN_COMBO:
                    chkPILanternCombo.setChecked(true);
                    break;
                case GlobalConstant.LANTERN_POSITION_INDICATOR:
                    chkPositionIndicator.setChecked(true);
                    break;
            }
        }

    }

    private void goToNext(String descriptor){
        if (!isLastFocused()) return;

        if(flag){
            MADSurveyApp.getInstance().getLanternData().setDescriptor(descriptor);
            lanternDataHandler.update(MADSurveyApp.getInstance().getLanternData());
            flag = false;
            getActivity().onBackPressed();
        }
        else {
            // This lantern is already exists in DB;
            if (lanternData == null) {
                lanternData = lanternDataHandler.insertNewLanternWithFloorNumber(MADSurveyApp.getInstance().getProjectData().getId(),
                        MADSurveyApp.getInstance().getBankNum(),//BankNum
                        MADSurveyApp.getInstance().getLanternNum(),//LanternNum
                        MADSurveyApp.getInstance().getFloorDescriptor());//FloorDescriptor
            }
            lanternData.setDescriptor(descriptor);
            MADSurveyApp.getInstance().setLanternData(lanternData);
            lanternDataHandler.update(lanternData);
            ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_LANTERN_MOUNTING, "lantern_mounting");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;

            case R.id.btnPositionIndicator:
                goToNext(GlobalConstant.LANTERN_POSITION_INDICATOR);
                break;
            case R.id.btnLantern:
                goToNext(GlobalConstant.LANTERN_LANTERN);
                break;
            case R.id.btnPILanternCombo:
                goToNext(GlobalConstant.LANTERN_PI_LANTERN_COMBO);
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
