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
import com.mad.survey.dialogs.MadCommonAlertDialog;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.BankData;
import com.mad.survey.models.HallEntranceData;
import com.mad.survey.models.ProjectData;
import com.mad.survey.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class HallEntranceFloorDescriptionFragment extends BaseFragment implements View.OnClickListener ,OnFragmentResumedListener{

    private TextView txtSubTitle;
    private TextView txtFloorNumber;
    private EditText edtFloorDescriptor;
    private MadCommonAlertDialog dlg;

    private List<String> floorNumbers;

    private View parent;

    public static HallEntranceFloorDescriptionFragment newInstance(){
        HallEntranceFloorDescriptionFragment fragment = new HallEntranceFloorDescriptionFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_bank_floor_description, container, false);

        initView(parent);
        updateUIs();
        openInstructionDlg();
        return parent;
    }
    private void openInstructionDlg(){
        dlg = new MadCommonAlertDialog(getActivity(), getString(R.string.instruction), "", "", getString(R.string.instruction_go_to_top_floor_entrance), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.btnOK) {
                    dlg.dismiss();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            edtFloorDescriptor.requestFocus();
                            Utils.showKeyboard(getActivity(), true, edtFloorDescriptor);
                        }
                    }, 200);
                }
            }
        });
        dlg.show();
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        txtFloorNumber = (TextView) parent.findViewById(R.id.txtFloorNumber);
        edtFloorDescriptor = (EditText) parent.findViewById(R.id.edtFloorDescriptor);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);

    }

    private void updateUIs(){
        edtFloorDescriptor.setText("");
        ProjectData projectData = MADSurveyApp.getInstance().getProjectData();

        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtFloorNumber.setText(getString(R.string.floor_record_n_edit_title));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum()+1, MADSurveyApp.getInstance().getBankData().getName()));
            txtFloorNumber.setText(getString(R.string.floor_record_n_title, MADSurveyApp.getInstance().getFloorNum()+1, projectData.getNumFloors()));
        }

        List<String> floorNumbers1 = new ArrayList<>();
        List<String> floorNumbers2 = new ArrayList<>();
        List<String> floorNumbers3 = new ArrayList<>();

        floorNumbers1 = hallStationDataHandler.getFloorNumbers(MADSurveyApp.getInstance().getProjectData().getId(),
                MADSurveyApp.getInstance().getBankNum());

        floorNumbers2 = lanternDataHandler.getFloorNumbers(MADSurveyApp.getInstance().getProjectData().getId(),
                    MADSurveyApp.getInstance().getBankNum());

        floorNumbers3 = hallEntranceDataHandler.getFloorNumbers(MADSurveyApp.getInstance().getProjectData().getId(),
                    MADSurveyApp.getInstance().getBankNum());

        floorNumbers = floorNumbers1.size()>floorNumbers2.size()?floorNumbers1:floorNumbers2;
        floorNumbers = floorNumbers.size()>floorNumbers3.size()?floorNumbers:floorNumbers3;

        int currentFloor = MADSurveyApp.getInstance().getFloorNum();
        /*if(floorNumbers.size() > currentFloor  ) {
            String floorNumber = floorNumbers.get(currentFloor);
            edtFloorDescriptor.setText(floorNumber);
        }*/
        /*
        if(hallStationData != null){
            edtFloorDescriptor.setText(hallStationData.getFloorNumber());
        }
        */

        //Added by Alex - 2018/01/24
        if (!MADSurveyApp.getInstance().isEditMode() && MADSurveyApp.getInstance().getFloorNum() > 0){
            ((BaseActivity) getActivity()).setBackable(false);
            parent.findViewById(R.id.btnBack).setVisibility(View.INVISIBLE);
        }
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        if(edtFloorDescriptor.getText().toString().equals("")){
            edtFloorDescriptor.requestFocus();
            showToast(getString(R.string.valid_msg_input_floor_descriptor), Toast.LENGTH_SHORT);
            return;
        }
        MADSurveyApp.getInstance().setFloorDescriptor(edtFloorDescriptor.getText().toString());
        ProjectData projectData = MADSurveyApp.getInstance().getProjectData();
        BankData bankData = MADSurveyApp.getInstance().getBankData();
        String floorDescription = edtFloorDescriptor.getText().toString();
        if(MADSurveyApp.getInstance().isEditMode()){

            int hallEntranceNum;
            /*
            if(floorNumbers.contains(floorDescription)){
                MADSurveyApp.getInstance().setFloorNum(floorNumbers.indexOf(floorDescription));
                int cntPerFloor = hallEntranceDataHandler.getCntPerFloor(projectData.getId(),bankData.getBankNum(),floorDescription);
                hallEntranceNum = cntPerFloor;
            }
            else{
                //------------------set hallStationNum = 0--------------
                hallEntranceNum = 0;
                MADSurveyApp.getInstance().getProjectData().setNumFloors(floorNumbers.size()+1);
                MADSurveyApp.getInstance().setFloorNum(floorNumbers.size());
                projectDataHandler.update(MADSurveyApp.getInstance().getProjectData());
            }
            */
            hallEntranceNum = hallEntranceDataHandler.getMaxHallEntranceNum(projectData.getId(),bankData.getBankNum(),floorDescription);
            MADSurveyApp.getInstance().setHallEntranceCarNum(hallEntranceNum + 1);
        }
        else{
            MADSurveyApp.getInstance().setHallEntranceCarNum(0);
        }

        // check if one hall entrance is already added in this bank
        List<HallEntranceData> dataList = hallEntranceDataHandler.getAllHallEntrances(projectData.getId(),bankData.getBankNum());
        if (dataList.size() > 0){
            ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_HALL_ENTRANCE_COPY, "hall_entrance_copy");
        }else{
            ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_HALL_ENTRANCE_DOOR_TYPE, "hall_entrance_door_type");
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
        }
    }

    @Override
    public void onFragmentResumed() {
        edtFloorDescriptor.requestFocus();
        updateUIs();
    }
}
