package com.mad.survey.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.mad.survey.R;
import com.mad.survey.globals.GlobalConstant;
import com.mad.survey.models.ProjectData;

public class SurveyDeleteDialog extends Dialog {
	protected Context mContext;

    private View.OnClickListener mClickListener;

    private ProjectData projectData;
    private TextView txtProjectName, txtProjectDate, txtProjectStatus;

	public SurveyDeleteDialog(final Context context, ProjectData project, View.OnClickListener listener) {
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

		setContentView(R.layout.dlg_existing_survey_delete);

        txtProjectName = (TextView) findViewById(R.id.txtProjectName);
        txtProjectDate = (TextView) findViewById(R.id.txtProjectDate);
        txtProjectStatus = (TextView) findViewById(R.id.txtProjectStatus);

        findViewById(R.id.btnYes).setOnClickListener(mClickListener);
        findViewById(R.id.btnNo).setOnClickListener(mClickListener);
        findViewById(R.id.btnClose).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }});

        txtProjectName.setText(projectData.getName());
        txtProjectDate.setText(projectData.getSurveyDate());
        txtProjectStatus.setText(projectData.getStatus().equals(GlobalConstant.STATUS_SUBMITTED)? mContext.getResources().getString(R.string.submitted):mContext.getResources().getString(R.string.not_submitted));
	}
}
