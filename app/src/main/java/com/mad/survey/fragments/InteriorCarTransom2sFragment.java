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

public class InteriorCarTransom2sFragment extends BaseFragment implements View.OnClickListener, OnFragmentResumedListener {

    private TextView txtCarNumber;
    private EditText edtHeight, edtWidth, edtLeft, edtCenterLeft, edtCenterRight, edtRight;

    public static InteriorCarTransom2sFragment newInstance(){
        InteriorCarTransom2sFragment fragment = new InteriorCarTransom2sFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_interior_car_transom_2s, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);
        edtHeight = (EditText) parent.findViewById(R.id.edtHeight);
        edtWidth = (EditText) parent.findViewById(R.id.edtWidth);
        edtLeft = (EditText) parent.findViewById(R.id.edtLeft);
        edtCenterLeft = (EditText) parent.findViewById(R.id.edtCenterLeft);
        edtCenterRight = (EditText) parent.findViewById(R.id.edtCenterRight);
        edtRight = (EditText) parent.findViewById(R.id.edtRight);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
        parent.findViewById(R.id.btnHelp).setOnClickListener(this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtHeight.requestFocus();
                Utils.showKeyboard(getActivity(), true, edtHeight);
            }
        });

        setHeaderScrollConfiguration(parent, getString(R.string.sub_title_cab_interior), getString(R.string.sub_title_car_transom_measurements), true, true);
        setBackdoorTitle(parent);
    }

    private void updateUIs(){
        edtHeight.setText("");
        edtWidth.setText("");
        edtLeft.setText("");
        edtCenterLeft.setText("");
        edtCenterRight.setText("");
        edtRight.setText("");

        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_edit_title, MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_title, MADSurveyApp.getInstance().getInteriorCarNum()+1, bankData.getNumOfInteriorCar(),MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }
        InteriorCarDoorData interiorCarDoorData = MADSurveyApp.getInstance().getInteriorCarDoorData();

        double height  = interiorCarDoorData.getTransomMeasurementsHeight();
        double width = interiorCarDoorData.getTransomMeasurementsWidth();
        double left = interiorCarDoorData.getTransomMeasurementsLeft();
        double centerLeft = interiorCarDoorData.getTransomMeasurementsCenterLeft();
        double centerRight = interiorCarDoorData.getTransomMeasurementsCenterRight();
        double right = interiorCarDoorData.getTransomMeasurementsRight();

        if(height>=0)
            edtHeight.setText(height+"");
        if(width>=0)
            edtWidth.setText(width+"");
        if(left>=0)
            edtLeft.setText(left+"");
        if(centerLeft>=0)
            edtCenterLeft.setText(centerLeft+"");
        if(centerRight>=0)
            edtCenterRight.setText(centerRight+"");
        if(right>=0)
            edtRight.setText(right+"");
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        double height = ConversionUtils.getDoubleFromEditText(edtHeight);
        double width = ConversionUtils.getDoubleFromEditText(edtWidth);
        double left = ConversionUtils.getDoubleFromEditText(edtLeft);
        double centerLeft = ConversionUtils.getDoubleFromEditText(edtCenterLeft);
        double centerRight = ConversionUtils.getDoubleFromEditText(edtCenterRight);
        double right = ConversionUtils.getDoubleFromEditText(edtRight);
        if(height<0){
            edtHeight.requestFocus();
            showToast(getString(R.string.valid_msg_input_height), Toast.LENGTH_SHORT);
            return;
        }
        if(width<0){
            edtWidth.requestFocus();
            showToast(getString(R.string.valid_msg_input_width), Toast.LENGTH_SHORT);
            return;
        }
        if(left<0){
            edtLeft.requestFocus();
            showToast(getString(R.string.valid_msg_input_left), Toast.LENGTH_SHORT);
            return;
        }
        if(centerLeft<0){
            edtCenterLeft.requestFocus();
            showToast(getString(R.string.valid_msg_input_center_left), Toast.LENGTH_SHORT);
            return;
        }
        if(centerRight<0){
            edtCenterRight.requestFocus();
            showToast(getString(R.string.valid_msg_input_center_right), Toast.LENGTH_SHORT);
            return;
        }
        if(right<0){
            edtRight.requestFocus();
            showToast(getString(R.string.valid_msg_input_right), Toast.LENGTH_SHORT);
            return;
        }

        MADSurveyApp.getInstance().getInteriorCarDoorData().setTransomMeasurementsHeight(height);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setTransomMeasurementsWidth(width);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setTransomMeasurementsLeft(left);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setTransomMeasurementsCenterLeft(centerLeft);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setTransomMeasurementsCenterRight(centerRight);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setTransomMeasurementsRight(right);

        interiorCarDoorDataHandler.update(MADSurveyApp.getInstance().getInteriorCarDoorData());
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_TRANSOM_PROFILE_2S, "interior_car_transom_profile_2s");
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
                showHelpDialog(getActivity(), getString(R.string.help_title_car_interior_measurements), R.drawable.img_help_48_interior_car_transom_2s_help, GlobalConstant.HELP_PHOTO_SIZE_RATE);
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
