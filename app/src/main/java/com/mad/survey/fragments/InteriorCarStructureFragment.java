package com.mad.survey.fragments;

import android.os.Bundle;
import android.provider.Settings;
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
import com.mad.survey.models.InteriorCarData;

public class InteriorCarStructureFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtCarNumber;
    private TextView txtSubTitle;
    private CheckBox chkYes,chkNo,chkTBD;

    public static InteriorCarStructureFragment newInstance(){
        InteriorCarStructureFragment fragment = new InteriorCarStructureFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_interior_car_structure, container, false);

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
        chkTBD = (CheckBox) parent.findViewById(R.id.chkTBD);


        parent.findViewById(R.id.btnYes).setOnClickListener(this);
        parent.findViewById(R.id.btnNo).setOnClickListener(this);
        parent.findViewById(R.id.btnTBD).setOnClickListener(this);
        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
    }

    private void updateUIs(){
        chkYes.setChecked(false);
        chkNo.setChecked(false);
        chkTBD.setChecked(false);
        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_edit_title, MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_title, MADSurveyApp.getInstance().getInteriorCarNum()+1, bankData.getNumOfInteriorCar(),MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }
        InteriorCarData interiorCarData = MADSurveyApp.getInstance().getInteriorCarData();
        String birdCage = interiorCarData.getBirdCage();
        switch (birdCage){
            case GlobalConstant.BIRD_CAGE_YES:
                chkYes.setChecked(true);
                break;
            case GlobalConstant.BIRD_CAGE_NO:
                chkNo.setChecked(true);
                break;
            case GlobalConstant.BIRD_CAGE_TBD:
                chkTBD.setChecked(true);
                break;
        }
    }

    private void goToNext(String birdCage){
        if (!isLastFocused()) return;


        //---------------------------set TEMP_DOOR_STYLE = Front Door
        BaseActivity.TEMP_DOOR_STYLE = 1;
        //------------------------------------------------------------

        MADSurveyApp.getInstance().getInteriorCarData().setBirdCage(birdCage);
        interiorCarDataHandler.update(MADSurveyApp.getInstance().getInteriorCarData());
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_WALL_TYPE, "interior_car_wall_type");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.btnYes:
                goToNext(GlobalConstant.BIRD_CAGE_YES);
                break;
            case R.id.btnNo:
                goToNext(GlobalConstant.BIRD_CAGE_NO);
                break;
            case R.id.btnTBD:
                goToNext(GlobalConstant.BIRD_CAGE_TBD);
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
