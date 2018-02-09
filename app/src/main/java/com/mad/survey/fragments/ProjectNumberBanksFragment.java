package com.mad.survey.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.mad.survey.MADSurveyApp;
import com.mad.survey.R;
import com.mad.survey.activities.BaseActivity;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.ProjectData;
import com.mad.survey.utils.ConversionUtils;
import com.mad.survey.utils.Utils;

public class ProjectNumberBanksFragment extends BaseFragment implements View.OnClickListener,OnFragmentResumedListener {

    private EditText edtNumberBanks;

    public static ProjectNumberBanksFragment newInstance(){
        ProjectNumberBanksFragment fragment = new ProjectNumberBanksFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_project_number_banks, container, false);

        // Go to next screen if non-relative items is surveying
        if (MADSurveyApp.getInstance().getProjectData().getHallStations() == 0 &&
        MADSurveyApp.getInstance().getProjectData().getHallLanterns() == 0 &&
        MADSurveyApp.getInstance().getProjectData().getCops() == 0 &&
        MADSurveyApp.getInstance().getProjectData().getCabInteriors() == 0 &&
        MADSurveyApp.getInstance().getProjectData().getHallEntrances() == 0
        ){
            setBankNumAndGoNext(0);
        }

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        edtNumberBanks = (EditText) parent.findViewById(R.id.edtNumberBanks);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtNumberBanks.requestFocus();
                Utils.showKeyboard(getActivity(), true, edtNumberBanks);
            }
        });
    }

    private void updateUIs(){
        edtNumberBanks.setText("");
        ProjectData projectData = MADSurveyApp.getInstance().getProjectData();

        if (projectData.getNumBanks() > 0) {
            edtNumberBanks.setText(MADSurveyApp.getInstance().getProjectData().getNumBanks() + "");
        }
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        int numBanks = ConversionUtils.getIntegerFromEditText(edtNumberBanks);
        if (numBanks <= 0){
            showToast(getString(R.string.valid_msg_input_banks_count), Toast.LENGTH_SHORT);
            return;
        }

        setBankNumAndGoNext(numBanks);
    }

    private void setBankNumAndGoNext(int numBanks){
        MADSurveyApp.getInstance().setBankNum(0);
        MADSurveyApp.getInstance().getProjectData().setNumBanks(numBanks);
        projectDataHandler.update(MADSurveyApp.getInstance().getProjectData());
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_PROJECT_NUMBER_FLOORS, "project_number_floors");
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
