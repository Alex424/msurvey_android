package com.mad.survey.fragments;

import android.os.Bundle;
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
import com.mad.survey.models.ProjectData;
import com.mad.survey.utils.ConversionUtils;

public class HallStationReviewFragment extends BaseFragment implements View.OnClickListener, OnFragmentResumedListener {

    private TextView txtFloorIdentifier, txtHallStationDescriptor;
    private TextView edtDescription;
    private TextView edtMounting;
    private TextView edtWallMaterial;
    private EditText edtCoverWidth;
    private EditText edtCoverHeight;
    private EditText edtScrewCenterWidth;
    private EditText edtScrewCenterHeight;
    private EditText edtAFFValue;


    public static HallStationReviewFragment newInstance(){
        HallStationReviewFragment fragment = new HallStationReviewFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_hallstation_review, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtFloorIdentifier = (TextView) parent.findViewById(R.id.txtFloorIdentifier);
        txtHallStationDescriptor = (TextView) parent.findViewById(R.id.txtHallStationDescriptor);
        edtDescription = (TextView) parent.findViewById(R.id.edtDescription);
        edtMounting = (TextView) parent.findViewById(R.id.edtMounting);
        edtWallMaterial = (TextView) parent.findViewById(R.id.edtWallMaterial);
        edtCoverWidth = (EditText) parent.findViewById(R.id.edtCoverWidth);
        edtCoverHeight = (EditText) parent.findViewById(R.id.edtCoverHeight);
        edtScrewCenterWidth = (EditText) parent.findViewById(R.id.edtCoverScrewCenterWidth);
        edtScrewCenterHeight = (EditText) parent.findViewById(R.id.edtCoverScrewCenterHeight);
        edtAFFValue = (EditText) parent.findViewById(R.id.edtAFFValue);

        parent.findViewById(R.id.btnHelp).setOnClickListener(this);
        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
        edtDescription.setOnClickListener(this);
        edtMounting.setOnClickListener(this);
        edtWallMaterial.setOnClickListener(this);

        setHeaderScrollConfiguration(parent, getString(R.string.sub_title_hall_station), getString(R.string.sub_title_review), true, true);
    }

    private void updateUIs(){

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

        if(hallStationData != null){
            edtDescription.setText(hallStationData.getDescription());
            edtMounting.setText(hallStationData.getMount());
            edtWallMaterial.setText(hallStationData.getWallFinish());
            edtCoverWidth.setText(hallStationData.getWidth() + "");
            edtCoverHeight.setText(hallStationData.getHeight() + "");
            edtScrewCenterWidth.setText(hallStationData.getScrewCenterWidth() + "");
            edtScrewCenterHeight.setText(hallStationData.getScrewCenterHeight() + "");
            edtAFFValue.setText(hallStationData.getAffValue() + "");
        }
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        double width = ConversionUtils.getDoubleFromEditText(edtCoverWidth);
        double height = ConversionUtils.getDoubleFromEditText(edtCoverHeight);
        double screwWidth = ConversionUtils.getDoubleFromEditText(edtScrewCenterWidth);
        double screwHeight = ConversionUtils.getDoubleFromEditText(edtScrewCenterHeight);
        double aff = ConversionUtils.getDoubleFromEditText(edtAFFValue);
        //--------------------you have to modify this code--------------------
        if (width < 0){
            edtCoverWidth.requestFocus();
            showToast(getString(R.string.valid_msg_input_cover_width), Toast.LENGTH_SHORT);
            return;
        }
        if (height < 0){
            edtCoverHeight.requestFocus();
            showToast(getString(R.string.valid_msg_input_cover_height), Toast.LENGTH_SHORT);
            return;
        }
        if (screwWidth < 0){
            edtScrewCenterWidth.requestFocus();
            showToast(getString(R.string.valid_msg_input_screw_width), Toast.LENGTH_SHORT);
            return;
        }
        if (screwHeight < 0){
            edtScrewCenterHeight.requestFocus();
            showToast(getString(R.string.valid_msg_input_screw_height), Toast.LENGTH_SHORT);
            return;
        }
        if (aff < 0){
            edtAFFValue.requestFocus();
            showToast(getString(R.string.valid_msg_input_aff_value), Toast.LENGTH_SHORT);
            return;
        }
        MADSurveyApp.getInstance().getHallStationData().setWidth(width);
        MADSurveyApp.getInstance().getHallStationData().setHeight(height);
        MADSurveyApp.getInstance().getHallStationData().setScrewCenterHeight(screwHeight);
        MADSurveyApp.getInstance().getHallStationData().setScrewCenterWidth(screwWidth);
        MADSurveyApp.getInstance().getHallStationData().setAffValue(aff);
        hallStationDataHandler.update(MADSurveyApp.getInstance().getHallStationData());
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_HALLSTATION_NOTES, "hallstation_notes");
    }

    private void goToHallStationDescription(){
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_HALLSTATION_DESCRIPTION, "hallstation_description",true);
    }
    private void gotoHallStationMounting(){
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_HALLSTATION_MOUNTING, "hallstation_mounting",true);
    }
    private void gotoHallStationWallMaterial(){
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_HALLSTATION_WALLMATERIAL, "hallstation_wallmaterial",true);
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
                showHelpDialog(getActivity(), getString(R.string.help_title_hall_station), R.drawable.img_help_15_hallstation_review_help, GlobalConstant.HELP_PHOTO_SIZE_RATE);
                break;
            case R.id.edtDescription:
                goToHallStationDescription();
                break;
            case R.id.edtWallMaterial:
                gotoHallStationWallMaterial();
                break;
            case R.id.edtMounting:
                gotoHallStationMounting();
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
