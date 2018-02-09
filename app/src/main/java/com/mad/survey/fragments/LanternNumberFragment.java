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
import com.mad.survey.models.HallStationData;
import com.mad.survey.models.LanternData;
import com.mad.survey.models.ProjectData;
import com.mad.survey.models.handlers.LanternDataHandler;
import com.mad.survey.utils.ConversionUtils;
import com.mad.survey.utils.Utils;

public class LanternNumberFragment extends BaseFragment implements View.OnClickListener,OnFragmentResumedListener {

    private TextView txtFloorIdentifier;
    private TextView txtSubTitle;
    private EditText edtLanternNumber;

    public static LanternNumberFragment newInstance(){
        LanternNumberFragment fragment = new LanternNumberFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_lantern_number, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        txtFloorIdentifier = (TextView) parent.findViewById(R.id.txtFloorIdentifier);
        edtLanternNumber = (EditText) parent.findViewById(R.id.edtLanternNumber);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtLanternNumber.requestFocus();
                Utils.showKeyboard(getActivity(), true, edtLanternNumber);
            }
        });
    }

    private void updateUIs(){
        edtLanternNumber.setText("");
        ProjectData projectData = MADSurveyApp.getInstance().getProjectData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtFloorIdentifier.setText(getString(R.string.floor_n_identifier_edit_title, MADSurveyApp.getInstance().getFloorDescriptor()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title,MADSurveyApp.getInstance().getBankNum()+1,MADSurveyApp.getInstance().getBankData().getName()));
            txtFloorIdentifier.setText(getString(R.string.floor_n_identifier_title, MADSurveyApp.getInstance().getFloorNum()+1,projectData.getNumFloors() , MADSurveyApp.getInstance().getFloorDescriptor()));
        }

        int lanternNumber = lanternDataHandler.getLanternCntPerFloor(MADSurveyApp.getInstance().getProjectData().getId(),
                MADSurveyApp.getInstance().getBankNum(),
                MADSurveyApp.getInstance().getFloorDescriptor());
        if (lanternNumber > 0) {
            edtLanternNumber.setText(lanternNumber + "");
        }
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        ProjectData projectData;
        String str = edtLanternNumber.getText().toString();
        if(str.equals("")){
            edtLanternNumber.requestFocus();
            showToast(getString(R.string.valid_msg_input_lantern_value), Toast.LENGTH_SHORT);
            return;
        }
        int numLanterns = ConversionUtils.getIntegerFromEditText(edtLanternNumber);
        if(numLanterns == 0) {
            lanternDataHandler.insertNewLanternWithFloorNumber(MADSurveyApp.getInstance().getProjectData().getId(),
                        MADSurveyApp.getInstance().getBankNum(),//BankNum
                        GlobalConstant.EMPTY_LANTERN_RECORD_ID,//LanternNum
                        MADSurveyApp.getInstance().getFloorDescriptor());//FloorDescriptor

            projectData = MADSurveyApp.getInstance().getProjectData();
            int numFloors = projectData.getNumFloors();
            int currentFloor = MADSurveyApp.getInstance().getFloorNum();
            if(numFloors > currentFloor + 1) {
                MADSurveyApp.getInstance().setFloorNum(currentFloor + 1);
                ((BaseActivity) getActivity()).backToSpecificFragment("floor_description");
            }
            else{
                ////GoTo COPS if it is checked///////////
                if(projectData.getCops() == 1){
                    ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_CAR_DESCRIPTION, "car_description");
                }
                else if(projectData.getCabInteriors() == 1)
                    ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_COUNT, "interior_car_count");
                else if(projectData.getHallEntrances() == 1)
                    ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_HALL_ENTRANCE_DOOR_TYPE, "hall_entrance_door_type");

                else{
                    int numBanks = projectData.getNumBanks();
                    int currentBank = MADSurveyApp.getInstance().getBankNum();
                    if(numBanks > currentBank + 1){
                        MADSurveyApp.getInstance().setFloorNum(0);
                        MADSurveyApp.getInstance().setBankNum(currentBank + 1);
                        ((BaseActivity) getActivity()).backToSpecificFragment("bank_name");
                    }
                    else{
                        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_PROJECT_ALL_ENTERED, "project_all_entered");
                    }
                }
            }
        }
        else {
            MADSurveyApp.getInstance().setLanternNum(0);
            MADSurveyApp.getInstance().setLanternCnt(numLanterns);
            int lanternNumber = lanternDataHandler.getLanternCntPerBank(MADSurveyApp.getInstance().getProjectData().getId(),
                    MADSurveyApp.getInstance().getBankNum());

            if (lanternNumber == 0)
                ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_LANTERN_DESCRIPTION, "lantern_description");
            else
                ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_LANTERN, "lantern");
        }

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
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
