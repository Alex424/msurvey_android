package com.mad.survey.fragments;

import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mad.survey.MADSurveyApp;
import com.mad.survey.R;
import com.mad.survey.activities.BaseActivity;
import com.mad.survey.globals.GlobalConstant;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.LobbyData;
import com.mad.survey.models.ProjectData;

public class LobbyVisibilityFragment extends BaseFragment implements View.OnClickListener,OnFragmentResumedListener {

    private TextView txtSubTitle;
    private CheckBox chkYes, chkNo;

    public static LobbyVisibilityFragment newInstance(){
        LobbyVisibilityFragment fragment = new LobbyVisibilityFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_lobby_visibility, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        chkYes = (CheckBox) parent.findViewById(R.id.chkYes);
        chkNo = (CheckBox) parent.findViewById(R.id.chkNo);
        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);

        parent.findViewById(R.id.btnYes).setOnClickListener(this);
        parent.findViewById(R.id.btnNo).setOnClickListener(this);
        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
    }

    private void updateUIs(){

        chkYes.setChecked(false);
        chkNo.setChecked(false);

        ProjectData projectData = MADSurveyApp.getInstance().getProjectData();
        LobbyData lobbyData = MADSurveyApp.getInstance().getLobbyData();
        int currentLobby = lobbyData.getLobbyNum();
        int numLobbyPanels = projectData.getNumLobbyPanels();
        if(numLobbyPanels>0)
            txtSubTitle.setText(getString(R.string.lobby_panel_n_title, currentLobby+1, numLobbyPanels));

        if (lobbyData.getVisibility() == GlobalConstant.LOBBY_ELEVATORS_VISIBLE){
            chkYes.setChecked(true);
        }else if(lobbyData.getVisibility() == GlobalConstant.LOBBY_ELEVATORS_INVISIBLE){
            chkNo.setChecked(true);
        }

    }

    private void goToNext(int visibility){
        if (!isLastFocused()) return;


        MADSurveyApp.getInstance().getLobbyData().setVisibility(visibility);
        lobbyDataHandler.update(MADSurveyApp.getInstance().getLobbyData());
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_LOBBY_MEASUREMENTS, "lobby_measurements");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;

            case R.id.btnYes:
                goToNext(GlobalConstant.LOBBY_ELEVATORS_VISIBLE);
                break;
            case R.id.btnNo:
                goToNext(GlobalConstant.LOBBY_ELEVATORS_INVISIBLE);
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
