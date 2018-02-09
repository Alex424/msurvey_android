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
import com.mad.survey.models.LobbyData;
import com.mad.survey.models.ProjectData;
import com.mad.survey.utils.ConversionUtils;
import com.mad.survey.utils.Utils;

public class LobbyMeasurementsFragment extends BaseFragment implements View.OnClickListener ,OnFragmentResumedListener{


    private EditText edtPanelWidth, edtPanelHeight, edtScrewCenterWidth, edtScrewCenterHeight;

    public static LobbyMeasurementsFragment newInstance(){
        LobbyMeasurementsFragment fragment = new LobbyMeasurementsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_lobby_measurements, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        edtPanelWidth = (EditText) parent.findViewById(R.id.edtPanelWidth);
        edtPanelHeight = (EditText) parent.findViewById(R.id.edtPanelHeight);
        edtScrewCenterWidth = (EditText) parent.findViewById(R.id.edtCoverScrewCenterWidth);
        edtScrewCenterHeight = (EditText) parent.findViewById(R.id.edtCoverScrewCenterHeight);

        parent.findViewById(R.id.btnHelp).setOnClickListener(this);
        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtPanelWidth.requestFocus();
                Utils.showKeyboard(getActivity(), true, edtPanelWidth);
            }
        });

        setHeaderScrollConfiguration(parent, getString(R.string.sub_title_lobby), getString(R.string.sub_title_measurements), true, true);
    }

    private void updateUIs(){
        ProjectData projectData = MADSurveyApp.getInstance().getProjectData();
        LobbyData lobbyData = MADSurveyApp.getInstance().getLobbyData();
        int currentLobby = lobbyData.getLobbyNum();
        int numLobbyPanels = projectData.getNumLobbyPanels();
        if(numLobbyPanels>0)
            txtSubTitle.setText(getString(R.string.lobby_panel_n_title, currentLobby+1, numLobbyPanels));

        if(lobbyData.getPanelWidth() >= 0)
            edtPanelWidth.setText(lobbyData.getPanelWidth() + "");
        if(lobbyData.getPanelHeight() >= 0)
            edtPanelHeight.setText(lobbyData.getPanelHeight() + "");
        if(lobbyData.getScrewCenterWidth() >= 0)
            edtScrewCenterWidth.setText(lobbyData.getScrewCenterWidth() + "");
        if(lobbyData.getScrewCenterHeight() >= 0)
            edtScrewCenterHeight.setText(lobbyData.getScrewCenterHeight() + "");
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        double panelWidth = ConversionUtils.getDoubleFromEditText(edtPanelWidth);
        double panelHeight = ConversionUtils.getDoubleFromEditText(edtPanelHeight);
        double screwCenterWidth = ConversionUtils.getDoubleFromEditText(edtScrewCenterWidth);
        double screwCenterHeight = ConversionUtils.getDoubleFromEditText(edtScrewCenterHeight);
        if (panelWidth < 0){
            edtPanelWidth.requestFocus();
            showToast(getString(R.string.valid_msg_input_panel_width), Toast.LENGTH_SHORT);
            return;
        }
        if (panelHeight < 0){
            edtPanelHeight.requestFocus();
            showToast(getString(R.string.valid_msg_input_panel_height), Toast.LENGTH_SHORT);
            return;
        }
        if (screwCenterWidth < 0){
            edtScrewCenterWidth.requestFocus();
            showToast(getString(R.string.valid_msg_input_screw_center_width), Toast.LENGTH_SHORT);
            return;
        }
        if (screwCenterHeight < 0){
            edtScrewCenterHeight.requestFocus();
            showToast(getString(R.string.valid_msg_input_screw_center_height), Toast.LENGTH_SHORT);
            return;
        }

        MADSurveyApp.getInstance().getLobbyData().setPanelWidth(panelWidth);
        MADSurveyApp.getInstance().getLobbyData().setPanelHeight(panelHeight);
        MADSurveyApp.getInstance().getLobbyData().setScrewCenterWidth(screwCenterWidth);
        MADSurveyApp.getInstance().getLobbyData().setScrewCenterHeight(screwCenterHeight);

        lobbyDataHandler.update(MADSurveyApp.getInstance().getLobbyData());
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_LOBBY_FEATURES, "lobby_features");
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
                showHelpDialog(getActivity(), getString(R.string.help_title_lobby_panel), R.drawable.img_help_9_lobby_measurements_help, GlobalConstant.HELP_PHOTO_SIZE_RATE);
                break;
        }
    }
    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
