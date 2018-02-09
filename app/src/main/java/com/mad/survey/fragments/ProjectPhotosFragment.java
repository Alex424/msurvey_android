package com.mad.survey.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mad.survey.MADSurveyApp;
import com.mad.survey.R;
import com.mad.survey.activities.BaseActivity;
import com.mad.survey.dialogs.MadCommonAlertDialog;
import com.mad.survey.dialogs.PhotoSelectDialog;
import com.mad.survey.globals.GlobalConstant;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.listeners.OnPhotoTakenListener;
import com.mad.survey.models.BankData;
import com.mad.survey.models.CarData;
import com.mad.survey.models.HallStationData;
import com.mad.survey.models.InteriorCarData;
import com.mad.survey.models.InteriorCarDoorData;
import com.mad.survey.models.PhotoData;
import com.mad.survey.models.ProjectData;
import com.mad.survey.utils.Utils;

import java.util.List;

public class ProjectPhotosFragment extends BaseFragment implements View.OnClickListener, OnPhotoTakenListener, OnFragmentResumedListener {

    private RelativeLayout layoutMainPhoto;
    private ImageView imgMainPhoto;

    private ImageView imgPhoto;
    private HorizontalScrollView hscThumbImages;
    private LinearLayout layoutPhotoItems;
    private LayoutInflater inflater;
    private TextView btnRetake, btnDelete;
    private TextView txtPhotoTypeDesc;
    private PhotoSelectDialog photoSelectDlg;
    private PhotoData selectedPhoto;
    private int photoType = GlobalConstant.PROJECT_PHOTO_TYPE_BUILDING;
    private int nextFragmentId;
    private String nextFragmentTag;
    private int projectId;
    private boolean isRetake = false;
    private int linkedId;
    private ProjectData projectData;

    public static ProjectPhotosFragment newInstance(int _linkedId,int _photoType, int _nextFragmentId, String _nextFragmentTag){
        ProjectPhotosFragment fragment = new ProjectPhotosFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("linked_id", _linkedId);
        bundle.putInt("photo_type", _photoType);
        bundle.putInt("fragment_id", _nextFragmentId);
        bundle.putString("fragment_tag", _nextFragmentTag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_project_photos, container, false);

        this.inflater = inflater;

        projectId = MADSurveyApp.getInstance().getProjectData().getId();
        selectedPhoto = null;
        projectData = MADSurveyApp.getInstance().getProjectData();
        linkedId = getArguments().getInt("linked_id");
        photoType = getArguments().getInt("photo_type");
        nextFragmentId = getArguments().getInt("fragment_id");
        nextFragmentTag = getArguments().getString("fragment_tag");

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        layoutMainPhoto = (RelativeLayout) parent.findViewById(R.id.layoutMainPhoto);
        imgMainPhoto = (ImageView) parent.findViewById(R.id.imgMainPhoto);
        hscThumbImages = (HorizontalScrollView) parent.findViewById(R.id.hscThumbImages);
        layoutPhotoItems = (LinearLayout) parent.findViewById(R.id.layoutPhotoItems);
        txtPhotoTypeDesc = (TextView) parent.findViewById(R.id.txtPhotoTypeDesc);
        btnRetake = (TextView) parent.findViewById(R.id.btnRetake);
        btnDelete = (TextView) parent.findViewById(R.id.btnDelete);

        parent.findViewById(R.id.btnRetake).setOnClickListener(this);
        parent.findViewById(R.id.btnDelete).setOnClickListener(this);
        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
        parent.findViewById(R.id.btnTakePhoto).setOnClickListener(this);
        parent.findViewById(R.id.btnScrollRight).setOnClickListener(this);
        parent.findViewById(R.id.btnScrollLeft).setOnClickListener(this);
        btnRetake.setVisibility(View.GONE);
        btnDelete.setVisibility(View.GONE);

     /*   new Handler().post(new Runnable() {
            @Override
            public void run() {

            }
        });
        */

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layoutMainPhoto.getLayoutParams();
        params.height = (int)(displayWidth * GlobalConstant.PHOTO_SIZE_RATE);
        layoutMainPhoto.setLayoutParams(params);
    }

