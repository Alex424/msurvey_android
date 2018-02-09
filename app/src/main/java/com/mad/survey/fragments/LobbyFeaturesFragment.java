package com.mad.survey.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.mad.survey.models.LobbyData;
import com.mad.survey.models.ProjectData;
import com.mad.survey.utils.Utils;

public class LobbyFeaturesFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtSubTitle;
    private EditText edtSpecialFeatures, edtIntegralCommunication;
    private boolean icFocus = false;

    public static LobbyFeaturesFragment newInstance(){
        LobbyFeaturesFragment fragment = new LobbyFeaturesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_lobby_features, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        edtSpecialFeatures = (EditText) parent.findViewById(R.id.edtSpecialFeatures);
        edtIntegralCommunication = (EditText) parent.findViewById(R.id.edtIntegralCommunication);
        edtIntegralCommunication.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // code to execute when EditText loses focus
                    icFocus = true;
                    Log.d(getClass().getName(), "Integral Communication Focus: Focus ON");
                }
            }
        });

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtSpecialFeatures.requestFocus();
                Utils.showKeyboard(getActivity(), true, edtSpecialFeatures);
            }
        });
    }

    private void updateUIs(){
        ProjectData projectData = MADSurveyApp.getInstance().getProjectData();
        LobbyData lobbyData = MADSurveyApp.getInstance().getLobbyData();
        int currentLobby = lobbyData.getLobbyNum();
        int numLobbyPanels = projectData.getNumLobbyPanels();
        if(numLobbyPanels>0)
            txtSubTitle.setText(getString(R.string.lobby_panel_n_title, currentLobby+1, numLobbyPanels));

        edtSpecialFeatures.setText(lobbyData.getSpecialFeature());
        edtIntegralCommunication.setText(lobbyData.getSpecialCommunicationOption());
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        String specialFeatures = edtSpecialFeatures.getText().toString();
        String integralCommunication = edtIntegralCommunication.getText().toString();

        //Commented by Alex - 2018/01/24
        /*if (integralCommunication.equals("") && !icFocus){
            edtIntegralCommunication.requestFocus();
            return;
        }*/

        MADSurveyApp.getInstance().getLobbyData().setSpecialFeature(specialFeatures);
        MADSurveyApp.getInstance().getLobbyData().setSpecialCommunicationOption(integralCommunication);
        lobbyDataHandler.update(MADSurveyApp.getInstance().getLobbyData());
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_LOBBY_NOTES, "lobby_notes");

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
