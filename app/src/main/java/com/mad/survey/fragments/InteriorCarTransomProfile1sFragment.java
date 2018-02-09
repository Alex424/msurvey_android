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

public class InteriorCarTransomProfile1sFragment extends BaseFragment implements View.OnClickListener, OnFragmentResumedListener {

    private TextView txtCarNumber;
    private EditText edtHeight, edtDepth, edtReturn, edtColonnade;

    public static InteriorCarTransomProfile1sFragment newInstance(){
        InteriorCarTransomProfile1sFragment fragment = new InteriorCarTransomProfile1sFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_interior_car_transom_profile_1s, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);
        edtHeight = (EditText) parent.findViewById(R.id.edtHeight);
        edtDepth = (EditText) parent.findViewById(R.id.edtDepth);
        edtReturn = (EditText) parent.findViewById(R.id.edtReturn);
        edtColonnade = (EditText) parent.findViewById(R.id.edtColonnade);

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

        setHeaderScrollConfiguration(parent, getString(R.string.sub_title_cab_interior), getString(R.string.sub_title_car_transom_profile), true, true);
        setBackdoorTitle(parent);

    }

    private void updateUIs(){
        edtHeight.setText("");
        edtDepth.setText("");
        edtReturn.setText("");
        edtColonnade.setText("");

        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_edit_title, MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_title, MADSurveyApp.getInstance().getInteriorCarNum()+1, bankData.getNumOfInteriorCar(),MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }
        InteriorCarDoorData interiorCarDoorData = MADSurveyApp.getInstance().getInteriorCarDoorData();

        double height = interiorCarDoorData.getTransomProfileHeight();
        double depth = interiorCarDoorData.getTransomProfileDepth();
        double returnR = interiorCarDoorData.getTransomProfileReturn();
        double colonnade = interiorCarDoorData.getTransomProfileColonnade();

        if(height>=0)
            edtHeight.setText(height+"");
        if(depth>=0)
            edtDepth.setText(depth+"");
        if(returnR>=0)
            edtReturn.setText(returnR+"");
        if(colonnade>=0)
            edtColonnade.setText(colonnade + "");

    }

    private void goToNext(){
        if (!isLastFocused()) return;

        double height = ConversionUtils.getDoubleFromEditText(edtHeight);
        double depth = ConversionUtils.getDoubleFromEditText(edtDepth);
        double returnR = ConversionUtils.getDoubleFromEditText(edtReturn);
        double colonnade = ConversionUtils.getDoubleFromEditText(edtColonnade);

        if(height<0){
            edtHeight.requestFocus();
            showToast(getString(R.string.valid_msg_input_height), Toast.LENGTH_SHORT);
            return;
        }
        if(depth<0){
            edtDepth.requestFocus();
            showToast(getString(R.string.valid_msg_input_depth), Toast.LENGTH_SHORT);
            return;
        }
        if(returnR<0){
            edtReturn.requestFocus();
            showToast(getString(R.string.valid_msg_input_return), Toast.LENGTH_SHORT);
            return;
        }
        if(colonnade<0){
            edtColonnade.requestFocus();
            showToast(getString(R.string.valid_msg_input_colonnade), Toast.LENGTH_SHORT);
            return;
        }
        MADSurveyApp.getInstance().getInteriorCarDoorData().setTransomProfileHeight(height);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setTransomProfileDepth(depth);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setTransomProfileReturn(returnR);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setTransomProfileColonnade(colonnade);

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
                showHelpDialog(getActivity(), getString(R.string.help_title_car_interior_measurements), R.drawable.img_help_49_interior_car_transom_profile_1s_help, GlobalConstant.HELP_PHOTO_SIZE_RATE);
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
