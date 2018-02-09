package com.mad.survey.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.mad.survey.MADSurveyApp;
import com.mad.survey.R;
import com.mad.survey.activities.BaseActivity;
import com.mad.survey.globals.GlobalConstant;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.BankData;
import com.mad.survey.models.HallStationData;
import com.mad.survey.models.LanternData;
import com.mad.survey.models.ProjectData;

public class LanternWallmaterialFragment extends BaseFragment implements View.OnClickListener ,OnFragmentResumedListener{

    private TextView txtFloorIdentifier, txtLanternPINo;

    private CheckBox chkDrywall, chkPlaster;
    private CheckBox chkConcrete, chkBrick, chkMarble;
    private CheckBox chkGranit, chkGlass, chkTile;
    private CheckBox chkMetal, chkWood, chkOther;
    private EditText edtOtherName;
    private TextView btnNext;
    private static boolean flag = false;
    public static LanternWallmaterialFragment newInstance(boolean tag){

        flag = tag;
        LanternWallmaterialFragment fragment = new LanternWallmaterialFragment();
        return fragment;
    }
    public static LanternWallmaterialFragment newInstance(){

        LanternWallmaterialFragment fragment = new LanternWallmaterialFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_lantern_wallmaterial, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtFloorIdentifier = (TextView) parent.findViewById(R.id.txtFloorIdentifier);
        txtLanternPINo = (TextView) parent.findViewById(R.id.txtLanternPINo);

        chkDrywall = (CheckBox) parent.findViewById(R.id.chkDrywall);
        chkPlaster = (CheckBox) parent.findViewById(R.id.chkPlaster);
        chkConcrete = (CheckBox) parent.findViewById(R.id.chkConcrete);
        chkBrick = (CheckBox) parent.findViewById(R.id.chkBrick);
        chkMarble = (CheckBox) parent.findViewById(R.id.chkMarble);
        chkGranit = (CheckBox) parent.findViewById(R.id.chkGranit);
        chkGlass = (CheckBox) parent.findViewById(R.id.chkGlass);
        chkTile = (CheckBox) parent.findViewById(R.id.chkTile);
        chkMetal = (CheckBox) parent.findViewById(R.id.chkMetal);
        chkWood = (CheckBox) parent.findViewById(R.id.chkWood);
        chkOther = (CheckBox) parent.findViewById(R.id.chkOther);
        edtOtherName = (EditText) parent.findViewById(R.id.edtOtherName);
        btnNext = (TextView) parent.findViewById(R.id.btnNext);

        parent.findViewById(R.id.btnDrywall).setOnClickListener(this);
        parent.findViewById(R.id.btnPlaster).setOnClickListener(this);
        parent.findViewById(R.id.btnConcrete).setOnClickListener(this);
        parent.findViewById(R.id.btnBrick).setOnClickListener(this);
        parent.findViewById(R.id.btnMarble).setOnClickListener(this);
        parent.findViewById(R.id.btnGranit).setOnClickListener(this);
        parent.findViewById(R.id.btnGlass).setOnClickListener(this);
        parent.findViewById(R.id.btnTile).setOnClickListener(this);
        parent.findViewById(R.id.btnMetal).setOnClickListener(this);
        parent.findViewById(R.id.btnWood).setOnClickListener(this);
        parent.findViewById(R.id.btnOther).setOnClickListener(this);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);

