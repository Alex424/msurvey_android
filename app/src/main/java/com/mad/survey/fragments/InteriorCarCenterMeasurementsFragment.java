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
import com.mad.survey.models.InteriorCarData;
import com.mad.survey.models.InteriorCarDoorData;
import com.mad.survey.utils.ConversionUtils;
import com.mad.survey.utils.Utils;

public class InteriorCarCenterMeasurementsFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtCarNumber;
    private EditText edtWidth;
    private EditText edtHeight;
    private EditText edtSideWallMainWidth;
    private EditText edtSideWallAuxWidth;
    private EditText edtDoorOpeningWidth;
    private EditText edtDoorOpeningHeight;

    public static InteriorCarCenterMeasurementsFragment newInstance(){
        InteriorCarCenterMeasurementsFragment fragment = new InteriorCarCenterMeasurementsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_interior_car_center_measurements, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);

        edtWidth = (EditText) parent.findViewById(R.id.edtWidth);
        edtHeight = (EditText) parent.findViewById(R.id.edtHeight);
        edtSideWallMainWidth = (EditText) parent.findViewById(R.id.edtSideWallMainWidth);
        edtSideWallAuxWidth = (EditText) parent.findViewById(R.id.edtSideWallAuxWidth);
        edtDoorOpeningWidth = (EditText) parent.findViewById(R.id.edtDoorOpeningWidth);
        edtDoorOpeningHeight = (EditText) parent.findViewById(R.id.edtDoorOpeningHeight);

        parent.findViewById(R.id.btnHelp).setOnClickListener(this);
        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtWidth.requestFocus();
                Utils.showKeyboard(getActivity(), true, edtWidth);
            }
        });

        setHeaderScrollConfiguration(parent, getString(R.string.sub_title_cab_interior), getString(R.string.sub_title_car_center_measurements), true, true);
    }

    private void updateUIs(){
        edtWidth.setText("");
        edtHeight.setText("");
        edtSideWallAuxWidth.setText("");
        edtSideWallMainWidth.setText("");
        edtDoorOpeningHeight.setText("");
        edtDoorOpeningWidth.setText("");
        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_edit_title, MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_title, MADSurveyApp.getInstance().getInteriorCarNum()+1, bankData.getNumOfInteriorCar(),MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }

        InteriorCarDoorData interiorCarDoorData = MADSurveyApp.getInstance().getInteriorCarDoorData();

        if(interiorCarDoorData.getWidth()>=0)
            edtWidth.setText(interiorCarDoorData.getWidth()+"");
        if(interiorCarDoorData.getHeight()>=0)
            edtHeight.setText(interiorCarDoorData.getHeight()+"");
        if(interiorCarDoorData.getSideWallAuxWidth()>=0)
            edtSideWallAuxWidth.setText(interiorCarDoorData.getSideWallAuxWidth()+"");
        if(interiorCarDoorData.getSideWallMainWidth()>=0)
            edtSideWallMainWidth.setText(interiorCarDoorData.getSideWallMainWidth()+"");
        if(interiorCarDoorData.getDoorOpeningWidth()>=0)
            edtDoorOpeningWidth.setText(interiorCarDoorData.getDoorOpeningWidth()+"");
        if(interiorCarDoorData.getDoorOpeningHeight()>=0)
            edtDoorOpeningHeight.setText(interiorCarDoorData.getDoorOpeningHeight()+"");

    }

    private void goToNext(){
        if (!isLastFocused()) return;

        double width = ConversionUtils.getDoubleFromEditText(edtWidth);
        double height = ConversionUtils.getDoubleFromEditText(edtHeight);
        double sideWallAuxWidth = ConversionUtils.getDoubleFromEditText(edtSideWallAuxWidth);
        double sideWallMainWidth = ConversionUtils.getDoubleFromEditText(edtSideWallMainWidth);
        double doorOpeningWidth = ConversionUtils.getDoubleFromEditText(edtDoorOpeningWidth);
        double doorOpeningHeight = ConversionUtils.getDoubleFromEditText(edtDoorOpeningHeight);
        if(width<0){
            edtWidth.requestFocus();
            showToast(getString(R.string.valid_msg_input_width), Toast.LENGTH_SHORT);
            return;
        }
        if(height<0){
            edtHeight.requestFocus();
            showToast(getString(R.string.valid_msg_input_height), Toast.LENGTH_SHORT);
            return;
        }
        if(sideWallMainWidth<0){
            edtSideWallMainWidth.requestFocus();
            showToast(getString(R.string.valid_msg_input_side_wall_main_width), Toast.LENGTH_SHORT);
            return;
        }
        if(sideWallAuxWidth<0){
            edtSideWallAuxWidth.requestFocus();
            showToast(getString(R.string.valid_msg_input_side_wall_aux_width), Toast.LENGTH_SHORT);
            return;
        }

        if(doorOpeningHeight<0){
            edtDoorOpeningHeight.requestFocus();
            showToast(getString(R.string.valid_msg_input_door_opening_height), Toast.LENGTH_SHORT);
            return;
        }
        if(doorOpeningWidth<0){
            edtDoorOpeningWidth.requestFocus();
            showToast(getString(R.string.valid_msg_input_door_opening_width), Toast.LENGTH_SHORT);
            return;
        }
        MADSurveyApp.getInstance().getInteriorCarDoorData().setWidth(width);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setHeight(height);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setSideWallAuxWidth(sideWallAuxWidth);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setSideWallMainWidth(sideWallMainWidth);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setDoorOpeningWidth(doorOpeningWidth);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setDoorOpeningHeight(doorOpeningHeight);
        interiorCarDoorDataHandler.update(MADSurveyApp.getInstance().getInteriorCarDoorData());
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_FRONT_RETURN_TYPE, "interior_car_license");
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
                showHelpDialog(getActivity(), getString(R.string.help_title_car_interior_measurements), R.drawable.img_help_44_interior_car_center_measurement_help, GlobalConstant.HELP_PHOTO_SIZE_RATE);
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