    private void updateUIs(){
        if (photoType == GlobalConstant.PROJECT_PHOTO_TYPE_BUILDING){
            txtPhotoTypeDesc.setText(getString(R.string.please_take_photos_building));
        }else if (photoType == GlobalConstant.PROJECT_PHOTO_TYPE_LOBBY){
            txtPhotoTypeDesc.setText(getString(R.string.please_take_photos_lobby));
        }else if (photoType == GlobalConstant.PROJECT_PHOTO_TYPE_BANK){
            txtPhotoTypeDesc.setText(getString(R.string.please_take_photos_bank));
        }else if (photoType == GlobalConstant.PROJECT_PHOTO_TYPE_HALLSTATION){
            txtPhotoTypeDesc.setText(getString(R.string.please_take_photos_hallstation));
        }else if (photoType == GlobalConstant.PROJECT_PHOTO_TYPE_LANTERNPI){
            txtPhotoTypeDesc.setText(getString(R.string.please_take_photos_lantern));
        }else if (photoType == GlobalConstant.PROJECT_PHOTO_TYPE_CAR){
            txtPhotoTypeDesc.setText(getString(R.string.please_take_photos_car));
        }else if (photoType == GlobalConstant.PROJECT_PHOTO_TYPE_CAR_COP){
            txtPhotoTypeDesc.setText(getString(R.string.please_take_photos_car_cop));
        }else if (photoType == GlobalConstant.PROJECT_PHOTO_TYPE_CAR_RIDING_LANTERN){
            txtPhotoTypeDesc.setText(getString(R.string.please_take_photos_car_riding_lantern));
        }else if (photoType == GlobalConstant.PROJECT_PHOTO_TYPE_CAR_SEPERATEPI){
            txtPhotoTypeDesc.setText(getString(R.string.please_take_photos_car_seperatepi));
        }else if (photoType == GlobalConstant.PROJECT_PHOTO_TYPE_CAR_INTERIOR){
            txtPhotoTypeDesc.setText(getString(R.string.please_take_photos_elevator_cab_interior));
        }else if (photoType == GlobalConstant.PROJECT_PHOTO_TYPE_HALL_ENTRANCE){
            txtPhotoTypeDesc.setText(getString(R.string.please_take_photos_hall_entrance));
        }

        layoutPhotoItems.removeAllViews();
        PhotoData photoData;
        List<PhotoData> photosList = photoDataHandler.getAll(linkedId, photoType, 0);
        for (int i = 0; i < photosList.size(); i++){
            photoData = photosList.get(i);
            View layoutItem = populateImageView(photoData);
            layoutPhotoItems.addView(layoutItem);
            resizeImageView(layoutItem);
        }

        photoData = photosList.size()!=0?photosList.get(0):null;
        showImage(photoData);

    }

    void resizeImageView(View layoutItem){
        int nHorizontalHeight = getResources().getDimensionPixelSize(R.dimen.dp_80) - getResources().getDimensionPixelSize(R.dimen.dp_10) * 2;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layoutItem.getLayoutParams();
        params.width = (int) (nHorizontalHeight * 1.15);
        params.height = nHorizontalHeight;
        params.rightMargin = getResources().getDimensionPixelSize(R.dimen.dp_5);
        layoutItem.setLayoutParams(params);
    }

    private View populateImageView(PhotoData photoData){
        View layoutItem = inflater.inflate(R.layout.view_photo_item, null);
        ImageView imgPhotoItem = (ImageView) layoutItem.findViewById(R.id.imgPhotoItem);
        imgPhotoItem.setImageBitmap(Utils.getSafeDecodeBitmap(GlobalConstant.getPhotoFilePath(photoData.getFileName()), 100));

        layoutItem.setId(R.id.imgThumbnail);
        layoutItem.setTag(photoData);
        layoutItem.setOnClickListener(this);

        photoData.setThumbView(imgPhotoItem);

        return layoutItem;
    }


