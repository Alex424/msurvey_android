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
import com.mad.survey.globals.GlobalConstant;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.BankData;
import com.mad.survey.models.InteriorCarDoorData;

public class InteriorCarDoorTypeFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{


    private TextView txtCarNumber;
    private TextView txtSubTitle;
    private CheckBox chkSingle,chkTwo;

    public static InteriorCarDoorTypeFragment newInstance(){
        InteriorCarDoorTypeFragment fragment = new InteriorCarDoorTypeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_interior_car_door_type, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);
        chkSingle = (CheckBox) parent.findViewById(R.id.chkSingleSpeed);
        chkTwo = (CheckBox) parent.findViewById(R.id.chkTwoSpeed);

        parent.findViewById(R.id.btnSingleSpeed).setOnClickListener(this);
        parent.findViewById(R.id.btnTwoSpeed).setOnClickListener(this);
        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
    }

    private void updateUIs(){
        chkSingle.setChecked(false);
        chkTwo.setChecked(false);
        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_edit_title, MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_title, MADSurveyApp.getInstance().getInteriorCarNum()+1, bankData.getNumOfInteriorCar(),MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }
        InteriorCarDoorData interiorCarDoorData = MADSurveyApp.getInstance().getInteriorCarDoorData();
        int doorType = interiorCarDoorData.getCarDoorType();
        //single speed door
        if(doorType == 2)
            chkSingle.setChecked(true);
        else if(doorType == 1)
            chkTwo.setChecked(true);


    }

    private void goToNext(){
        if (!isLastFocused()) return;

        MADSurveyApp.getInstance().getInteriorCarDoorData().setCarDoorType(BaseActivity.TEMP_DOOR_TYPE);
        interiorCarDoorDataHandler.update(MADSurveyApp.getInstance().getInteriorCarDoorData());
        if(BaseActivity.TEMP_OPENING == 2)
            ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_DOOR_OPENING_DIRECTION, "interior_car_opening_direction");
        else {
            if (MADSurveyApp.getInstance().getInteriorCarDoorData().getWallType().equals(GlobalConstant.WALL_TYPE_5_PIECE)){
                ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_LTRANSOM, "interior_car_l_transom");
            }else{
                if (BaseActivity.TEMP_DOOR_TYPE == 1) {
                    ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_TRANSOM_2S, "interior_car_transom_2s");
                }else if (BaseActivity.TEMP_DOOR_TYPE == 2){
                    ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_TRANSOM_1S, "interior_car_transom_1s");
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.btnSingleSpeed:
                BaseActivity.TEMP_DOOR_TYPE = 2;
                goToNext();
                break;
            case R.id.btnTwoSpeed:
                BaseActivity.TEMP_DOOR_TYPE = 1;
                goToNext();
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
