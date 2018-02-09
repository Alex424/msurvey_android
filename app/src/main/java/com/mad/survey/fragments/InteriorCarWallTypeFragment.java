package com.mad.survey.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mad.survey.MADSurveyApp;
import com.mad.survey.R;
import com.mad.survey.activities.BaseActivity;
import com.mad.survey.dialogs.MadCommonAlertDialog;
import com.mad.survey.globals.GlobalConstant;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.BankData;
import com.mad.survey.models.InteriorCarData;
import com.mad.survey.models.InteriorCarDoorData;

public class InteriorCarWallTypeFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private View parent;
    private TextView txtCarNumber;
    private TextView txtSubTitle;
    private CheckBox chk3Piece,chk5Piece, chkHybrid;
    private EditText edtNotes;
    private MadCommonAlertDialog dlg;

    public static InteriorCarWallTypeFragment newInstance(){
        InteriorCarWallTypeFragment fragment = new InteriorCarWallTypeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_interior_car_wall_type, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);
        chk3Piece = (CheckBox) parent.findViewById(R.id.chk3Piece);
        chk5Piece = (CheckBox) parent.findViewById(R.id.chk5Piece);
        chkHybrid = (CheckBox) parent.findViewById(R.id.chkHybrid);
        edtNotes = (EditText) parent.findViewById(R.id.edtNotes);
        parent.findViewById(R.id.btn3Piece).setOnClickListener(this);
        parent.findViewById(R.id.btn5Piece).setOnClickListener(this);
        parent.findViewById(R.id.btnHybrid).setOnClickListener(this);
        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
        parent.findViewById(R.id.btnHelp).setOnClickListener(this);
    }

    private void updateUIs(){
        chk3Piece.setChecked(false);
        chk5Piece.setChecked(false);
        chkHybrid.setChecked(false);
        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_edit_title, MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_title, MADSurveyApp.getInstance().getInteriorCarNum()+1, bankData.getNumOfInteriorCar(),MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }
        InteriorCarData interiorCarData = MADSurveyApp.getInstance().getInteriorCarData();
        int doorId = -1;
        if(BaseActivity.TEMP_DOOR_STYLE == 1)
            doorId = interiorCarData.getFrontDoorId();
        else
            doorId = interiorCarData.getBackDoorId();

        InteriorCarDoorData interiorCarDoorData = interiorCarDoorDataHandler.get(doorId);
        MADSurveyApp.getInstance().setInteriorCarDoorData(interiorCarDoorData);
        if(interiorCarDoorData!=null) {
            if (interiorCarDoorData.getWallType().equals(GlobalConstant.WALL_TYPE_3_PIECE)){
                chk3Piece.setChecked(true);
            }else if (interiorCarDoorData.getWallType().equals(GlobalConstant.WALL_TYPE_5_PIECE)){
                chk5Piece.setChecked(true);
            }else if (interiorCarDoorData.getWallType().equals(GlobalConstant.WALL_TYPE_HYBRID)){
                chkHybrid.setChecked(true);
            }
            edtNotes.setText(interiorCarDoorData.getNotes());
        }

        //Added by Alex - 2018/01/24
        if (!MADSurveyApp.getInstance().isEditMode() && BaseActivity.TEMP_DOOR_STYLE == 2){
            ((BaseActivity) getActivity()).setBackable(false);
            parent.findViewById(R.id.btnBack).setVisibility(View.INVISIBLE);
        }

    }
    private void goToNext(){
        if (!isLastFocused()) return;

        String wallType = "";
        if (chk3Piece.isChecked()){
            wallType = GlobalConstant.WALL_TYPE_3_PIECE;
        }else if (chk5Piece.isChecked()){
            wallType = GlobalConstant.WALL_TYPE_5_PIECE;
        }else if (chkHybrid.isChecked()){
            wallType = GlobalConstant.WALL_TYPE_HYBRID;
        }

        if (wallType.equals("")){
            showToast(getString(R.string.valid_msg_input_wall_type), Toast.LENGTH_SHORT);
            return;
        }

        InteriorCarData interiorCarData = MADSurveyApp.getInstance().getInteriorCarData();
        InteriorCarDoorData interiorCarDoorData = interiorCarDoorDataHandler.insertNewInteriorCarDoorWithStartWallTypeAndNotes(MADSurveyApp.getInstance().getProjectData().getId(),
                interiorCarData.getId(),
                BaseActivity.TEMP_DOOR_STYLE,
                wallType,
                edtNotes.getText().toString());
        if (interiorCarDoorData != null){
            MADSurveyApp.getInstance().setInteriorCarDoorData(interiorCarDoorData);
                //front door
            if(BaseActivity.TEMP_DOOR_STYLE == 1)
                interiorCarData.setFrontDoorId(interiorCarDoorData.getId());
                //Back door
            else if(BaseActivity.TEMP_DOOR_STYLE == 2)
                interiorCarData.setBackDoorId(interiorCarDoorData.getId());
            MADSurveyApp.getInstance().setInteriorCarData(interiorCarData);
            interiorCarDataHandler.update(interiorCarData);
        }

        else{
            InteriorCarDoorData interiorCarDoorData1 = MADSurveyApp.getInstance().getInteriorCarDoorData();
            interiorCarDoorData1.setWallType(wallType);
            interiorCarDoorData1.setNotes(edtNotes.getText().toString());
            MADSurveyApp.getInstance().setInteriorCarDoorData(interiorCarDoorData1);
            interiorCarDoorDataHandler.update(interiorCarDoorData1);
        }

        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_OPENING, "interior_car_opening");
    }

    private void updateCheckUIs(CheckBox chkBox){
        chk3Piece.setChecked(false);
        chk5Piece.setChecked(false);
        chkHybrid.setChecked(false);

        chkBox.setChecked(true);
    }

    // This is for showing popup for BACK DOOR for EDIT MODE only
    // Added by Alex 2017/09/18
    private void openInstructionDlg(){
        if (BaseActivity.TEMP_DOOR_STYLE_EDIT == 100) {
            dlg = new MadCommonAlertDialog(getActivity(), getString(R.string.instruction), "", "", getString(R.string.instruction_car_interior_backdoor), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view.getId() == R.id.btnOK) {
                        dlg.dismiss();
                    }
                }
            });
            dlg.show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.btn3Piece:
                updateCheckUIs(chk3Piece);
                break;
            case R.id.btn5Piece:
                updateCheckUIs(chk5Piece);
                break;
            case R.id.btnHybrid:
                updateCheckUIs(chkHybrid);
                break;
            case R.id.btnNext:
                goToNext();
                break;
            case R.id.btnHelp:
                showHelpDialog(getActivity(), getString(R.string.help_title_wall_type), R.drawable.img_help_interior_wall_type, GlobalConstant.HELP_PHOTO_SIZE_RATE);
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
        setBackdoorTitle(parent);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                openInstructionDlg();
            }
        }, 200);

    }
}
