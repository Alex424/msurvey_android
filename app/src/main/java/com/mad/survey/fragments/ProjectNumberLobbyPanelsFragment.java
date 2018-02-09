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
import com.mad.survey.models.LobbyData;
import com.mad.survey.models.ProjectData;
import com.mad.survey.utils.ConversionUtils;
import com.mad.survey.utils.Utils;

public class ProjectNumberLobbyPanelsFragment extends BaseFragment implements View.OnClickListener ,OnFragmentResumedListener{

    private EditText edtNumberLobbyPanels;
    public static ProjectNumberLobbyPanelsFragment newInstance(){
        ProjectNumberLobbyPanelsFragment fragment = new ProjectNumberLobbyPanelsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_project_number_lobby_panels, container, false);

        // Go to next screen if non-relative items is surveying
        if (MADSurveyApp.getInstance().getProjectData().getLobbyPanels() == 0){
            setLobbyPanelNumAndGoNext(0);
        }

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        edtNumberLobbyPanels = (EditText) parent.findViewById(R.id.edtNumberLobbyPanels);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtNumberLobbyPanels.requestFocus();
                Utils.showKeyboard(getActivity(), true, edtNumberLobbyPanels);
            }
        });
    }

    private void updateUIs(){
        edtNumberLobbyPanels.setText("");
        ProjectData projectData = MADSurveyApp.getInstance().getProjectData();

        if (projectData.getNumLobbyPanels() > 0) {
            edtNumberLobbyPanels.setText(MADSurveyApp.getInstance().getProjectData().getNumLobbyPanels() + "");
        }
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        int numLobbyPanels = ConversionUtils.getIntegerFromEditText(edtNumberLobbyPanels);
        if (numLobbyPanels <= 0){
            showToast(getString(R.string.valid_msg_input_lobbypanels_count), Toast.LENGTH_SHORT);
            return;
        }

        setLobbyPanelNumAndGoNext(numLobbyPanels);
    }

    private void setLobbyPanelNumAndGoNext(int numLobbyPanels){
        MADSurveyApp.getInstance().getProjectData().setNumLobbyPanels(numLobbyPanels);
        projectDataHandler.update(MADSurveyApp.getInstance().getProjectData());

        MADSurveyApp.getInstance().setLobbyNum(0);
        ProjectData projectData = MADSurveyApp.getInstance().getProjectData();
        if(projectData.getLobbyPanels() == 1)
            ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_LOBBY_LOCATION, "lobby_location");
        else
            ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_BANK_NAME, "bank_name");
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
