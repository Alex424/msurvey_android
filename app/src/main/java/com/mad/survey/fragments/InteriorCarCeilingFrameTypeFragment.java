package com.mad.survey.fragments;

import android.os.Bundle;
import android.provider.Settings;
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
import com.mad.survey.globals.GlobalConstant;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.BankData;
import com.mad.survey.models.InteriorCarData;

public class InteriorCarCeilingFrameTypeFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtCarNumber;

    private CheckBox chkCeilingMounted, chkWallMounted;
    private CheckBox chkBolted, chkWelded, chkOther;
    private EditText edtOtherName;
    private TextView btnNext;

    private String ceilingFrame = "",mountingType = "";
    public static InteriorCarCeilingFrameTypeFragment newInstance(){
        InteriorCarCeilingFrameTypeFragment fragment = new InteriorCarCeilingFrameTypeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_interior_car_ceiling_frame_type, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);

        chkCeilingMounted = (CheckBox) parent.findViewById(R.id.chkCeilingMounted);
        chkWallMounted = (CheckBox) parent.findViewById(R.id.chkWallMounted);
        chkBolted = (CheckBox) parent.findViewById(R.id.chkBolted);
        chkWelded = (CheckBox) parent.findViewById(R.id.chkWelded);
        chkOther = (CheckBox) parent.findViewById(R.id.chkOther);
        edtOtherName = (EditText) parent.findViewById(R.id.edtOtherName);
        btnNext = (TextView) parent.findViewById(R.id.btnNext);

        parent.findViewById(R.id.btnCeilingMounted).setOnClickListener(this);
        parent.findViewById(R.id.btnWallMounted).setOnClickListener(this);
        parent.findViewById(R.id.btnBolted).setOnClickListener(this);
        parent.findViewById(R.id.btnWelded).setOnClickListener(this);
        parent.findViewById(R.id.btnOther).setOnClickListener(this);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);

        setHeaderScrollConfiguration(parent, getString(R.string.sub_title_cab_interior), getString(R.string.sub_title_car_ceiling_frame_type), true, true);
    }

    private void updateUIs(){
        chkCeilingMounted.setChecked(false);
        chkWallMounted.setChecked(false);
        chkBolted.setChecked(false);
        chkWelded.setChecked(false);
        chkOther.setChecked(false);
        ceilingFrame = mountingType = "";

        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_edit_title, MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_title, MADSurveyApp.getInstance().getInteriorCarNum()+1, bankData.getNumOfInteriorCar(),MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }
        InteriorCarData interiorCarData = MADSurveyApp.getInstance().getInteriorCarData();
        ceilingFrame  = interiorCarData.getTypeOfCeilingFrame();
        mountingType = interiorCarData.getMount();

        if(ceilingFrame.equals(GlobalConstant.TYPE_OF_CEILING_FRAME_CEILING)){
            chkCeilingMounted.setChecked(true);
        }
        else if(ceilingFrame.equals(GlobalConstant.TYPE_OF_CEILING_FRAME_WALL)){
            chkWallMounted.setChecked(true);
        }

        if(!mountingType.equals("")) {
            switch (mountingType) {
                case GlobalConstant.TYPE_OF_CEILING_MOUNTING_TYPE_BOLTED:
                    chkBolted.setChecked(true);
                    break;
                case GlobalConstant.TYPE_OF_CEILING_MOUNTING_TYPE_WELDED:
                    chkWelded.setChecked(true);
                    break;
                default:
                    updateOtherUIs();
                    chkOther.setChecked(true);
                    edtOtherName.setText(mountingType);
                    break;
            }
            edtOtherName.setVisibility(chkOther.isChecked()? View.VISIBLE:View.GONE);

        }
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        if(chkOther.isChecked())
            mountingType = edtOtherName.getText().toString();
        if(mountingType.equals("")){
            showToast("Please select mounting Type!", Toast.LENGTH_SHORT);
            return;
        }
        if(ceilingFrame.equals("")){
            showToast("Please select ceiling Frame!", Toast.LENGTH_SHORT);
            return;
        }
        MADSurveyApp.getInstance().getInteriorCarData().setTypeOfCeilingFrame(ceilingFrame);
        MADSurveyApp.getInstance().getInteriorCarData().setMount(mountingType);
        interiorCarDataHandler.update(MADSurveyApp.getInstance().getInteriorCarData());
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_CEILING_ESCAPE_HATCH_LOCATION, "interior_car_ceiling_escape_hatch_location");
    }

    private void updateOtherUIs(){

        chkBolted.setChecked(false);
        chkWelded.setChecked(false);

        chkOther.setChecked(!chkOther.isChecked());
        edtOtherName.setVisibility(chkOther.isChecked()? View.VISIBLE:View.GONE);
        btnNext.setVisibility(chkOther.isChecked()? View.VISIBLE:View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.btnCeilingMounted:
                ceilingFrame = GlobalConstant.TYPE_OF_CEILING_FRAME_CEILING;
                chkCeilingMounted.setChecked(true);
                chkWallMounted.setChecked(false);
                break;
            case R.id.btnWallMounted:
                ceilingFrame = GlobalConstant.TYPE_OF_CEILING_FRAME_WALL;
                chkCeilingMounted.setChecked(false);
                chkWallMounted.setChecked(true);
                break;
            case R.id.btnBolted:
                mountingType = GlobalConstant.TYPE_OF_CEILING_MOUNTING_TYPE_BOLTED;
                chkBolted.setChecked(true);
                chkWelded.setChecked(false);
                chkOther.setChecked(false);
                edtOtherName.setVisibility(View.GONE);
                break;
            case R.id.btnWelded:
                mountingType = GlobalConstant.TYPE_OF_CEILING_MOUNTING_TYPE_WELDED;
                chkBolted.setChecked(false);
                chkWelded.setChecked(true);
                chkOther.setChecked(false);
                edtOtherName.setVisibility(View.GONE);
                break;
            case R.id.btnNext:
                goToNext();
                break;
            case R.id.btnOther:
                chkBolted.setChecked(false);
                chkWelded.setChecked(false);
                updateOtherUIs();
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
