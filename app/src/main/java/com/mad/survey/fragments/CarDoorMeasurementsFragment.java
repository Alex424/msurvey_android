package com.mad.survey.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mad.survey.MADSurveyApp;
import com.mad.survey.R;
import com.mad.survey.activities.BaseActivity;
import com.mad.survey.globals.GlobalConstant;
import com.mad.survey.models.BankData;
import com.mad.survey.models.CarData;
import com.mad.survey.utils.ConversionUtils;
import com.mad.survey.utils.Utils;

public class CarDoorMeasurementsFragment extends BaseFragment implements View.OnClickListener {

    private TextView txtCarNumber;
    private EditText edtFrontOpeningHeight, edtBackOpeningHeight;
    private EditText edtFrontReturnJambWidth, edtBackReturnJambWidth;
    private EditText edtFrontStrikeJambWidth, edtBackStrikeJambWidth;
    private TextView btnCopyFromFrontDoor,txtBackDoor;
    private  boolean isThereBackDoor = false;

    public static CarDoorMeasurementsFragment newInstance(){
        CarDoorMeasurementsFragment fragment = new CarDoorMeasurementsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_car_door_measurements, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);
        edtFrontOpeningHeight = (EditText) parent.findViewById(R.id.edtFrontOpeningHeight);
        edtBackOpeningHeight = (EditText) parent.findViewById(R.id.edtBackOpeningHeight);
        edtFrontReturnJambWidth = (EditText) parent.findViewById(R.id.edtFrontReturnJambWidth);
        edtBackReturnJambWidth = (EditText) parent.findViewById(R.id.edtBackReturnJambWidth);
        edtFrontStrikeJambWidth = (EditText) parent.findViewById(R.id.edtFrontStrikeJambWidth);
        edtBackStrikeJambWidth = (EditText) parent.findViewById(R.id.edtBackStrikeJambWidth);
        btnCopyFromFrontDoor = (TextView) parent.findViewById(R.id.btnCopyFromFront);
        txtBackDoor = (TextView) parent.findViewById(R.id.txtBackDoor);

