package com.mad.survey.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DatePickerDialog.OnDateSetListener;

import com.mad.survey.MADSurveyApp;
import com.mad.survey.R;
import com.mad.survey.activities.BaseActivity;
import com.mad.survey.globals.GlobalConstant;
import com.mad.survey.models.ProjectData;
import com.mad.survey.preferences.AppPreference;
import com.mad.survey.utils.ConversionUtils;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ProjectDetailsFragment extends BaseFragment implements View.OnClickListener {

    private EditText edtProjectNo, edtProjectName, edtCompanyName, edtCompanyContact;
    private TextView txtSurveyDate;
    GregorianCalendar calendar;
    SimpleDateFormat df;
    public static ProjectDetailsFragment newInstance(){
        ProjectDetailsFragment fragment = new ProjectDetailsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_project_details, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        edtProjectName = (EditText) parent.findViewById(R.id.edtProjectName);
        edtProjectNo = (EditText) parent.findViewById(R.id.edtProjectNo);
        edtCompanyName = (EditText) parent.findViewById(R.id.edtCompanyName);
        edtCompanyContact = (EditText) parent.findViewById(R.id.edtCompanyContact);
        txtSurveyDate = (TextView) parent.findViewById(R.id.edtSurveyDate);

        setHeaderTitle(parent, MADSurveyApp.getInstance().isEditMode()? MADSurveyApp.getInstance().getProjectData().getName():getString(R.string.new_survey));

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
        parent.findViewById(R.id.edtSurveyDate).setOnClickListener(this);

        df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        calendar = (GregorianCalendar) GregorianCalendar.getInstance();

        setHeaderScrollConfiguration(parent, getString(R.string.sub_title_project_details), "", true, false);

    }

    private void updateUIs(){
        if (MADSurveyApp.getInstance().getProjectData() == null) {
            Date d = new Date();
            txtSurveyDate.setText(df.format(d.getTime()));
            edtCompanyName.setText(AppPreference.getStringPrefValue(getActivity(), AppPreference.PREF_KEY_YOUR_COMPANY));
            edtCompanyContact.setText(AppPreference.getStringPrefValue(getActivity(), AppPreference.PREF_KEY_YOUR_EMAIL));
        }else{
            edtProjectNo.setText(MADSurveyApp.getInstance().getProjectData().getNo() + "");
            edtProjectName.setText(MADSurveyApp.getInstance().getProjectData().getName());
            txtSurveyDate.setText(MADSurveyApp.getInstance().getProjectData().getSurveyDate());
            edtCompanyName.setText(MADSurveyApp.getInstance().getProjectData().getCompanyName());
            edtCompanyContact.setText(MADSurveyApp.getInstance().getProjectData().getCompanyContact());
        }
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        String projectNo = edtProjectNo.getText().toString();
        String projectName = edtProjectName.getText().toString();
        String companyName = edtCompanyName.getText().toString();
        String companyContact = edtCompanyContact.getText().toString();
        String surveyDate = txtSurveyDate.getText().toString();

/*
        if (projectNo.equals("")){
            edtProjectNo.requestFocus();
            showToast(getString(R.string.valid_msg_input_project_no), Toast.LENGTH_SHORT);
            return;
        }
*/
        if(projectName.equals("")){
            edtProjectName.requestFocus();
            showToast(getString(R.string.valid_msg_input_project_name), Toast.LENGTH_SHORT);
            return;
        }
        int _scale_unit = AppPreference.getIntPrefValue(getActivity(), AppPreference.PREF_KEY_UNITS_LABEL);
        String scaleUnit = (_scale_unit== GlobalConstant.PROJECT_SETTING_UNITS_INCHES)?"inch":"centimeter";
        // If user entered this screen from main screen for creating new one
        if (MADSurveyApp.getInstance().getProjectData() == null){
            /*
            if (projectDataHandler.checkProjectIdDuplicate(projectNo)){
                edtProjectNo.setText("");
                edtProjectNo.requestFocus();
                showToast(getString(R.string.valid_msg_project_already_exists), Toast.LENGTH_SHORT);
                return;
            }
            */
            // Generating new project data
            ProjectData project = new ProjectData();
            if(projectNo.equals(""))
                project.setNo(0);
            else
                project.setNo(Integer.parseInt(projectNo));
            project.setName(projectName);
            project.setCompanyName(companyName);
            project.setCompanyContact(companyContact);
            project.setSurveyDate(surveyDate);
            project.setScaleUnit(scaleUnit);

            // Inserting new project into local database
            long nID = projectDataHandler.insert(project);
            project.setId((int) nID);
            MADSurveyApp.getInstance().setProjectData(project);
        }else{
            // If this screen is come from the next(project type) screen by pressing 'back' button.
            if(projectNo.equals(""))
                MADSurveyApp.getInstance().getProjectData().setNo(0);
            else
                MADSurveyApp.getInstance().getProjectData().setNo(Integer.parseInt(projectNo));

            MADSurveyApp.getInstance().getProjectData().setName(projectName);
            MADSurveyApp.getInstance().getProjectData().setCompanyName(companyName);
            MADSurveyApp.getInstance().getProjectData().setCompanyContact(companyContact);
            MADSurveyApp.getInstance().getProjectData().setSurveyDate(surveyDate);
            MADSurveyApp.getInstance().getProjectData().setScaleUnit(scaleUnit);

            // Updating the project from database
            projectDataHandler.update(MADSurveyApp.getInstance().getProjectData());
        }

        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_ITEMS_TO_SURVEY, "items_to_survey");
    }

    private void selectSurveyDate(){
        new DatePickerDialog(getActivity(), new OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(GregorianCalendar.YEAR, year);
                calendar.set(GregorianCalendar.MONTH, monthOfYear);
                calendar.set(GregorianCalendar.DAY_OF_MONTH, dayOfMonth);
                txtSurveyDate.setText(df.format(calendar.getTime()));
            }
        }, calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH), calendar.get(GregorianCalendar.DAY_OF_MONTH)).show();
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
            case R.id.edtSurveyDate:
                selectSurveyDate();
                break;
        }
    }
}
