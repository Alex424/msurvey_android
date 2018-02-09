package com.mad.survey.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.mad.survey.MADSurveyApp;
import com.mad.survey.R;
import com.mad.survey.activities.BaseActivity;
import com.mad.survey.globals.GlobalConstant;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.BankData;
import com.mad.survey.models.HallStationData;
import com.mad.survey.models.LanternData;
import com.mad.survey.models.ProjectData;
import com.mad.survey.utils.Utils;

public class LanternNotesFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtFloorIdentifier, txtLanternPINo;
    private TextView txtSubTitle;
    private EditText edtNotes;

    public static LanternNotesFragment newInstance(){
        LanternNotesFragment fragment = new LanternNotesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_lantern_notes, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        txtFloorIdentifier = (TextView) parent.findViewById(R.id.txtFloorIdentifier);
        txtLanternPINo = (TextView) parent.findViewById(R.id.txtLanternPINo);

        edtNotes = (EditText) parent.findViewById(R.id.edtNotes);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtNotes.requestFocus();
                Utils.showKeyboard(getActivity(), true, edtNotes);
            }
        });
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
        edtNotes.setText(lanternData.getNotes());
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        String notes = edtNotes.getText().toString();
        if(!notes.equals("")){
            MADSurveyApp.getInstance().getLanternData().setNotes(notes);
            lanternDataHandler.update(MADSurveyApp.getInstance().getLanternData());
        }
        int lanternId = MADSurveyApp.getInstance().getLanternData().getId();
        ((BaseActivity) getActivity()).replacePhotosFragment(lanternId,GlobalConstant.PROJECT_PHOTO_TYPE_LANTERNPI, BaseActivity.FRAGMENT_ID_CAR_DESCRIPTION, "car_description");
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