        parent.findViewById(R.id.btnCopyFromFront).setOnClickListener(this);
        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
        parent.findViewById(R.id.btnHelp).setOnClickListener(this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtFrontOpeningHeight.requestFocus();
                Utils.showKeyboard(getActivity(), true, edtFrontOpeningHeight);
            }
        });

        setHeaderScrollConfiguration(parent, getString(R.string.cops), getString(R.string.sub_title_car_door_measurements), true, true);
    }

    private void updateUIs(){
        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_edit_title, MADSurveyApp.getInstance().getCarData().getCarNumber()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_title, MADSurveyApp.getInstance().getCarNum()+1, bankData.getNumOfCar(),MADSurveyApp.getInstance().getCarData().getCarNumber()));
        }
        CarData carData = MADSurveyApp.getInstance().getCarData();

        //There isn't a backDoor in this CAR
        if(carData.getIsThereRearDoor() == 2){
            isThereBackDoor = false;

            edtBackOpeningHeight.setVisibility(View.INVISIBLE);
            edtBackReturnJambWidth.setVisibility(View.INVISIBLE);
            edtBackStrikeJambWidth.setVisibility(View.INVISIBLE);
            btnCopyFromFrontDoor.setVisibility(View.INVISIBLE);
            txtBackDoor.setVisibility(View.INVISIBLE);

            edtFrontStrikeJambWidth.setNextFocusDownId(0);
            edtFrontStrikeJambWidth.setImeOptions(EditorInfo.IME_ACTION_DONE);

        }else if(carData.getIsThereRearDoor() == 1){
            isThereBackDoor = true;
            edtBackOpeningHeight.setVisibility(View.VISIBLE);
            edtBackReturnJambWidth.setVisibility(View.VISIBLE);
            edtBackStrikeJambWidth.setVisibility(View.VISIBLE);
            btnCopyFromFrontDoor.setVisibility(View.VISIBLE);
            txtBackDoor.setVisibility(View.VISIBLE);
            if(carData.getRearDoorSlideJambWidth()>=0)
                edtBackReturnJambWidth.setText(carData.getRearDoorSlideJambWidth() + "");
            if(carData.getRearDoorStrikeJambWidth()>=0)
                edtBackStrikeJambWidth.setText(carData.getRearDoorStrikeJambWidth() + "");
            if(carData.getRearDoorOpeningHeight()>=0)
                edtBackOpeningHeight.setText(carData.getRearDoorOpeningHeight()+"");


            edtFrontStrikeJambWidth.setNextFocusDownId(R.id.edtBackOpeningHeight);
            edtBackStrikeJambWidth.setImeOptions(EditorInfo.IME_ACTION_DONE);
        }
        if(carData.getFrontDoorOpeningHeight()>=0)
            edtFrontOpeningHeight.setText(carData.getFrontDoorOpeningHeight() + "");
        if(carData.getFrontDoorSlideJambWidth()>=0)
            edtFrontReturnJambWidth.setText(carData.getFrontDoorSlideJambWidth() + "");
        if(carData.getFrontDoorStrikeJambWidth()>=0)
            edtFrontStrikeJambWidth.setText(carData.getFrontDoorStrikeJambWidth() + "");
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        double frontDoorOpeningHeight,frontDoorReturnJambWidth,frontDoorStrikeJambWidth;
        double backDoorOpeningHeight,backDoorReturnJambWidth,backDoorStrikeJambWidth;
        frontDoorOpeningHeight = ConversionUtils.getDoubleFromEditText(edtFrontOpeningHeight);
        frontDoorReturnJambWidth = ConversionUtils.getDoubleFromEditText(edtFrontReturnJambWidth);
        frontDoorStrikeJambWidth = ConversionUtils.getDoubleFromEditText(edtFrontStrikeJambWidth);
        if(frontDoorOpeningHeight < 0){
            edtFrontOpeningHeight.requestFocus();
            showToast(getString(R.string.valid_msg_input_car_front_door_opening_height), Toast.LENGTH_SHORT);
            return;
        }
        if(frontDoorReturnJambWidth < 0){
            edtFrontReturnJambWidth.requestFocus();
            showToast(getString(R.string.valid_msg_input_car_front_door_return_jamb_width), Toast.LENGTH_SHORT);
            return;
        }
        if(frontDoorStrikeJambWidth < 0){
            edtFrontStrikeJambWidth.requestFocus();
            showToast(getString(R.string.valid_msg_input_car_front_door_strike_jamb_width), Toast.LENGTH_SHORT);
            return;
        }
        if(isThereBackDoor) {
            backDoorOpeningHeight = ConversionUtils.getDoubleFromEditText(edtBackOpeningHeight);
            backDoorReturnJambWidth = ConversionUtils.getDoubleFromEditText(edtBackReturnJambWidth);
            backDoorStrikeJambWidth = ConversionUtils.getDoubleFromEditText(edtBackStrikeJambWidth);

            if(backDoorOpeningHeight < 0){
                edtBackOpeningHeight.requestFocus();
                showToast(getString(R.string.valid_msg_input_car_back_door_opening_height), Toast.LENGTH_SHORT);
                return;
            }
            if(backDoorReturnJambWidth < 0){
                edtBackReturnJambWidth.requestFocus();
                showToast(getString(R.string.valid_msg_input_car_back_door_return_jamb_width), Toast.LENGTH_SHORT);
                return;
            }
            if(backDoorStrikeJambWidth < 0){
                edtBackStrikeJambWidth.requestFocus();
                showToast(getString(R.string.valid_msg_input_car_back_door_strike_jamb_width), Toast.LENGTH_SHORT);
                return;
            }
            MADSurveyApp.getInstance().getCarData().setRearDoorOpeningHeight(backDoorOpeningHeight);
            MADSurveyApp.getInstance().getCarData().setRearDoorSlideJambWidth(backDoorReturnJambWidth);
            MADSurveyApp.getInstance().getCarData().setRearDoorStrikeJambWidth(backDoorStrikeJambWidth);
        }
        MADSurveyApp.getInstance().getCarData().setFrontDoorOpeningHeight(frontDoorOpeningHeight);
        MADSurveyApp.getInstance().getCarData().setFrontDoorSlideJambWidth(frontDoorReturnJambWidth);
        MADSurveyApp.getInstance().getCarData().setFrontDoorStrikeJambWidth(frontDoorStrikeJambWidth);
        carDataHandler.update(MADSurveyApp.getInstance().getCarData());
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_CAR_HANDRAIL, "car_handrail");
    }

    public void copyFromFrontMeasurements(){
        if(isThereBackDoor){
            double frontDoorOpeningHeight,frontDoorReturnJambWidth,frontDoorStrikeJambWidth;
            frontDoorOpeningHeight = ConversionUtils.getDoubleFromEditText(edtFrontOpeningHeight);
            frontDoorReturnJambWidth = ConversionUtils.getDoubleFromEditText(edtFrontReturnJambWidth);
            frontDoorStrikeJambWidth = ConversionUtils.getDoubleFromEditText(edtFrontStrikeJambWidth);
            /*
            if(frontDoorOpeningHeight <= 0){
                return;
            }
            if(frontDoorReturnJambWidth <= 0){
                return;
            }
            if(frontDoorStrikeJambWidth <= 0){
                return;
            }
            */
            edtBackOpeningHeight.setText(frontDoorOpeningHeight + "");
            edtBackReturnJambWidth.setText(frontDoorReturnJambWidth + "");
            edtBackStrikeJambWidth.setText(frontDoorStrikeJambWidth + "");
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
            case R.id.btnCopyFromFront:
                copyFromFrontMeasurements();
                break;
            case R.id.btnHelp:
                showHelpDialog(getActivity(), getString(R.string.help_title_car_door_measurements), R.drawable.img_help_28_car_door_measurements_help, GlobalConstant.HELP_PHOTO_SIZE_RATE);
                break;
        }
    }
}
