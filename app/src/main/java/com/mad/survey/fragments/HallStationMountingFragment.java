package com.mad.survey.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.mad.survey.MADSurveyApp;
import com.mad.survey.R;
import com.mad.survey.activities.BaseActivity;
import com.mad.survey.globals.GlobalConstant;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.BankData;
import com.mad.survey.models.HallStationData;
import com.mad.survey.models.ProjectData;

public class HallStationMountingFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtFloorIdentifier, txtHallStationDescriptor;
    private TextView txtSubTitle;

    private CheckBox chkFlushMounting, chkSurfaceMounting;

    private static boolean flag = false;

    public static HallStationMountingFragment newInstance(){
        HallStationMountingFragment fragment = new HallStationMountingFragment();
        return fragment;
    }
    public static HallStationMountingFragment newInstance(boolean tag){
        flag = tag;
        HallStationMountingFragment fragment = new HallStationMountingFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_hallstation_mounting, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        txtFloorIdentifier = (TextView) parent.findViewById(R.id.txtFloorIdentifier);
        txtHallStationDescriptor = (TextView) parent.findViewById(R.id.txtHallStationDescriptor);

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
        ProjectData projectData = MADSurveyApp.getInstance().getProjectData();
        BankData bankData = MADSurveyApp.getInstance().getBankData();
        HallStationData hallStationData = MADSurveyApp.getInstance().getHallStationData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtFloorIdentifier.setText(getString(R.string.floor_n_identifier_edit_title, MADSurveyApp.getInstance().getFloorDescriptor()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtFloorIdentifier.setText(getString(R.string.floor_n_identifier_title, MADSurveyApp.getInstance().getFloorNum() + 1, projectData.getNumFloors(), MADSurveyApp.getInstance().getFloorDescriptor()));
            txtHallStationDescriptor.setText(getString(R.string.hall_station_descriptor_n_title,  MADSurveyApp.getInstance().getHallStationNum()+1, bankData.getNumOfRiser()));
        }

        String mount = hallStationData.getMount();
        if (mount.equals(GlobalConstant.FLUSH_MOUNT))
            chkFlushMounting.setChecked(true);
        else if (mount.equals(GlobalConstant.SURFACE_MOUNT))
            chkSurfaceMounting.setChecked(true);


    }

    private void goToNext(String mount){
        if (!isLastFocused()) return;

        MADSurveyApp.getInstance().getHallStationData().setMount(mount);
        hallStationDataHandler.update(MADSurveyApp.getInstance().getHallStationData());
        if(flag){
            flag = false;
            getActivity().onBackPressed();
        }
        else {
            ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_HALLSTATION_WALLMATERIAL, "hallstation_wallmaterial");
        }
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
