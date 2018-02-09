package com.mad.survey.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
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
import com.mad.survey.models.ProjectData;

public class HallStationDescriptionFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtFloorIdentifier, txtHallStationDescriptor;

    private CheckBox chkTerminalHallStation, chkIntermediateHallStation;
    private CheckBox chkFireOperationStation, chkEPOStation, chkAccessStation;
    private CheckBox chkSwingServiceHallStation, chkSwingServiceTerminal, chkOther;
    private EditText edtOtherName;
    private TextView btnNext;
    private HallStationData hallStationData;
    private static boolean flag = false;

    public static HallStationDescriptionFragment newInstance(){
        HallStationDescriptionFragment fragment = new HallStationDescriptionFragment();
        return fragment;
    }
    public static HallStationDescriptionFragment newInstance(boolean tag){
        flag = tag;
        HallStationDescriptionFragment fragment = new HallStationDescriptionFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_hallstation_description, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtFloorIdentifier = (TextView) parent.findViewById(R.id.txtFloorIdentifier);
        txtHallStationDescriptor = (TextView) parent.findViewById(R.id.txtHallStationDescriptor);

        chkTerminalHallStation = (CheckBox) parent.findViewById(R.id.chkTerminalHallStation);
        chkIntermediateHallStation = (CheckBox) parent.findViewById(R.id.chkIntermediateHallStation);
        chkFireOperationStation = (CheckBox) parent.findViewById(R.id.chkFireOperationStation);
        chkEPOStation = (CheckBox) parent.findViewById(R.id.chkEPOStation);
        chkAccessStation = (CheckBox) parent.findViewById(R.id.chkAccessStation);
        chkSwingServiceHallStation = (CheckBox) parent.findViewById(R.id.chkSwingServiceHallStation);
        chkSwingServiceTerminal = (CheckBox) parent.findViewById(R.id.chkSwingServiceTerminal);
        chkOther = (CheckBox) parent.findViewById(R.id.chkOther);
        edtOtherName = (EditText) parent.findViewById(R.id.edtOtherName);
        btnNext = (TextView) parent.findViewById(R.id.btnNext);

        parent.findViewById(R.id.btnTerminalHallStation).setOnClickListener(this);
        parent.findViewById(R.id.btnIntermediateHallStation).setOnClickListener(this);
        parent.findViewById(R.id.btnFireOperationStation).setOnClickListener(this);
        parent.findViewById(R.id.btnEPOStation).setOnClickListener(this);
        parent.findViewById(R.id.btnAccessStation).setOnClickListener(this);
        parent.findViewById(R.id.btnSwingServiceHallStation).setOnClickListener(this);
        parent.findViewById(R.id.btnSwingServiceTerminal).setOnClickListener(this);
        parent.findViewById(R.id.btnOther).setOnClickListener(this);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);

        setHeaderScrollConfiguration(parent, getString(R.string.sub_title_hall_station), getString(R.string.sub_title_description), true, true);
    }

    private void updateUIs(){
        chkOther.setChecked(false);
        chkSwingServiceTerminal.setChecked(false);
        chkSwingServiceHallStation.setChecked(false);
        chkAccessStation.setChecked(false);
        chkEPOStation.setChecked(false);
        chkFireOperationStation.setChecked(false);
        chkTerminalHallStation.setChecked(false);
        chkIntermediateHallStation.setChecked(false);
        edtOtherName.setText("");

        ProjectData projectData = MADSurveyApp.getInstance().getProjectData();
        BankData bankData = MADSurveyApp.getInstance().getBankData();
        hallStationData = hallStationDataHandler.get(projectData.getId(),
                MADSurveyApp.getInstance().getBankNum(),
                MADSurveyApp.getInstance().getHallStationNum(),
                MADSurveyApp.getInstance().getFloorDescriptor());
        MADSurveyApp.getInstance().setHallStationData(hallStationData);

        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtFloorIdentifier.setText(getString(R.string.floor_n_identifier_edit_title, MADSurveyApp.getInstance().getFloorDescriptor()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtFloorIdentifier.setText(getString(R.string.floor_n_identifier_title, MADSurveyApp.getInstance().getFloorNum() + 1, projectData.getNumFloors(), MADSurveyApp.getInstance().getFloorDescriptor()));
            txtHallStationDescriptor.setText(getString(R.string.hall_station_descriptor_n_title,  MADSurveyApp.getInstance().getHallStationNum()+1, bankData.getNumOfRiser()));
        }

        if(hallStationData != null){
            String descriptor = hallStationData.getDescription();
            switch (descriptor){
                case GlobalConstant.TERMINAL_HALL_STATION:
                    chkTerminalHallStation.setChecked(true);
                    break;
                case GlobalConstant.INTERMEDIATE_HALL_STATION:
                    chkIntermediateHallStation.setChecked(true);
                    break;
                case GlobalConstant.FIRE_OPERATION_STATION:
                    chkFireOperationStation.setChecked(true);
                    break;
                case GlobalConstant.EPO_STATION:
                    chkEPOStation.setChecked(true);
                    break;
                case GlobalConstant.ACCESS_STATION:
                    chkAccessStation.setChecked(true);
                    break;
                case GlobalConstant.SWING_SERVICE_HALL_STATION:
                    chkSwingServiceHallStation.setChecked(true);
                    break;
                case GlobalConstant.SWING_SERVICE_TERMINAL_STATION:
                    chkSwingServiceTerminal.setChecked(true);
                    break;
                default:
                    updateOtherUIs();
                    chkOther.setChecked(true);
                    edtOtherName.setText(descriptor);
                    break;
            }
            edtOtherName.setVisibility(chkOther.isChecked()? View.VISIBLE:View.GONE);
            btnNext.setVisibility(chkOther.isChecked()? View.VISIBLE:View.GONE);
        }
    }

    private void goToNext(String descriptor){
        if (!isLastFocused()) return;


        if(flag){
            MADSurveyApp.getInstance().getHallStationData().setDescription(descriptor);
            hallStationDataHandler.update(MADSurveyApp.getInstance().getHallStationData());
            flag = false;
            getActivity().onBackPressed();

        }
        else {
            if (hallStationData == null) {
                hallStationData = hallStationDataHandler.insertNewHallStationWithFloorNumber(MADSurveyApp.getInstance().getProjectData().getId(),
                        MADSurveyApp.getInstance().getBankNum(),//BankNum
                        MADSurveyApp.getInstance().getHallStationNum(),//HallStationNum
                        MADSurveyApp.getInstance().getFloorDescriptor(),//FloorDescriptor
                        MADSurveyApp.getInstance().getFloorNum());
            }
            hallStationData.setDescription(descriptor);
            MADSurveyApp.getInstance().setHallStationData(hallStationData);
            hallStationDataHandler.update(hallStationData);
            ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_HALLSTATION_MOUNTING, "hallstation_mounting");
        }
    }

    private void updateOtherUIs(){
        chkTerminalHallStation.setChecked(false);
        chkIntermediateHallStation.setChecked(false);
        chkFireOperationStation.setChecked(false);
        chkSwingServiceHallStation.setChecked(false);
        chkSwingServiceTerminal.setChecked(false);
        chkAccessStation.setChecked(false);
        chkEPOStation.setChecked(false);
        edtOtherName.setText("");

        chkOther.setChecked(!chkOther.isChecked());
        edtOtherName.setVisibility(chkOther.isChecked()? View.VISIBLE:View.GONE);
        btnNext.setVisibility(chkOther.isChecked()? View.VISIBLE:View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.btnNext:
                goToNext(edtOtherName.getText().toString());
                break;
            case R.id.btnOther:
                updateOtherUIs();
                break;
            case R.id.btnTerminalHallStation:
                goToNext(GlobalConstant.TERMINAL_HALL_STATION);
                break;
            case R.id.btnIntermediateHallStation:
                goToNext(GlobalConstant.INTERMEDIATE_HALL_STATION);
                break;
            case R.id.btnFireOperationStation:
                goToNext(GlobalConstant.FIRE_OPERATION_STATION);
                break;
            case R.id.btnEPOStation:
                goToNext(GlobalConstant.EPO_STATION);
                break;
            case R.id.btnAccessStation:
                goToNext(GlobalConstant.ACCESS_STATION);
                break;
            case R.id.btnSwingServiceHallStation:
                goToNext(GlobalConstant.SWING_SERVICE_HALL_STATION);
                break;
            case R.id.btnSwingServiceTerminal:
                goToNext(GlobalConstant.SWING_SERVICE_TERMINAL_STATION);
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
