package com.mad.survey.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mad.survey.MADSurveyApp;
import com.mad.survey.R;
import com.mad.survey.activities.BaseActivity;
import com.mad.survey.globals.GlobalConstant;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.BankData;
import com.mad.survey.models.InteriorCarDoorData;
import com.mad.survey.utils.ConversionUtils;
import com.mad.survey.utils.Utils;

public class InteriorCarCenterReturnMeasurementsAFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtCarNumber;
    private EditText edtLA, edtLB, edtLC, edtLD, edtLE;
    private EditText edtRA, edtRB, edtRC, edtRD, edtRE;
    private EditText edtHeight;

    public static InteriorCarCenterReturnMeasurementsAFragment newInstance(){
        InteriorCarCenterReturnMeasurementsAFragment fragment = new InteriorCarCenterReturnMeasurementsAFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_interior_car_center_return_measurements_a, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);
        edtLA = (EditText) parent.findViewById(R.id.edtLA);
        edtLB = (EditText) parent.findViewById(R.id.edtLB);
        edtLC = (EditText) parent.findViewById(R.id.edtLC);
        edtLD = (EditText) parent.findViewById(R.id.edtLD);
        edtLE = (EditText) parent.findViewById(R.id.edtLE);
        edtRA = (EditText) parent.findViewById(R.id.edtRA);
        edtRB = (EditText) parent.findViewById(R.id.edtRB);
        edtRC = (EditText) parent.findViewById(R.id.edtRC);
        edtRD = (EditText) parent.findViewById(R.id.edtRD);
        edtRE = (EditText) parent.findViewById(R.id.edtRE);
        edtHeight = (EditText) parent.findViewById(R.id.edtHeight);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
        parent.findViewById(R.id.btnHelp).setOnClickListener(this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtLA.requestFocus();
                Utils.showKeyboard(getActivity(), true, edtLA);
            }
        });

        setHeaderScrollConfiguration(parent, getString(R.string.sub_title_cab_interior), getString(R.string.sub_title_car_center_return_measurements), true, true);
        setBackdoorTitle(parent);
    }

    private void updateUIs(){
        edtLA.setText("");
        edtLB.setText("");
        edtLC.setText("");
        edtLD.setText("");
        edtLE.setText("");

        edtRA.setText("");
        edtRB.setText("");
        edtRC.setText("");
        edtRD.setText("");
        edtRE.setText("");
        edtHeight.setText("");

        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_edit_title, MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_title, MADSurveyApp.getInstance().getInteriorCarNum()+1, bankData.getNumOfInteriorCar(),MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }
        InteriorCarDoorData interiorCarDoorData = MADSurveyApp.getInstance().getInteriorCarDoorData();
        double LA = interiorCarDoorData.getLeftSideA();
        double LB = interiorCarDoorData.getLeftSideB();
        double LC = interiorCarDoorData.getLeftSideC();
        double LD = interiorCarDoorData.getLeftSideD();
        double LE = interiorCarDoorData.getLeftSideE();

        double RA = interiorCarDoorData.getRightSideA();
        double RB = interiorCarDoorData.getRightSideB();
        double RC = interiorCarDoorData.getRightSideC();
        double RD = interiorCarDoorData.getRightSideD();
        double RE = interiorCarDoorData.getRightSideE();

        double height = interiorCarDoorData.getFrontReturnMeasurementsHeight();

        if(LA>=0){
            edtLA.setText(LA+"");
        }
        if(LB>=0){
            edtLB.setText(LB+"");
        }
        if(LC>=0){
            edtLC.setText(LC+"");
        }
        if(LD>=0){
            edtLD.setText(LD+"");
        }
        if(LE>=0){
            edtLE.setText(LE+"");
        }

        if(RA>=0){
            edtRA.setText(RA+"");
        }
        if(RB>=0){
            edtRB.setText(RB+"");
        }
        if(RC>=0){
            edtRC.setText(RC+"");
        }
        if(RD>=0){
            edtRD.setText(RD+"");
        }
        if(RE>=0){
            edtRE.setText(RE+"");
        }

        if(height>=0){
            edtHeight.setText(height+"");
        }
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        double LA = ConversionUtils.getDoubleFromEditText(edtLA);
        double LB = ConversionUtils.getDoubleFromEditText(edtLB);
        double LC = ConversionUtils.getDoubleFromEditText(edtLC);
        double LD = ConversionUtils.getDoubleFromEditText(edtLD);
        double LE = ConversionUtils.getDoubleFromEditText(edtLE);

        double RA = ConversionUtils.getDoubleFromEditText(edtRA);
        double RB = ConversionUtils.getDoubleFromEditText(edtRB);
        double RC = ConversionUtils.getDoubleFromEditText(edtRC);
        double RD = ConversionUtils.getDoubleFromEditText(edtRD);
        double RE = ConversionUtils.getDoubleFromEditText(edtRE);

        double height = ConversionUtils.getDoubleFromEditText(edtHeight);

        if(LA<0){
            edtLA.requestFocus();
            showToast(getString(R.string.valid_msg_input_LA), Toast.LENGTH_SHORT);
            return;
        }
        if(LB<0){
            edtLB.requestFocus();
            showToast(getString(R.string.valid_msg_input_LB), Toast.LENGTH_SHORT);
            return;
        }
        if(LC<0){
            edtLC.requestFocus();
            showToast(getString(R.string.valid_msg_input_LC), Toast.LENGTH_SHORT);
            return;
        }
        if(LD<0){
            edtLD.requestFocus();
            showToast(getString(R.string.valid_msg_input_LD), Toast.LENGTH_SHORT);
            return;
        }
        if(LE<0){
            edtLE.requestFocus();
            showToast(getString(R.string.valid_msg_input_LE), Toast.LENGTH_SHORT);
            return;
        }

        if(RA<0){
            edtRA.requestFocus();
            showToast(getString(R.string.valid_msg_input_RA), Toast.LENGTH_SHORT);
            return;
        }
        if(RB<0){
            edtRB.requestFocus();
            showToast(getString(R.string.valid_msg_input_RB), Toast.LENGTH_SHORT);
            return;
        }
        if(RC<0){
            edtRC.requestFocus();
            showToast(getString(R.string.valid_msg_input_RC), Toast.LENGTH_SHORT);
            return;
        }
        if(RD<0){
            edtRD.requestFocus();
            showToast(getString(R.string.valid_msg_input_RD), Toast.LENGTH_SHORT);
            return;
        }
        if(RE<0){
            edtRE.requestFocus();
            showToast(getString(R.string.valid_msg_input_RE), Toast.LENGTH_SHORT);
            return;
        }
        if(height<0){
            edtHeight.requestFocus();
            showToast(getString(R.string.valid_msg_input_height), Toast.LENGTH_SHORT);
            return;
        }

        MADSurveyApp.getInstance().getInteriorCarDoorData().setLeftSideA(LA);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setLeftSideB(LB);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setLeftSideC(LC);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setLeftSideD(LD);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setLeftSideE(LE);

        MADSurveyApp.getInstance().getInteriorCarDoorData().setRightSideA(RA);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setRightSideB(RB);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setRightSideC(RC);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setRightSideD(RD);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setRightSideE(RE);

        MADSurveyApp.getInstance().getInteriorCarDoorData().setFrontReturnMeasurementsHeight(height);

        interiorCarDoorDataHandler.update(MADSurveyApp.getInstance().getInteriorCarDoorData());
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_DOOR_TYPE, "interior_car_door_type");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.btnNext:
                goToNext();
                break;
            case R.id.btnHelp:
                showHelpDialog(getActivity(), getString(R.string.help_title_car_interior_measurements), R.drawable.img_help_46_interior_car_center_return_measurements_a_help, GlobalConstant.HELP_PHOTO_SIZE_RATE);
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
