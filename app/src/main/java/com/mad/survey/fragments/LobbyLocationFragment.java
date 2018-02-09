package com.mad.survey.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mad.survey.MADSurveyApp;
import com.mad.survey.R;
import com.mad.survey.activities.BaseActivity;
import com.mad.survey.globals.GlobalConstant;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.LobbyData;
import com.mad.survey.models.ProjectData;
import com.mad.survey.utils.ConversionUtils;

public class LobbyLocationFragment extends BaseFragment implements View.OnClickListener,OnFragmentResumedListener {

    private CheckBox chkCACFRoom, chkFireRecallPanel, chkSecurityDesk, chkOther;
    private EditText edtOtherName;
    private TextView btnNext;
    private TextView txtSubTitle;

    private int projectId = 0;

    private View parent;

    public static LobbyLocationFragment newInstance(){
        LobbyLocationFragment fragment = new LobbyLocationFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_lobby_location, container, false);

        projectId = MADSurveyApp.getInstance().getProjectData().getId();
        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        chkCACFRoom = (CheckBox) parent.findViewById(R.id.chkCACFRoom);
        chkFireRecallPanel = (CheckBox) parent.findViewById(R.id.chkFireRecallPanel);
        chkSecurityDesk = (CheckBox) parent.findViewById(R.id.chkSecurityDesk);
        chkOther = (CheckBox) parent.findViewById(R.id.chkOther);
        edtOtherName = (EditText) parent.findViewById(R.id.edtOtherName);
        btnNext = (TextView) parent.findViewById(R.id.btnNext);

        parent.findViewById(R.id.btnCACFRoom).setOnClickListener(this);
        parent.findViewById(R.id.btnFireRecallPanel).setOnClickListener(this);
        parent.findViewById(R.id.btnSecurityDesk).setOnClickListener(this);
        parent.findViewById(R.id.btnOther).setOnClickListener(this);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
    }

    private void updateUIs(){
        chkCACFRoom.setChecked(false);
        chkFireRecallPanel.setChecked(false);
        chkSecurityDesk.setChecked(false);
        chkOther.setChecked(false);
        edtOtherName.setVisibility(View.GONE);
        btnNext.setVisibility(View.GONE);
        edtOtherName.setText("");

        ProjectData projectData = MADSurveyApp.getInstance().getProjectData();
        int currentLobby = MADSurveyApp.getInstance().getLobbyNum();
        LobbyData lobbyData = lobbyDataHandler.get(projectId, currentLobby);
        MADSurveyApp.getInstance().setLobbyData(lobbyData);
        int numLobbyPanels = projectData.getNumLobbyPanels();

        if(numLobbyPanels>0)
            txtSubTitle.setText(getString(R.string.lobby_panel_n_title, currentLobby+1, numLobbyPanels));

        if(lobbyData != null){
            String location = lobbyData.getLocation();
            switch (location){
                case GlobalConstant.LOBBY_LOCATION_CACFROOM:
                    chkCACFRoom.setChecked(true);
                    break;
                case GlobalConstant.LOBBY_LOCATION_FIRE_RECALL_PANEL:
                    chkFireRecallPanel.setChecked(true);
                    break;
                case GlobalConstant.LOBBY_LOCATION_SECURITY_DESK:
                    chkSecurityDesk.setChecked(true);
                    break;
                default:
                    chkOther.setChecked(true);
                    edtOtherName.setText(location);
                    edtOtherName.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.VISIBLE);
                    break;
            }
        }

        //Added by Alex - 2018/01/24
        if (!MADSurveyApp.getInstance().isEditMode() && currentLobby > 0){
            ((BaseActivity) getActivity()).setBackable(false);
            parent.findViewById(R.id.btnBack).setVisibility(View.INVISIBLE);
        }
    }

    private void goToNext(String location){
        if (!isLastFocused()) return;

        int currentLobby = MADSurveyApp.getInstance().getLobbyNum();
        // If there isn't currentLobby in DB,INSERT
        if(!lobbyDataHandler.checkLobbyExistence(projectId,currentLobby)) {
            LobbyData lobbyData = new LobbyData();
            lobbyData.setProjectId(projectId);
            lobbyData.setLobbyNum(currentLobby);
            lobbyData.setLocation(location);
            // Inserting new Lobby into local database
            long nID = lobbyDataHandler.insert(lobbyData);
            lobbyData.setId((int) nID);
            MADSurveyApp.getInstance().setLobbyData(lobbyData);
        }
        // else,UPDATE
        else {
            LobbyData lobbyData = MADSurveyApp.getInstance().getLobbyData();
            lobbyData.setLocation(location);
            lobbyDataHandler.update(lobbyData);
        }

        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_LOBBY_VISIBILITY, "lobby_visibility");
    }

    private void updateOtherUIs(){
        chkCACFRoom.setChecked(false);
        chkFireRecallPanel.setChecked(false);
        chkSecurityDesk.setChecked(false);
        chkOther.setChecked(!chkOther.isChecked());
        edtOtherName.setVisibility(chkOther.isChecked()? View.VISIBLE:View.GONE);
        btnNext.setVisibility(chkOther.isChecked()? View.VISIBLE:View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.btnNext:
                goToNext(edtOtherName.getText().toString());
                break;
            case R.id.btnOther:
                updateOtherUIs();
                break;
            case R.id.btnCACFRoom:
                goToNext(GlobalConstant.LOBBY_LOCATION_CACFROOM);
                break;
            case R.id.btnFireRecallPanel:
                goToNext(GlobalConstant.LOBBY_LOCATION_FIRE_RECALL_PANEL);
                break;
            case R.id.btnSecurityDesk:
                goToNext(GlobalConstant.LOBBY_LOCATION_SECURITY_DESK);
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
