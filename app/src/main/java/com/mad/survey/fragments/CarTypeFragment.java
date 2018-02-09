package com.mad.survey.fragments;

import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.mad.survey.MADSurveyApp;
import com.mad.survey.R;
import com.mad.survey.activities.BaseActivity;
import com.mad.survey.globals.GlobalConstant;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.BankData;
import com.mad.survey.models.CarData;
import com.mad.survey.utils.Utils;

public class CarTypeFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtCarNumber;
    private TextView txtSubTitle;

    private CheckBox chkPassengerElevator, chkServiceElevator;
    private CheckBox chkFreightElevator, chkOther;
    private EditText edtOtherName;
    private TextView btnNext;

    public static CarTypeFragment newInstance(){
        CarTypeFragment fragment = new CarTypeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_car_type, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);

        chkPassengerElevator = (CheckBox) parent.findViewById(R.id.chkPassengerElevator);
        chkServiceElevator = (CheckBox) parent.findViewById(R.id.chkServiceElevator);
        chkFreightElevator = (CheckBox) parent.findViewById(R.id.chkFreightElevator);
        chkOther = (CheckBox) parent.findViewById(R.id.chkOther);
        edtOtherName = (EditText) parent.findViewById(R.id.edtOtherName);
        btnNext = (TextView) parent.findViewById(R.id.btnNext);

        parent.findViewById(R.id.btnPassengerElevator).setOnClickListener(this);
        parent.findViewById(R.id.btnServiceElevator).setOnClickListener(this);
        parent.findViewById(R.id.btnFreightElevator).setOnClickListener(this);
        parent.findViewById(R.id.btnOther).setOnClickListener(this);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
    }

    private void updateUIs(){
        chkFreightElevator.setChecked(false);
        chkServiceElevator.setChecked(false);
        chkPassengerElevator.setChecked(false);
        chkOther.setChecked(false);
        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_edit_title, MADSurveyApp.getInstance().getCarData().getCarNumber()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_title, MADSurveyApp.getInstance().getCarNum()+1, bankData.getNumOfCar(),MADSurveyApp.getInstance().getCarData().getCarNumber()));
        }
        CarData carData =MADSurveyApp.getInstance().getCarData();
        String carType = carData.getDescription();
        if(!carType.equals("")) {
            switch (carType) {
                case GlobalConstant.CAR_SERVICE_ELEVATOR:
                    chkServiceElevator.setChecked(true);
                    break;
                case GlobalConstant.CAR_PASSENGER_ELEVATOR:
                    chkPassengerElevator.setChecked(true);
                    break;
                case GlobalConstant.CAR_FREIGHT_ELEVATOR:
                    chkFreightElevator.setChecked(true);
                    break;
                default:
                    updateOtherUIs();
                    chkOther.setChecked(true);
                    edtOtherName.setText(carType);
                    break;
            }
            edtOtherName.setVisibility(chkOther.isChecked()? View.VISIBLE:View.GONE);
            btnNext.setVisibility(chkOther.isChecked()? View.VISIBLE:View.GONE);
        }
    }

    private void goToNext(String carType){
        if (!isLastFocused()) return;

        MADSurveyApp.getInstance().getCarData().setDescription(carType);
        carDataHandler.update(MADSurveyApp.getInstance().getCarData());
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_CAR_BACKDOOR, "car_backdoor");
    }

    private void updateOtherUIs(){
        chkPassengerElevator.setChecked(false);
        chkServiceElevator.setChecked(false);
        chkFreightElevator.setChecked(false);
        chkOther.setChecked(!chkOther.isChecked());
        edtOtherName.setVisibility(chkOther.isChecked() ? View.VISIBLE : View.GONE);
        edtOtherName.requestFocus();
        Utils.showKeyboard(getActivity(), true, edtOtherName);
        btnNext.setVisibility(chkOther.isChecked()? View.VISIBLE:View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.btnNext:
                goToNext(edtOtherName.getText().toString());
                break;
            case R.id.btnOther:
                updateOtherUIs();
                break;
            case R.id.btnPassengerElevator:
                goToNext(GlobalConstant.CAR_PASSENGER_ELEVATOR);
                break;
            case R.id.btnFreightElevator:
                goToNext(GlobalConstant.CAR_FREIGHT_ELEVATOR);
                break;
            case R.id.btnServiceElevator:
                goToNext(GlobalConstant.CAR_SERVICE_ELEVATOR);
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
