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

public class InteriorCarSlamPostTypeAFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtCarNumber;
    private EditText edtA, edtB, edtC, edtD, edtE, edtF, edtG, edtH;

    public static InteriorCarSlamPostTypeAFragment newInstance(){
        InteriorCarSlamPostTypeAFragment fragment = new InteriorCarSlamPostTypeAFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_interior_car_slam_post_type_a, container, false);

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
        edtF = (EditText) parent.findViewById(R.id.edtF);
        edtG = (EditText) parent.findViewById(R.id.edtG);
        edtH = (EditText) parent.findViewById(R.id.edtH);

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

        setHeaderScrollConfiguration(parent, getString(R.string.sub_title_cab_interior), getString(R.string.sub_title_car_slam_post_type_measurements), true, true);
        setBackdoorTitle(parent);
    }

    private void updateUIs(){
        edtA.setText("");
        edtB.setText("");
        edtC.setText("");
        edtD.setText("");
        edtE.setText("");
        edtF.setText("");
        edtG.setText("");
        edtH.setText("");
        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_edit_title, MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_title, MADSurveyApp.getInstance().getInteriorCarNum()+1, bankData.getNumOfInteriorCar(),MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }
        InteriorCarDoorData interiorCarDoorData = MADSurveyApp.getInstance().getInteriorCarDoorData();
        if(interiorCarDoorData.getSlamPostMeasurementsA()>=0)
            edtA.setText(interiorCarDoorData.getSlamPostMeasurementsA()+"");
        if(interiorCarDoorData.getSlamPostMeasurementsB()>=0)
            edtB.setText(interiorCarDoorData.getSlamPostMeasurementsB()+"");
        if(interiorCarDoorData.getSlamPostMeasurementsC()>=0)
            edtC.setText(interiorCarDoorData.getSlamPostMeasurementsC()+"");
        if(interiorCarDoorData.getSlamPostMeasurementsD()>=0)
            edtD.setText(interiorCarDoorData.getSlamPostMeasurementsD()+"");
        if(interiorCarDoorData.getSlamPostMeasurementsE()>=0)
            edtE.setText(interiorCarDoorData.getSlamPostMeasurementsE()+"");
        if(interiorCarDoorData.getSlamPostMeasurementsF()>=0)
            edtF.setText(interiorCarDoorData.getSlamPostMeasurementsF()+"");
        if(interiorCarDoorData.getSlamPostMeasurementsG()>=0)
            edtG.setText(interiorCarDoorData.getSlamPostMeasurementsG()+"");
        if(interiorCarDoorData.getSlamPostMeasurementsH()>=0)
            edtH.setText(interiorCarDoorData.getSlamPostMeasurementsH()+"");
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        InteriorCarDoorData interiorCarDoorData = MADSurveyApp.getInstance().getInteriorCarDoorData();
        double A = ConversionUtils.getDoubleFromEditText(edtA);
        double B = ConversionUtils.getDoubleFromEditText(edtB);
        double C = ConversionUtils.getDoubleFromEditText(edtC);
        double D = ConversionUtils.getDoubleFromEditText(edtD);
        double E = ConversionUtils.getDoubleFromEditText(edtE);
        double F = ConversionUtils.getDoubleFromEditText(edtF);
        double G = ConversionUtils.getDoubleFromEditText(edtG);
        double H = ConversionUtils.getDoubleFromEditText(edtH);

        if(A < 0){
            edtA.requestFocus();
            showToast(getString(R.string.valid_msg_input_A), Toast.LENGTH_SHORT);
            return;
        }
        if(B < 0){
            edtB.requestFocus();
            showToast(getString(R.string.valid_msg_input_B), Toast.LENGTH_SHORT);
            return;
        }
        if(C < 0){
            edtC.requestFocus();
            showToast(getString(R.string.valid_msg_input_C), Toast.LENGTH_SHORT);
            return;
        }
        if(D < 0){
            edtD.requestFocus();
            showToast(getString(R.string.valid_msg_input_D), Toast.LENGTH_SHORT);
            return;
        }
        if(E < 0){
            edtE.requestFocus();
            showToast(getString(R.string.valid_msg_input_E), Toast.LENGTH_SHORT);
            return;
        }
        if(F < 0){
            edtF.requestFocus();
            showToast(getString(R.string.valid_msg_input_F), Toast.LENGTH_SHORT);
            return;
        }
        if(G < 0){
            edtG.requestFocus();
            showToast(getString(R.string.valid_msg_input_G), Toast.LENGTH_SHORT);
            return;
        }
        if(H < 0){
            edtH.requestFocus();
            showToast(getString(R.string.valid_msg_input_H), Toast.LENGTH_SHORT);
            return;
        }
        interiorCarDoorData.setSlamPostMeasurementsA(A);
        interiorCarDoorData.setSlamPostMeasurementsB(B);
        interiorCarDoorData.setSlamPostMeasurementsC(C);
        interiorCarDoorData.setSlamPostMeasurementsD(D);
        interiorCarDoorData.setSlamPostMeasurementsE(E);
        interiorCarDoorData.setSlamPostMeasurementsF(F);
        interiorCarDoorData.setSlamPostMeasurementsG(G);
        interiorCarDoorData.setSlamPostMeasurementsH(H);
        interiorCarDoorDataHandler.update(interiorCarDoorData);
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
                showHelpDialog(getActivity(), getString(R.string.help_title_car_interior_measurements), R.drawable.img_help_45_interior_car_slam_post_type_a_help, GlobalConstant.HELP_PHOTO_SIZE_RATE);
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