        setHeaderScrollConfiguration(parent, getString(R.string.sub_title_hall_lantern), getString(R.string.sub_title_wall_material), true, true);
    }

    private void updateUIs(){
        chkBrick.setChecked(false);
        chkConcrete.setChecked(false);
        chkDrywall.setChecked(false);
        chkGlass.setChecked(false);
        chkGranit.setChecked(false);
        chkMarble.setChecked(false);
        chkMetal.setChecked(false);
        chkPlaster.setChecked(false);
        chkTile.setChecked(false);
        chkWood.setChecked(false);
        chkOther.setChecked(false);
        ProjectData projectData = MADSurveyApp.getInstance().getProjectData();

        LanternData lanternData = MADSurveyApp.getInstance().getLanternData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtFloorIdentifier.setText(getString(R.string.floor_n_identifier_edit_title, MADSurveyApp.getInstance().getFloorDescriptor()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title,MADSurveyApp.getInstance().getBankNum()+1,MADSurveyApp.getInstance().getBankData().getName()));
            txtFloorIdentifier.setText(getString(R.string.floor_n_identifier_title, MADSurveyApp.getInstance().getFloorNum()+1,projectData.getNumFloors() , MADSurveyApp.getInstance().getFloorDescriptor()));
            txtLanternPINo.setText(getString(R.string.lantern_pi_n_title, MADSurveyApp.getInstance().getLanternNum()+1, MADSurveyApp.getInstance().getLanternCnt()));
        }

        String wallMaterial = lanternData.getWallFinish();
        switch (wallMaterial){
            case GlobalConstant.HALLSTATION_BRICK:
                chkBrick.setChecked(true);
                break;
            case GlobalConstant.HALLSTATION_CONCRETE:
                chkConcrete.setChecked(true);
                break;
            case GlobalConstant.HALLSTATION_DRYWALL:
                chkDrywall.setChecked(true);
                break;
            case GlobalConstant.HALLSTATION_GLASS:
                chkGlass.setChecked(true);
                break;
            case GlobalConstant.HALLSTATION_GRANIT:
                chkGranit.setChecked(true);
                break;
            case GlobalConstant.HALLSTATION_MARBLE:
                chkMarble.setChecked(true);
                break;
            case GlobalConstant.HALLSTATION_METAL:
                chkMetal.setChecked(true);
                break;
            case GlobalConstant.HALLSTATION_PLASTER:
                chkPlaster.setChecked(true);
                break;
            case GlobalConstant.HALLSTATION_TILE:
                chkTile.setChecked(true);
                break;
            case GlobalConstant.HALLSTATION_WOOD:
                chkWood.setChecked(true);
                break;
            default:
                chkOther.setChecked(true);
                updateOtherUIs();
                edtOtherName.setText(wallMaterial);
                break;
        }
        edtOtherName.setVisibility(chkOther.isChecked()? View.VISIBLE:View.GONE);
        btnNext.setVisibility(chkOther.isChecked()? View.VISIBLE:View.GONE);

    }

    private void goToNext(String wallMaterial){
        if (!isLastFocused()) return;


        MADSurveyApp.getInstance().getLanternData().setWallFinish(wallMaterial);
        lanternDataHandler.update(MADSurveyApp.getInstance().getLanternData());
        if(flag){
            flag = false;
            getActivity().onBackPressed();
        }
        else
            ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_LANTERN_MEASUREMENTS, "lantern_measurements");
    }

    private void updateOtherUIs(){
        chkBrick.setChecked(false);
        chkConcrete.setChecked(false);
        chkDrywall.setChecked(false);
        chkGlass.setChecked(false);
        chkGranit.setChecked(false);
        chkMarble.setChecked(false);
        chkMetal.setChecked(false);
        chkPlaster.setChecked(false);
        chkTile.setChecked(false);
        chkWood.setChecked(false);

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

            case R.id.btnOther:
                updateOtherUIs();
                break;
            case R.id.btnDrywall:
                goToNext(GlobalConstant.HALLSTATION_DRYWALL);
                break;
            case R.id.btnPlaster:
                goToNext(GlobalConstant.HALLSTATION_PLASTER);
                break;
            case R.id.btnConcrete:
                goToNext(GlobalConstant.HALLSTATION_CONCRETE);
                break;
            case R.id.btnBrick:
                goToNext(GlobalConstant.HALLSTATION_BRICK);
                break;
            case R.id.btnMarble:
                goToNext(GlobalConstant.HALLSTATION_MARBLE);
                break;
            case R.id.btnGranit:
                goToNext(GlobalConstant.HALLSTATION_GRANIT);
                break;
            case R.id.btnGlass:
                goToNext(GlobalConstant.HALLSTATION_GLASS);
                break;
            case R.id.btnTile:
                goToNext(GlobalConstant.HALLSTATION_TILE);
                break;
            case R.id.btnMetal:
                goToNext(GlobalConstant.HALLSTATION_METAL);
                break;
            case R.id.btnWood:
                goToNext(GlobalConstant.HALLSTATION_WOOD);
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
