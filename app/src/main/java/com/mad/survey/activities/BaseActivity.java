package com.mad.survey.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.mad.survey.R;
import com.mad.survey.adapters.EditBankListAdapter;
import com.mad.survey.fragments.*;
import com.mad.survey.globals.GlobalConstant;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.listeners.OnPhotoTakenListener;
import com.mad.survey.utils.Utils;

import java.io.File;
import java.util.List;

public class BaseActivity extends AppCompatActivity {
    protected Fragment currentFragment = null;

    public final static int TAKE_GALLERY = 51;
    public final static int TAKE_CAMERA = 52;
    protected boolean isActivityActive;
    protected String deviceType;

    protected int displayWidth = 0;
    protected int displayHeight = 0;


    public static final int FRAGMENT_ID_PROJECT_DETAILS = 1;
    public static final int FRAGMENT_ID_ITEMS_TO_SURVEY = 2;
    public static final int FRAGMENT_ID_PROJECT_NOTES = 3;
    public static final int FRAGMENT_ID_PROJECT_PHOTOS = 4;
    public static final int FRAGMENT_ID_PROJECT_JOB_TYPE = 5;
    public static final int FRAGMENT_ID_PROJECT_NUMBER_BANKS = 6;
    public static final int FRAGMENT_ID_PROJECT_NUMBER_FLOORS = 7;
    public static final int FRAGMENT_ID_PROJECT_NUMBER_LOBBY_PANELS = 8;
    public static final int FRAGMENT_ID_LOBBY_LOCATION = 9;
    public static final int FRAGMENT_ID_LOBBY_VISIBILITY = 10;
    public static final int FRAGMENT_ID_LOBBY_MEASUREMENTS = 11;
    public static final int FRAGMENT_ID_LOBBY_FEATURES = 12;
    public static final int FRAGMENT_ID_LOBBY_NOTES = 13;
    public static final int FRAGMENT_ID_BANK_NAME = 14;
    public static final int FRAGMENT_ID_BANK_NUMBER_CARS = 15;
    public static final int FRAGMENT_ID_BANK_NUMBER_RISERS = 16;
    public static final int FRAGMENT_ID_BANK_NOTES = 17;
    public static final int FRAGMENT_ID_BANK_FLOOR_DESCRIPTION = 18;
    public static final int FRAGMENT_ID_HALLSTATION = 19;
    public static final int FRAGMENT_ID_HALLSTATION_LIST = 20;
    public static final int FRAGMENT_ID_HALLSTATION_SAMEAS_MEASUREMENTS = 21;
    public static final int FRAGMENT_ID_HALLSTATION_REVIEW = 22;
    public static final int FRAGMENT_ID_HALLSTATION_DESCRIPTION = 23;
    public static final int FRAGMENT_ID_HALLSTATION_MOUNTING = 24;
    public static final int FRAGMENT_ID_HALLSTATION_WALLMATERIAL =25;
    public static final int FRAGMENT_ID_HALLSTATION_MEASUREMENTS = 26;
    public static final int FRAGMENT_ID_HALLSTATION_NOTES = 27;
    public static final int FRAGMENT_ID_LANTERN_NUMBER = 28;
    public static final int FRAGMENT_ID_LANTERN = 29;
    public static final int FRAGMENT_ID_LANTERN_LIST = 30;
    public static final int FRAGMENT_ID_LANTERN_SAMEAS_MEASUREMENTS = 31;
    public static final int FRAGMENT_ID_LANTERN_REVIEW = 32;
    public static final int FRAGMENT_ID_LANTERN_DESCRIPTION = 33;
    public static final int FRAGMENT_ID_LANTERN_MOUNTING = 34;
    public static final int FRAGMENT_ID_LANTERN_WALLMATERIAL = 35;
    public static final int FRAGMENT_ID_LANTERN_MEASUREMENTS = 36;
    public static final int FRAGMENT_ID_LANTERN_NOTES = 37;
    public static final int FRAGMENT_ID_CAR_DESCRIPTION = 38;
    public static final int FRAGMENT_ID_CAR_LICENSE = 39;
    public static final int FRAGMENT_ID_CAR_TYPE = 40;
    public static final int FRAGMENT_ID_CAR_BACKDOOR = 41;
    public static final int FRAGMENT_ID_CAR_DOOR_COINCIDING = 42;
    public static final int FRAGMENT_ID_CAR_PUSHBUTTONS = 43;
    public static final int FRAGMENT_ID_CAR_DOOR_MEASUREMENTS = 44;
    public static final int FRAGMENT_ID_CAR_HANDRAIL = 45;
    public static final int FRAGMENT_ID_CAR_MEASUREMENTS = 46;
    public static final int FRAGMENT_ID_CAR_NOTES = 47;
    public static final int FRAGMENT_ID_CAR_COP_NUMBER = 48;
    public static final int FRAGMENT_ID_CAR_COP_NAME = 49;
    public static final int FRAGMENT_ID_CAR_COP_STYLE = 50;
    public static final int FRAGMENT_ID_CAR_COP_APPLIED_MEASUREMENTS = 51;
    public static final int FRAGMENT_ID_CAR_COP_SWING_MEASUREMENTS = 52;
    public static final int FRAGMENT_ID_CAR_COP_NOTES = 53;
    public static final int FRAGMENT_ID_CAR_RIDING_LANTERN = 54;
    public static final int FRAGMENT_ID_CAR_RIDING_LANTERN_MOUNTING = 55;
    public static final int FRAGMENT_ID_CAR_RIDING_LANTERN_MEASUREMENTS = 56;
    public static final int FRAGMENT_ID_CAR_RIDING_LANTERN_NOTES = 57;
    public static final int FRAGMENT_ID_CAR_SEPERATEPI = 58;
    public static final int FRAGMENT_ID_CAR_SEPERATEPI_MOUNTING = 59;
    public static final int FRAGMENT_ID_CAR_SEPERATEPI_MEASUREMENTS = 60;
    public static final int FRAGMENT_ID_CAR_SEPERATEPI_NOTES = 61;
    public static final int FRAGMENT_ID_INTERIOR_CAR_COUNT = 62;
    public static final int FRAGMENT_ID_INTERIOR_CAR_DESCRIPTION = 63;
    public static final int FRAGMENT_ID_INTERIOR_CAR_LICENSE = 64;
    public static final int FRAGMENT_ID_INTERIOR_CAR_COPY = 300;
    public static final int FRAGMENT_ID_INTERIOR_CAR_LIST = 301;
    public static final int FRAGMENT_ID_INTERIOR_CAR_BACKDOOR = 65;
    public static final int FRAGMENT_ID_INTERIOR_CAR_FLOORING = 66;
    public static final int FRAGMENT_ID_INTERIOR_CAR_TILLER_COVER = 67;
    public static final int FRAGMENT_ID_INTERIOR_CAR_FLOOR_HEIGHT = 68;
    public static final int FRAGMENT_ID_INTERIOR_CAR_CEILING_EXHAUST_FAN = 69;
    public static final int FRAGMENT_ID_INTERIOR_CAR_CEILING_EXHAUST_FAN_LOCATION = 70;
    public static final int FRAGMENT_ID_INTERIOR_CAR_CEILING_FRAME_TYPE = 71;
    public static final int FRAGMENT_ID_INTERIOR_CAR_CEILING_ESCAPE_HATCH_LOCATION = 72;
    public static final int FRAGMENT_ID_INTERIOR_CAR_TYPE = 73;
    public static final int FRAGMENT_ID_INTERIOR_CAR_STRUCTURE = 74;
    public static final int FRAGMENT_ID_INTERIOR_CAR_WALL_TYPE = 302;
    public static final int FRAGMENT_ID_INTERIOR_CAR_OPENING = 75;
    public static final int FRAGMENT_ID_INTERIOR_CAR_CENTER_MEASUREMENTS = 76;
    public static final int FRAGMENT_ID_INTERIOR_CAR_SINGLE_SIDE_MEASUREMENTS = 77;
    public static final int FRAGMENT_ID_INTERIOR_CAR_SLAM_POST_TYPE = 78;
    public static final int FRAGMENT_ID_INTERIOR_CAR_SLAM_POST_TYPE_OTHER = 79;
    public static final int FRAGMENT_ID_INTERIOR_CAR_SLAM_POST_TYPE_A = 80;
    public static final int FRAGMENT_ID_INTERIOR_CAR_SLAM_POST_TYPE_B = 81;
    public static final int FRAGMENT_ID_INTERIOR_CAR_SLAM_POST_TYPE_C = 82;
    public static final int FRAGMENT_ID_INTERIOR_CAR_FRONT_RETURN_TYPE = 83;
    public static final int FRAGMENT_ID_INTERIOR_CAR_CENTER_RETURN_MEASUREMENTS_A = 84;
    public static final int FRAGMENT_ID_INTERIOR_CAR_CENTER_RETURN_MEASUREMENTS_B = 85;
    public static final int FRAGMENT_ID_INTERIOR_CAR_SINGLE_SIDE_RETURN_MEASUREMENTS_A = 86;
    public static final int FRAGMENT_ID_INTERIOR_CAR_SINGLE_SIDE_RETURN_MEASUREMENTS_B = 87;
    public static final int FRAGMENT_ID_INTERIOR_CAR_FRONT_RETURN_MEASUREMENTS_OTHER = 88;
    public static final int FRAGMENT_ID_INTERIOR_CAR_DOOR_TYPE = 89;
    public static final int FRAGMENT_ID_INTERIOR_CAR_DOOR_OPENING_DIRECTION = 90;
    public static final int FRAGMENT_ID_INTERIOR_CAR_TRANSOM_2S = 91;
    public static final int FRAGMENT_ID_INTERIOR_CAR_TRANSOM_1S = 92;
    public static final int FRAGMENT_ID_INTERIOR_CAR_TRANSOM_PROFILE_2S = 93;
    public static final int FRAGMENT_ID_INTERIOR_CAR_TRANSOM_PROFILE_1S = 94;
    public static final int FRAGMENT_ID_INTERIOR_CAR_LTRANSOM = 303;
    public static final int FRAGMENT_ID_INTERIOR_CAR_HEADER = 304;
    public static final int FRAGMENT_ID_INTERIOR_CAR_FLAT_FRONT = 305;
    public static final int FRAGMENT_ID_INTERIOR_CAR_COP_INSTALLED = 95;
    public static final int FRAGMENT_ID_INTERIOR_CAR_MAINCOP_RETURN = 96;
    public static final int FRAGMENT_ID_INTERIOR_CAR_AUXCOP_RETURN = 97;
    public static final int FRAGMENT_ID_INTERIOR_CAR_NOTES = 98;
    public static final int FRAGMENT_ID_INTERIOR_CAR_BACK_WALL_CLONE = 99;
    public static final int FRAGMENT_ID_INTERIOR_CAR_BACK_WALL = 100;
    public static final int FRAGMENT_ID_HALL_ENTRANCE_COPY = 310;
    public static final int FRAGMENT_ID_HALL_ENTRANCE_LIST = 311;
    public static final int FRAGMENT_ID_HALL_ENTRANCE_DOOR_TYPE = 101;
    public static final int FRAGMENT_ID_HALL_ENTRANCE_DOOR_OPENING_DIRECTION = 102;
    public static final int FRAGMENT_ID_HALL_ENTRANCE_FRONT_RETURN_MEASUREMENTS = 103;
    public static final int FRAGMENT_ID_HALL_ENTRANCE_TRANSOM_MEASUREMENTS_CENTER = 104;
    public static final int FRAGMENT_ID_HALL_ENTRANCE_TRANSOM_MEASUREMENTS_2S = 105;
    public static final int FRAGMENT_ID_HALL_ENTRANCE_TRANSOM_MEASUREMENTS_1S = 106;
    public static final int FRAGMENT_ID_HALL_ENTRANCE_NOTES = 107;
    public static final int FRAGMENT_ID_HALL_ENTRANCE_SKIP = 400;
    public static final int FRAGMENT_ID_PROJECT_ALL_ENTERED = 108;
    public static final int FRAGMENT_ID_HALL_ENTRANCE_FLOOR_IDENTIFIER = 109;
    public static final int FRAGMENT_ID_SUBMIT_PROJECT = 200;
    public static final int FRAGMENT_ID_SUBMIT_CONFIRM = 201;
    public static final int FRAGMENT_ID_PROJECT_EDIT = 202;
    public static final int FRAGMENT_ID_EDIT_LOBBY_LIST = 203;
    public static final int FRAGMENT_ID_EDIT_BANK_LIST = 204;
    public static final int FRAGMENT_ID_EDIT_HALLSTATION_LIST = 205;
    public static final int FRAGMENT_ID_EDIT_LANTERN_LIST = 206;
    public static final int FRAGMENT_ID_EDIT_CAR_LIST = 207;
    public static final int FRAGMENT_ID_EDIT_INTERIOR_CAR_LIST = 208;
    public static final int FRAGMENT_ID_EDIT_HALL_ENTRANCE_LIST = 209;






