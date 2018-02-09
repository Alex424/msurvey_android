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

public class ProjectNumberFloorsFragment extends BaseFragment implements View.OnClickListener,OnFragmentResumedListener {

    private EditText edtNumberFloors;

    public static ProjectNumberFloorsFragment newInstance(){
        ProjectNumberFloorsFragment fragment = new ProjectNumberFloorsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_project_number_floors, container, false);

        // Go to next screen if non-relative items is surveying
        if (MADSurveyApp.getInstance().getProjectData().getHallStations() == 0 &&
                MADSurveyApp.getInstance().getProjectData().getHallLanterns() == 0 &&
                MADSurveyApp.getInstance().getProjectData().getCops() == 0 &&
                MADSurveyApp.getInstance().getProjectData().getCabInteriors() == 0 &&
                MADSurveyApp.getInstance().getProjectData().getHallEntrances() == 0
                ){
            setFloorNumAndGoNext(0);
        }

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        edtNumberFloors = (EditText) parent.findViewById(R.id.edtNumberFloors);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtNumberFloors.requestFocus();
                Utils.showKeyboard(getActivity(), true, edtNumberFloors);
            }
        });
    }

    private void updateUIs(){
        edtNumberFloors.setText("");
        ProjectData projectData = MADSurveyApp.getInstance().getProjectData();

        if (projectData.getNumFloors() > 0) {
            edtNumberFloors.setText(MADSurveyApp.getInstance().getProjectData().getNumFloors() + "");
        }
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        int numFloors = ConversionUtils.getIntegerFromEditText(edtNumberFloors);
        if (numFloors <= 0){
            showToast(getString(R.string.valid_msg_input_floors_count), Toast.LENGTH_SHORT);
            return;
        }

        setFloorNumAndGoNext(numFloors);
    }

    private void setFloorNumAndGoNext(int numFloors){
        MADSurveyApp.getInstance().getProjectData().setNumFloors(numFloors);
        projectDataHandler.update(MADSurveyApp.getInstance().getProjectData());
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_PROJECT_NUMBER_LOBBY_PANELS, "project_number_lobby_panels");
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
