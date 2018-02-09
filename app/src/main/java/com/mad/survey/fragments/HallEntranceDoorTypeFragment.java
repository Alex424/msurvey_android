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
import com.mad.survey.models.HallEntranceData;
import com.mad.survey.models.InteriorCarData;

public class HallEntranceDoorTypeFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtCarNumber;
    private TextView txtSubTitle;
    private TextView txtFloorIdentifier;
    private CheckBox chkCenter,chkSingle,chkTwo;

    public static HallEntranceDoorTypeFragment newInstance(){
        HallEntranceDoorTypeFragment fragment = new HallEntranceDoorTypeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_hall_entrance_door_type, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);
        txtFloorIdentifier = (TextView) parent.findViewById(R.id.txtFloorIdentifier);
        chkCenter = (CheckBox) parent.findViewById(R.id.chkCenter);
        chkSingle = (CheckBox) parent.findViewById(R.id.chkSingleSpeed);
        chkTwo = (CheckBox) parent.findViewById(R.id.chkTwoSpeed);

        parent.findViewById(R.id.btnCenter).setOnClickListener(this);
        parent.findViewById(R.id.btnSingleSpeed).setOnClickListener(this);
        parent.findViewById(R.id.btnTwoSpeed).setOnClickListener(this);
        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
    }


    private void updateUIs(){

        chkCenter.setChecked(false);
        chkSingle.setChecked(false);
        chkTwo.setChecked(false);

        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtFloorIdentifier.setText(getString(R.string.floor_n_identifier_edit_title, MADSurveyApp.getInstance().getFloorDescriptor()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtFloorIdentifier.setText(getString(R.string.floor_n_identifier_title, MADSurveyApp.getInstance().getFloorNum() + 1, MADSurveyApp.getInstance().getProjectData().getNumFloors(), MADSurveyApp.getInstance().getFloorDescriptor()));
            txtCarNumber.setText(getString(R.string.car_n_title, MADSurveyApp.getInstance().getHallEntranceCarNum()+1, MADSurveyApp.getInstance().getBankData().getNumOfCar()));
        }

        HallEntranceData hallEntranceData = hallEntranceDataHandler.get(MADSurveyApp.getInstance().getProjectData().getId(),
                MADSurveyApp.getInstance().getBankNum(),
                MADSurveyApp.getInstance().getFloorDescriptor(),
                MADSurveyApp.getInstance().getHallEntranceCarNum());
        if(hallEntranceData!=null){
            MADSurveyApp.getInstance().setHallEntranceData(hallEntranceData);
            int type = hallEntranceData.getDoorType();
            switch (type){
                case 1:
                    chkTwo.setChecked(true);
                    break;
                case 2:
                    chkSingle.setChecked(true);
                    break;
                case 3:
                    chkCenter.setChecked(true);
                    break;
            }
        }

    }

    private void saveData(){

        // If there isn't currentHallEntrance in DB,INSERT
        HallEntranceData hallEntranceData = hallEntranceDataHandler.insertNewHallEntrance(MADSurveyApp.getInstance().getProjectData().getId(),
                MADSurveyApp.getInstance().getBankNum(),
                MADSurveyApp.getInstance().getHallEntranceCarNum(),
                MADSurveyApp.getInstance().getFloorDescriptor(),
                BaseActivity.TEMP_DOOR_TYPE);
        if(hallEntranceData!=null){
            MADSurveyApp.getInstance().setHallEntranceData(hallEntranceData);
        }
        else{
            HallEntranceData hallEntranceData1 = MADSurveyApp.getInstance().getHallEntranceData();
            hallEntranceData1.setDoorType(BaseActivity.TEMP_DOOR_TYPE);
            MADSurveyApp.getInstance().setHallEntranceData(hallEntranceData1);
            hallEntranceDataHandler.update(hallEntranceData1);
        }
    }
    private void goToSelectDirection(){
        saveData();
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_HALL_ENTRANCE_DOOR_OPENING_DIRECTION, "hall_entrance_door_opening_direction");
    }

    private void goToMeasurements(){
        saveData();
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_HALL_ENTRANCE_FRONT_RETURN_MEASUREMENTS, "hall_entrnace_front_return_measurements");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.btnCenter:
                BaseActivity.TEMP_DOOR_TYPE = 3;
                goToMeasurements();
                break;
            case R.id.btnSingleSpeed:
                BaseActivity.TEMP_DOOR_TYPE = 2;
                goToSelectDirection();
                break;
            case R.id.btnTwoSpeed:
                BaseActivity.TEMP_DOOR_TYPE = 1;
                goToSelectDirection();
                break;
            case R.id.btnNext:
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