    public static int TEMP_OPENING = 1;         // 1: Center Opening, 2: Single Side
    public static int TEMP_DOOR_TYPE = 1;       // 2 Single Speed, 1: Two Speed Door
    public static int TEMP_DOOR_STYLE = 1;      // 1: Front Door , 2: Back Door
    public static int TEMP_DOOR_STYLE_EDIT = -1;    //


    private boolean backable = true;

    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        deviceType = getString(R.string.device_type);
        isActivityActive = true;

        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        if( Build.VERSION.SDK_INT >= 13 ) {
            Point size = new Point();
            display.getSize(size);
            displayWidth = size.x;
            displayHeight = size.y;
        } else {
            displayWidth = display.getWidth();
            displayHeight = display.getHeight();
        }

    }

    public boolean isActivityActive() {
        return isActivityActive;
    }

    public void setIsActivityActive(boolean isActivityActive) {
        this.isActivityActive = isActivityActive;
    }

    public void setHeaderTitle(String title){
        ((TextView) findViewById(R.id.txtHeaderTitle)).setText(title);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActivityActive = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActivityActive = false;
    }

    public void onStop() {
        super.onStop();
        isActivityActive = false;
        System.gc();
    }

    public boolean isActivityStopped() {
        return !isActivityActive;
    }

    protected void showToast(String text, int duration) {
        if (isActivityStopped()) {
            return;
        }

        Toast.makeText(this, text, duration).show();
    }

    protected boolean isTablet(){
        return deviceType.equals("tablet");
    }

    public void doTakePhoto(){

        if (isActivityActive) {
            Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File file = new File(GlobalConstant.getCameraTempFilePath());
            //it.putExtra(MediaStore.EXTRA_OUTPUT, tempPictuePath ) ;
            it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            startActivityForResult(it, TAKE_CAMERA);
        }
    }
    public void doSelectFromGallery(){

        if (isActivityActive) {
            Intent intent = new Intent( Intent.ACTION_PICK ) ;
            intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE) ;
            startActivityForResult( intent, TAKE_GALLERY ) ;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == TAKE_CAMERA) {
            // Upload receipt image here
            if (currentFragment instanceof OnPhotoTakenListener){
                ((OnPhotoTakenListener) currentFragment).onPhotoTaken(true,"");
            }


        }
        else if(requestCode == TAKE_GALLERY){
            Uri imgUri = data.getData();

            String _strPhotoPath = Utils.getRealPathFromURI(this, imgUri);
            if (currentFragment instanceof OnPhotoTakenListener){
                ((OnPhotoTakenListener) currentFragment).onPhotoTaken(false,_strPhotoPath);
            }
        }
    }
    public void backToSpecificFragment(String tag){
        FragmentManager fm = getSupportFragmentManager();

        List<Fragment> fragments = fm.getFragments();
        int count = fm.getBackStackEntryCount();

        for (int i = count - 1; i >= 0; i--){
            Fragment fragment = fragments.get(i);
            if (!fragment.getTag().equals(tag)){
                fm.popBackStack();
                continue;
            }

            currentFragment = fragment;
            if (fragment instanceof OnFragmentResumedListener){
                ((OnFragmentResumedListener) fragment).onFragmentResumed();
            }
            Utils.hideKeyboard(this);
            break;
        }
    }

    public boolean isBackable() {
        return backable;
    }

    public void setBackable(boolean backable) {
        this.backable = backable;
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

        if (!isBackable()) return;

        FragmentManager fm = getSupportFragmentManager();

        int count = fm.getBackStackEntryCount();
        if (count <= 1) {
            finish();
        } else {
            //fm.popBackStack();
            fm.popBackStackImmediate();

            Fragment fragment = fm.getFragments().get(count - 2);
            currentFragment = fragment;
            if (fragment instanceof OnFragmentResumedListener){
                ((OnFragmentResumedListener) fragment).onFragmentResumed();
            }

            Utils.hideKeyboard(this);
        }
    }

    public void replaceFragment(int nFragId, String tag){
        replaceFragment(nFragId, tag, 0, "");
    }
    public void replaceFragment(int nFragId,String tag,int flag){
        if(nFragId == BaseActivity.FRAGMENT_ID_EDIT_BANK_LIST){
            currentFragment = EditBankListFragment.newInstance(flag);
        }
        getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.fragmentContent, currentFragment, tag).commit();
        Utils.hideKeyboard(this);
    }
    public void replaceFragment(int nFragId, String tag,boolean flag){

        switch(nFragId){
            case BaseActivity.FRAGMENT_ID_HALLSTATION_DESCRIPTION:
                currentFragment = HallStationDescriptionFragment.newInstance(flag);
                break;
            case BaseActivity.FRAGMENT_ID_HALLSTATION_MOUNTING:
                currentFragment = HallStationMountingFragment.newInstance(flag);
                break;
            case BaseActivity.FRAGMENT_ID_HALLSTATION_WALLMATERIAL:
                currentFragment = HallStationWallmaterialFragment.newInstance(flag);
                break;
            case BaseActivity.FRAGMENT_ID_LANTERN_DESCRIPTION:
                currentFragment = LanternDescriptionFragment.newInstance(flag);
                break;
            case BaseActivity.FRAGMENT_ID_LANTERN_MOUNTING:
                currentFragment = LanternMountingFragment.newInstance(flag);
                break;
            case BaseActivity.FRAGMENT_ID_LANTERN_WALLMATERIAL:
                currentFragment = LanternWallmaterialFragment.newInstance(flag);
                break;
            case BaseActivity.FRAGMENT_ID_BANK_FLOOR_DESCRIPTION:
                currentFragment = BankFloorDescriptionFragment.newInstance(flag);
                break;
        }
        getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.fragmentContent, currentFragment, tag).commit();
        Utils.hideKeyboard(this);
    }


    public void replacePhotosFragment(int linkedId,int photoType, int nextFragId, String nextFragTag){
        currentFragment = ProjectPhotosFragment.newInstance(linkedId,photoType, nextFragId, nextFragTag);
        getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.fragmentContent, currentFragment, "photos_" + photoType).commit();

        Utils.hideKeyboard(this);
    }

    public void replaceFragment(int nFragId, String tag, int nextFragId, String nextFragTag){
        switch(nFragId){
            case FRAGMENT_ID_PROJECT_DETAILS:
                currentFragment = ProjectDetailsFragment.newInstance();
                break;
            case FRAGMENT_ID_ITEMS_TO_SURVEY:
                currentFragment = ProjectItemsToSurveyFragment.newInstance();
                break;
            case FRAGMENT_ID_PROJECT_NOTES:
                currentFragment = ProjectNotesFragment.newInstance();
                break;
            case FRAGMENT_ID_PROJECT_JOB_TYPE:
                currentFragment = ProjectJobTypeFragment.newInstance();
                break;
            case FRAGMENT_ID_PROJECT_NUMBER_BANKS:
                currentFragment = ProjectNumberBanksFragment.newInstance();
                break;
            case FRAGMENT_ID_PROJECT_NUMBER_FLOORS:
                currentFragment = ProjectNumberFloorsFragment.newInstance();
                break;
            case FRAGMENT_ID_PROJECT_NUMBER_LOBBY_PANELS:
                currentFragment = ProjectNumberLobbyPanelsFragment.newInstance();
                break;
            case FRAGMENT_ID_LOBBY_LOCATION:
                currentFragment = LobbyLocationFragment.newInstance();
                break;
            case FRAGMENT_ID_LOBBY_VISIBILITY:
                currentFragment = LobbyVisibilityFragment.newInstance();
                break;
            case FRAGMENT_ID_LOBBY_MEASUREMENTS:
                currentFragment = LobbyMeasurementsFragment.newInstance();
                break;
            case FRAGMENT_ID_LOBBY_FEATURES:
                currentFragment = LobbyFeaturesFragment.newInstance();
                break;
            case FRAGMENT_ID_LOBBY_NOTES:
                currentFragment = LobbyNotesFragment.newInstance();
                break;
            case FRAGMENT_ID_BANK_NAME:
                currentFragment = BankNameFragment.newInstance();
                break;
            case FRAGMENT_ID_BANK_NUMBER_CARS:
                currentFragment = BankNumberCarsFragment.newInstance();
                break;
            case FRAGMENT_ID_BANK_NUMBER_RISERS:
                currentFragment = BankNumberRisersFragment.newInstance();
                break;
            case FRAGMENT_ID_BANK_NOTES:
                currentFragment = BankNotesFragment.newInstance();
                break;
            case FRAGMENT_ID_BANK_FLOOR_DESCRIPTION:
                currentFragment = BankFloorDescriptionFragment.newInstance();
                break;
            case FRAGMENT_ID_HALLSTATION:
                currentFragment = HallStationFragment.newInstance();
                break;
            case FRAGMENT_ID_HALLSTATION_LIST:
                currentFragment = HallStationListFragment.newInstance();
                break;
            case FRAGMENT_ID_HALLSTATION_SAMEAS_MEASUREMENTS:
                currentFragment = HallStationSameasMeasurementsFragment.newInstance();
                break;
            case FRAGMENT_ID_HALLSTATION_REVIEW:
                currentFragment = HallStationReviewFragment.newInstance();
                break;
            case FRAGMENT_ID_HALLSTATION_DESCRIPTION:
                currentFragment = HallStationDescriptionFragment.newInstance();
                break;
            case FRAGMENT_ID_HALLSTATION_MOUNTING:
                currentFragment = HallStationMountingFragment.newInstance();
                break;
            case FRAGMENT_ID_HALLSTATION_WALLMATERIAL:
                currentFragment = HallStationWallmaterialFragment.newInstance();
                break;
            case FRAGMENT_ID_HALLSTATION_MEASUREMENTS:
                currentFragment = HallStationMeasurementsFragment.newInstance();
                break;
            case FRAGMENT_ID_HALLSTATION_NOTES:
                currentFragment = HallStationNotesFragment.newInstance();
                break;
            case FRAGMENT_ID_LANTERN_NUMBER:
                currentFragment = LanternNumberFragment.newInstance();
                break;
            case FRAGMENT_ID_LANTERN:
                currentFragment = LanternFragment.newInstance();
                break;
            case FRAGMENT_ID_LANTERN_LIST:
                currentFragment = LanternListFragment.newInstance();
                break;
            case FRAGMENT_ID_LANTERN_SAMEAS_MEASUREMENTS:
                currentFragment = LanternSameasMeasurementsFragment.newInstance();
                break;
            case FRAGMENT_ID_LANTERN_REVIEW:
                currentFragment = LanternReviewFragment.newInstance();
                break;
            case FRAGMENT_ID_LANTERN_DESCRIPTION:
                currentFragment = LanternDescriptionFragment.newInstance();
                break;
            case FRAGMENT_ID_LANTERN_MOUNTING:
                currentFragment = LanternMountingFragment.newInstance();
                break;
            case FRAGMENT_ID_LANTERN_WALLMATERIAL:
                currentFragment = LanternWallmaterialFragment.newInstance();
                break;
            case FRAGMENT_ID_LANTERN_MEASUREMENTS:
                currentFragment = LanternMeasurementsFragment.newInstance();
                break;
            case FRAGMENT_ID_LANTERN_NOTES:
                currentFragment = LanternNotesFragment.newInstance();
                break;
            case FRAGMENT_ID_CAR_DESCRIPTION:
                currentFragment = CarDescriptionFragment.newInstance();
                break;
            case FRAGMENT_ID_CAR_LICENSE:
                currentFragment = CarLicenseFragment.newInstance();
                break;
            case FRAGMENT_ID_CAR_TYPE:
                currentFragment = CarTypeFragment.newInstance();
                break;
            case FRAGMENT_ID_CAR_BACKDOOR:
                currentFragment = CarBackdoorFragment.newInstance();
                break;
            case FRAGMENT_ID_CAR_DOOR_COINCIDING:
                currentFragment = CarDoorCoincidingFragment.newInstance();
                break;
            case FRAGMENT_ID_CAR_PUSHBUTTONS:
                currentFragment = CarPushButtonsFragment.newInstance();
                break;
            case FRAGMENT_ID_CAR_DOOR_MEASUREMENTS:
                currentFragment = CarDoorMeasurementsFragment.newInstance();
                break;
            case FRAGMENT_ID_CAR_HANDRAIL:
                currentFragment = CarHandrailFragment.newInstance();
                break;
            case FRAGMENT_ID_CAR_MEASUREMENTS:
                currentFragment = CarMeasurementsFragment.newInstance();
                break;
            case FRAGMENT_ID_CAR_NOTES:
                currentFragment = CarNotesFragment.newInstance();
                break;
            case FRAGMENT_ID_CAR_COP_NUMBER:
                currentFragment = CarCopNumberFragment.newInstance();
                break;
            case FRAGMENT_ID_CAR_COP_NAME:
                currentFragment = CarCopNameFragment.newInstance();
                break;
            case FRAGMENT_ID_CAR_COP_STYLE:
                currentFragment = CarCopStyleFragment.newInstance();
                break;
            case FRAGMENT_ID_CAR_COP_APPLIED_MEASUREMENTS:
                currentFragment = CarCopAppliedMeasurementsFragment.newInstance();
                break;
            case FRAGMENT_ID_CAR_COP_SWING_MEASUREMENTS:
                currentFragment = CarCopSwingMeasurementsFragment.newInstance();
                break;
            case FRAGMENT_ID_CAR_COP_NOTES:
                currentFragment = CarCopNotesFragment.newInstance();
                break;
            case FRAGMENT_ID_CAR_RIDING_LANTERN:
                currentFragment = CarRidingLanternFragment.newInstance();
                break;
            case FRAGMENT_ID_CAR_RIDING_LANTERN_MOUNTING:
                currentFragment = CarRidingLanternMountingFragment.newInstance();
                break;
            case FRAGMENT_ID_CAR_RIDING_LANTERN_MEASUREMENTS:
                currentFragment = CarRidingLanternMeasurementsFragment.newInstance();
                break;
            case FRAGMENT_ID_CAR_RIDING_LANTERN_NOTES:
                currentFragment = CarRidingLanternNotesFragment.newInstance();
                break;
            case FRAGMENT_ID_CAR_SEPERATEPI:
                currentFragment = CarSeperatePIFragment.newInstance();
                break;
            case FRAGMENT_ID_CAR_SEPERATEPI_MOUNTING:
                currentFragment = CarSeperatePIMountingFragment.newInstance();
                break;
            case FRAGMENT_ID_CAR_SEPERATEPI_MEASUREMENTS:
                currentFragment = CarSeperatePIMeasurementsFragment.newInstance();
                break;
            case FRAGMENT_ID_CAR_SEPERATEPI_NOTES:
                currentFragment = CarSeperatePINotesFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_COUNT:
                currentFragment = InteriorCarCountFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_DESCRIPTION:
                currentFragment = InteriorCarDescriptionFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_LICENSE:
                currentFragment = InteriorCarLicenseFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_COPY:
                currentFragment = InteriorCarCopyFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_LIST:
                currentFragment = InteriorCarsListFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_BACKDOOR:
                currentFragment = InteriorCarBackdoorFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_FLOORING:
                currentFragment = InteriorCarFlooringFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_TILLER_COVER:
                currentFragment = InteriorCarTillerCoverFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_FLOOR_HEIGHT:
                currentFragment = InteriorCarFloorHeightFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_CEILING_EXHAUST_FAN:
                currentFragment = InteriorCarCeilingExhaustFanFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_CEILING_EXHAUST_FAN_LOCATION:
                currentFragment = InteriorCarCeilingExhaustFanLocationFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_CEILING_FRAME_TYPE:
                currentFragment = InteriorCarCeilingFrameTypeFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_CEILING_ESCAPE_HATCH_LOCATION:
                currentFragment = InteriorCarCeilingEscapeHatchLocationFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_TYPE:
                currentFragment = InteriorCarTypeFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_STRUCTURE:
                currentFragment = InteriorCarStructureFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_WALL_TYPE:
                currentFragment = InteriorCarWallTypeFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_OPENING:
                currentFragment = InteriorCarOpeningFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_CENTER_MEASUREMENTS:
                currentFragment = InteriorCarCenterMeasurementsFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_SINGLE_SIDE_MEASUREMENTS:
                currentFragment = InteriorCarSingleSideMeasurementsFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_SLAM_POST_TYPE:
                currentFragment = InteriorCarSlamPostTypeFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_SLAM_POST_TYPE_OTHER:
                currentFragment = InteriorCarSlamPostTypeOtherFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_SLAM_POST_TYPE_A:
                currentFragment = InteriorCarSlamPostTypeAFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_SLAM_POST_TYPE_B:
                currentFragment = InteriorCarSlamPostTypeBFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_SLAM_POST_TYPE_C:
                currentFragment = InteriorCarSlamPostTypeCFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_FRONT_RETURN_TYPE:
                currentFragment = InteriorCarFrontReturnTypeFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_CENTER_RETURN_MEASUREMENTS_A:
                currentFragment = InteriorCarCenterReturnMeasurementsAFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_CENTER_RETURN_MEASUREMENTS_B:
                currentFragment = InteriorCarCenterReturnMeasurementsBFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_SINGLE_SIDE_RETURN_MEASUREMENTS_A:
                currentFragment = InteriorCarSingleSideReturnMeasurementsAFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_SINGLE_SIDE_RETURN_MEASUREMENTS_B:
                currentFragment = InteriorCarSingleSideReturnMeasurementsBFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_FRONT_RETURN_MEASUREMENTS_OTHER:
                currentFragment = InteriorCarFrontReturnMeasurementsOtherFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_DOOR_TYPE:
                currentFragment = InteriorCarDoorTypeFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_DOOR_OPENING_DIRECTION:
                currentFragment = InteriorCarDoorOpeningDirectionFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_TRANSOM_2S:
                currentFragment = InteriorCarTransom2sFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_TRANSOM_1S:
                currentFragment = InteriorCarTransom1sFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_TRANSOM_PROFILE_2S:
                currentFragment = InteriorCarTransomProfile2sFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_TRANSOM_PROFILE_1S:
                currentFragment = InteriorCarTransomProfile1sFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_LTRANSOM:
                currentFragment = InteriorCarLTransomFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_HEADER:
                currentFragment = InteriorCarHeaderFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_FLAT_FRONT:
                currentFragment = InteriorCarFlatFrontFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_COP_INSTALLED:
                currentFragment = InteriorCarCopInstalledFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_MAINCOP_RETURN:
                currentFragment = InteriorCarMainCopReturnFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_AUXCOP_RETURN:
                currentFragment = InteriorCarAuxCopReturnFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_NOTES:
                currentFragment = InteriorCarNotesFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_BACK_WALL_CLONE:
                currentFragment = InteriorCarBackWallCloneFragment.newInstance();
                break;
            case FRAGMENT_ID_INTERIOR_CAR_BACK_WALL:
                currentFragment = InteriorCarBackWallFragment.newInstance();
                break;
            case FRAGMENT_ID_HALL_ENTRANCE_COPY:
                currentFragment = HallEntranceCopyFragment.newInstance();
                break;
            case FRAGMENT_ID_HALL_ENTRANCE_LIST:
                currentFragment = HallEntranceListFragment.newInstance();
                break;
            case FRAGMENT_ID_HALL_ENTRANCE_DOOR_TYPE:
                currentFragment = HallEntranceDoorTypeFragment.newInstance();
                break;
            case FRAGMENT_ID_HALL_ENTRANCE_DOOR_OPENING_DIRECTION:
                currentFragment = HallEntranceDoorOpeningDirectionFragment.newInstance();
                break;
            case FRAGMENT_ID_HALL_ENTRANCE_FRONT_RETURN_MEASUREMENTS:
                currentFragment = HallEntranceFrontReturnMeasurementsFragment.newInstance();
                break;
            case FRAGMENT_ID_HALL_ENTRANCE_TRANSOM_MEASUREMENTS_CENTER:
                currentFragment = HallEntranceTransomMeasurementsCenterFragment.newInstance();
                break;
            case FRAGMENT_ID_HALL_ENTRANCE_TRANSOM_MEASUREMENTS_2S:
                currentFragment = HallEntranceTransomMeasurements2sFragment.newInstance();
                break;
            case FRAGMENT_ID_HALL_ENTRANCE_TRANSOM_MEASUREMENTS_1S:
                currentFragment = HallEntranceTransomMeasurements1sFragment.newInstance();
                break;
            case FRAGMENT_ID_HALL_ENTRANCE_NOTES:
                currentFragment = HallEntranceNotesFragment.newInstance();
                break;
            case FRAGMENT_ID_HALL_ENTRANCE_SKIP:
                currentFragment = HallEntranceSkipRestFragment.newInstance();
                break;
            case FRAGMENT_ID_PROJECT_ALL_ENTERED:
                currentFragment = ProjectAllEnteredFragment.newInstance();
                break;
            case FRAGMENT_ID_HALL_ENTRANCE_FLOOR_IDENTIFIER:
                currentFragment = HallEntranceFloorDescriptionFragment.newInstance();
                break;
            case FRAGMENT_ID_SUBMIT_PROJECT:
                currentFragment = SubmitProjectFragment.newInstance();
                break;
            case FRAGMENT_ID_SUBMIT_CONFIRM:
                currentFragment = SubmitProjectConfirmFragment.newInstance();
                break;
            case FRAGMENT_ID_PROJECT_EDIT:
                currentFragment = ProjectEditFragment.newInstance();
                break;
            case FRAGMENT_ID_EDIT_LOBBY_LIST:
                currentFragment = EditLobbyListFragment.newInstance();
                break;
            case FRAGMENT_ID_EDIT_BANK_LIST:
                currentFragment = EditBankListFragment.newInstance(0);
                break;
            case FRAGMENT_ID_EDIT_HALLSTATION_LIST:
                currentFragment = EditHallStationListFragment.newInstance();
                break;
            case FRAGMENT_ID_EDIT_LANTERN_LIST:
                currentFragment = EditLanternListFragment.newInstance();
                break;
            case FRAGMENT_ID_EDIT_CAR_LIST:
                currentFragment = EditCarListFragment.newInstance();
                break;
            case FRAGMENT_ID_EDIT_INTERIOR_CAR_LIST:
                currentFragment = EditInteriorCarListFragment.newInstance();
                break;
            case FRAGMENT_ID_EDIT_HALL_ENTRANCE_LIST:
                currentFragment = EditHallEntranceListFragment.newInstance();
                break;



        }
        getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.fragmentContent, currentFragment, tag).commit();

        Utils.hideKeyboard(this);
    }

    public void backForProjectDone(){
        FragmentManager fm = getSupportFragmentManager();

        List<Fragment> fragments = fm.getFragments();
        int count = fm.getBackStackEntryCount();

        for (int i = count - 1; i >= 0; i--){
            Fragment fragment = fragments.get(i);
            if (fragment.getTag().equals("submit_project") || fragment.getTag().equals("submit_confirm")){
                fm.popBackStack();
                continue;
            }

            break;
        }
    }
    public void popupAllFragmentsAndExit(){
        FragmentManager fm = getSupportFragmentManager();

        int count = fm.getBackStackEntryCount();

        for (int i = 0; i < count; i++){
            fm.popBackStack();
        }

        finish();
    }





}