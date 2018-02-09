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
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.BankData;
import com.mad.survey.models.InteriorCarData;
import com.mad.survey.utils.Utils;

public class InteriorCarDescriptionFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private View parent;
    private TextView txtSubTitle;
    private TextView txtCarNumber;
    private EditText edtCarDescriptor;

    public static InteriorCarDescriptionFragment newInstance(){
        InteriorCarDescriptionFragment fragment = new InteriorCarDescriptionFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_interior_car_description, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);
        edtCarDescriptor = (EditText) parent.findViewById(R.id.edtCarDescriptor);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtCarDescriptor.requestFocus();
                Utils.showKeyboard(getActivity(), true, edtCarDescriptor);
            }
        });
    }

    private void updateUIs(){

        edtCarDescriptor.setText("");

        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_n_title, MADSurveyApp.getInstance().getInteriorCarNum()+1, bankData.getNumOfInteriorCar()));
        }

        InteriorCarData interiorCarData = interiorCarDataHandler.get(MADSurveyApp.getInstance().getProjectData().getId(),
                MADSurveyApp.getInstance().getBankNum(),
                MADSurveyApp.getInstance().getInteriorCarNum());
        MADSurveyApp.getInstance().setInteriorCarData(interiorCarData);
        if(interiorCarData != null)
            edtCarDescriptor.setText(interiorCarData.getCarDescription());

        //Added by Alex - 2018/01/24
        if (!MADSurveyApp.getInstance().isEditMode() && MADSurveyApp.getInstance().getInteriorCarNum() > 0){
            ((BaseActivity) getActivity()).setBackable(false);
            parent.findViewById(R.id.btnBack).setVisibility(View.INVISIBLE);
        }
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        String description = edtCarDescriptor.getText().toString();
        if(description.equals("")){
            edtCarDescriptor.requestFocus();
            showToast(getString(R.string.valid_msg_input_car_descriptor), Toast.LENGTH_SHORT);
            return;
        }
        InteriorCarData interiorCarData = interiorCarDataHandler.insertNewInteriorCarWithDescription(MADSurveyApp.getInstance().getProjectData().getId(),
                MADSurveyApp.getInstance().getBankNum(),
                MADSurveyApp.getInstance().getInteriorCarNum(),
                description);
        if(interiorCarData != null){
            MADSurveyApp.getInstance().setInteriorCarData(interiorCarData);
        }
        else{
            InteriorCarData interiorCarData1 = MADSurveyApp.getInstance().getInteriorCarData();
            interiorCarData1.setCarDescription(description);
            MADSurveyApp.getInstance().setInteriorCarData(interiorCarData1);
            interiorCarDataHandler.update(interiorCarData1);
        }
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_LICENSE, "interior_car_license");
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
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
