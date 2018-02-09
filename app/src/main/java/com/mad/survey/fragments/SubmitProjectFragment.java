package com.mad.survey.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.mad.survey.MADSurveyApp;
import com.mad.survey.R;
import com.mad.survey.activities.BaseActivity;
import com.mad.survey.activities.ExistingSurveysActivity;
import com.mad.survey.activities.MainActivity;
import com.mad.survey.dialogs.CustomProgressDialog;
import com.mad.survey.dialogs.MadCommonAlertDialog;
import com.mad.survey.dialogs.SurveyDeleteConfirmDialog;
import com.mad.survey.dialogs.SurveyDeleteDialog;
import com.mad.survey.dialogs.SurveySubmitConfirmDialog;
import com.mad.survey.dialogs.SurveySubmitDialog;
import com.mad.survey.globals.GlobalConstant;
import com.mad.survey.models.BankData;
import com.mad.survey.models.CarData;
import com.mad.survey.models.CopData;
import com.mad.survey.models.HallEntranceData;
import com.mad.survey.models.HallStationData;
import com.mad.survey.models.InteriorCarData;
import com.mad.survey.models.InteriorCarDoorData;
import com.mad.survey.models.LanternData;
import com.mad.survey.models.LobbyData;
import com.mad.survey.models.PhotoData;
import com.mad.survey.models.ProjectData;
import com.mad.survey.utils.ConversionUtils;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SubmitProjectFragment extends BaseFragment implements View.OnClickListener {

    private TextView txtProjectName, txtProjectDate, txtProjectState;
    private TextView txtBanksInfo, txtLobbyInfo, txtHallLanternsInfo, txtHallStationsInfo;
    private TextView txtCopsInfo, txtCarInteriorsInfo, txtHallEntranceInfo;

    //private static final String SERVER_URL  = "http://192.168.109.1/madsurvey2/";
    private static final String SERVER_URL  = "http://madfixtures.net/madsurvey2/";
    //private static final String SERVER_URL  = "http://mypmpnow.com/madsurvey2/";

    private static AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
    static {
        client.setConnectTimeout(60000);
        client.setTimeout(60000);
        client.setResponseTimeout(60000);
    }
    private ProjectData projectData;
    private CustomProgressDialog progressDialog;

    public static SubmitProjectFragment newInstance(){
        SubmitProjectFragment fragment = new SubmitProjectFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_submit_project, container, false);

        initView(parent);
        updateUIs();
        projectData = MADSurveyApp.getInstance().getProjectData();

        return parent;
    }

    private void initView(View parent){
        txtProjectName = (TextView) parent.findViewById(R.id.txtProjectName);
        txtProjectDate = (TextView) parent.findViewById(R.id.txtProjectDate);
        txtProjectState = (TextView) parent.findViewById(R.id.txtProjectStatus);

        txtBanksInfo = (TextView) parent.findViewById(R.id.txtBanksInfo);
        txtLobbyInfo = (TextView) parent.findViewById(R.id.txtLobbyInfo);
        txtHallLanternsInfo = (TextView) parent.findViewById(R.id.txtHallLanternsInfo);
        txtHallStationsInfo = (TextView) parent.findViewById(R.id.txtHallStationsInfo);
        txtCopsInfo = (TextView) parent.findViewById(R.id.txtCopsInfo);
        txtCarInteriorsInfo = (TextView) parent.findViewById(R.id.txtCarInteriorsInfo);
        txtHallEntranceInfo = (TextView) parent.findViewById(R.id.txtHallEntranceInfo);

        setHeaderTitle(parent, getString(R.string.submit_project));

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnSubmitProject).setOnClickListener(this);
    }

    private void updateUIs(){
        ProjectData projectData = MADSurveyApp.getInstance().getProjectData();
        txtProjectName.setText(projectData.getName());
        txtProjectDate.setText(projectData.getSurveyDate());
        txtProjectState.setText(projectData.getStatus());
        txtBanksInfo.setText(bankDataHandler.getAllCnt(projectData.getId())+" Banks");
        txtLobbyInfo.setText(lobbyDataHandler.getAllCnt(projectData.getId())+" Lobby Panel");
        txtHallLanternsInfo.setText(lanternDataHandler.getAllCnt(projectData.getId())+ " Hall Lanterns");
        txtHallStationsInfo.setText(hallStationDataHandler.getAllCnt(projectData.getId())+" Hall Stations");
        txtCopsInfo.setText(copDataHandler.getAllCnt(projectData.getId()) + " COPS");
        txtCarInteriorsInfo.setText(interiorCarDataHandler.getAllCnt(projectData.getId()) + " Car Interiors");
        txtHallEntranceInfo.setText(hallEntranceDataHandler.getAllCnt(projectData.getId()) + " Hall Entrances");
    }
    private List<CopData> getAllCops(int projectId,int bankId,int carNum){
        List<CopData> copDatas = new ArrayList<>();
        copDatas.addAll(copDataHandler.getAll(projectId, bankId, carNum));
        return copDatas;
    }
    private List<PhotoData> getAllPhotosOfCDAorSPI(CarData carData,String str){
        List<PhotoData> photoDatas = new ArrayList<>();
        if(str.equals("cda"))
            photoDatas.addAll(photoDataHandler.getAll(carData.getId(),GlobalConstant.PROJECT_PHOTO_TYPE_CAR_RIDING_LANTERN,1));
        else if(str.equals("spi"))
            photoDatas.addAll(photoDataHandler.getAll(carData.getId(),GlobalConstant.PROJECT_PHOTO_TYPE_CAR_SEPERATEPI,1));
        return photoDatas;
    }
    private List<PhotoData> getAllPhotosOfFixture(Object object){
        List<PhotoData> photoDatas = new ArrayList<>();
        if(object instanceof ProjectData){
            ProjectData data = (ProjectData) object;
            photoDatas.addAll(photoDataHandler.getAll(data.getId(), GlobalConstant.PROJECT_PHOTO_TYPE_BUILDING,1));
        }
        else if (object instanceof BankData){
            BankData data = (BankData) object;
            photoDatas.addAll(photoDataHandler.getAll(data.getId(), GlobalConstant.PROJECT_PHOTO_TYPE_BANK, 1));
        }else if (object instanceof LobbyData){
            LobbyData data = (LobbyData) object;
            photoDatas.addAll(photoDataHandler.getAll(data.getId(),GlobalConstant.PROJECT_PHOTO_TYPE_LOBBY,1));
        }else if (object instanceof HallStationData){
            HallStationData data = (HallStationData) object;
            photoDatas.addAll(photoDataHandler.getAll(data.getId(),GlobalConstant.PROJECT_PHOTO_TYPE_HALLSTATION,1));
        }else if(object instanceof LanternData){
            LanternData data = (LanternData) object;
            photoDatas.addAll(photoDataHandler.getAll(data.getId(),GlobalConstant.PROJECT_PHOTO_TYPE_LANTERNPI,1));
        }else if(object instanceof CarData){
            CarData data = (CarData) object;
            photoDatas.addAll(photoDataHandler.getAll(data.getId(),GlobalConstant.PROJECT_PHOTO_TYPE_CAR,1));
        }else if(object instanceof  CopData){
            CopData data = (CopData) object;
            photoDatas.addAll(photoDataHandler.getAll(data.getId(),GlobalConstant.PROJECT_PHOTO_TYPE_CAR_COP,1));

        }else if(object instanceof InteriorCarData){
            InteriorCarData data = (InteriorCarData) object;
            photoDatas.addAll(photoDataHandler.getAll(data.getId(),GlobalConstant.PROJECT_PHOTO_TYPE_CAR_INTERIOR,1));
        }else if(object instanceof HallEntranceData){
            HallEntranceData data = (HallEntranceData) object;
            photoDatas.addAll(photoDataHandler.getAll(data.getId(),GlobalConstant.PROJECT_PHOTO_TYPE_HALL_ENTRANCE,1));
        }

        return photoDatas;
    }

    private void doSubmitProjectData(){

        ProjectData projectData = MADSurveyApp.getInstance().getProjectData();
        projectData.setPhotosList(getAllPhotosOfFixture(projectData));
        JSONObject projectDataJSON = projectData.getProjectDataJSON(getActivity());


        try {
            JSONArray banksAry = new JSONArray();
            JSONArray lobbysAry = new JSONArray();
            List<Object> lobbyList = lobbyDataHandler.getAll(projectData.getId());
            for (int j = 0; j < lobbyList.size(); j++) {
                LobbyData lobbyData = (LobbyData) lobbyList.get(j);
                lobbyData.setPhotosList(getAllPhotosOfFixture(lobbyData));
                lobbysAry.put(j,lobbyData.getPostJSON());
            }
            projectDataJSON.put("panel_list", lobbysAry);
            // Get all banks
            List<BankData> banksList = bankDataHandler.getAll(projectData.getId(), true);

            for (int i = 0; i < banksList.size(); i++) {
                BankData bank = banksList.get(i);
                bank.setPhotosList(getAllPhotosOfFixture(bank));

                List<Object> hallList = hallStationDataHandler.getAll(projectData.getId(),bank.getBankNum());
                for(int j = 0; j< hallList.size(); j++){
                    HallStationData hallStationData = (HallStationData) hallList.get(j);
                    hallStationData.setPhotosList(getAllPhotosOfFixture(hallStationData));
                }

                List<Object> lanternList = lanternDataHandler.getAll(projectData.getId(),bank.getBankNum());
                for (int j = 0; j < lanternList.size(); j++) {
                    LanternData lanternData = (LanternData) lanternList.get(j);
                    lanternData.setPhotosList(getAllPhotosOfFixture(lanternData));
                }

                List<Object> carList = carDataHandler.getAll(projectData.getId(),bank.getBankNum());
                for (int j = 0; j < carList.size(); j++) {
                    CarData carData = (CarData) carList.get(j);
                    carData.setJobType(projectData.getJobType() == GlobalConstant.PROJECT_JOB_TYPE_SERVICE ? "Service" : "Mod");
                    carData.setPhotosList(getAllPhotosOfFixture(carData));
                    List<CopData> copList = getAllCops(projectData.getId(), bank.getBankNum(), carData.getCarNum());
                    carData.setCopsList(copList);
                    for(int k = 0 ; k < copList.size() ; k ++){
                        CopData copData = copList.get(k);
                        copData.setPhotosList(getAllPhotosOfFixture(copData));
                    }
                    carData.setCdiPhotosList(getAllPhotosOfCDAorSPI(carData,"cda"));
                    carData.setSpiPhotosList(getAllPhotosOfCDAorSPI(carData, "spi"));
                }

                List<Object> cabInteriorList = interiorCarDataHandler.getAll(projectData.getId(),bank.getBankNum());
                for(int j = 0; j < cabInteriorList.size(); j++){
                    InteriorCarData interiorCarData = (InteriorCarData) cabInteriorList.get(j);
                    InteriorCarDoorData frontDoorData = interiorCarDoorDataHandler.get(interiorCarData.getFrontDoorId());
                    InteriorCarDoorData backDoorData = interiorCarDoorDataHandler.get(interiorCarData.getBackDoorId());
                    interiorCarData.setFrontDoor(frontDoorData);
                    interiorCarData.setBackDoor(backDoorData);
                    interiorCarData.setPhotosList(getAllPhotosOfFixture(interiorCarData));
                }

                List<Object> hallEntranceList = hallEntranceDataHandler.getAll(projectData.getId(),bank.getBankNum());
                for(int j = 0; j < hallEntranceList.size(); j++){
                    HallEntranceData hallEntranceData = (HallEntranceData) hallEntranceList.get(j);
                    hallEntranceData.setPhotosList(getAllPhotosOfFixture(hallEntranceData));
                }

                banksAry.put(i, bank.getPostJSON(hallList, lanternList, carList,cabInteriorList,hallEntranceList));
            }

            projectDataJSON.put("bank_list", banksAry);
        }catch(Exception e){
            e.printStackTrace();
        }

        Log.d("JSON:",projectDataJSON.toString());
        uploadProjectJSON(projectData.getPostParams(getActivity(), projectDataJSON, submitTimeForFileName));
    }

    private List<PhotoData> photosList = new ArrayList<>();
    private void doSubmitAllPhotos() {

        currentTime = System.currentTimeMillis();
        projectData.setSubmitTime(ConversionUtils.convertDateToString(new Date(currentTime), ConversionUtils.APP_FMT_DATETIME));
        submitTimeForFileName = ConversionUtils.convertDateToString(new Date(currentTime), ConversionUtils.APP_FMT_FLIGHTKEY_DATETIME);
        photosList.addAll(getAllPhotosOfFixture(projectData));
        int i;
        List<Object> lobbyList = lobbyDataHandler.getAll(projectData.getId());
        for (i = 0; i < lobbyList.size(); i++) {
            LobbyData lobbyData = (LobbyData) lobbyList.get(i);
            photosList.addAll(getAllPhotosOfFixture(lobbyData));
        }
        List<BankData> banksList = bankDataHandler.getAll(projectData.getId(), true);
        for (i = 0; i < banksList.size(); i++) {
            BankData bank = banksList.get(i);
            photosList.addAll(getAllPhotosOfFixture(bank));
            List<Object> hallList = hallStationDataHandler.getAll(projectData.getId(),bank.getBankNum());
            for(int j = 0; j< hallList.size(); j++){
                HallStationData hallStationData = (HallStationData) hallList.get(j);
                photosList.addAll(getAllPhotosOfFixture(hallStationData));
            }

            List<Object> lanternList = lanternDataHandler.getAll(projectData.getId(),bank.getBankNum());
            for (int j = 0; j < lanternList.size(); j++) {
                LanternData lanternData = (LanternData) lanternList.get(j);
                photosList.addAll(getAllPhotosOfFixture(lanternData));
            }
            List<Object> copList = copDataHandler.getAll(projectData.getId(),bank.getBankNum());
            for (int j = 0; j < copList.size(); j++) {
                CopData copData = (CopData) copList.get(j);
                photosList.addAll(getAllPhotosOfFixture(copData));
            }

            List<Object> carList = carDataHandler.getAll(projectData.getId(),bank.getBankNum());
            for (int j = 0; j < carList.size(); j++) {
                CarData carData = (CarData) carList.get(j);
                photosList.addAll(getAllPhotosOfFixture(carData));
                photosList.addAll(getAllPhotosOfCDAorSPI(carData,"cda"));
                photosList.addAll(getAllPhotosOfCDAorSPI(carData,"spi"));
            }

            List<Object> cabInteriorList = interiorCarDataHandler.getAll(projectData.getId(),bank.getBankNum());
            for(int j = 0; j < cabInteriorList.size(); j++){
                InteriorCarData interiorCarData = (InteriorCarData) cabInteriorList.get(j);
                InteriorCarDoorData frontDoorData = interiorCarDoorDataHandler.get(interiorCarData.getFrontDoorId());
                InteriorCarDoorData backDoorData = interiorCarDoorDataHandler.get(interiorCarData.getBackDoorId());
                interiorCarData.setFrontDoor(frontDoorData);
                interiorCarData.setBackDoor(backDoorData);
                photosList.addAll(getAllPhotosOfFixture(interiorCarData));
            }

            List<Object> hallEntranceList = hallEntranceDataHandler.getAll(projectData.getId(), bank.getBankNum());
            for(int j = 0; j < hallEntranceList.size(); j++){
                HallEntranceData hallEntranceData = (HallEntranceData) hallEntranceList.get(j);
                photosList.addAll(getAllPhotosOfFixture(hallEntranceData));
            }
        }

        // Now we start to uploading the photo
        progressDialog = showProgressDialog(progressDialog, getString(R.string.progress_msg_uploading_photo));
        uploadPhoto(0);
    }

    // Uploading photo data to server
    private void uploadProjectJSON(RequestParams postParams){
        progressDialog = showProgressDialog(progressDialog, getString(R.string.progress_msg_please_wait));
        try {
            Log.d(getClass().getName(), "Uploading Project JSON: " + postParams.toString());
            client.post(SERVER_URL + "postProject.php", postParams, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int i, Header[] headers, String response, Throwable throwable) {
                    Log.d(getClass().getName(), "Project Upload Failure: " + response);
                    showToast(getString(R.string.err_msg_project_upload_failure), Toast.LENGTH_SHORT);
                    dismissProgressDialog(progressDialog);
                }

                @Override
                public void onSuccess(int i, Header[] headers, String response) {
                    Log.d(getClass().getName(), "Project Upload Result: " + response);
                    dismissProgressDialog(progressDialog);
                    projectData.setStatus(GlobalConstant.STATUS_SUBMITTED);
                    projectDataHandler.update(projectData);
                    //
                    doOpenSubmitConfirmDialog();
                }
            });
        }catch(Exception e){
            dismissProgressDialog(progressDialog);
            e.printStackTrace();
        }
    }

    private String submitTimeForFileName = "";
    private long currentTime;

    private void uploadPhoto(final int index){
        if (index > photosList.size() - 1) {
            dismissProgressDialog(progressDialog);
            // Now we start uploading the project data json
            doSubmitProjectData();
            return;
        }

        final PhotoData photo = photosList.get(index);

        RequestParams params = new RequestParams();
        params.put("projectname", projectData.getFileName(submitTimeForFileName));
        File file = new File(GlobalConstant.getPhotoFilePath(photo.getFileName()));
        try {
            params.put("file", file);
            Log.d(getClass().getName(), "Uploading Photo: " + photo.getFileName());
            client.post(SERVER_URL + "uploadPhoto.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int i, Header[] headers, String response, Throwable throwable) {
                    Log.d(getClass().getName(), "Photo Upload Failure: " + response);
                    showToast(getString(R.string.err_msg_image_upload_failure), Toast.LENGTH_SHORT);
                    dismissProgressDialog(progressDialog);
                }

                @Override
                public void onSuccess(int i, Header[] headers, String response) {
                    Log.d(getClass().getName(), "Photo Upload Result: " + response);

                    try{
                        JSONObject objResult = new JSONObject(response);
                        photo.setFileURL(objResult.getString("full_name"));
                        photoDataHandler.update(photo);

                        uploadPhoto(index + 1);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }catch(Exception e){}
    }

    private void doSubmitProject(){
        MadCommonAlertDialog dlg = new MadCommonAlertDialog(getActivity(), "Project 001", "2017-03-20", "", getString(R.string.submitted_successfully), null);
        dlg.show();
    }

    SurveySubmitDialog submitDlg;
    SurveySubmitConfirmDialog submitConfirmDlg;
    private void doOpenSubmitDialog(){
        submitDlg = new SurveySubmitDialog(getActivity(), projectData, this);
        submitDlg.show();
    }

    private void doOpenSubmitConfirmDialog(){
        submitConfirmDlg = new SurveySubmitConfirmDialog(getActivity(), projectData, this);
        submitConfirmDlg.show();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.btnSubmitProject:
                doOpenSubmitDialog();
                break;
            case R.id.btnYes:
                submitDlg.dismiss();
                doSubmitAllPhotos();
                break;
            case R.id.btnNo:
                submitDlg.dismiss();
                break;
            case R.id.btnOK:
                submitConfirmDlg.dismiss();
                //updateUIs();
                getActivity().finish();
                break;

        }
    }
}
