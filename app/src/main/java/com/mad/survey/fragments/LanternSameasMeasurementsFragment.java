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
import com.mad.survey.models.LanternData;
import com.mad.survey.models.ProjectData;
import com.mad.survey.utils.ConversionUtils;
import com.mad.survey.utils.Utils;

public class LanternSameasMeasurementsFragment extends BaseFragment implements View.OnClickListener,OnFragmentResumedListener{

    private TextView txtFloorIdentifier, txtLanternPINo;
    private TextView txtSubTitle;
    private EditText edtSpaceAvailableWidth, edtSpaceAvailableHeight;

    public static LanternSameasMeasurementsFragment newInstance(){
        LanternSameasMeasurementsFragment fragment = new LanternSameasMeasurementsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_lantern_sameas_measurements, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        txtFloorIdentifier = (TextView) parent.findViewById(R.id.txtFloorIdentifier);
        txtLanternPINo = (TextView) parent.findViewById(R.id.txtLanternPINo);
        edtSpaceAvailableWidth = (EditText) parent.findViewById(R.id.edtSpaceAvailableWidth);
        edtSpaceAvailableHeight = (EditText) parent.findViewById(R.id.edtSpaceAvailableHeight);

        parent.findViewById(R.id.btnHelp).setOnClickListener(this);
        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtSpaceAvailableWidth.requestFocus();
                Utils.showKeyboard(getActivity(), true, edtSpaceAvailableWidth);
            }
        });
    }

    private void updateUIs(){
        edtSpaceAvailableWidth.setText("");
        edtSpaceAvailableHeight.setText("");
        ProjectData projectData = MADSurveyApp.getInstance().getProjectData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtFloorIdentifier.setText(getString(R.string.floor_n_identifier_edit_title, MADSurveyApp.getInstance().getFloorDescriptor()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title,MADSurveyApp.getInstance().getBankNum()+1,MADSurveyApp.getInstance().getBankData().getName()));
            txtFloorIdentifier.setText(getString(R.string.floor_n_identifier_title, MADSurveyApp.getInstance().getFloorNum()+1,projectData.getNumFloors() , MADSurveyApp.getInstance().getFloorDescriptor()));
            txtLanternPINo.setText(getString(R.string.lantern_pi_n_title, MADSurveyApp.getInstance().getLanternNum()+1, MADSurveyApp.getInstance().getLanternCnt()));
        }
        double width = MADSurveyApp.getInstance().getLanternData().getSpaceAvailableWidth();
        double height = MADSurveyApp.getInstance().getLanternData().getSpaceAvailableHeight();
        if(width>=0)
            edtSpaceAvailableWidth.setText(width+"");
        if(height>=0)
            edtSpaceAvailableHeight.setText(height+"");
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        double spaceAvailableWidth = ConversionUtils.getDoubleFromEditText(edtSpaceAvailableWidth);
        double spaceAvailableHeight = ConversionUtils.getDoubleFromEditText(edtSpaceAvailableHeight);
        if(spaceAvailableWidth < 0){
            edtSpaceAvailableWidth.requestFocus();
            showToast(getString(R.string.valid_msg_input_spaceAvailable_width), Toast.LENGTH_SHORT);
            return;
        }
        if(spaceAvailableHeight < 0){
            edtSpaceAvailableHeight.requestFocus();
            showToast(getString(R.string.valid_msg_input_spaceAvailable_height), Toast.LENGTH_SHORT);
            return;
        }


        MADSurveyApp.getInstance().getLanternData().setSpaceAvailableWidth(spaceAvailableWidth);
        MADSurveyApp.getInstance().getLanternData().setSpaceAvailableHeight(spaceAvailableHeight);
        lanternDataHandler.update(MADSurveyApp.getInstance().getLanternData());

        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_LANTERN_REVIEW, "lantern_review");
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
