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
import com.mad.survey.models.InteriorCarData;
import com.mad.survey.utils.ConversionUtils;
import com.mad.survey.utils.Utils;

public class InteriorCarCeilingExhaustFanLocationFragment extends BaseFragment implements View.OnClickListener {

    private TextView txtSubTitle;
    private TextView txtCarNumber;
    private EditText edtExhaustFanLocation;
    private EditText edtExToLeftWall;
    private EditText edtExToBackWall;
    private EditText edtExWidth;
    private EditText edtExLength;

    public static InteriorCarCeilingExhaustFanLocationFragment newInstance(){
        InteriorCarCeilingExhaustFanLocationFragment fragment = new InteriorCarCeilingExhaustFanLocationFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_interior_car_ceiling_exhaust_fan_location, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);
        edtExhaustFanLocation = (EditText) parent.findViewById(R.id.edtExhaustFanLocation);
        edtExToLeftWall = (EditText) parent.findViewById(R.id.edtExToLeftWall);
        edtExToBackWall = (EditText) parent.findViewById(R.id.edtExToBackWall);
        edtExWidth = (EditText) parent.findViewById(R.id.edtExWidth);
        edtExLength = (EditText) parent.findViewById(R.id.edtExLength);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
        parent.findViewById(R.id.btnHelp).setOnClickListener(this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtExhaustFanLocation.requestFocus();
                Utils.showKeyboard(getActivity(), true, edtExhaustFanLocation);
            }
        });

        setHeaderScrollConfiguration(parent, getString(R.string.sub_title_cab_interior), getString(R.string.sub_title_car_interior_exhaust_fan_location), true, true);
    }

    private void updateUIs(){
        edtExhaustFanLocation.setText("");
        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_edit_title, MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_title, MADSurveyApp.getInstance().getInteriorCarNum()+1, bankData.getNumOfInteriorCar(),MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }
        InteriorCarData interiorCarData = MADSurveyApp.getInstance().getInteriorCarData();
        edtExhaustFanLocation.setText(interiorCarData.getExhaustFanLocation());

        if (interiorCarData.getExToLeftWall() >= 0)
            edtExToLeftWall.setText(interiorCarData.getExToLeftWall() + "");
        if (interiorCarData.getExToBackWall() >= 0)
            edtExToBackWall.setText(interiorCarData.getExToBackWall() + "");
        if (interiorCarData.getExWidth() >= 0)
            edtExWidth.setText(interiorCarData.getExWidth() + "");
        if (interiorCarData.getExLength() >= 0)
            edtExLength.setText(interiorCarData.getExLength() + "");
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        String location = edtExhaustFanLocation.getText().toString();
        if(location.equals("")){
            edtExhaustFanLocation.requestFocus();
            showToast(getString(R.string.valid_msg_input_exhaust_fan_location), Toast.LENGTH_SHORT);
            return;
        }
        MADSurveyApp.getInstance().getInteriorCarData().setExhaustFanLocation(location);

        MADSurveyApp.getInstance().getInteriorCarData().setExToLeftWall(ConversionUtils.getDoubleFromEditText(edtExToLeftWall));
        MADSurveyApp.getInstance().getInteriorCarData().setExToBackWall(ConversionUtils.getDoubleFromEditText(edtExToBackWall));
        MADSurveyApp.getInstance().getInteriorCarData().setExWidth(ConversionUtils.getDoubleFromEditText(edtExWidth));
        MADSurveyApp.getInstance().getInteriorCarData().setExLength(ConversionUtils.getDoubleFromEditText(edtExLength));

        interiorCarDataHandler.update(MADSurveyApp.getInstance().getInteriorCarData());
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_CEILING_FRAME_TYPE, "interior_car_ceiling_frame_type");
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
                showHelpDialog(getActivity(), getString(R.string.help_title_exhaust_fan_location), R.drawable.img_help_interior_car_ceiling_exhaust_fan_location, GlobalConstant.HELP_PHOTO_SIZE_RATE);
                break;
        }
    }
}
