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
import com.mad.survey.models.ProjectData;
import com.mad.survey.utils.Utils;

public class HallStationNotesFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtFloorIdentifier, txtHallStationDescriptor;
    private TextView txtSubTitle;
    private EditText edtNotes;

    public static HallStationNotesFragment newInstance(){
        HallStationNotesFragment fragment = new HallStationNotesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_hallstation_notes, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        txtFloorIdentifier = (TextView) parent.findViewById(R.id.txtFloorIdentifier);
        txtHallStationDescriptor = (TextView) parent.findViewById(R.id.txtHallStationDescriptor);

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
        edtNotes.setText(hallStationData.getNotes());
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        String notes = edtNotes.getText().toString();
        if(!notes.equals("")){
            MADSurveyApp.getInstance().getHallStationData().setNotes(notes);
            hallStationDataHandler.update(MADSurveyApp.getInstance().getHallStationData());
        }
        int hallStationId = MADSurveyApp.getInstance().getHallStationData().getId();
        ((BaseActivity) getActivity()).replacePhotosFragment(hallStationId, GlobalConstant.PROJECT_PHOTO_TYPE_HALLSTATION, BaseActivity.FRAGMENT_ID_LANTERN_NUMBER, "lantern_number");
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
