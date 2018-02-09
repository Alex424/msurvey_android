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
import com.mad.survey.models.InteriorCarDoorData;

public class InteriorCarFrontReturnTypeFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtCarNumber;
    private TextView txtSubTitle;

    private CheckBox chkA, chkB, chkOther;

    public static InteriorCarFrontReturnTypeFragment newInstance(){
        InteriorCarFrontReturnTypeFragment fragment = new InteriorCarFrontReturnTypeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_interior_car_front_return_type, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);

        chkA = (CheckBox) parent.findViewById(R.id.chkA);
        chkB = (CheckBox) parent.findViewById(R.id.chkB);
        chkOther = (CheckBox) parent.findViewById(R.id.chkOther);

        parent.findViewById(R.id.btnA).setOnClickListener(this);
        parent.findViewById(R.id.btnB).setOnClickListener(this);
        parent.findViewById(R.id.btnOther).setOnClickListener(this);
        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);

        setBackdoorTitle(parent);
    }

    private void updateUIs(){
        chkA.setChecked(false);
        chkB.setChecked(false);
        chkOther.setChecked(false);
        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_edit_title, MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_title, MADSurveyApp.getInstance().getInteriorCarNum()+1, bankData.getNumOfInteriorCar(),MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }
        InteriorCarDoorData interiorCarDoorData = MADSurveyApp.getInstance().getInteriorCarDoorData();
        String type = interiorCarDoorData.getTypeOfFrontReturn();
        switch (type){
            case "A":
                chkA.setChecked(true);
                break;
            case "B":
                chkB.setChecked(true);
                break;
            case "OTHER":
                chkOther.setChecked(true);
                break;
        }

    }
    private void saveData(String type){
        MADSurveyApp.getInstance().getInteriorCarDoorData().setTypeOfFrontReturn(type);
        interiorCarDoorDataHandler.update(MADSurveyApp.getInstance().getInteriorCarDoorData());
    }


    private void goToTypeOther(){
        saveData("OTHER");
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_FRONT_RETURN_MEASUREMENTS_OTHER, "front_return_measurements_other");
    }

    private void goToTypeA(){
        saveData("A");
        if (BaseActivity.TEMP_OPENING == 1){
            ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_CENTER_RETURN_MEASUREMENTS_A, "center_return_measurements_a");
        }else if (BaseActivity.TEMP_OPENING == 2){
            ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_SINGLE_SIDE_RETURN_MEASUREMENTS_A, "single_side_return_measurements_a");
        }

    }

    private void goToTypeB(){
        saveData("B");
        if (BaseActivity.TEMP_OPENING == 1){
            ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_CENTER_RETURN_MEASUREMENTS_B, "center_return_measurements_b");
        }else if (BaseActivity.TEMP_OPENING == 2){
            ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_SINGLE_SIDE_RETURN_MEASUREMENTS_B, "single_side_return_measurements_b");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.btnA:
                goToTypeA();
                break;
            case R.id.btnB:
                goToTypeB();
                break;
            case R.id.btnOther:
                goToTypeOther();
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
