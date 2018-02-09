package com.mad.survey.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mad.survey.MADSurveyApp;
import com.mad.survey.R;
import com.mad.survey.activities.BaseActivity;
import com.mad.survey.adapters.SurveysListAdapter;
import com.mad.survey.dialogs.SurveyDeleteConfirmDialog;
import com.mad.survey.dialogs.SurveyDeleteDialog;
import com.mad.survey.dialogs.SurveyOperationDialog;
import com.mad.survey.models.ProjectData;

import java.util.ArrayList;
import java.util.List;

public class ExistingSurveysFragment extends BaseFragment implements View.OnClickListener {

    private ListView lstSurveys;
    private List<ProjectData> dataList = new ArrayList<>();
    private SurveysListAdapter lstAdapter;
    private ProjectData selectedSurvey = null;

    public static ExistingSurveysFragment newInstance(){
        ExistingSurveysFragment fragment = new ExistingSurveysFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_existing_surveys, container, false);

        initView(parent, inflater);
        loadData();

        return parent;
    }

    private void initView(View parent, LayoutInflater inflater){
        lstSurveys = (ListView) parent.findViewById(R.id.lstSurveys);

        lstAdapter = new SurveysListAdapter(getActivity(), inflater, this);
        lstSurveys.setAdapter(lstAdapter);

        setHeaderTitle(parent, getString(R.string.existing_surveys));

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
    }

    private void loadData(){
        dataList.clear();
        dataList.addAll(projectDataHandler.getAll());
        lstAdapter.setData(dataList);
    }

    SurveyOperationDialog dlg;
    SurveyDeleteDialog deleteDlg;
    SurveyDeleteConfirmDialog deleteConfirmDlg;
    private void doOpenProjectOperationDlg(){
        dlg = new SurveyOperationDialog(getActivity(), selectedSurvey, this);
        dlg.show();
    }

    private void goToEditProject(){
        MADSurveyApp.getInstance().setIsEditMode(true);
        MADSurveyApp.getInstance().setProjectData(selectedSurvey);
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_PROJECT_EDIT, "edit_option");
    }

    private void doOpenDeleteDialog(){
        deleteDlg = new SurveyDeleteDialog(getActivity(), selectedSurvey, this);
        deleteDlg.show();
    }

    private void deleteProject(){
        deleteDlg.dismiss();


        int projectId = selectedSurvey.getId();
        String arg[] = new String[]{Integer.toString(projectId)};
        //------------delete lobby data
        lobbyDataHandler.delete("projectId = ?",arg);
        //------------delete bank data
        bankDataHandler.delete("projectId = ?",arg);
        //------------delete hallStation data
        hallStationDataHandler.delete("projectId = ?",arg);
        //------------delete hallLantern data
        lanternDataHandler.delete("projectId = ?",arg);
        //------------delete carData
        carDataHandler.delete("projectId = ?",arg);
        //------------delete copData
        copDataHandler.delete("projectId = ?",arg);
        //------------delete InteriorCar data
        interiorCarDataHandler.delete("projectId = ?",arg);
        //------------delete carDoor data
        interiorCarDoorDataHandler.delete("projectId = ?",arg);
        //------------delete hallentrance data
        hallEntranceDataHandler.delete("projectId = ?",arg);
        //------------delete photo data and save photo in YOUR DEVICE------
        photoDataHandler.delete("projectId = ?",arg);
        //------------delete project record
        projectDataHandler.delete(selectedSurvey);

        loadData();
        doOpenDeleteConfirmDialog();
    }

    private void doOpenDeleteConfirmDialog(){
        deleteConfirmDlg = new SurveyDeleteConfirmDialog(getActivity(), selectedSurvey, this);
        deleteConfirmDlg.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.layoutSurvey:
                selectedSurvey = (ProjectData) v.getTag();
                doOpenProjectOperationDlg();
                break;
            case R.id.btnEdit:
                dlg.dismiss();
                goToEditProject();
                break;
            case R.id.btnDelete:
                dlg.dismiss();
                doOpenDeleteDialog();
                break;
            case R.id.btnSubmit:
                dlg.dismiss();
                MADSurveyApp.getInstance().setProjectData(selectedSurvey);
                ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_SUBMIT_PROJECT, "submit_project");
                break;
            case R.id.btnYes:
                deleteProject();
                break;
            case R.id.btnNo:
                deleteDlg.dismiss();
                break;
            case R.id.btnOK:
                deleteConfirmDlg.dismiss();
                break;
        }
    }
}
