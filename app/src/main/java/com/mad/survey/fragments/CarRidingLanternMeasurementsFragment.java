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
import com.mad.survey.models.CarData;
import com.mad.survey.utils.ConversionUtils;
import com.mad.survey.utils.Utils;

public class CarRidingLanternMeasurementsFragment extends BaseFragment implements View.OnClickListener, OnFragmentResumedListener {

    private TextView txtCarNumber;
    private EditText edtQuantityPerCar, edtCoverWidth, edtCoverHeight, edtCoverDepth;
    private EditText edtCoverScrewCenterWidth, edtCoverScrewCenterHeight;

    public static CarRidingLanternMeasurementsFragment newInstance(){
        CarRidingLanternMeasurementsFragment fragment = new CarRidingLanternMeasurementsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_car_riding_lantern_measurements, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);
        edtQuantityPerCar = (EditText) parent.findViewById(R.id.edtQuantityPerCar);
        edtCoverWidth = (EditText) parent.findViewById(R.id.edtCoverWidth);
        edtCoverHeight = (EditText) parent.findViewById(R.id.edtCoverHeight);
        edtCoverScrewCenterWidth = (EditText) parent.findViewById(R.id.edtCoverScrewCenterWidth);
        edtCoverScrewCenterHeight = (EditText) parent.findViewById(R.id.edtCoverScrewCenterHeight);
        edtCoverDepth = (EditText) parent.findViewById(R.id.edtCoverDepth);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
        parent.findViewById(R.id.btnHelp).setOnClickListener(this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtQuantityPerCar.requestFocus();
                Utils.showKeyboard(getActivity(), true, edtQuantityPerCar);
            }
        });

        setHeaderScrollConfiguration(parent, getString(R.string.cops), getString(R.string.sub_title_car_riding_lantern_measurements), true, true);
    }

    private void updateUIs(){
        edtQuantityPerCar.setText("");
        edtCoverDepth.setText("");
        edtCoverWidth.setText("");
        edtCoverHeight.setText("");
        edtCoverScrewCenterHeight.setText("");
        edtCoverScrewCenterWidth.setText("");
        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_edit_title, MADSurveyApp.getInstance().getCarData().getCarNumber()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_title, MADSurveyApp.getInstance().getCarNum()+1, bankData.getNumOfCar(),MADSurveyApp.getInstance().getCarData().getCarNumber()));
        }

        CarData carData = MADSurveyApp.getInstance().getCarData();
        if(carData.getNumberPerCabCDI()>0)
            edtQuantityPerCar.setText(carData.getNumberPerCabCDI()+"");
        if(carData.getDepthCDI()>=0)
            edtCoverDepth.setText(carData.getDepthCDI()+"");
        if(carData.getCoverWidthCDI()>=0)
            edtCoverWidth.setText(carData.getCoverWidthCDI()+"");
        if(carData.getCoverHeightCDI()>=0)
            edtCoverHeight.setText(carData.getCoverHeightCDI()+"");
        if(carData.getCoverScrewCenterWidthCDI()>=0)
            edtCoverScrewCenterWidth.setText(carData.getCoverScrewCenterWidthCDI()+"");
        if(carData.getCoverScrewCenterHeightCDI()>=0)
            edtCoverScrewCenterHeight.setText(carData.getCoverScrewCenterHeightCDI()+"");
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        int numberPerCar = ConversionUtils.getIntegerFromEditText(edtQuantityPerCar);
        double coverDepth = ConversionUtils.getDoubleFromEditText(edtCoverDepth);
        double coverWidth = ConversionUtils.getDoubleFromEditText(edtCoverWidth);
        double coverHeight = ConversionUtils.getDoubleFromEditText(edtCoverHeight);
        double coverScrewCenterWidth = ConversionUtils.getDoubleFromEditText(edtCoverScrewCenterWidth);
        double coverScrewCenterHeight = ConversionUtils.getDoubleFromEditText(edtCoverScrewCenterHeight);

        if(numberPerCar<=0){
            edtQuantityPerCar.requestFocus();
            showToast(getString(R.string.valid_msg_input_quantity_per_car), Toast.LENGTH_SHORT);
            return;
        }
        if(coverWidth<0){
            edtCoverWidth.requestFocus();
            showToast(getString(R.string.valid_msg_input_cover_width), Toast.LENGTH_SHORT);
            return;
        }
        if(coverHeight<0){
            edtCoverHeight.requestFocus();
            showToast(getString(R.string.valid_msg_input_cover_height), Toast.LENGTH_SHORT);
            return;
        }
        if(coverDepth<0){
            edtCoverDepth.requestFocus();
            showToast(getString(R.string.valid_msg_input_cover_depth), Toast.LENGTH_SHORT);
            return;
        }

        if(coverScrewCenterWidth<0){
            edtCoverScrewCenterWidth.requestFocus();
            showToast(getString(R.string.valid_msg_input_screw_center_width), Toast.LENGTH_SHORT);
            return;
        }
        if(coverScrewCenterHeight<0){
            edtCoverScrewCenterHeight.requestFocus();
            showToast(getString(R.string.valid_msg_input_screw_center_height), Toast.LENGTH_SHORT);
            return;
        }
        CarData carData = MADSurveyApp.getInstance().getCarData();
        carData.setNumberPerCabCDI(numberPerCar);
        carData.setCoverWidthCDI(coverWidth);
        carData.setCoverHeightCDI(coverHeight);
        carData.setDepthCDI(coverDepth);
        carData.setCoverScrewCenterHeightCDI(coverScrewCenterHeight);
        carData.setCoverScrewCenterWidthCDI(coverScrewCenterWidth);
        MADSurveyApp.getInstance().setCarData(carData);
        carDataHandler.update(carData);
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_CAR_RIDING_LANTERN_NOTES, "car_cop_notes");
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
                showHelpDialog(getActivity(), getString(R.string.help_title_car_riding_lantern_measurements), R.drawable.img_help_34_car_riding_lantern_measurements_help, GlobalConstant.HELP_PHOTO_SIZE_RATE);
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
