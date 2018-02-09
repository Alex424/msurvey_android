package com.mad.survey.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mad.survey.MADSurveyApp;
import com.mad.survey.R;
import com.mad.survey.activities.BaseActivity;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.BankData;
import com.mad.survey.models.CarData;
import com.mad.survey.models.ProjectData;

public class CarSeperatePIFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtSubTitle;
    private TextView txtCarNumber;
    private CheckBox chkYes,chkNo;

    public static CarSeperatePIFragment newInstance(){
        CarSeperatePIFragment fragment = new CarSeperatePIFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_car_seperatepi, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);
        chkYes = (CheckBox) parent.findViewById(R.id.chkYes);
        chkNo = (CheckBox) parent.findViewById(R.id.chkNo);

        parent.findViewById(R.id.btnYes).setOnClickListener(this);
        parent.findViewById(R.id.btnNo).setOnClickListener(this);
        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
    }

    private void updateUIs(){
        chkYes.setChecked(false);
        chkNo.setChecked(false);

        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_edit_title, MADSurveyApp.getInstance().getCarData().getCarNumber()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_title, MADSurveyApp.getInstance().getCarNum()+1, bankData.getNumOfCar(),MADSurveyApp.getInstance().getCarData().getCarNumber()));
        }

        CarData carData = MADSurveyApp.getInstance().getCarData();
        int yes = carData.getIsThereSPI();
        if(yes == 1)
            chkYes.setChecked(true);
        else if(yes == 2)
            chkNo.setChecked(true);

    }

    private void goToNext(int yes){
        if (!isLastFocused()) return;

        MADSurveyApp.getInstance().getCarData().setIsThereSPI(yes);
        carDataHandler.update(MADSurveyApp.getInstance().getCarData());
        ProjectData projectData = MADSurveyApp.getInstance().getProjectData();
        if(yes == 1)
            ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_CAR_SEPERATEPI_MOUNTING, "car_seperatepi_mounting");
        else if(yes == 2) {
            if(MADSurveyApp.getInstance().isEditMode()){
                ((BaseActivity) getActivity()).backToSpecificFragment("edit_car_list");
            }
            else{
                int currentCar = MADSurveyApp.getInstance().getCarNum();
                BankData bankData = MADSurveyApp.getInstance().getBankData();
                int numCars = bankData.getNumOfCar();
                if(numCars > currentCar + 1 ){
                    MADSurveyApp.getInstance().setCarNum(currentCar + 1);
                    ((BaseActivity) getActivity()).backToSpecificFragment("car_description");
                }
                else{
                    if(projectData.getCabInteriors() == 1)
                        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_COUNT, "interior_car_count");
                    else if(projectData.getHallEntrances() == 1) {
                        MADSurveyApp.getInstance().setFloorNum(0);
                        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_HALL_ENTRANCE_FLOOR_IDENTIFIER, "hall_entrance_floor_description");
                    }
                    else {
                        int numBanks = MADSurveyApp.getInstance().getProjectData().getNumBanks();
                        int currentBank = MADSurveyApp.getInstance().getBankNum();
                        if (numBanks > currentBank + 1) {
                            MADSurveyApp.getInstance().setFloorNum(0);
                            MADSurveyApp.getInstance().setBankNum(currentBank + 1);
                            ((BaseActivity) getActivity()).backToSpecificFragment("bank_name");
                        } else {
                            ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_PROJECT_ALL_ENTERED, "project_all_entered");
                        }
                    }
                }
            }

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.btnYes:
                goToNext(1);
                break;
            case R.id.btnNo:
                goToNext(2);
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
