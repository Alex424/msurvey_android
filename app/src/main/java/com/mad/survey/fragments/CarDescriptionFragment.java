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
import com.mad.survey.models.CarData;
import com.mad.survey.utils.Utils;

public class CarDescriptionFragment extends BaseFragment implements View.OnClickListener,OnFragmentResumedListener {

    private TextView txtSubTitle;
    private TextView txtCarNumber;
    private EditText edtCarDescriptor;

    private View parent;

    public static CarDescriptionFragment newInstance(){
        CarDescriptionFragment fragment = new CarDescriptionFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_car_description, container, false);

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
        CarData carData = carDataHandler.get(MADSurveyApp.getInstance().getProjectData().getId(),
                MADSurveyApp.getInstance().getBankNum(),
                MADSurveyApp.getInstance().getCarNum());
        MADSurveyApp.getInstance().setCarData(carData);

        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_n_title, MADSurveyApp.getInstance().getCarNum()+1, bankData.getNumOfCar()));
        }

        if(carData != null)
            edtCarDescriptor.setText(carData.getCarNumber());

        //Added by Alex - 2018/01/24
        if (!MADSurveyApp.getInstance().isEditMode() && MADSurveyApp.getInstance().getCarNum() > 0){
            ((BaseActivity) getActivity()).setBackable(false);
            parent.findViewById(R.id.btnBack).setVisibility(View.INVISIBLE);
        }
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        String descriptor = edtCarDescriptor.getText().toString();
        if(descriptor.equals("")){
            edtCarDescriptor.requestFocus();
            showToast(getString(R.string.valid_msg_input_car_descriptor), Toast.LENGTH_SHORT);
            return;
        }
        int currentCar = MADSurveyApp.getInstance().getCarNum();
        int projectId  = MADSurveyApp.getInstance().getProjectData().getId();
        int bankNum = MADSurveyApp.getInstance().getBankNum();
        // If there isn't currentCar in DB,INSERT
        CarData carData = carDataHandler.insertNewcarWithCarNumber(projectId, bankNum, currentCar, descriptor);
        if(carData!=null){
            MADSurveyApp.getInstance().setCarData(carData);
        }
        else{
            CarData carData1 = MADSurveyApp.getInstance().getCarData();
            carData1.setCarNumber(descriptor);
            MADSurveyApp.getInstance().setCarData(carData1);
            carDataHandler.update(carData1);
        }
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_CAR_LICENSE, "car_license");
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
