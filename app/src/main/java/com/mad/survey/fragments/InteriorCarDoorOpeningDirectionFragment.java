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
import com.mad.survey.models.InteriorCarDoorData;

public class InteriorCarDoorOpeningDirectionFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtCarNumber;
    private TextView txtSubTitle;
    private CheckBox chkLeft,chkRight;

    public static final int DIRECTION_LEFT = 1;
    public static final int DIRECTION_RIGHT = 2;

    public static InteriorCarDoorOpeningDirectionFragment newInstance(){
        InteriorCarDoorOpeningDirectionFragment fragment = new InteriorCarDoorOpeningDirectionFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_interior_car_door_opening_direction, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);
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
        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_edit_title, MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_title, MADSurveyApp.getInstance().getInteriorCarNum()+1, bankData.getNumOfInteriorCar(),MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }
        InteriorCarDoorData interiorCarDoorData = MADSurveyApp.getInstance().getInteriorCarDoorData();
        int direction = interiorCarDoorData.getCarDoorOpeningDirection();
        if(direction == DIRECTION_LEFT)
            chkLeft.setChecked(true);
        else if(direction == DIRECTION_RIGHT)
            chkRight.setChecked(true);
    }

    private void goToNext(int direction){
        if (!isLastFocused()) return;

        MADSurveyApp.getInstance().getInteriorCarDoorData().setCarDoorOpeningDirection(direction);
        interiorCarDoorDataHandler.update(MADSurveyApp.getInstance().getInteriorCarDoorData());

        if (MADSurveyApp.getInstance().getInteriorCarDoorData().getWallType().equals(GlobalConstant.WALL_TYPE_5_PIECE)){
            ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_LTRANSOM, "interior_car_l_transom");
        }else{
            if (BaseActivity.TEMP_DOOR_TYPE == 1) {
                ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_TRANSOM_2S, "interior_car_transom_2s");
            }else if (BaseActivity.TEMP_DOOR_TYPE == 2){
                ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_TRANSOM_1S, "interior_car_transom_1s");
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.btnToLeft:
                goToNext(DIRECTION_LEFT);
                break;
            case R.id.btnToRight:
                goToNext(DIRECTION_RIGHT);
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
