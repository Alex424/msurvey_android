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
import com.mad.survey.dialogs.CarUnitSelectDialog;
import com.mad.survey.dialogs.PhotoSelectDialog;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.BankData;
import com.mad.survey.models.CarData;
import com.mad.survey.utils.ConversionUtils;
import com.mad.survey.utils.Utils;

public class CarLicenseFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtCarNumber;
    private EditText edtInstallationNumber, edtCarCapacity, edtNoOfPeople;
    private TextView txtUnit;

    public static CarLicenseFragment newInstance(){
        CarLicenseFragment fragment = new CarLicenseFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_car_license, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);
        edtInstallationNumber = (EditText) parent.findViewById(R.id.edtInstallationNumber);
        edtCarCapacity = (EditText) parent.findViewById(R.id.edtCarCapacity);
        edtNoOfPeople = (EditText) parent.findViewById(R.id.edtNoOfPeople);
        txtUnit = (TextView) parent.findViewById(R.id.btnUnit);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
        parent.findViewById(R.id.btnUnit).setOnClickListener(this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtInstallationNumber.requestFocus();
                Utils.showKeyboard(getActivity(), true, edtInstallationNumber);
            }
        });

        setHeaderScrollConfiguration(parent, getString(R.string.cops), getString(R.string.sub_title_car_license), true, true);
    }

    private void updateUIs(){

        edtInstallationNumber.setText("");
        edtNoOfPeople.setText("");
        edtCarCapacity.setText("");

        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_edit_title, MADSurveyApp.getInstance().getCarData().getCarNumber()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_title, MADSurveyApp.getInstance().getCarNum()+1, bankData.getNumOfCar(),MADSurveyApp.getInstance().getCarData().getCarNumber()));
        }
        CarData carData =MADSurveyApp.getInstance().getCarData();

        edtInstallationNumber.setText(carData.getInstallNumber());
        if(carData.getCapacityNumberPersons() > 0)
            edtNoOfPeople.setText(carData.getCapacityNumberPersons()+"");
        if(carData.getCapacityWeight() >= 0)
            edtCarCapacity.setText(carData.getCapacityWeight()+"");
        txtUnit.setText(carData.getWeightScale());
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        String installNo = edtInstallationNumber.getText().toString();
        double carCapacity = ConversionUtils.getDoubleFromEditText(edtCarCapacity);
        int numPeople = ConversionUtils.getIntegerFromEditText(edtNoOfPeople);
        if(installNo.equals("")){
            edtInstallationNumber.requestFocus();
            showToast(getString(R.string.valid_msg_input_car_install_no), Toast.LENGTH_SHORT);
            return;
        }
        if(carCapacity < 0){
            edtCarCapacity.requestFocus();
            showToast(getString(R.string.valid_msg_input_car_capacity), Toast.LENGTH_SHORT);
            return;
        }
        if(numPeople <= 0){
            edtNoOfPeople.requestFocus();
            showToast(getString(R.string.valid_msg_input_car_number_people), Toast.LENGTH_SHORT);
            return;
        }
        MADSurveyApp.getInstance().getCarData().setInstallNumber(installNo);
        MADSurveyApp.getInstance().getCarData().setCapacityWeight(carCapacity);
        MADSurveyApp.getInstance().getCarData().setCapacityNumberPersons(numPeople);
        MADSurveyApp.getInstance().getCarData().setWeightScale(txtUnit.getText().toString());
        carDataHandler.update(MADSurveyApp.getInstance().getCarData());
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_CAR_TYPE, "car_type");
    }

    CarUnitSelectDialog dlg;
    private void goToSelectUnit(){
        dlg = new CarUnitSelectDialog(getActivity(), this);
        dlg.show();
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
            case R.id.btnUnit:
                goToSelectUnit();
                break;
            case R.id.btnKG:
                txtUnit.setText(getString(R.string.car_capacity_unit_kg));

                dlg.dismiss();
                break;
            case R.id.btnLBS:
                txtUnit.setText(getString(R.string.car_capacity_unit_lbs));

                dlg.dismiss();
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
