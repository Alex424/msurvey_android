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
import com.mad.survey.globals.GlobalConstant;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.BankData;
import com.mad.survey.models.CarData;

public class CarBackdoorFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtSubTitle;
    private TextView txtCarNumber;
    private CheckBox chkYes,chkNo;

    public static CarBackdoorFragment newInstance(){
        CarBackdoorFragment fragment = new CarBackdoorFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_car_backdoor, container, false);

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
        int yes = carData.getIsThereRearDoor();
        if(yes == 1)
            chkYes.setChecked(true);
        else if(yes == 2)
            chkNo.setChecked(true);
    }

    private void goToNext(int yes){
        if (!isLastFocused()) return;

        if(yes == 2){
            MADSurveyApp.getInstance().getCarData().setRearDoorOpeningHeight(-1);
            MADSurveyApp.getInstance().getCarData().setRearDoorSlideJambWidth(-1);
            MADSurveyApp.getInstance().getCarData().setRearDoorStrikeJambWidth(-1);
        }
        MADSurveyApp.getInstance().getCarData().setIsThereRearDoor(yes);
        carDataHandler.update(MADSurveyApp.getInstance().getCarData());

        // If selected No back door then, just skip the next screen, and set the coinciding as NO
        if (yes == 2) {
            MADSurveyApp.getInstance().getCarData().setDoorsCoinciding(GlobalConstant.CAR_DOOR_NON_COINCIDING);
            carDataHandler.update(MADSurveyApp.getInstance().getCarData());
            ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_CAR_PUSHBUTTONS, "car_pushbuttons");
        }else{
            MADSurveyApp.getInstance().getCarData().setDoorsCoinciding("");
            ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_CAR_DOOR_COINCIDING, "car_door_coinciding");
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
