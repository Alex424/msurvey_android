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

public class InteriorCarHeaderFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtCarNumber;
    private EditText edtHoistWay, edtThroat;
    private EditText edtWidth, edtHeight;
    private EditText edtReturnWall;

    public static InteriorCarHeaderFragment newInstance(){
        InteriorCarHeaderFragment fragment = new InteriorCarHeaderFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_interior_car_header, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);
        edtHeight = (EditText) parent.findViewById(R.id.edtHeight);
        edtWidth = (EditText) parent.findViewById(R.id.edtWidth);
        edtHoistWay = (EditText) parent.findViewById(R.id.edtHoistway);
        edtThroat = (EditText) parent.findViewById(R.id.edtThroat);
        edtReturnWall = (EditText) parent.findViewById(R.id.edtReturnwall);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
        parent.findViewById(R.id.btnHelp).setOnClickListener(this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtHoistWay.requestFocus();
                Utils.showKeyboard(getActivity(), true, edtHoistWay);
            }
        });

        setBackdoorTitle(parent);
        setHeaderScrollConfiguration(parent, getString(R.string.sub_title_cab_interior), getString(R.string.sub_title_car_interior_header), true, true);
    }

    private void updateUIs(){
        edtHoistWay.setText("");
        edtThroat.setText("");
        edtWidth.setText("");
        edtHeight.setText("");
        edtReturnWall.setText("");

        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_edit_title, MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_title, MADSurveyApp.getInstance().getInteriorCarNum()+1, bankData.getNumOfInteriorCar(),MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }
        InteriorCarDoorData interiorCarDoorData = MADSurveyApp.getInstance().getInteriorCarDoorData();

        double hoistWay = interiorCarDoorData.getHeaderReturnHoistWay();
        double throat = interiorCarDoorData.getHeaderThroat();
        double width = interiorCarDoorData.getHeaderWidth();
        double height = interiorCarDoorData.getHeaderHeight();
        double returnWall = interiorCarDoorData.getHeaderReturnWall();

        if (hoistWay >= 0){
            edtHoistWay.setText(hoistWay + "");
        }
        if (throat >= 0){
            edtThroat.setText(throat + "");
        }
        if(width>=0){
            edtWidth.setText(width + "");
        }
        if(height>=0)
            edtHeight.setText(height+"");
        if (returnWall >= 0){
            edtReturnWall.setText(returnWall + "");
        }
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        double hoistWay = ConversionUtils.getDoubleFromEditText(edtHoistWay);
        double throat = ConversionUtils.getDoubleFromEditText(edtThroat);
        double width = ConversionUtils.getDoubleFromEditText(edtWidth);
        double height = ConversionUtils.getDoubleFromEditText(edtHeight);
        double returnWall = ConversionUtils.getDoubleFromEditText(edtReturnWall);


        if(hoistWay<0){
            edtHoistWay.requestFocus();
            showToast(getString(R.string.valid_msg_input_return_hoist_way), Toast.LENGTH_SHORT);
            return;
        }

        if(throat<0){
            edtThroat.requestFocus();
            showToast(getString(R.string.valid_msg_input_throat), Toast.LENGTH_SHORT);
            return;
        }

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

        if(returnWall<0){
            edtReturnWall.requestFocus();
            showToast(getString(R.string.valid_msg_input_return_wall), Toast.LENGTH_SHORT);
            return;
        }

        MADSurveyApp.getInstance().getInteriorCarDoorData().setHeaderReturnHoistWay(hoistWay);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setHeaderThroat(throat);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setHeaderWidth(width);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setHeaderHeight(height);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setHeaderReturnWall(returnWall);

        interiorCarDoorDataHandler.update(MADSurveyApp.getInstance().getInteriorCarDoorData());

        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_FLAT_FRONT, "interior_car_flat_front");
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
                showHelpDialog(getActivity(), getString(R.string.help_title_car_interior_measurements), R.drawable.img_help_interior_car_header, GlobalConstant.HELP_PHOTO_SIZE_RATE);
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
