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
import com.mad.survey.models.HallEntranceData;
import com.mad.survey.utils.ConversionUtils;
import com.mad.survey.utils.Utils;

import org.w3c.dom.Text;

public class HallEntranceFrontReturnMeasurementsFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtCarNumber;
    private TextView txtFloorIdentifier;
    private EditText edtLA, edtLB, edtLC, edtLD;
    private EditText edtRA, edtRB, edtRC, edtRD;
    private EditText edtHeight;

    public static HallEntranceFrontReturnMeasurementsFragment newInstance(){
        HallEntranceFrontReturnMeasurementsFragment fragment = new HallEntranceFrontReturnMeasurementsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_hall_entrance_front_return_measurements, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);
        txtFloorIdentifier = (TextView) parent.findViewById(R.id.txtFloorIdentifier);
        edtLA = (EditText) parent.findViewById(R.id.edtLA);
        edtLB = (EditText) parent.findViewById(R.id.edtLB);
        edtLC = (EditText) parent.findViewById(R.id.edtLC);
        edtLD = (EditText) parent.findViewById(R.id.edtLD);
        edtRA = (EditText) parent.findViewById(R.id.edtRA);
        edtRB = (EditText) parent.findViewById(R.id.edtRB);
        edtRC = (EditText) parent.findViewById(R.id.edtRC);
        edtRD = (EditText) parent.findViewById(R.id.edtRD);
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

        setHeaderScrollConfiguration(parent, getString(R.string.hall_entrance), getString(R.string.sub_title_hall_entrance_front_return_measurements), true, true);
    }

    private void updateUIs(){
        edtLA.setText("");
        edtLB.setText("");
        edtLC.setText("");
        edtLD.setText("");
        edtRA.setText("");
        edtRB.setText("");
        edtRC.setText("");
        edtRD.setText("");
        edtHeight.setText("");


        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtFloorIdentifier.setText(getString(R.string.floor_n_identifier_edit_title, MADSurveyApp.getInstance().getFloorDescriptor()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtFloorIdentifier.setText(getString(R.string.floor_n_identifier_title, MADSurveyApp.getInstance().getFloorNum() + 1, MADSurveyApp.getInstance().getProjectData().getNumFloors(), MADSurveyApp.getInstance().getFloorDescriptor()));
            txtCarNumber.setText(getString(R.string.car_n_title, MADSurveyApp.getInstance().getHallEntranceCarNum()+1, MADSurveyApp.getInstance().getBankData().getNumOfCar()));
        }
        HallEntranceData hallEntranceData = MADSurveyApp.getInstance().getHallEntranceData();

        double LA = hallEntranceData.getLeftSideA();
        double LB = hallEntranceData.getLeftSideB();
        double LC = hallEntranceData.getLeftSideC();
        double LD = hallEntranceData.getLeftSideD();

        double RA = hallEntranceData.getRightSideA();
        double RB = hallEntranceData.getRightSideB();
        double RC = hallEntranceData.getRightSideC();
        double RD = hallEntranceData.getRightSideD();
        double height = hallEntranceData.getHeight();

        if(LA>=0)
            edtLA.setText(LA+"");
        if(LB>=0)
            edtLB.setText(LB+"");
        if(LC>=0)
            edtLC.setText(LC+"");
        if(LD>=0)
            edtLD.setText(LD+"");

        if(RA>=0)
            edtRA.setText(RA+"");
        if(RB>=0)
            edtRB.setText(RB+"");
        if(RC>=0)
            edtRC.setText(RC+"");
        if(RD>=0)
            edtRD.setText(RD+"");
        if(height>=0)
            edtHeight.setText(height+"");

    }

    private void goToNext(){
        if (!isLastFocused()) return;

        double LA = ConversionUtils.getDoubleFromEditText(edtLA);
        double LB = ConversionUtils.getDoubleFromEditText(edtLB);
        double LC = ConversionUtils.getDoubleFromEditText(edtLC);
        double LD = ConversionUtils.getDoubleFromEditText(edtLD);

        double RA = ConversionUtils.getDoubleFromEditText(edtRA);
        double RB = ConversionUtils.getDoubleFromEditText(edtRB);
        double RC = ConversionUtils.getDoubleFromEditText(edtRC);
        double RD = ConversionUtils.getDoubleFromEditText(edtRD);

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
        if(height<0){
            edtHeight.requestFocus();
            showToast(getString(R.string.valid_msg_input_height), Toast.LENGTH_SHORT);
            return;
        }

        MADSurveyApp.getInstance().getHallEntranceData().setLeftSideA(LA);
        MADSurveyApp.getInstance().getHallEntranceData().setLeftSideB(LB);
        MADSurveyApp.getInstance().getHallEntranceData().setLeftSideC(LC);
        MADSurveyApp.getInstance().getHallEntranceData().setLeftSideD(LD);

        MADSurveyApp.getInstance().getHallEntranceData().setRightSideA(RA);
        MADSurveyApp.getInstance().getHallEntranceData().setRightSideB(RB);
        MADSurveyApp.getInstance().getHallEntranceData().setRightSideC(RC);
        MADSurveyApp.getInstance().getHallEntranceData().setRightSideD(RD);

        MADSurveyApp.getInstance().getHallEntranceData().setHeight(height);
        hallEntranceDataHandler.update(MADSurveyApp.getInstance().getHallEntranceData());

        if (BaseActivity.TEMP_DOOR_TYPE == 1){
            ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_HALL_ENTRANCE_TRANSOM_MEASUREMENTS_2S, "hall_entrance_transom_measurements_2s");
        }else if (BaseActivity.TEMP_DOOR_TYPE == 2){
            ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_HALL_ENTRANCE_TRANSOM_MEASUREMENTS_1S, "hall_entrance_transom_measurements_1s");
        }else if (BaseActivity.TEMP_DOOR_TYPE == 3){
            ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_HALL_ENTRANCE_TRANSOM_MEASUREMENTS_CENTER, "hall_entrance_transom_measurements_center");
        }
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
                showHelpDialog(getActivity(), getString(R.string.help_title_hall_entrance), R.drawable.img_help_54_hall_entrance_front_return_measurements_help, GlobalConstant.HELP_PHOTO_SIZE_RATE);
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
