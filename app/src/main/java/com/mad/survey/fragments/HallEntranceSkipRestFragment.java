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

public class HallEntranceSkipRestFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtCarNumber;
    private TextView txtSubTitle;
    private TextView txtFloorIdentifier;
    private CheckBox chkYes,chkNo;

    public static HallEntranceSkipRestFragment newInstance(){
        HallEntranceSkipRestFragment fragment = new HallEntranceSkipRestFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_hall_entrance_skip, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);
        txtFloorIdentifier = (TextView) parent.findViewById(R.id.txtFloorIdentifier);
        chkYes = (CheckBox) parent.findViewById(R.id.chkYes);
        chkNo = (CheckBox) parent.findViewById(R.id.chkNo);

        parent.findViewById(R.id.btnYes).setOnClickListener(this);
        parent.findViewById(R.id.btnNo).setOnClickListener(this);
        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
    }


    private void updateUIs(){
        chkYes.setChecked(false);
        chkNo.setChecked(false);

        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtFloorIdentifier.setText(getString(R.string.floor_n_identifier_edit_title, MADSurveyApp.getInstance().getFloorDescriptor()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtFloorIdentifier.setText(getString(R.string.floor_n_identifier_title, MADSurveyApp.getInstance().getFloorNum() + 1, MADSurveyApp.getInstance().getProjectData().getNumFloors(), MADSurveyApp.getInstance().getFloorDescriptor()));
            txtCarNumber.setText(getString(R.string.car_n_title, MADSurveyApp.getInstance().getHallEntranceCarNum()+1, MADSurveyApp.getInstance().getBankData().getNumOfCar()));
        }

    }

    private void goToNextHallEntrance(){
        int currentFloor = MADSurveyApp.getInstance().getFloorNum();
        MADSurveyApp.getInstance().setHallEntranceCarNum(0);
        MADSurveyApp.getInstance().setFloorNum(currentFloor + 1);
        ((BaseActivity) getActivity()).backToSpecificFragment("hall_entrance_floor_description");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.btnYes:
                goForNextBankMeasurements();
                break;
            case R.id.btnNo:
                goToNextHallEntrance();
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
