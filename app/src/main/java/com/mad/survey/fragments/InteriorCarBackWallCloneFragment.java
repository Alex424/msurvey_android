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
import com.mad.survey.models.BankData;
import com.mad.survey.models.InteriorCarData;
import com.mad.survey.models.InteriorCarDoorData;

public class InteriorCarBackWallCloneFragment extends BaseFragment implements View.OnClickListener {

    private CheckBox chkUnique, chkSameAsFrontReturn;
    private TextView txtSubTitle;
    private TextView txtCarNumber;

    public static InteriorCarBackWallCloneFragment newInstance(){
        InteriorCarBackWallCloneFragment fragment = new InteriorCarBackWallCloneFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_interior_car_back_wall_clone, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);
        chkUnique = (CheckBox) parent.findViewById(R.id.chkUnique);
        chkSameAsFrontReturn = (CheckBox) parent.findViewById(R.id.chkSameAsFrontReturn);

        parent.findViewById(R.id.btnUnique).setOnClickListener(this);
        parent.findViewById(R.id.btnSameAsFrontReturn).setOnClickListener(this);
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

    private void goToNext(){
        if (!isLastFocused()) return;

        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_BACK_WALL, "interior_car_backwall");
    }

    private void onClickUnique(){
        //set TEMP_DOOR_STYLE = BACK_DOOR
        BaseActivity.TEMP_DOOR_STYLE = 2;
        //-------------------------------
        ((BaseActivity) getActivity()).backToSpecificFragment("interior_car_wall_type");
    }

    private void onClickSameAs(){
        //set TEMP_DOOR_STYLE = BACK_DOOR
        BaseActivity.TEMP_DOOR_STYLE = 2;
        //-------------------------------
        InteriorCarDoorData frontDoor = MADSurveyApp.getInstance().getInteriorCarDoorData();
        InteriorCarDoorData backDoor = interiorCarDoorDataHandler.copyFrontToBackDoor(frontDoor);
        if(backDoor != null){
            MADSurveyApp.getInstance().getInteriorCarData().setBackDoorId(backDoor.getId());
            MADSurveyApp.getInstance().setInteriorCarDoorData(backDoor);
            //update interiorCarData
            //////////////////////////
            interiorCarDataHandler.update(MADSurveyApp.getInstance().getInteriorCarData());
        }
        ((BaseActivity) getActivity()).backToSpecificFragment("interior_car_wall_type");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.btnUnique:
                onClickUnique();
                break;
            case R.id.btnSameAsFrontReturn:
                onClickSameAs();
                break;
        }
    }
}
