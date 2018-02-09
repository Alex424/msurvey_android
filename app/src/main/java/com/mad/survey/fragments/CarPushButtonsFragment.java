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
import com.mad.survey.models.CarData;
import com.mad.survey.utils.ConversionUtils;
import com.mad.survey.utils.Utils;

public class CarPushButtonsFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtSubTitle;
    private TextView txtCarNumber;
    private EditText edtNoOfPushButtons, edtFloorMarkings;

    public static CarPushButtonsFragment newInstance(){
        CarPushButtonsFragment fragment = new CarPushButtonsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_car_pushbuttons, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);
        edtNoOfPushButtons = (EditText) parent.findViewById(R.id.edtNoOfPushButtons);
        edtFloorMarkings = (EditText) parent.findViewById(R.id.edtFloorMarkings);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
        parent.findViewById(R.id.btnHelp).setOnClickListener(this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtNoOfPushButtons.requestFocus();
                Utils.showKeyboard(getActivity(), true, edtNoOfPushButtons);
            }
        });
    }

    private void updateUIs(){
        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_edit_title, MADSurveyApp.getInstance().getCarData().getCarNumber()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_title, MADSurveyApp.getInstance().getCarNum()+1, bankData.getNumOfCar(),MADSurveyApp.getInstance().getCarData().getCarNumber()));
        }
        CarData carData = MADSurveyApp.getInstance().getCarData();
            if(carData.getNumberOfOpenings()>0)
                edtNoOfPushButtons.setText(carData.getNumberOfOpenings()+"");
            edtFloorMarkings.setText(carData.getFloorMarkings());
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        int numPushButtons = ConversionUtils.getIntegerFromEditText(edtNoOfPushButtons);
        String floorMarking = edtFloorMarkings.getText().toString();
        if(numPushButtons <= 0){
            edtNoOfPushButtons.requestFocus();
            showToast(getString(R.string.valid_msg_input_car_number_pushbutton), Toast.LENGTH_SHORT);
            return;
        }
        if(floorMarking.equals("")){
            edtFloorMarkings.requestFocus();
            showToast(getString(R.string.valid_msg_input_car_floor_marking), Toast.LENGTH_SHORT);
            return;
        }
        MADSurveyApp.getInstance().getCarData().setNumberOfOpenings(numPushButtons);
        MADSurveyApp.getInstance().getCarData().setFloorMarkings(floorMarking);
        carDataHandler.update(MADSurveyApp.getInstance().getCarData());
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_CAR_DOOR_MEASUREMENTS, "car_door_measurements");
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
            case R.id.btnHelp:
                showHelpDialog(getActivity(), getString(R.string.help_title_car_push_buttons), R.drawable.img_help_27_car_pushbuttons_help, GlobalConstant.HELP_PHOTO_SIZE_RATE);
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
