package com.mad.survey.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mad.survey.MADSurveyApp;
import com.mad.survey.R;
import com.mad.survey.activities.BaseActivity;
import com.mad.survey.dialogs.MadCommonAlertDialog;
import com.mad.survey.globals.GlobalConstant;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.BankData;
import com.mad.survey.models.InteriorCarData;
import com.mad.survey.models.InteriorCarDoorData;
import com.mad.survey.utils.Utils;

public class InteriorCarOpeningFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private View parent;
    private TextView txtCarNumber;
    private TextView txtSubTitle;
    private CheckBox chkYes,chkNo;
    private MadCommonAlertDialog dlg;

    public static InteriorCarOpeningFragment newInstance(){
        InteriorCarOpeningFragment fragment = new InteriorCarOpeningFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_interior_car_opening, container, false);

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
        int doorId = -1;
        if(BaseActivity.TEMP_DOOR_STYLE == 1)
            doorId = interiorCarData.getFrontDoorId();
        else
            doorId = interiorCarData.getBackDoorId();

        InteriorCarDoorData interiorCarDoorData = interiorCarDoorDataHandler.get(doorId);
        MADSurveyApp.getInstance().setInteriorCarDoorData(interiorCarDoorData);
        if(interiorCarDoorData!=null) {
            int yes = interiorCarDoorData.getCenterOpening();
            if (yes == 1) {
                chkYes.setChecked(true);
            } else if (yes == 2) {
                chkNo.setChecked(true);
            }
        }

    }
    private void setData(){
        /*InteriorCarData interiorCarData = MADSurveyApp.getInstance().getInteriorCarData();
        InteriorCarDoorData interiorCarDoorData = interiorCarDoorDataHandler.insertNewInteriorCarDoor(MADSurveyApp.getInstance().getProjectData().getId(),
                interiorCarData.getId(),
                BaseActivity.TEMP_DOOR_STYLE,
                BaseActivity.TEMP_OPENING);
        if(interiorCarDoorData != null){
            MADSurveyApp.getInstance().setInteriorCarDoorData(interiorCarDoorData);
                //front door
            if(BaseActivity.TEMP_DOOR_STYLE == 1)
                interiorCarData.setFrontDoorId(interiorCarDoorData.getId());
                //Back door
            else if(BaseActivity.TEMP_DOOR_STYLE == 2)
                interiorCarData.setBackDoorId(interiorCarDoorData.getId());
            MADSurveyApp.getInstance().setInteriorCarData(interiorCarData);
            interiorCarDataHandler.update(interiorCarData);
        }

        else{
            InteriorCarDoorData interiorCarDoorData1 = MADSurveyApp.getInstance().getInteriorCarDoorData();
            interiorCarDoorData1.setCenterOpening(BaseActivity.TEMP_OPENING);
            MADSurveyApp.getInstance().setInteriorCarDoorData(interiorCarDoorData1);
            interiorCarDoorDataHandler.update(interiorCarDoorData1);
        }*/


        MADSurveyApp.getInstance().getInteriorCarDoorData().setCenterOpening(BaseActivity.TEMP_OPENING);
        interiorCarDoorDataHandler.update(MADSurveyApp.getInstance().getInteriorCarDoorData());
    }
    private void goToCenterMeasurements(){
        setData();
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_CENTER_MEASUREMENTS, "interior_car_center_measurements");
    }

    private void goToSingleSideMeasurements(){
        setData();
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_SINGLE_SIDE_MEASUREMENTS, "interior_car_single_side_measurements");
    }


    // This is for showing popup for BACK DOOR for EDIT MODE only
    // Added by Alex 2017/09/18
    private void openInstructionDlg(){
        if (BaseActivity.TEMP_DOOR_STYLE_EDIT == 100) {
            dlg = new MadCommonAlertDialog(getActivity(), getString(R.string.instruction), "", "", getString(R.string.instruction_car_interior_backdoor), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view.getId() == R.id.btnOK) {
                        dlg.dismiss();
                    }
                }
            });
            dlg.show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.btnYes:
                BaseActivity.TEMP_OPENING = 1;
                goToCenterMeasurements();
                break;
            case R.id.btnNo:
                BaseActivity.TEMP_OPENING = 2;
                goToSingleSideMeasurements();
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
        setBackdoorTitle(parent);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                openInstructionDlg();
            }
        }, 200);

    }
}
