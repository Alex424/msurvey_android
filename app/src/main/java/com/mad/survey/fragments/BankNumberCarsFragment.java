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
import com.mad.survey.models.ProjectData;
import com.mad.survey.utils.ConversionUtils;
import com.mad.survey.utils.Utils;

public class BankNumberCarsFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtSubTitle;
    private EditText edtNumberCars;

    public static BankNumberCarsFragment newInstance(){
        BankNumberCarsFragment fragment = new BankNumberCarsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_bank_number_cars, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        edtNumberCars = (EditText) parent.findViewById(R.id.edtNumberCars);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtNumberCars.requestFocus();
                Utils.showKeyboard(getActivity(), true, edtNumberCars);
            }
        });
    }

    private void updateUIs(){
        edtNumberCars.setText("");
        ProjectData projectData = MADSurveyApp.getInstance().getProjectData();
        int currentBank = MADSurveyApp.getInstance().getBankNum();
        int numBanks = projectData.getNumBanks();
        if(numBanks>0)
            txtSubTitle.setText(getString(R.string.bank_description_n_title, currentBank+1, numBanks));
        if(MADSurveyApp.getInstance().getBankData().getNumOfCar()>0)
            edtNumberCars.setText(MADSurveyApp.getInstance().getBankData().getNumOfCar()+"");
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        int numCars = ConversionUtils.getIntegerFromEditText(edtNumberCars);
        if (numCars <= 0){
            showToast(getString(R.string.valid_msg_input_cars_count), Toast.LENGTH_SHORT);
            return;
        }

        MADSurveyApp.getInstance().getBankData().setNumOfCar(numCars);
        bankDataHandler.update(MADSurveyApp.getInstance().getBankData());
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_BANK_NUMBER_RISERS, "bank_number_risers");
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
