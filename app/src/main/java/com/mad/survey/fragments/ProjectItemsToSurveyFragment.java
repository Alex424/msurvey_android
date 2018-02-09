package com.mad.survey.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.mad.survey.MADSurveyApp;
import com.mad.survey.R;
import com.mad.survey.activities.BaseActivity;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.ProjectData;
import com.mad.survey.utils.Utils;

public class ProjectItemsToSurveyFragment extends BaseFragment implements View.OnClickListener,OnFragmentResumedListener {

    private CheckBox chkAll, chkAllFixtures, chkLobbyPanels, chkHallStations, chkHallLanterns, chkCops, chkCabInteriors, chkHallEntrance;
    private int cntAll = 0,cntAllFixture = 0;

    public static ProjectItemsToSurveyFragment newInstance(){
        ProjectItemsToSurveyFragment fragment = new ProjectItemsToSurveyFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_project_items_to_survey, container, false);
        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        chkAll = (CheckBox) parent.findViewById(R.id.chkAll);
        chkAllFixtures = (CheckBox) parent.findViewById(R.id.chkAllFixtures);
        chkLobbyPanels = (CheckBox) parent.findViewById(R.id.chkLobbyPanels);
        chkHallStations = (CheckBox) parent.findViewById(R.id.chkHallStations);
        chkHallLanterns = (CheckBox) parent.findViewById(R.id.chkHallLanterns);
        chkCops = (CheckBox) parent.findViewById(R.id.chkCops);
        chkCabInteriors = (CheckBox) parent.findViewById(R.id.chkCabInteriors);
        chkHallEntrance = (CheckBox) parent.findViewById(R.id.chkHallEntrance);

        parent.findViewById(R.id.btnSurveyAll).setOnClickListener(this);
        parent.findViewById(R.id.btnAllFixtures).setOnClickListener(this);
        parent.findViewById(R.id.btnLobbyPanels).setOnClickListener(this);
        parent.findViewById(R.id.btnHallStations).setOnClickListener(this);
        parent.findViewById(R.id.btnHallLanterns).setOnClickListener(this);
        parent.findViewById(R.id.btnCops).setOnClickListener(this);
        parent.findViewById(R.id.btnCabInteriors).setOnClickListener(this);
        parent.findViewById(R.id.btnHallEntrance).setOnClickListener(this);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);

    }

    private void updateUIs(){
        cntAll = cntAllFixture = 0;
        chkAll.setChecked(false);
        chkAllFixtures.setChecked(false);
        ProjectData projectData = MADSurveyApp.getInstance().getProjectData();
        chkLobbyPanels.setChecked(projectData.getLobbyPanels() == 1);
        chkHallStations.setChecked(projectData.getHallStations() == 1);
        chkHallLanterns.setChecked(projectData.getHallLanterns() == 1);
        chkCops.setChecked(projectData.getCops() == 1);
        chkHallEntrance.setChecked(projectData.getHallEntrances() == 1);
        chkCabInteriors.setChecked(projectData.getCabInteriors() == 1);
        if(projectData.getLobbyPanels() == 1) {
            cntAll++;
            cntAllFixture++;
        }
        if(projectData.getHallStations() == 1) {
            cntAll++;
            cntAllFixture++;
        }
        if(projectData.getHallLanterns() == 1) {
            cntAll++;
            cntAllFixture++;
        }
        if(projectData.getCops() == 1) {
            cntAll++;
            cntAllFixture++;
        }
        if(projectData.getHallEntrances() == 1)
            cntAll++;
        if(projectData.getCabInteriors() == 1)
            cntAll++;
        if(cntAll == 6)
            chkAll.setChecked(true);
        if(cntAllFixture == 4)
            chkAllFixtures.setChecked(true);
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        ProjectData projectData = MADSurveyApp.getInstance().getProjectData();
        projectData.setLobbyPanels(chkLobbyPanels.isChecked()?1:0);
        projectData.setHallStations(chkHallStations.isChecked()?1:0);
        projectData.setHallLanterns(chkHallLanterns.isChecked()?1:0);
        projectData.setCops(chkCops.isChecked()?1:0);
        projectData.setCabInteriors(chkCabInteriors.isChecked()?1:0);
        projectData.setHallEntrances(chkHallEntrance.isChecked()?1:0);
        projectDataHandler.update(projectData);
        if(cntAll == 0){
            showToast(getString(R.string.valid_msg_input_project_item_to_survey), Toast.LENGTH_SHORT);
        }
        else {
            ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_PROJECT_NOTES, "project_notes");
        }
    }

    private void setCheckSurveyAll(boolean isChecked){
        cntAll = isChecked?0:6;
        cntAllFixture = isChecked?0:4;

        chkAll.setChecked(!isChecked);
        chkAllFixtures.setChecked(!isChecked);
        chkLobbyPanels.setChecked(!isChecked);
        chkHallStations.setChecked(!isChecked);
        chkHallLanterns.setChecked(!isChecked);
        chkCops.setChecked(!isChecked);
        chkCabInteriors.setChecked(!isChecked);
        chkHallEntrance.setChecked(!isChecked);
    }
    private void setCheckAllFixtures(boolean isChecked){

        cntAll +=isChecked?-4:(4-cntAllFixture);
        cntAllFixture = isChecked?0:4;

        chkAllFixtures.setChecked(!isChecked);
        chkLobbyPanels.setChecked(!isChecked);
        chkHallStations.setChecked(!isChecked);
        chkHallLanterns.setChecked(!isChecked);
        chkCops.setChecked(!isChecked);
        if(cntAll == 6)
            chkAll.setChecked(true);
        else
            chkAll.setChecked(false);
    }

    void setCnts(boolean flag,boolean flag1){
        int temp;
        temp = flag?-1:1;
        cntAll+=temp;
        if(flag1)
            cntAllFixture+=temp;
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
            case R.id.btnSurveyAll:
                setCheckSurveyAll(chkAll.isChecked());
                break;
            case R.id.btnAllFixtures:
                setCheckAllFixtures(chkAllFixtures.isChecked());
                break;

            case R.id.btnLobbyPanels:
                setCnts(chkLobbyPanels.isChecked(),true);
                chkLobbyPanels.setChecked(!chkLobbyPanels.isChecked());
                break;
            case R.id.btnHallStations:
                setCnts(chkHallStations.isChecked(),true);
                chkHallStations.setChecked(!chkHallStations.isChecked());
                break;
            case R.id.btnHallLanterns:
                setCnts(chkHallLanterns.isChecked(),true);
                chkHallLanterns.setChecked(!chkHallLanterns.isChecked());
                break;
            case R.id.btnCops:
                setCnts(chkCops.isChecked(),true);
                chkCops.setChecked(!chkCops.isChecked());
                break;
            case R.id.btnCabInteriors:
                setCnts(chkCabInteriors.isChecked(),false);
                chkCabInteriors.setChecked(!chkCabInteriors.isChecked());
                break;
            case R.id.btnHallEntrance:
                setCnts(chkHallEntrance.isChecked(),false);
                chkHallEntrance.setChecked(!chkHallEntrance.isChecked());
                break;
        }

        chkAll.setChecked(cntAll == 6);
        chkAllFixtures.setChecked(cntAllFixture == 4);
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
