package com.mad.survey.fragments;

import android.os.Bundle;
import android.provider.Settings;
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
import com.mad.survey.models.InteriorCarData;

public class InteriorCarFlooringFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtCarNumber;

    private CheckBox chkCeramic, chkPorcelain;
    private CheckBox chkRubberTiles, chkMarble;
    private CheckBox chkGranit, chkOther;
    private EditText edtOtherName;
    private TextView btnNext;

    public static InteriorCarFlooringFragment newInstance(){
        InteriorCarFlooringFragment fragment = new InteriorCarFlooringFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_interior_car_flooring, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);

        chkCeramic = (CheckBox) parent.findViewById(R.id.chkCeramic);
        chkPorcelain = (CheckBox) parent.findViewById(R.id.chkPorcelain);
        chkRubberTiles = (CheckBox) parent.findViewById(R.id.chkRubberTiles);
        chkMarble = (CheckBox) parent.findViewById(R.id.chkMarble);
        chkGranit = (CheckBox) parent.findViewById(R.id.chkGranit);
        chkOther = (CheckBox) parent.findViewById(R.id.chkOther);
        edtOtherName = (EditText) parent.findViewById(R.id.edtOtherName);
        btnNext = (TextView) parent.findViewById(R.id.btnNext);

        parent.findViewById(R.id.btnCeramic).setOnClickListener(this);
        parent.findViewById(R.id.btnPorcelain).setOnClickListener(this);
        parent.findViewById(R.id.btnRubberTiles).setOnClickListener(this);
        parent.findViewById(R.id.btnMarble).setOnClickListener(this);
        parent.findViewById(R.id.btnGranit).setOnClickListener(this);
        parent.findViewById(R.id.btnOther).setOnClickListener(this);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);

        setHeaderScrollConfiguration(parent, getString(R.string.sub_title_cab_interior), getString(R.string.sub_title_car_flooring), true, true);
    }

    private void updateUIs(){

        chkCeramic.setChecked(false);
        chkPorcelain.setChecked(false);
        chkRubberTiles.setChecked(false);
        chkMarble.setChecked(false);
        chkGranit.setChecked(false);
        chkOther.setChecked(false);

        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_edit_title, MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_title, MADSurveyApp.getInstance().getInteriorCarNum()+1, bankData.getNumOfInteriorCar(),MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }
        InteriorCarData interiorCarData = MADSurveyApp.getInstance().getInteriorCarData();

        String carFlooring = interiorCarData.getCarFlooring();
        if(!carFlooring.equals("")) {
            switch (carFlooring) {
                case GlobalConstant.FLOORING_CERAMIC:
                    chkCeramic.setChecked(true);
                    break;
                case GlobalConstant.FLOORING_GRANIT:
                    chkGranit.setChecked(true);
                    break;
                case GlobalConstant.FLOORING_MARBLE:
                    chkMarble.setChecked(true);
                    break;
                case GlobalConstant.FLOORING_PORCELAIN:
                    chkPorcelain.setChecked(true);
                    break;
                case GlobalConstant.FLOORING_RUBBER_TILES:
                    chkRubberTiles.setChecked(true);
                    break;
                default:
                    updateOtherUIs();
                    chkOther.setChecked(true);
                    edtOtherName.setText(carFlooring);
                    break;
            }
            edtOtherName.setVisibility(chkOther.isChecked()? View.VISIBLE:View.GONE);
            btnNext.setVisibility(chkOther.isChecked()? View.VISIBLE:View.GONE);
        }
    }

    private void goToNext(String carFlooring){
        if (!isLastFocused()) return;

        MADSurveyApp.getInstance().getInteriorCarData().setCarFlooring(carFlooring);
        interiorCarDataHandler.update(MADSurveyApp.getInstance().getInteriorCarData());
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_TILLER_COVER, "interior_car_tiller_cover");
    }

    private void updateOtherUIs(){
        chkCeramic.setChecked(false);
        chkPorcelain.setChecked(false);
        chkRubberTiles.setChecked(false);
        chkMarble.setChecked(false);
        chkGranit.setChecked(false);
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
            case R.id.btnNext:
                goToNext(edtOtherName.getText().toString());
                break;
            case R.id.btnOther:
                updateOtherUIs();
                break;
            case R.id.btnCeramic:
                goToNext(GlobalConstant.FLOORING_CERAMIC);
                break;
            case R.id.btnPorcelain:
                goToNext(GlobalConstant.FLOORING_PORCELAIN);
                break;
            case R.id.btnRubberTiles:
                goToNext(GlobalConstant.FLOORING_RUBBER_TILES);
                break;
            case R.id.btnMarble:
                goToNext(GlobalConstant.FLOORING_MARBLE);
                break;
            case R.id.btnGranit:
                goToNext(GlobalConstant.FLOORING_GRANIT);
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
