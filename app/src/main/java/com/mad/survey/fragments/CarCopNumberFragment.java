package com.mad.survey.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.mad.survey.MADSurveyApp;
import com.mad.survey.R;
import com.mad.survey.activities.BaseActivity;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.BankData;
import com.mad.survey.models.CarData;

public class CarCopNumberFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtCarNumber;
    private TextView txtSubTitle;

    private CheckBox chkNo1, chkNo2, chkNo3, chkNo4;

    public static CarCopNumberFragment newInstance(){
        CarCopNumberFragment fragment = new CarCopNumberFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_car_cop_number, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);

        chkNo1 = (CheckBox) parent.findViewById(R.id.chkNo1);
        chkNo2 = (CheckBox) parent.findViewById(R.id.chkNo2);
        chkNo3 = (CheckBox) parent.findViewById(R.id.chkNo3);
        chkNo4 = (CheckBox) parent.findViewById(R.id.chkNo4);

        parent.findViewById(R.id.btnNo1).setOnClickListener(this);
        parent.findViewById(R.id.btnNo2).setOnClickListener(this);
        parent.findViewById(R.id.btnNo3).setOnClickListener(this);
        parent.findViewById(R.id.btnNo4).setOnClickListener(this);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
    }

    private void updateUIs(){
        chkNo1.setChecked(false);
        chkNo2.setChecked(false);
        chkNo3.setChecked(false);
        chkNo4.setChecked(false);
        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
        }
        txtCarNumber.setText(getString(R.string.car_number_n_title, MADSurveyApp.getInstance().getCarNum()+1, bankData.getNumOfCar(),MADSurveyApp.getInstance().getCarData().getCarNumber()));
        CarData carData =MADSurveyApp.getInstance().getCarData();
        int numberOfCops = carData.getNumberOfCops();
        switch (numberOfCops){
            case 1:
                chkNo1.setChecked(true);
                break;
            case 2:
                chkNo2.setChecked(true);
                break;
            case 3:
                chkNo3.setChecked(true);
                break;
            case 4:
                chkNo4.setChecked(true);
                break;
        }
    }

    private void goToNext(int cnt){
        if (!isLastFocused()) return;

        MADSurveyApp.getInstance().setCopNum(0);
        MADSurveyApp.getInstance().getCarData().setNumberOfCops(cnt);
        carDataHandler.update(MADSurveyApp.getInstance().getCarData());
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_CAR_COP_NAME, "car_cop_name");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.btnNo1:
                goToNext(1);
                break;
            case R.id.btnNo2:
                goToNext(2);
                break;
            case R.id.btnNo3:
                goToNext(3);
                break;
            case R.id.btnNo4:
                goToNext(4);
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
