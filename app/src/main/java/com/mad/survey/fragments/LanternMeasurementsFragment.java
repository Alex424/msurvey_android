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
import com.mad.survey.utils.ConversionUtils;
import com.mad.survey.utils.Utils;

public class LanternMeasurementsFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtFloorIdentifier, txtLanternPINo;
    private EditText edtCoverWidth;
    private EditText edtCoverHeight;
    private EditText edtCoverDepth;
    private EditText edtScrewCenterWidth;
    private EditText edtScrewCenterHeight;
    private EditText edtSpaceAvailableWidth;
    private EditText edtSpaceAvailableHeight;

    public static LanternMeasurementsFragment newInstance(){
        LanternMeasurementsFragment fragment = new LanternMeasurementsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_lantern_measurements, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtFloorIdentifier = (TextView) parent.findViewById(R.id.txtFloorIdentifier);
        txtLanternPINo = (TextView) parent.findViewById(R.id.txtLanternPINo);
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
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtCoverWidth.requestFocus();
                Utils.showKeyboard(getActivity(), true, edtCoverWidth);
            }
        });

        setHeaderScrollConfiguration(parent, getString(R.string.sub_title_hall_lantern), getString(R.string.sub_title_measurements), true, true);
    }

    private void updateUIs(){
        edtCoverDepth.setText("");
        edtSpaceAvailableWidth.setText("");
        edtSpaceAvailableHeight.setText("");
        edtCoverHeight.setText("");
        edtCoverWidth.setText("");
        edtScrewCenterHeight.setText("");
        edtScrewCenterWidth.setText("");
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
        if(lanternData.getWidth()>=0)
            edtCoverWidth.setText(lanternData.getWidth()+"");
        if(lanternData.getHeight()>=0)
            edtCoverHeight.setText(lanternData.getHeight()+"");
        if(lanternData.getScrewCenterHeight()>=0)
            edtScrewCenterHeight.setText(lanternData.getScrewCenterHeight()+"");
        if(lanternData.getScrewCenterWidth()>=0)
            edtScrewCenterWidth.setText(lanternData.getScrewCenterWidth()+"");
        if(lanternData.getDepth()>=0)
            edtCoverDepth.setText(lanternData.getDepth()+"");
        if(lanternData.getSpaceAvailableHeight()>=0)
            edtSpaceAvailableHeight.setText(lanternData.getSpaceAvailableHeight()+"");
        if(lanternData.getSpaceAvailableWidth()>=0)
            edtSpaceAvailableWidth.setText(lanternData.getSpaceAvailableHeight()+"");
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
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
