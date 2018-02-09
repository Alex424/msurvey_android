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
import com.mad.survey.models.InteriorCarData;

public class InteriorCarCeilingExhaustFanFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtSubTitle;
    private TextView txtCarNumber;
    private CheckBox chkYes,chkNo;

    public static InteriorCarCeilingExhaustFanFragment newInstance(){
        InteriorCarCeilingExhaustFanFragment fragment = new InteriorCarCeilingExhaustFanFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_interior_car_ceiling_exhaust_fan, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);
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
            txtCarNumber.setText(getString(R.string.car_number_n_edit_title, MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_title, MADSurveyApp.getInstance().getInteriorCarNum()+1, bankData.getNumOfInteriorCar(),MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }
        InteriorCarData interiorCarData = MADSurveyApp.getInstance().getInteriorCarData();

        int yes = interiorCarData.getIsThereExhaustFan();
        if(yes == 1)
            chkYes.setChecked(true);
        else if(yes == 2)
            chkNo.setChecked(true);
    }

    private void goToNext(int yes){
        if (!isLastFocused()) return;

        MADSurveyApp.getInstance().getInteriorCarData().setIsThereExhaustFan(yes);
        interiorCarDataHandler.update(MADSurveyApp.getInstance().getInteriorCarData());
        if(yes == 1)
            ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_CEILING_EXHAUST_FAN_LOCATION, "interior_car_ceiling_exhaust_fan_location");
        else if(yes == 2)
            ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_CEILING_FRAME_TYPE, "interior_car_ceiling_frame_type");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.btnYes:
                goToNext(1);
                break;
            case R.id.btnNo:
                goToNext(2);
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
