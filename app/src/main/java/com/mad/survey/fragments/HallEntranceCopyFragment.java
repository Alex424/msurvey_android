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
import com.mad.survey.models.InteriorCarData;

public class HallEntranceCopyFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private CheckBox chkUnique, chkSameAsLast, chkSameAsExisting;
    private TextView txtCarNumber;
    private TextView txtSubTitle;
    private TextView txtFloorIdentifier;

    private View parent;

    public static HallEntranceCopyFragment newInstance(){
        HallEntranceCopyFragment fragment = new HallEntranceCopyFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_hall_entrance_copy, container, false);
        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);
        txtFloorIdentifier = (TextView) parent.findViewById(R.id.txtFloorIdentifier);
        chkUnique = (CheckBox) parent.findViewById(R.id.chkUnique);
        chkSameAsLast = (CheckBox) parent.findViewById(R.id.chkSameAsLast);
        chkSameAsExisting = (CheckBox) parent.findViewById(R.id.chkSameAsExisting);

        parent.findViewById(R.id.btnUnique).setOnClickListener(this);
        parent.findViewById(R.id.btnSameAsLast).setOnClickListener(this);
        parent.findViewById(R.id.btnSameAsExisting).setOnClickListener(this);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
    }

    private void updateUIs(){
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtFloorIdentifier.setText(getString(R.string.floor_n_identifier_edit_title, MADSurveyApp.getInstance().getFloorDescriptor()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtFloorIdentifier.setText(getString(R.string.floor_n_identifier_title, MADSurveyApp.getInstance().getFloorNum() + 1, MADSurveyApp.getInstance().getProjectData().getNumFloors(), MADSurveyApp.getInstance().getFloorDescriptor()));
            txtCarNumber.setText(getString(R.string.car_n_title, MADSurveyApp.getInstance().getHallEntranceCarNum()+1, MADSurveyApp.getInstance().getBankData().getNumOfCar()));
        }

        //Added by Alex - 2018/01/24
        if (!MADSurveyApp.getInstance().isEditMode() && MADSurveyApp.getInstance().getHallEntranceCarNum() > 0){
            ((BaseActivity) getActivity()).setBackable(false);
            parent.findViewById(R.id.btnBack).setVisibility(View.INVISIBLE);
        }
    }

    private void doUnique(){
        MADSurveyApp.getInstance().setHallEntranceData(null);

        HallEntranceData hallEntranceData = hallEntranceDataHandler.get(MADSurveyApp.getInstance().getProjectData().getId(),
                MADSurveyApp.getInstance().getBankNum(),
                MADSurveyApp.getInstance().getFloorDescriptor(),
                MADSurveyApp.getInstance().getHallEntranceCarNum());
        if (hallEntranceData != null){
            hallEntranceDataHandler.delete(hallEntranceData);
        }

        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_HALL_ENTRANCE_DOOR_TYPE, "hall_entrance_door_type");
    }

    private void doSameAsLast(){
        // Get the last hall entrance of this current bank.
        // modified by Alex 2017/11/20

        HallEntranceData previousHallEntranceData = hallEntranceDataHandler.getHallEntranceDataForSameAsLast(MADSurveyApp.getInstance().getProjectData().getId(),
                MADSurveyApp.getInstance().getBankNum());

        if (previousHallEntranceData == null) return;

        // add new hall entrance with same data of previous one
        previousHallEntranceData.setFloorDescription(MADSurveyApp.getInstance().getFloorDescriptor());
        previousHallEntranceData.setCarNum(MADSurveyApp.getInstance().getHallEntranceCarNum());
        long nId = hallEntranceDataHandler.insert(previousHallEntranceData);
        previousHallEntranceData.setId((int) nId);

        MADSurveyApp.getInstance().setHallEntranceData(previousHallEntranceData);

        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_HALL_ENTRANCE_DOOR_TYPE, "hall_entrance_door_type");
    }

    private void doSameAsExisting(){
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_HALL_ENTRANCE_LIST, "hall_entrance_list");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.btnUnique:
                doUnique();
                break;
            case R.id.btnSameAsLast:
                doSameAsLast();
                break;
            case R.id.btnSameAsExisting:
                doSameAsExisting();
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
