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
import com.mad.survey.models.HallStationData;
import com.mad.survey.models.InteriorCarData;
import com.mad.survey.models.InteriorCarDoorData;
import com.mad.survey.models.ProjectData;

public class InteriorCarCopyFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private CheckBox chkUnique, chkSameAsLast, chkSameAsExisting;
    private TextView txtCarNumber;
    private TextView txtSubTitle;

    public static InteriorCarCopyFragment newInstance(){
        InteriorCarCopyFragment fragment = new InteriorCarCopyFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_interior_car_copy, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);
        chkUnique = (CheckBox) parent.findViewById(R.id.chkUnique);
        chkSameAsLast = (CheckBox) parent.findViewById(R.id.chkSameAsLast);
        chkSameAsExisting = (CheckBox) parent.findViewById(R.id.chkSameAsExisting);

        parent.findViewById(R.id.btnUnique).setOnClickListener(this);
        parent.findViewById(R.id.btnSameAsLast).setOnClickListener(this);
        parent.findViewById(R.id.btnSameAsExisting).setOnClickListener(this);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
    }

    private void updateUIs(){
        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_edit_title, MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_title, MADSurveyApp.getInstance().getInteriorCarNum()+1, bankData.getNumOfInteriorCar(),MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }

    }

    private void doUnique(){
        InteriorCarData currentInteriorCarData = MADSurveyApp.getInstance().getInteriorCarData();

        String desc = currentInteriorCarData.getCarDescription();
        String installNo = currentInteriorCarData.getInstallNumber();
        double capacity = currentInteriorCarData.getCarCapacity();
        int noOfPeople = currentInteriorCarData.getNumberOfPeople();
        String scale = currentInteriorCarData.getWeightScale();
        double weight = currentInteriorCarData.getCarWeight();

        interiorCarDataHandler.delete(currentInteriorCarData);

        InteriorCarData interiorCarData = interiorCarDataHandler.insertNewInteriorCarWithDescription(MADSurveyApp.getInstance().getProjectData().getId(),
                MADSurveyApp.getInstance().getBankNum(),
                MADSurveyApp.getInstance().getInteriorCarNum(),
                desc);

        interiorCarData.setInstallNumber(installNo);
        interiorCarData.setCarCapacity(capacity);
        interiorCarData.setNumberOfPeople(noOfPeople);
        interiorCarData.setWeightScale(scale);
        interiorCarData.setCarWeight(weight);

        interiorCarDataHandler.update(interiorCarData);
        MADSurveyApp.getInstance().setInteriorCarData(interiorCarData);

        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_BACKDOOR, "interior_car_backdoor");
    }

    private void doSameAsLast(){
        // Get the last interior car of this current bank.
        // modified by Alex 2017/11/20
        InteriorCarData previousInteriorCarData = interiorCarDataHandler.getPreviousCarDataForSameAsLast(MADSurveyApp.getInstance().getProjectData().getId(),
                MADSurveyApp.getInstance().getBankNum());
        copySameAsInteriorCarData(previousInteriorCarData);
    }

    private void doSameAsExisting(){
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_LIST, "interior_car_list");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.btnUnique:
                doUnique();
                break;
            case R.id.btnSameAsLast:
                doSameAsLast();
                break;
            case R.id.btnSameAsExisting:
                doSameAsExisting();
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
