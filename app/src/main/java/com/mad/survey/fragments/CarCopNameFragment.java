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
import com.mad.survey.globals.GlobalConstant;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.BankData;
import com.mad.survey.models.CarData;
import com.mad.survey.models.CopData;
import com.mad.survey.utils.Utils;

public class CarCopNameFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtSubTitle;
    private TextView txtCarNumber;
    private EditText edtCopName;
    private CopData copData;
    private CopData firstCopData = null;

    private View parent;

    public static CarCopNameFragment newInstance(){
        CarCopNameFragment fragment = new CarCopNameFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_car_cop_name, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);

        edtCopName = (EditText) parent.findViewById(R.id.edtCopName);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtCopName.requestFocus();
                Utils.showKeyboard(getActivity(), true, edtCopName);
            }
        });
    }

    private void updateUIs(){
        edtCopName.setText("");

        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
        }
        txtCarNumber.setText(getString(R.string.cop_n_title, MADSurveyApp.getInstance().getCopNum() + 1, MADSurveyApp.getInstance().getCarData().getNumberOfCops(), MADSurveyApp.getInstance().getCarData().getCarNumber()));
        copData = copDataHandler.get(MADSurveyApp.getInstance().getProjectData().getId(),
                    MADSurveyApp.getInstance().getBankNum(),
                    MADSurveyApp.getInstance().getCarNum(),
                    MADSurveyApp.getInstance().getCopNum());

        MADSurveyApp.getInstance().setCopData(copData);

        if(copData != null){
            edtCopName.setText(copData.getCopName());
        }
        else{
            if(MADSurveyApp.getInstance().getCopNum() == 0) {
                edtCopName.setText("MAIN COP");
            }else{
                // make the first cops data as default
                // modified 2017/09/05, alex
                firstCopData = copDataHandler.get(MADSurveyApp.getInstance().getProjectData().getId(),
                        MADSurveyApp.getInstance().getBankNum(),
                        MADSurveyApp.getInstance().getCarNum(),
                        0);
                //edtCopName.setText(firstCopData.getCopName());
                edtCopName.setText("AUX COP" + MADSurveyApp.getInstance().getCopNum());
            }
        }

        //Added by Alex - 2018/01/24
        if (!MADSurveyApp.getInstance().isEditMode() && MADSurveyApp.getInstance().getCopNum() > 0){
            ((BaseActivity) getActivity()).setBackable(false);
            parent.findViewById(R.id.btnBack).setVisibility(View.INVISIBLE);
        }
    }

    private void goToNext(){
        if (!isLastFocused()) return;
        
        String copName = edtCopName.getText().toString();
        if(copName.equals("")){
            edtCopName.requestFocus();
            showToast(getString(R.string.valid_msg_input_car_cop_name), Toast.LENGTH_SHORT);
            return;
        }
        if(copData == null) {
            copData = copDataHandler.insertNewCopWithCopName(MADSurveyApp.getInstance().getProjectData().getId(),
                    MADSurveyApp.getInstance().getBankNum(),
                    MADSurveyApp.getInstance().getCarNum(),
                    MADSurveyApp.getInstance().getCopNum(),
                    copName);

            if (firstCopData != null){
                // data copy from the first cop data
                // modified 2017/09/05, alex

                copData.setOptions(firstCopData.getOptions());
                copData.setReturnHinging(firstCopData.getReturnHinging());
                // for applied measurements
                copData.setReturnPanelWidth(firstCopData.getReturnPanelWidth());
                copData.setReturnPanelHeight(firstCopData.getReturnPanelHeight());
                copData.setCoverWidth(firstCopData.getCoverWidth());
                copData.setCoverHeight(firstCopData.getCoverHeight());
                copData.setCoverToOpening(firstCopData.getCoverToOpening());
                copData.setCoverAff(firstCopData.getCoverAff());
                // for swing measurements
                copData.setSwingPanelWidth(firstCopData.getSwingPanelWidth());
                copData.setSwingPanelHeight(firstCopData.getSwingPanelHeight());
                copData.setCoverAff(firstCopData.getCoverAff());
                // notes and photos
                copData.setNotes(firstCopData.getNotes());
            }
        }
        copData.setCopName(copName);
        MADSurveyApp.getInstance().setCopData(copData);
        copDataHandler.update(copData);
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_CAR_COP_STYLE, "car_cop_style");
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
        updateUIs();
    }
}
