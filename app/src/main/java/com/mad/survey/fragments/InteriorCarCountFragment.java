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
import com.mad.survey.utils.ConversionUtils;
import com.mad.survey.utils.Utils;

public class InteriorCarCountFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtSubTitle;
    private EditText edtElevatorCarsNum;

    public static InteriorCarCountFragment newInstance(){
        InteriorCarCountFragment fragment = new InteriorCarCountFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_interior_car_count, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        edtElevatorCarsNum = (EditText) parent.findViewById(R.id.edtElevatorCarsNum);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtElevatorCarsNum.requestFocus();
                Utils.showKeyboard(getActivity(), true, edtElevatorCarsNum);
            }
        });
    }

    private void updateUIs(){
        edtElevatorCarsNum.setText("");
        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
        }

        if(bankData.getNumOfInteriorCar()>0)
            edtElevatorCarsNum.setText(bankData.getNumOfInteriorCar()+"");
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        int numOfCars = ConversionUtils.getIntegerFromEditText(edtElevatorCarsNum);
        if(numOfCars<=0){
            edtElevatorCarsNum.requestFocus();
            showToast(getString(R.string.valid_msg_input_number_of_car), Toast.LENGTH_SHORT);
            return;
        }
        MADSurveyApp.getInstance().setInteriorCarNum(0);
        MADSurveyApp.getInstance().getBankData().setNumOfInteriorCar(numOfCars);
        bankDataHandler.update(MADSurveyApp.getInstance().getBankData());
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_DESCRIPTION, "interior_car_description");
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