    private MadCommonAlertDialog dlg;
    private void goToNext(){
        if (!isLastFocused()) return;

        if (photoType == GlobalConstant.PROJECT_PHOTO_TYPE_BANK){
            if(MADSurveyApp.getInstance().isEditMode()){
                ((BaseActivity) getActivity()).backToSpecificFragment("edit_bank_list");
            }
            else {
                ProjectData projectData = MADSurveyApp.getInstance().getProjectData();
                if (projectData.getHallStations() == 1 || projectData.getHallLanterns() == 1) {
                    MADSurveyApp.getInstance().setFloorNum(0);
                    ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_BANK_FLOOR_DESCRIPTION, "floor_description");


                } else {
                    if (projectData.getCops() == 1) {
                        MADSurveyApp.getInstance().setCarNum(0);
                        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_CAR_DESCRIPTION, "car_description");
                    } else if (projectData.getCabInteriors() == 1) {
                        MADSurveyApp.getInstance().setInteriorCarNum(0);
                        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_COUNT, "interior_car_count");
                    } else if (projectData.getHallEntrances() == 1) {
                        MADSurveyApp.getInstance().setFloorNum(0);
                        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_HALL_ENTRANCE_FLOOR_IDENTIFIER, "hall_entrance_floor_description");


                    }
                }
            }
        }
        else if(photoType == GlobalConstant.PROJECT_PHOTO_TYPE_LOBBY){

            boolean editMode = MADSurveyApp.getInstance().isEditMode();
            if(editMode){
                ((BaseActivity) getActivity()).backToSpecificFragment("edit_lobby_list");
            }else{
                ProjectData projectData  = MADSurveyApp.getInstance().getProjectData();
                int currentLobby = MADSurveyApp.getInstance().getLobbyNum();
                int numLobbyPanels = projectData.getNumLobbyPanels();
                if(numLobbyPanels > currentLobby+1) {
                    MADSurveyApp.getInstance().setLobbyNum(currentLobby + 1);
                    ((BaseActivity) getActivity()).backToSpecificFragment("lobby_location");
                }
                else{
                    if(projectData.getHallStations() == 1 || projectData.getHallLanterns() == 1 || projectData.getCops() == 1 || projectData.getCabInteriors() == 1|| projectData.getHallEntrances() == 1){
                        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_BANK_NAME, "bank_name");
                    }
                    else{
                        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_PROJECT_ALL_ENTERED, "project_all_entered");
                    }
                }
            }



        }

        else if(photoType == GlobalConstant.PROJECT_PHOTO_TYPE_HALLSTATION){
            if(MADSurveyApp.getInstance().isEditMode()){
                ((BaseActivity) getActivity()).backToSpecificFragment("edit_hallStation_list");
            }
            else{
                BankData bankData = MADSurveyApp.getInstance().getBankData();
                int currentHallStation = MADSurveyApp.getInstance().getHallStationNum();
                int numHallStations = bankData.getNumOfRiser();
                if(numHallStations > currentHallStation + 1) {
                    MADSurveyApp.getInstance().setHallStationNum(currentHallStation + 1);
                    if (currentHallStation == 0) {
                        ((BaseActivity) getActivity()).backToSpecificFragment("floor_description");
                        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_HALLSTATION, "hallstation");
                    }
                    else {
                        MADSurveyApp.getInstance().setHallStationNum(currentHallStation + 1);
                        ((BaseActivity) getActivity()).backToSpecificFragment("hallstation");
                    }

                }
                else {
                    if(projectData.getHallLanterns() == 1)
                        ((BaseActivity) getActivity()).replaceFragment(nextFragmentId, nextFragmentTag);
                    else{
                        int numFloors = projectData.getNumFloors();
                        int currentFloor = MADSurveyApp.getInstance().getFloorNum();
                        if(numFloors > currentFloor + 1) {
                            MADSurveyApp.getInstance().setHallStationNum(0);
                            MADSurveyApp.getInstance().setFloorNum(currentFloor + 1);
                            ((BaseActivity) getActivity()).backToSpecificFragment("floor_description");
                        }
                        else{
                            if(projectData.getCops() == 1) {
                                MADSurveyApp.getInstance().setCarNum(0);
                                ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_CAR_DESCRIPTION, "car_description");
                            }
                            else if(projectData.getCabInteriors() == 1) {
                                MADSurveyApp.getInstance().setInteriorCarNum(0);
                                ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_COUNT, "interior_car_count");
                            }
                            else if(projectData.getHallEntrances() == 1) {
                                MADSurveyApp.getInstance().setFloorNum(0);
                                ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_HALL_ENTRANCE_FLOOR_IDENTIFIER, "hall_entrance_floor_description");
                            }
                            else {
                                int numBanks = projectData.getNumBanks();
                                int currentBank = MADSurveyApp.getInstance().getBankNum();
                                if (numBanks > currentBank + 1) {
                                    MADSurveyApp.getInstance().setFloorNum(0);
                                    MADSurveyApp.getInstance().setBankNum(currentBank + 1);
                                    ((BaseActivity) getActivity()).backToSpecificFragment("bank_name");
                                } else {
                                    ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_PROJECT_ALL_ENTERED, "project_all_entered");
                                }
                            }
                        }
                    }
                }
            }


        }
        else if(photoType == GlobalConstant.PROJECT_PHOTO_TYPE_LANTERNPI){
            if(MADSurveyApp.getInstance().isEditMode()){
                ((BaseActivity) getActivity()).backToSpecificFragment("edit_lantern_list");
            }
            else{
                int currentLantern = MADSurveyApp.getInstance().getLanternNum();
                int numLanterns = MADSurveyApp.getInstance().getLanternCnt();
                if(numLanterns > currentLantern + 1) {

                    MADSurveyApp.getInstance().setLanternNum(currentLantern + 1);
                    if(currentLantern == 0) {
                        ((BaseActivity) getActivity()).backToSpecificFragment("lantern_number");
                        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_LANTERN, "lantern");
                    }
                    else {

                        MADSurveyApp.getInstance().setLanternNum(currentLantern + 1);
                        ((BaseActivity) getActivity()).backToSpecificFragment("lantern");
                    }
                }
                else {
                    int numFloors = projectData.getNumFloors();
                    int currentFloor = MADSurveyApp.getInstance().getFloorNum();
                    if (numFloors > currentFloor + 1) {
                        MADSurveyApp.getInstance().setFloorNum(currentFloor + 1);
                        ((BaseActivity) getActivity()).backToSpecificFragment("floor_description");
                    } else {
                        if (projectData.getCops() == 1) {
                            MADSurveyApp.getInstance().setCopNum(0);
                            ((BaseActivity) getActivity()).replaceFragment(nextFragmentId, nextFragmentTag);
                        }
                        else if (projectData.getCabInteriors() == 1)
                            ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_COUNT, "interior_car_count");
                        else if(projectData.getHallEntrances() == 1){
                            MADSurveyApp.getInstance().setFloorNum(0);
                            ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_HALL_ENTRANCE_FLOOR_IDENTIFIER, "hall_entrance_floor_description");
                        }
                        else {
                            int numBanks = projectData.getNumBanks();
                            int currentBank = MADSurveyApp.getInstance().getBankNum();
                            if (numBanks > currentBank + 1) {
                                MADSurveyApp.getInstance().setFloorNum(0);
                                MADSurveyApp.getInstance().setBankNum(currentBank + 1);
                                ((BaseActivity) getActivity()).backToSpecificFragment("bank_name");
                            }
                            else
                                ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_PROJECT_ALL_ENTERED, "project_all_entered");
                        }
                    }
                }
            }

        }
        else if(photoType == GlobalConstant.PROJECT_PHOTO_TYPE_CAR_COP){
            int currentCop = MADSurveyApp.getInstance().getCopNum();
            CarData carData = MADSurveyApp.getInstance().getCarData();
            int numCops = carData.getNumberOfCops();
            if(numCops > currentCop + 1 ){
                MADSurveyApp.getInstance().setCopNum(currentCop + 1);
                ((BaseActivity) getActivity()).backToSpecificFragment("car_cop_name");
            }
            else{
                ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_CAR_RIDING_LANTERN, "car_riding_lantern");
            }
        }
        else if(photoType == GlobalConstant.PROJECT_PHOTO_TYPE_CAR_SEPERATEPI) {
            if(MADSurveyApp.getInstance().isEditMode()){
                ((BaseActivity) getActivity()).backToSpecificFragment("edit_car_list");
            }
            else{
                int currentCar = MADSurveyApp.getInstance().getCarNum();
                BankData bankData = MADSurveyApp.getInstance().getBankData();
                int numCars = bankData.getNumOfCar();
                if(numCars > currentCar + 1 ){
                    MADSurveyApp.getInstance().setCarNum(currentCar + 1);
                    ((BaseActivity) getActivity()).backToSpecificFragment("car_description");
                }
                else{
                    if(projectData.getCabInteriors() == 1)
                        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_COUNT, "interior_car_count");
                    else if(projectData.getHallEntrances() == 1) {
                        MADSurveyApp.getInstance().setFloorNum(0);
                        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_HALL_ENTRANCE_FLOOR_IDENTIFIER, "hall_entrance_floor_description");
                    }
                    else {
                        int numBanks = projectData.getNumBanks();
                        int currentBank = MADSurveyApp.getInstance().getBankNum();
                        if (numBanks > currentBank + 1) {
                            MADSurveyApp.getInstance().setFloorNum(0);
                            MADSurveyApp.getInstance().setBankNum(currentBank + 1);
                            ((BaseActivity) getActivity()).backToSpecificFragment("bank_name");
                        } else {
                            ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_PROJECT_ALL_ENTERED, "project_all_entered");
                        }
                    }
                }
            }

        }
        else if(photoType == GlobalConstant.PROJECT_PHOTO_TYPE_CAR_INTERIOR){
            BaseActivity.TEMP_DOOR_STYLE_EDIT = -1;
                InteriorCarData interiorCarData = MADSurveyApp.getInstance().getInteriorCarData();
                if(interiorCarData.getIsThereBackDoor() == 1) {
                    // front door
                    if(BaseActivity.TEMP_DOOR_STYLE == 1){
                        if (interiorCarData.getBackDoorId() > 0){
                            // modified by Alex 2017/09/18
                            // Just go through the existing backdoor data, not going to unique/same as front door screen
                            BaseActivity.TEMP_DOOR_STYLE_EDIT = 100;
                            BaseActivity.TEMP_DOOR_STYLE = 2;
                            ((BaseActivity) getActivity()).backToSpecificFragment("interior_car_wall_type");
                        }else{
                            ((BaseActivity) getActivity()).replaceFragment(nextFragmentId, nextFragmentTag);
                        }
                    }
                        // back door
                    else if(BaseActivity.TEMP_DOOR_STYLE == 2)
                        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_BACK_WALL, "interior_car_back_wall");

                }
                else if(interiorCarData.getIsThereBackDoor() == 2)
                    ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_BACK_WALL, "interior_car_back_wall");
        }
        else if(photoType == GlobalConstant.PROJECT_PHOTO_TYPE_HALL_ENTRANCE){

            if(MADSurveyApp.getInstance().isEditMode()){
                ((BaseActivity) getActivity()).backToSpecificFragment("edit_hall_entrance_list");
            }
            else{

                //came same codes and logic from the project photos fragment
                int numHallEntrance = MADSurveyApp.getInstance().getBankData().getNumOfCar();
                int currentHallEntrance = MADSurveyApp.getInstance().getHallEntranceCarNum();
                if(numHallEntrance > currentHallEntrance + 1 ){
                    if (currentHallEntrance == 0) {
                        MADSurveyApp.getInstance().setHallEntranceCarNum(currentHallEntrance + 1);
                        ((BaseActivity) getActivity()).backToSpecificFragment("hall_entrance_floor_description");
                        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_HALL_ENTRANCE_COPY, "hall_entrance_copy");
                    }
                    else {
                        MADSurveyApp.getInstance().setHallEntranceCarNum(currentHallEntrance + 1);
                        ((BaseActivity) getActivity()).backToSpecificFragment("hall_entrance_copy");
                    }
                }
                else{
                    int numFloor = MADSurveyApp.getInstance().getProjectData().getNumFloors();
                    int currentFloor = MADSurveyApp.getInstance().getFloorNum();
                    if(numFloor > currentFloor + 1){
                        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_HALL_ENTRANCE_SKIP, "hall_entrance_skip");
                    }
                    else{
                        goForNextBankMeasurements();
                    }
                }
            }
        }

        else {
            ((BaseActivity) getActivity()).replaceFragment(nextFragmentId, nextFragmentTag);
        }
    }
    private void doCameraPhotoTake(){

        ((BaseActivity) getActivity()).doTakePhoto();
    }
    private void doSelectFromGallery(){

        ((BaseActivity) getActivity()).doSelectFromGallery();
    }

    private void doSelectPhoto(){
        photoSelectDlg = new PhotoSelectDialog(getActivity(), this);
        photoSelectDlg.show();
    }
    private void showImage(PhotoData photoData){
        selectedPhoto = photoData;
        if(selectedPhoto != null) {
            imgMainPhoto.setImageBitmap(Utils.getSafeDecodeBitmap(GlobalConstant.getPhotoFilePath(photoData.getFileName()), displayWidth));
            btnRetake.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
        }
        else {
            imgMainPhoto.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.img_placeholder_large));
            btnRetake.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }
        imgMainPhoto.setVisibility(View.VISIBLE);
    }
    private void doDeletePhoto(){
        if (selectedPhoto != null) {
            photoDataHandler.delete(selectedPhoto);
            updateUIs();
        }
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
            case R.id.btnTakePhoto:
                isRetake = false;
                doSelectPhoto();
                break;
            case R.id.btnRetake:
                isRetake = true;
                doSelectPhoto();
                break;
            case R.id.btnDelete:
                doDeletePhoto();
                break;
            case R.id.btnScrollLeft:
                hscThumbImages.smoothScrollTo(hscThumbImages.getScrollX() - 40, 0);
                break;
            case R.id.btnScrollRight:
                hscThumbImages.smoothScrollTo(hscThumbImages.getScrollX() + 40, 0);
                break;
            case R.id.btnFromCamera:
                doCameraPhotoTake();
                photoSelectDlg.dismiss();
                break;
            case R.id.btnFromGallery:
                doSelectFromGallery();
                photoSelectDlg.dismiss();
                break;
            case R.id.imgThumbnail:
                showImage((PhotoData) v.getTag());
                break;
        }
    }

    @Override
    public void onPhotoTaken(boolean flag,String filePath) {


        if(flag){
            //-------------From Camera--------------
            filePath = GlobalConstant.getCameraTempFilePath();

        }

        else{
            //----------------From Gallery--------------

        }

        String fileName = System.currentTimeMillis() + ".jpg";
        String photoFilePath = GlobalConstant.getPhotoFilePath(fileName);

        // Copy camera taken file||file from gallery into photo directory
        try {
            Utils.copyFile(filePath, photoFilePath);
        }catch(Exception e){
            e.printStackTrace();
        }

        if (!this.isRetake){
            // Adding new photo
            PhotoData photoData = new PhotoData();
            photoData.setProjectId(projectData.getId());
            photoData.setLinkedId(linkedId);
            photoData.setFileName(fileName);
            photoData.setFileURL("");
            photoData.setType(photoType);
            photoData.setIsRearEntrance(0);

            long nId = photoDataHandler.insert(photoData);
            photoData.setId((int) nId);
            View item = populateImageView(photoData);
            layoutPhotoItems.addView(item);
            resizeImageView(item);
            showImage(photoData);
            ((BaseActivity)getActivity()).setIsActivityActive(true);
            if(!flag)
                doSelectFromGallery();
            else
                doCameraPhotoTake();
        }else{
            // Updating the retaken photo
            selectedPhoto.setFileName(fileName);
            selectedPhoto.setFileURL("");
            photoDataHandler.update(selectedPhoto);

            // Update the selected thumbnail only
            ((ImageView)selectedPhoto.getThumbView()).setImageBitmap(Utils.getSafeDecodeBitmap(GlobalConstant.getPhotoFilePath(selectedPhoto.getFileName()), 100));

            showImage(selectedPhoto);
        }
    }


    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
