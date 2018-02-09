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

public class InteriorCarSingleSideReturnMeasurementsAFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtCarNumber;
    private EditText edtA, edtB, edtC, edtD, edtE;
    private EditText edtHeight;

    public static InteriorCarSingleSideReturnMeasurementsAFragment newInstance(){
        InteriorCarSingleSideReturnMeasurementsAFragment fragment = new InteriorCarSingleSideReturnMeasurementsAFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_interior_car_single_side_return_measurements_a, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);
        edtA = (EditText) parent.findViewById(R.id.edtA);
        edtB = (EditText) parent.findViewById(R.id.edtB);
        edtC = (EditText) parent.findViewById(R.id.edtC);
        edtD = (EditText) parent.findViewById(R.id.edtD);
        edtE = (EditText) parent.findViewById(R.id.edtE);
        edtHeight = (EditText) parent.findViewById(R.id.edtHeight);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
        parent.findViewById(R.id.btnHelp).setOnClickListener(this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtA.requestFocus();
                Utils.showKeyboard(getActivity(), true, edtA);
            }
        });

        setHeaderScrollConfiguration(parent, getString(R.string.sub_title_cab_interior), getString(R.string.sub_title_car_single_side_return_measurements), true, true);
        setBackdoorTitle(parent);
    }

    private void updateUIs(){
        edtA.setText("");
        edtB.setText("");
        edtC.setText("");
        edtD.setText("");
        edtE.setText("");

        edtHeight.setText("");

        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_edit_title, MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_title, MADSurveyApp.getInstance().getInteriorCarNum()+1, bankData.getNumOfInteriorCar(),MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }
        InteriorCarDoorData interiorCarDoorData = MADSurveyApp.getInstance().getInteriorCarDoorData();
        double A = interiorCarDoorData.getLeftSideA();
        double B = interiorCarDoorData.getLeftSideB();
        double C = interiorCarDoorData.getLeftSideC();
        double D = interiorCarDoorData.getLeftSideD();
        double E = interiorCarDoorData.getLeftSideE();


        double height = interiorCarDoorData.getFrontReturnMeasurementsHeight();

        if(A>=0){
            edtA.setText(A+"");
        }
        if(B>=0){
            edtB.setText(B+"");
        }
        if(C>=0){
            edtC.setText(C+"");
        }
        if(D>=0){
            edtD.setText(D+"");
        }
        if(E>=0){
            edtE.setText(E+"");
        }


        if(height>=0){
            edtHeight.setText(height+"");
        }

    }

    private void goToNext(){
        if (!isLastFocused()) return;

        double A = ConversionUtils.getDoubleFromEditText(edtA);
        double B = ConversionUtils.getDoubleFromEditText(edtB);
        double C = ConversionUtils.getDoubleFromEditText(edtC);
        double D = ConversionUtils.getDoubleFromEditText(edtD);
        double E = ConversionUtils.getDoubleFromEditText(edtE);


        double height = ConversionUtils.getDoubleFromEditText(edtHeight);

        if(A<0){
            edtA.requestFocus();
            showToast(getString(R.string.valid_msg_input_A), Toast.LENGTH_SHORT);
            return;
        }
        if(B<0){
            edtB.requestFocus();
            showToast(getString(R.string.valid_msg_input_B), Toast.LENGTH_SHORT);
            return;
        }
        if(C<0){
            edtC.requestFocus();
            showToast(getString(R.string.valid_msg_input_C), Toast.LENGTH_SHORT);
            return;
        }
        if(D<0){
            edtD.requestFocus();
            showToast(getString(R.string.valid_msg_input_D), Toast.LENGTH_SHORT);
            return;
        }
        if(E<0){
            edtE.requestFocus();
            showToast(getString(R.string.valid_msg_input_E), Toast.LENGTH_SHORT);
            return;
        }

        if(height<0){
            edtHeight.requestFocus();
            showToast(getString(R.string.valid_msg_input_height), Toast.LENGTH_SHORT);
            return;
        }

        MADSurveyApp.getInstance().getInteriorCarDoorData().setLeftSideA(A);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setLeftSideB(B);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setLeftSideC(C);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setLeftSideD(D);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setLeftSideE(E);
        MADSurveyApp.getInstance().getInteriorCarDoorData().setFrontReturnMeasurementsHeight(height);

        interiorCarDoorDataHandler.update(MADSurveyApp.getInstance().getInteriorCarDoorData());

        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_DOOR_TYPE, "interior_car_door_type");
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
                showHelpDialog(getActivity(), getString(R.string.help_title_car_interior_measurements), R.drawable.img_help_46_interior_car_single_side_return_measurements_a_help, GlobalConstant.HELP_PHOTO_SIZE_RATE);
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
