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

public class CarSeperatePIMountingFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtCarNumber;
    private TextView txtSubTitle;
    private CheckBox chkFlushMounting,chkSurfaceMounting;

    public static CarSeperatePIMountingFragment newInstance(){
        CarSeperatePIMountingFragment fragment = new CarSeperatePIMountingFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_car_seperatepi_mounting, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);
        chkFlushMounting = (CheckBox) parent.findViewById(R.id.chkFlushMounting);
        chkSurfaceMounting = (CheckBox) parent.findViewById(R.id.chkSurfaceMounting);

        parent.findViewById(R.id.btnFlushMounting).setOnClickListener(this);
        parent.findViewById(R.id.btnSurfaceMounting).setOnClickListener(this);
        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
    }

    private void updateUIs(){
        chkFlushMounting.setChecked(false);
        chkSurfaceMounting.setChecked(false);
        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_edit_title, MADSurveyApp.getInstance().getCarData().getCarNumber()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_title, MADSurveyApp.getInstance().getCarNum()+1, bankData.getNumOfCar(),MADSurveyApp.getInstance().getCarData().getCarNumber()));
        }

        CarData carData = MADSurveyApp.getInstance().getCarData();
        String mount = carData.getMountSPI();
        if (mount.equals(GlobalConstant.FLUSH_MOUNT) )
            chkFlushMounting.setChecked(true);
        else if (mount.equals(GlobalConstant.SURFACE_MOUNT) )
            chkSurfaceMounting.setChecked(true);

    }

    private void goToNext(String mount){
        if (!isLastFocused()) return;

        CarData carData = MADSurveyApp.getInstance().getCarData();
        carData.setMountSPI(mount);
        MADSurveyApp.getInstance().setCarData(carData);
        carDataHandler.update(carData);
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_CAR_SEPERATEPI_MEASUREMENTS, "car_seperatepi_measurements");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.btnFlushMounting:
                goToNext(GlobalConstant.FLUSH_MOUNT);
                break;
            case R.id.btnSurfaceMounting:
                goToNext(GlobalConstant.SURFACE_MOUNT);
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
