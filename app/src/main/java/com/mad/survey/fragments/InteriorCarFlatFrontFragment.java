package com.mad.survey.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethod;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class InteriorCarFlatFrontFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtCarNumber;
    private LinearLayout layoutLeft, layoutRight;
    private EditText edtLeftWidth, edtLeftHeight;
    private EditText edtRightWidth, edtRightHeight;
    private EditText firstFocus;

    private int doorOpeningDirection;

    public static InteriorCarFlatFrontFragment newInstance(){
        InteriorCarFlatFrontFragment fragment = new InteriorCarFlatFrontFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_interior_car_flat_front, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);
        layoutLeft = (LinearLayout) parent.findViewById(R.id.layoutLeft);
        layoutRight = (LinearLayout) parent.findViewById(R.id.layoutRight);
        edtLeftWidth = (EditText) parent.findViewById(R.id.edtLeftWidth);
        edtLeftHeight = (EditText) parent.findViewById(R.id.edtLeftHeight);
        edtRightWidth = (EditText) parent.findViewById(R.id.edtRightWidth);
        edtRightHeight = (EditText) parent.findViewById(R.id.edtRightHeight);

        if (MADSurveyApp.getInstance().getInteriorCarDoorData().getCenterOpening() == 1){
            // if center yes - center?
            layoutLeft.setVisibility(View.VISIBLE);
            layoutRight.setVisibility(View.VISIBLE);
            firstFocus = edtLeftWidth;
            edtRightHeight.setImeOptions(EditorInfo.IME_ACTION_DONE);
        }else if (MADSurveyApp.getInstance().getInteriorCarDoorData().getCenterOpening() == 2){
            // if center no - single side
            doorOpeningDirection = MADSurveyApp.getInstance().getInteriorCarDoorData().getCarDoorOpeningDirection();
            if (doorOpeningDirection == InteriorCarDoorOpeningDirectionFragment.DIRECTION_LEFT){
                layoutLeft.setVisibility(View.VISIBLE);
                layoutRight.setVisibility(View.GONE);
                firstFocus = edtLeftWidth;
                edtLeftHeight.setImeOptions(EditorInfo.IME_ACTION_DONE);
            }else if (doorOpeningDirection == InteriorCarDoorOpeningDirectionFragment.DIRECTION_RIGHT){
                layoutLeft.setVisibility(View.GONE);
                layoutRight.setVisibility(View.VISIBLE);
                firstFocus = edtRightWidth;
                edtRightHeight.setImeOptions(EditorInfo.IME_ACTION_DONE);
            }
        }


        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
        parent.findViewById(R.id.btnHelp).setOnClickListener(this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                firstFocus.requestFocus();
                Utils.showKeyboard(getActivity(), true, firstFocus);
            }
        });

        setBackdoorTitle(parent);
        setHeaderScrollConfiguration(parent, getString(R.string.sub_title_cab_interior), getString(R.string.sub_title_car_interior_flat_front), true, true);
    }

    private void updateUIs(){
        edtLeftWidth.setText("");
        edtLeftHeight.setText("");
        edtRightWidth.setText("");
        edtRightHeight.setText("");

        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_edit_title, MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_title, MADSurveyApp.getInstance().getInteriorCarNum()+1, bankData.getNumOfInteriorCar(),MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }
        InteriorCarDoorData interiorCarDoorData = MADSurveyApp.getInstance().getInteriorCarDoorData();

        double leftWidth = interiorCarDoorData.getFlatFrontLeftWidth();
        double leftHeight = interiorCarDoorData.getFlatFrontLeftHeight();
        double rightWidth = interiorCarDoorData.getFlatFrontRightWidth();
        double rightHeight = interiorCarDoorData.getFlatFrontRightHeight();

        if(leftWidth>=0){
            edtLeftWidth.setText(leftWidth + "");
        }
        if(leftHeight>=0)
            edtLeftHeight.setText(leftHeight+"");
        if(rightWidth>=0){
            edtRightWidth.setText(rightWidth + "");
        }
        if(rightHeight>=0)
            edtRightHeight.setText(rightHeight+"");
    }

    private void doLeftSetting(){
        double leftWidth = ConversionUtils.getDoubleFromEditText(edtLeftWidth);
        double leftHeight = ConversionUtils.getDoubleFromEditText(edtLeftHeight);

        if(leftWidth<0){
            edtLeftWidth.requestFocus();
            showToast(getString(R.string.valid_msg_input_left_width), Toast.LENGTH_SHORT);
            return;
        }

        if(leftHeight<0){
            edtLeftHeight.requestFocus();
            showToast(getString(R.string.valid_msg_input_left_height), Toast.LENGTH_SHORT);
            return;
        }

        MADSurveyApp.getInstance().getInteriorCarDoorData().setFlatFrontLeftWidth(leftWidth);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setFlatFrontLeftHeight(leftHeight);
    }

    private void doRightSetting(){
        double rightWidth = ConversionUtils.getDoubleFromEditText(edtRightWidth);
        double rightHeight = ConversionUtils.getDoubleFromEditText(edtRightHeight);

        if(rightWidth<0){
            edtRightWidth.requestFocus();
            showToast(getString(R.string.valid_msg_input_right_width), Toast.LENGTH_SHORT);
            return;
        }

        if(rightHeight<0){
            edtRightHeight.requestFocus();
            showToast(getString(R.string.valid_msg_input_right_height), Toast.LENGTH_SHORT);
            return;
        }

        MADSurveyApp.getInstance().getInteriorCarDoorData().setFlatFrontRightWidth(rightWidth);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setFlatFrontRightHeight(rightHeight);
    }
    private void goToNext(){
        if (!isLastFocused()) return;

        if (MADSurveyApp.getInstance().getInteriorCarDoorData().getCenterOpening() == 1){
            // if center yes - center?
            doLeftSetting();
            doRightSetting();
        }else{
            // if center no - single side?
            if (doorOpeningDirection == InteriorCarDoorOpeningDirectionFragment.DIRECTION_LEFT){
                doLeftSetting();
            }else if (doorOpeningDirection == InteriorCarDoorOpeningDirectionFragment.DIRECTION_RIGHT) {
                doRightSetting();
            }
        }

        interiorCarDoorDataHandler.update(MADSurveyApp.getInstance().getInteriorCarDoorData());

        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_COP_INSTALLED, "interior_car_cop_installed");
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
                showHelpDialog(getActivity(), getString(R.string.help_title_car_interior_measurements), R.drawable.img_help_interior_car_flat_front, GlobalConstant.HELP_PHOTO_SIZE_RATE);
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
