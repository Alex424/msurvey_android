package com.mad.survey.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mad.survey.R;
import com.mad.survey.globals.GlobalConstant;
import com.mad.survey.models.ProjectData;
import com.mad.survey.preferences.AppPreference;

public class SurveySubmitDialog extends Dialog {
    protected Context mContext;

    private View.OnClickListener mClickListener;

    private ProjectData projectData;
    private TextView txtProjectName, txtProjectDate, txtProjectStatus;

    private SwitchCompat swhEmail;
    private TextView txtEmailAddress;

    public SurveySubmitDialog(final Context context, ProjectData project, View.OnClickListener listener) {
        super(context, R.style.MADAlertDialogStyle);
        this.mContext = context;
        this.mClickListener = listener;
        this.projectData = project;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dlg_existing_survey_submit);

        txtProjectName = (TextView) findViewById(R.id.txtProjectName);
        txtProjectDate = (TextView) findViewById(R.id.txtProjectDate);
        txtProjectStatus = (TextView) findViewById(R.id.txtProjectStatus);
        swhEmail = (SwitchCompat) findViewById(R.id.swhEmail);
        txtEmailAddress = (TextView) findViewById(R.id.txtEmailAddress);

        String email = AppPreference.getStringPrefValue(mContext, AppPreference.PREF_KEY_YOUR_EMAIL);
        if (email.equals("")){
            txtEmailAddress.setVisibility(View.GONE);
        }else{
            txtEmailAddress.setVisibility(View.VISIBLE);
            txtEmailAddress.setText(email);
        }

        swhEmail.setChecked(AppPreference.getEmailReportPreference(mContext));
        swhEmail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                AppPreference.setEmailReportPreference(mContext, b);
            }
        });

        findViewById(R.id.btnYes).setOnClickListener(mClickListener);
        findViewById(R.id.btnNo).setOnClickListener(mClickListener);
        findViewById(R.id.btnClose).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });

        txtProjectName.setText(projectData.getName());
        txtProjectDate.setText(projectData.getSurveyDate());
        txtProjectStatus.setText(projectData.getStatus().equals(GlobalConstant.STATUS_SUBMITTED) ? mContext.getResources().getString(R.string.submitted) : mContext.getResources().getString(R.string.not_submitted));
    }
}
