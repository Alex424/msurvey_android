package com.mad.survey.fragments;

import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.mad.survey.MADSurveyApp;
import com.mad.survey.R;
import com.mad.survey.activities.BaseActivity;
import com.mad.survey.globals.GlobalConstant;
import com.mad.survey.models.CarData;
import com.mad.survey.models.CopData;

public class CarCopStyleFragment extends BaseFragment implements View.OnClickListener {

    private TextView txtSubTitle;
    private TextView txtCarNumber;
    private CheckBox chkApplied,chkSwing;
    private CheckBox chkLeft,chkRight;
    private String style = "",hingeSide = "";

    public static CarCopStyleFragment newInstance(){
        CarCopStyleFragment fragment = new CarCopStyleFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_car_cop_style, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);
        chkApplied = (CheckBox) parent.findViewById(R.id.chkApplied);
        chkSwing = (CheckBox) parent.findViewById(R.id.chkSwing);
        chkRight = (CheckBox) parent.findViewById(R.id.chkRight);
        chkLeft = (CheckBox) parent.findViewById(R.id.chkLeft);
        style = "";
        hingeSide = "";

        parent.findViewById(R.id.btnApplied).setOnClickListener(this);
        parent.findViewById(R.id.btnSwing).setOnClickListener(this);
        parent.findViewById(R.id.btnLeft).setOnClickListener(this);
        parent.findViewById(R.id.btnRight).setOnClickListener(this);
        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
    }

    private void updateUIs(){
        chkApplied.setChecked(false);
        chkSwing.setChecked(false);
        chkRight.setChecked(false);
        chkLeft.setChecked(false);

        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
        }
        txtCarNumber.setText(getString(R.string.cop_n_title, MADSurveyApp.getInstance().getCopNum() + 1, MADSurveyApp.getInstance().getCarData().getNumberOfCops(), MADSurveyApp.getInstance().getCarData().getCarNumber()));
        CopData copData = MADSurveyApp.getInstance().getCopData();
        if(copData.getOptions().equals(GlobalConstant.COP_STYLE_APPLIED)) {
            style = GlobalConstant.COP_STYLE_APPLIED;
            chkApplied.setChecked(true);
        }
        else if(copData.getOptions().equals(GlobalConstant.COP_STYLE_SWING)) {
            style = GlobalConstant.COP_STYLE_SWING;
            chkSwing.setChecked(true);
        }

        if(copData.getReturnHinging().equals(GlobalConstant.COP_HINGING_SIDE_RIGHT)) {
            hingeSide = GlobalConstant.COP_HINGING_SIDE_RIGHT;
            chkRight.setChecked(true);
        }
        else if(copData.getReturnHinging().equals(GlobalConstant.COP_HINGING_SIDE_LEFT)) {
            hingeSide = GlobalConstant.COP_HINGING_SIDE_LEFT;
            chkLeft.setChecked(true);
        }

    }

    private void goToNext(){
        if (!isLastFocused()) return;

        if(style.equals("")){
            showToast("Please select COP Style!", Toast.LENGTH_SHORT);
            return;
        }
        if(hingeSide.equals("")){
            showToast("Please select COP HINGE SIDE!",Toast.LENGTH_SHORT);
            return;
        }
        MADSurveyApp.getInstance().getCopData().setOptions(style);
        MADSurveyApp.getInstance().getCopData().setReturnHinging(hingeSide);
        if(style.equals(GlobalConstant.COP_STYLE_APPLIED)) {
            MADSurveyApp.getInstance().getCopData().setSwingPanelHeight(-1);
            MADSurveyApp.getInstance().getCopData().setSwingPanelWidth(-1);
            //MADSurveyApp.getInstance().getCopData().setCoverAff(-1);
            copDataHandler.update(MADSurveyApp.getInstance().getCopData());
            ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_CAR_COP_APPLIED_MEASUREMENTS, "car_cop_applied_measurements");
        }
        else if(style.equals((GlobalConstant.COP_STYLE_SWING))) {
            MADSurveyApp.getInstance().getCopData().setReturnPanelWidth(-1);
            MADSurveyApp.getInstance().getCopData().setReturnPanelHeight(-1);
            MADSurveyApp.getInstance().getCopData().setCoverWidth(-1);
            MADSurveyApp.getInstance().getCopData().setCoverHeight(-1);
            MADSurveyApp.getInstance().getCopData().setCoverToOpening(-1);
            //MADSurveyApp.getInstance().getCopData().setCoverAff(-1);
            copDataHandler.update(MADSurveyApp.getInstance().getCopData());
            ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_CAR_COP_SWING_MEASUREMENTS, "car_cop_swing_measurements");
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
            case R.id.btnLeft:
                hingeSide = GlobalConstant.COP_HINGING_SIDE_LEFT;
                chkLeft.setChecked(true);
                chkRight.setChecked(false);
                break;
            case R.id.btnRight:
                hingeSide = GlobalConstant.COP_HINGING_SIDE_RIGHT;
                chkLeft.setChecked(false);
                chkRight.setChecked(true);
                break;
            case R.id.btnApplied:
                style = GlobalConstant.COP_STYLE_APPLIED;
                chkApplied.setChecked(true);
                chkSwing.setChecked(false);
                break;
            case R.id.btnSwing:
                style = GlobalConstant.COP_STYLE_SWING;
                chkApplied.setChecked(false);
                chkSwing.setChecked(true);
                break;
        }
    }
}
