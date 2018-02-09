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
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.BankData;
import com.mad.survey.models.InteriorCarData;
import com.mad.survey.utils.ConversionUtils;
import com.mad.survey.utils.Utils;

public class InteriorCarLicenseFragment extends BaseFragment implements View.OnClickListener, OnFragmentResumedListener {

    private TextView txtCarNumber;
    private EditText edtInstallationNumber, edtCarCapacity, edtNoOfPeople;
    private TextView txtUnit;
    private EditText edtCarWeight;

    public static InteriorCarLicenseFragment newInstance() {
        InteriorCarLicenseFragment fragment = new InteriorCarLicenseFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_interior_car_license, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent) {
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);
        edtInstallationNumber = (EditText) parent.findViewById(R.id.edtInstallationNumber);
        edtCarCapacity = (EditText) parent.findViewById(R.id.edtCarCapacity);
        edtNoOfPeople = (EditText) parent.findViewById(R.id.edtNoOfPeople);
        txtUnit = (TextView) parent.findViewById(R.id.btnUnit);
        edtCarWeight = (EditText) parent.findViewById(R.id.edtCarWeight);

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

        setHeaderScrollConfiguration(parent, getString(R.string.sub_title_cab_interior), getString(R.string.sub_title_car_license), true, true);
    }

    private void updateUIs() {
        edtCarCapacity.setText("");
        edtCarWeight.setText("");
        edtInstallationNumber.setText("");
        edtNoOfPeople.setText("");

        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()) {
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_edit_title, MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        } else {
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_title, MADSurveyApp.getInstance().getInteriorCarNum() + 1, bankData.getNumOfInteriorCar(), MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }
        InteriorCarData interiorCarData = MADSurveyApp.getInstance().getInteriorCarData();

        if (interiorCarData.getCarCapacity() >= 0)
            edtCarCapacity.setText(interiorCarData.getCarCapacity() + "");
        if (interiorCarData.getCarWeight() >= 0)
            edtCarWeight.setText(interiorCarData.getCarWeight() + "");
        if (interiorCarData.getNumberOfPeople() > 0)
            edtNoOfPeople.setText(interiorCarData.getNumberOfPeople() + "");
        txtUnit.setText(interiorCarData.getWeightScale());
        edtInstallationNumber.setText(interiorCarData.getInstallNumber());
    }

    private void goToNext() {
        if (!isLastFocused()) return;

        double carCapacity = ConversionUtils.getDoubleFromEditText(edtCarCapacity);
        double carWeight = ConversionUtils.getDoubleFromEditText(edtCarWeight);
        int numberOfPeople = ConversionUtils.getIntegerFromEditText(edtNoOfPeople);
        String installNumber = edtInstallationNumber.getText().toString();
        if (installNumber.equals("")) {
            edtInstallationNumber.requestFocus();
            showToast(getString(R.string.valid_msg_input_car_install_no), Toast.LENGTH_SHORT);
            return;
        }
        if (carCapacity < 0) {
            edtCarCapacity.requestFocus();
            showToast(getString(R.string.valid_msg_input_car_capacity), Toast.LENGTH_SHORT);
            return;
        }
        if (numberOfPeople <= 0) {
            edtNoOfPeople.requestFocus();
            showToast(getString(R.string.valid_msg_input_car_number_people), Toast.LENGTH_SHORT);
            return;
        }
        if (carWeight < 0) {
            edtCarWeight.requestFocus();
            showToast(getString(R.string.valid_msg_input_car_weight), Toast.LENGTH_SHORT);
            return;
        }


        InteriorCarData interiorCarData = MADSurveyApp.getInstance().getInteriorCarData();
        interiorCarData.setWeightScale(txtUnit.getText().toString());
        interiorCarData.setCarWeight(carWeight);
        interiorCarData.setCarCapacity(carCapacity);
        interiorCarData.setNumberOfPeople(numberOfPeople);
        interiorCarData.setInstallNumber(installNumber);
        MADSurveyApp.getInstance().setInteriorCarData(interiorCarData);
        interiorCarDataHandler.update(interiorCarData);

        boolean goToInteriorCarCopy = false;
        if (MADSurveyApp.getInstance().isEditMode()){
            if (MADSurveyApp.getInstance().isInteriorAddFromEdit()
                && interiorCarDataHandler.getInteriorCarCountInBank(MADSurveyApp.getInstance().getProjectData().getId(),
                MADSurveyApp.getInstance().getBankNum()) > 1)
                goToInteriorCarCopy = true;
        }else{
            if (interiorCarDataHandler.getInteriorCarCountInBank(MADSurveyApp.getInstance().getProjectData().getId(),
                    MADSurveyApp.getInstance().getBankNum()) > 1)
                goToInteriorCarCopy = true;
        }

        if (goToInteriorCarCopy) {
            ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_COPY, "interior_car_copy");
        }else{
            ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_BACKDOOR, "interior_car_backdoor");
        }
    }

    CarUnitSelectDialog dlg;

    private void goToSelectUnit() {
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
