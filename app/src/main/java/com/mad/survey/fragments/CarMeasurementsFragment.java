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
import com.mad.survey.models.BankData;
import com.mad.survey.models.CarData;
import com.mad.survey.utils.ConversionUtils;
import com.mad.survey.utils.Utils;

public class CarMeasurementsFragment extends BaseFragment implements View.OnClickListener {

    private TextView txtCarNumber;
    private EditText edtHeightFromFloor, edtDistanceFromWall, edtDistanceFromReturn;

    public static CarMeasurementsFragment newInstance(){
        CarMeasurementsFragment fragment = new CarMeasurementsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_car_handrail_measurements, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);
        edtHeightFromFloor = (EditText) parent.findViewById(R.id.edtHeightFromFloor);
        edtDistanceFromWall = (EditText) parent.findViewById(R.id.edtDistanceFromWall);
        edtDistanceFromReturn = (EditText) parent.findViewById(R.id.edtDistanceFromReturn);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
        parent.findViewById(R.id.btnHelp).setOnClickListener(this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtHeightFromFloor.requestFocus();
                Utils.showKeyboard(getActivity(), true, edtHeightFromFloor);
            }
        });

        setHeaderScrollConfiguration(parent, getString(R.string.cops), getString(R.string.sub_title_handrail_measurements), true, true);
    }

    private void updateUIs(){
        edtHeightFromFloor.setText("");
        edtDistanceFromReturn.setText("");
        edtDistanceFromWall.setText("");
        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_edit_title, MADSurveyApp.getInstance().getCarData().getCarNumber()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_title, MADSurveyApp.getInstance().getCarNum()+1, bankData.getNumOfCar(),MADSurveyApp.getInstance().getCarData().getCarNumber()));
        }
        CarData carData = MADSurveyApp.getInstance().getCarData();
        if(carData.getHandRailHeightFromFloor() >= 0)
            edtHeightFromFloor.setText(carData.getHandRailHeightFromFloor() + "");
        if(carData.getHandRailDistanceFromWall() >= 0)
            edtDistanceFromWall.setText(carData.getHandRailDistanceFromWall() + "");
        if(carData.getHandRailDistanceFromReturn() >= 0)
            edtDistanceFromReturn.setText(carData.getHandRailDistanceFromReturn() + "");
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        double heightFromFloor,distanceFromWall,distanceFromReturn;
        heightFromFloor = ConversionUtils.getDoubleFromEditText(edtHeightFromFloor);
        distanceFromWall = ConversionUtils.getDoubleFromEditText(edtDistanceFromWall);
        distanceFromReturn = ConversionUtils.getDoubleFromEditText(edtDistanceFromReturn);
        if(heightFromFloor < 0 ){
            edtHeightFromFloor.requestFocus();
            showToast(getString(R.string.valid_msg_input_car_height_from_floor), Toast.LENGTH_SHORT);
            return;
        }
        if(distanceFromWall < 0){
            edtDistanceFromWall.requestFocus();
            showToast(getString(R.string.valid_msg_input_car_distance_from_wall), Toast.LENGTH_SHORT);
            return;
        }
        if(distanceFromReturn < 0){
            edtDistanceFromReturn.requestFocus();
            showToast(getString(R.string.valid_msg_input_car_distance_from_return), Toast.LENGTH_SHORT);
            return;
        }

        MADSurveyApp.getInstance().getCarData().setHandRailHeightFromFloor(heightFromFloor);
        MADSurveyApp.getInstance().getCarData().setHandRailDistanceFromReturn(distanceFromReturn);
        MADSurveyApp.getInstance().getCarData().setHandRailDistanceFromWall(distanceFromWall);
        carDataHandler.update(MADSurveyApp.getInstance().getCarData());
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_CAR_NOTES, "car_notes");
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
                //showHelpDialog(getActivity(), getString(R.string.help_title_car_push_buttons), R.drawable.lobby_measurements_help, GlobalConstant.HELP_PHOTO_SIZE_RATE);
                break;
        }
    }
}
