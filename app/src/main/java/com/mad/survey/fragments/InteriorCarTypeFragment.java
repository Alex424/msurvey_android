package com.mad.survey.fragments;

import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mad.survey.MADSurveyApp;
import com.mad.survey.R;
import com.mad.survey.activities.BaseActivity;
import com.mad.survey.globals.GlobalConstant;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.BankData;
import com.mad.survey.models.InteriorCarData;

public class InteriorCarTypeFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtCarNumber;
    private TextView txtSubTitle;

    private CheckBox chkHydraulic, chkGeared;

    public static InteriorCarTypeFragment newInstance(){
        InteriorCarTypeFragment fragment = new InteriorCarTypeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_interior_car_type, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);

        chkHydraulic = (CheckBox) parent.findViewById(R.id.chkHydraulic);
        chkGeared = (CheckBox) parent.findViewById(R.id.chkGeared);

        parent.findViewById(R.id.btnHydraulic).setOnClickListener(this);
        parent.findViewById(R.id.btnGeared).setOnClickListener(this);
        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
    }

    private void updateUIs(){
        chkGeared.setChecked(false);
        chkHydraulic.setChecked(false);
        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_edit_title, MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_title, MADSurveyApp.getInstance().getInteriorCarNum()+1, bankData.getNumOfInteriorCar(),MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }
        InteriorCarData interiorCarData = MADSurveyApp.getInstance().getInteriorCarData();
        String carType = interiorCarData.getTypeOfCar();
        if(carType.equals(GlobalConstant.TYPE_OF_CAR_HYDRAULIC)){
            chkHydraulic.setChecked(true);
        }
        else if(carType.equals(GlobalConstant.TYPE_OF_CAR_GEARED)){
            chkGeared.setChecked(true);
        }
    }

    private void goToNext(String carType){
        if (!isLastFocused()) return;

        MADSurveyApp.getInstance().getInteriorCarData().setTypeOfCar(carType);
        interiorCarDataHandler.update(MADSurveyApp.getInstance().getInteriorCarData());
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_STRUCTURE, "interior_car_structure");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.btnHydraulic:
                goToNext(GlobalConstant.TYPE_OF_CAR_HYDRAULIC);
                break;
            case R.id.btnGeared:
                goToNext(GlobalConstant.TYPE_OF_CAR_GEARED);
                break;

        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
