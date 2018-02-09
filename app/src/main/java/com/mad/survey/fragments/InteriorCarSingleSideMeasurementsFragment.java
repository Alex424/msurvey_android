package com.mad.survey.fragments;

import android.os.Bundle;
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

public class InteriorCarSingleSideMeasurementsFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtCarNumber;
    private EditText edtWidth;
    private EditText edtHeight;
    private EditText edtReturnSideWallDepth;
    private EditText edtSlamSideWallDepth;
    //private EditText edtSlideWallWidth;
    private EditText edtDoorOpeningWidth;
    private EditText edtDoorOpeningHeight;

    public static InteriorCarSingleSideMeasurementsFragment newInstance(){
        InteriorCarSingleSideMeasurementsFragment fragment = new InteriorCarSingleSideMeasurementsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_interior_car_single_side_measurements, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);

        edtWidth = (EditText) parent.findViewById(R.id.edtWidth);
        edtHeight = (EditText) parent.findViewById(R.id.edtHeight);
        edtReturnSideWallDepth = (EditText) parent.findViewById(R.id.edtReturnSideWallDepth);
        edtSlamSideWallDepth = (EditText) parent.findViewById(R.id.edtSlamSideWallDepth);
        //edtSlideWallWidth = (EditText) parent.findViewById(R.id.edtSlideWallWidth);
        edtDoorOpeningWidth = (EditText) parent.findViewById(R.id.edtDoorOpeningWidth);
        edtDoorOpeningHeight = (EditText) parent.findViewById(R.id.edtDoorOpeningHeight);

        parent.findViewById(R.id.btnHelp).setOnClickListener(this);
        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);

        setHeaderScrollConfiguration(parent, getString(R.string.sub_title_cab_interior), getString(R.string.sub_title_car_single_side_measurements), true, true);
    }

    private void updateUIs(){
        edtWidth.setText("");
        edtHeight.setText("");
        edtReturnSideWallDepth.setText("");
        edtSlamSideWallDepth.setText("");
        //edtSlideWallWidth.setText("");
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
        if(interiorCarDoorData.getReturnSideWallDepth()>=0)
            edtReturnSideWallDepth.setText(interiorCarDoorData.getReturnSideWallDepth()+"");
        if(interiorCarDoorData.getSlamSideWallDepth()>=0)
            edtSlamSideWallDepth.setText(interiorCarDoorData.getSlamSideWallDepth()+"");
        //if(interiorCarDoorData.getSlideWallWidth()>0)
        //    edtSlideWallWidth.setText(interiorCarDoorData.getSlideWallWidth()+"");
        if(interiorCarDoorData.getDoorOpeningWidth()>=0)
            edtDoorOpeningWidth.setText(interiorCarDoorData.getDoorOpeningWidth()+"");
        if(interiorCarDoorData.getDoorOpeningHeight()>=0)
            edtDoorOpeningHeight.setText(interiorCarDoorData.getDoorOpeningHeight()+"");
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        double width = ConversionUtils.getDoubleFromEditText(edtWidth);
        double height = ConversionUtils.getDoubleFromEditText(edtHeight);
        double returnSideWallDepth = ConversionUtils.getDoubleFromEditText(edtReturnSideWallDepth);
        double slamSideWallDepth = ConversionUtils.getDoubleFromEditText(edtSlamSideWallDepth);
        //double slideWallWidth = ConversionUtils.getDoubleFromEditText(edtSlideWallWidth);
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
        if(returnSideWallDepth<0){
            edtReturnSideWallDepth.requestFocus();
            showToast(getString(R.string.valid_msg_input_return_side_wall_depth), Toast.LENGTH_SHORT);
            return;
        }
        if(slamSideWallDepth<0){
            edtSlamSideWallDepth.requestFocus();
            showToast(getString(R.string.valid_msg_input_slam_side_wall_depth), Toast.LENGTH_SHORT);
            return;
        }
        /*if(slideWallWidth<=0){
            edtSlideWallWidth.requestFocus();
            showToast(getString(R.string.valid_msg_input_slide_wall_width), Toast.LENGTH_SHORT);
            return;
        }*/
        if(doorOpeningWidth<0){
            edtDoorOpeningWidth.requestFocus();
            showToast(getString(R.string.valid_msg_input_door_opening_width), Toast.LENGTH_SHORT);
            return;
        }
        if(doorOpeningHeight<0){
            edtDoorOpeningHeight.requestFocus();
            showToast(getString(R.string.valid_msg_input_door_opening_height), Toast.LENGTH_SHORT);
            return;
        }

        MADSurveyApp.getInstance().getInteriorCarDoorData().setWidth(width);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setHeight(height);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setReturnSideWallDepth(returnSideWallDepth);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setSlamSideWallDepth(slamSideWallDepth);
        //MADSurveyApp.getInstance().getInteriorCarDoorData().setSlideWallWidth(slideWallWidth);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setDoorOpeningWidth(doorOpeningWidth);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setDoorOpeningHeight(doorOpeningHeight);
        interiorCarDoorDataHandler.update(MADSurveyApp.getInstance().getInteriorCarDoorData());

        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_SLAM_POST_TYPE, "slam_post_type");
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
                showHelpDialog(getActivity(), getString(R.string.help_title_car_interior_measurements), R.drawable.img_help_interior_car_single_side_measurements, GlobalConstant.HELP_PHOTO_SIZE_RATE);
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
