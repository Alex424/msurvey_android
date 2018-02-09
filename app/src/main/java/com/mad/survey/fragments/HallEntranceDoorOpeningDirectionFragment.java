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
import com.mad.survey.models.HallEntranceData;

public class HallEntranceDoorOpeningDirectionFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtCarNumber;
    private TextView txtSubTitle;
    private TextView txtFloorIdentifier;
    private CheckBox chkLeft,chkRight;

    public static HallEntranceDoorOpeningDirectionFragment newInstance(){
        HallEntranceDoorOpeningDirectionFragment fragment = new HallEntranceDoorOpeningDirectionFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_hall_entrance_door_opening_direction, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);
        txtFloorIdentifier = (TextView) parent.findViewById(R.id.txtFloorIdentifier);
        chkLeft = (CheckBox) parent.findViewById(R.id.chkToLeft);
        chkRight = (CheckBox) parent.findViewById(R.id.chkToRight);

        parent.findViewById(R.id.btnToLeft).setOnClickListener(this);
        parent.findViewById(R.id.btnToRight).setOnClickListener(this);
        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
    }

    private void updateUIs(){
        chkLeft.setChecked(false);
        chkRight.setChecked(false);

        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtFloorIdentifier.setText(getString(R.string.floor_n_identifier_edit_title, MADSurveyApp.getInstance().getFloorDescriptor()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtFloorIdentifier.setText(getString(R.string.floor_n_identifier_title, MADSurveyApp.getInstance().getFloorNum() + 1, MADSurveyApp.getInstance().getProjectData().getNumFloors(), MADSurveyApp.getInstance().getFloorDescriptor()));
            txtCarNumber.setText(getString(R.string.car_n_title, MADSurveyApp.getInstance().getHallEntranceCarNum()+1, MADSurveyApp.getInstance().getBankData().getNumOfCar()));
        }

        HallEntranceData hallEntranceData = MADSurveyApp.getInstance().getHallEntranceData();
        int direction  = hallEntranceData.getDirection();
        if(direction == 1){
            chkLeft.setChecked(true);
        }
        else if(direction == 2){
            chkRight.setChecked(true);
        }
    }

    private void goToNext(int direction ){
        if (!isLastFocused()) return;

        MADSurveyApp.getInstance().getHallEntranceData().setDirection(direction);
        hallEntranceDataHandler.update(MADSurveyApp.getInstance().getHallEntranceData());
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_HALL_ENTRANCE_FRONT_RETURN_MEASUREMENTS, "hall_entrnace_front_return_measurements");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.btnToLeft:
                goToNext(1);
                break;
            case R.id.btnToRight:
                goToNext(2);
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
