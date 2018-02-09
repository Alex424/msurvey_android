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
import com.mad.survey.models.LanternData;
import com.mad.survey.models.ProjectData;
import com.mad.survey.utils.ConversionUtils;

public class LanternReviewFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtFloorIdentifier, txtLanternPINo;
    private TextView edtDescription;
    private TextView edtMounting;
    private TextView edtWallMaterial;
    private EditText edtCoverWidth;
    private EditText edtCoverHeight;
    private EditText edtCoverDepth;
    private EditText edtScrewCenterWidth;
    private EditText edtScrewCenterHeight;
    private EditText edtSpaceAvailableWidth;
    private EditText edtSpaceAvailableHeight;

    public static LanternReviewFragment newInstance(){
        LanternReviewFragment fragment = new LanternReviewFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_lantern_review, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtFloorIdentifier = (TextView) parent.findViewById(R.id.txtFloorIdentifier);
        txtLanternPINo = (TextView) parent.findViewById(R.id.txtLanternPINo);
        edtDescription = (TextView) parent.findViewById(R.id.edtDescription);
        edtMounting = (TextView) parent.findViewById(R.id.edtMounting);
        edtWallMaterial = (TextView) parent.findViewById(R.id.edtWallMaterial);
        edtCoverWidth = (EditText) parent.findViewById(R.id.edtCoverWidth);
        edtCoverHeight = (EditText) parent.findViewById(R.id.edtCoverHeight);
        edtCoverDepth = (EditText) parent.findViewById(R.id.edtCoverDepth);
        edtScrewCenterWidth = (EditText) parent.findViewById(R.id.edtCoverScrewCenterWidth);
        edtScrewCenterHeight = (EditText) parent.findViewById(R.id.edtCoverScrewCenterHeight);
        edtSpaceAvailableWidth = (EditText) parent.findViewById(R.id.edtSpaceAvailableWidth);
        edtSpaceAvailableHeight = (EditText) parent.findViewById(R.id.edtSpaceAvailableHeight);

        parent.findViewById(R.id.btnHelp).setOnClickListener(this);
        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);

        edtDescription.setOnClickListener(this);
        edtMounting.setOnClickListener(this);
        edtWallMaterial.setOnClickListener(this);

        setHeaderScrollConfiguration(parent, getString(R.string.sub_title_hall_lantern), getString(R.string.sub_title_review), true, true);
    }

    private void updateUIs(){

        ProjectData projectData = MADSurveyApp.getInstance().getProjectData();
        LanternData lanternData = MADSurveyApp.getInstance().getLanternData();

        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtFloorIdentifier.setText(getString(R.string.floor_n_identifier_edit_title, MADSurveyApp.getInstance().getFloorDescriptor()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title,MADSurveyApp.getInstance().getBankNum()+1,MADSurveyApp.getInstance().getBankData().getName()));
            txtFloorIdentifier.setText(getString(R.string.floor_n_identifier_title, MADSurveyApp.getInstance().getFloorNum()+1,projectData.getNumFloors() , MADSurveyApp.getInstance().getFloorDescriptor()));
            txtLanternPINo.setText(getString(R.string.lantern_pi_n_title, MADSurveyApp.getInstance().getLanternNum()+1, MADSurveyApp.getInstance().getLanternCnt()));
        }

        if(lanternData != null){
            edtDescription.setText(lanternData.getDescriptor());
            edtMounting.setText(lanternData.getMount());
            edtWallMaterial.setText(lanternData.getWallFinish());
            edtCoverWidth.setText(lanternData.getWidth() + "");
            edtCoverHeight.setText(lanternData.getHeight() + "");
            edtScrewCenterWidth.setText(lanternData.getScrewCenterWidth() + "");
            edtScrewCenterHeight.setText(lanternData.getScrewCenterHeight() + "");
            edtCoverDepth.setText(lanternData.getDepth()+"");
            edtSpaceAvailableHeight.setText(lanternData.getSpaceAvailableHeight()+"");
            edtSpaceAvailableWidth.setText(lanternData.getSpaceAvailableWidth()+"");
        }

    }

    private void goToNext(){
        if (!isLastFocused()) return;

        double width = ConversionUtils.getDoubleFromEditText(edtCoverWidth);
        double height = ConversionUtils.getDoubleFromEditText(edtCoverHeight);
        double screwWidth = ConversionUtils.getDoubleFromEditText(edtScrewCenterWidth);
        double screwHeight = ConversionUtils.getDoubleFromEditText(edtScrewCenterHeight);
        double depth = ConversionUtils.getDoubleFromEditText(edtCoverDepth);
        double spaceAvailableHeigth = ConversionUtils.getDoubleFromEditText(edtSpaceAvailableHeight);
        double spaceAvailableWidth = ConversionUtils.getDoubleFromEditText(edtSpaceAvailableWidth);
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

        if (depth < 0){
            edtCoverDepth.requestFocus();
            showToast(getString(R.string.valid_msg_input_cover_depth), Toast.LENGTH_SHORT);
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
        if (spaceAvailableWidth < 0){
            edtSpaceAvailableWidth.requestFocus();
            showToast(getString(R.string.valid_msg_input_space_available_width), Toast.LENGTH_SHORT);
            return;
        }
        if (spaceAvailableHeigth < 0){
            edtSpaceAvailableHeight.requestFocus();
            showToast(getString(R.string.valid_msg_input_space_available_height), Toast.LENGTH_SHORT);
            return;
        }
        MADSurveyApp.getInstance().getLanternData().setWidth(width);
        MADSurveyApp.getInstance().getLanternData().setHeight(height);
        MADSurveyApp.getInstance().getLanternData().setScrewCenterHeight(screwHeight);
        MADSurveyApp.getInstance().getLanternData().setScrewCenterWidth(screwWidth);
        MADSurveyApp.getInstance().getLanternData().setDepth(depth);
        MADSurveyApp.getInstance().getLanternData().setSpaceAvailableHeight(spaceAvailableHeigth);
        MADSurveyApp.getInstance().getLanternData().setSpaceAvailableWidth(spaceAvailableWidth);
        lanternDataHandler.update(MADSurveyApp.getInstance().getLanternData());
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_LANTERN_NOTES, "lantern_notes");
    }
    private void goToLanternDescription(){
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_LANTERN_DESCRIPTION, "lantern_description",true);
    }
    private void gotoLanternMounting(){
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_LANTERN_MOUNTING, "lantern_mounting", true);
    }
    private void gotoLanternWallMaterial(){
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_LANTERN_WALLMATERIAL, "lantern_wallmaterial",true);
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
                showLanternHelpDialog(MADSurveyApp.getInstance().getLanternData().getDescriptor());
                break;
            case R.id.edtDescription:
                goToLanternDescription();
                break;
            case R.id.edtMounting:
                gotoLanternMounting();
                break;
            case R.id.edtWallMaterial:
                gotoLanternWallMaterial();
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
