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
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.BankData;
import com.mad.survey.models.LobbyData;
import com.mad.survey.models.ProjectData;
import com.mad.survey.utils.Utils;

public class BankNameFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private View parent;
    private TextView txtSubTitle;
    private EditText edtBankName;
    private int projectId;

    public static BankNameFragment newInstance(){
        BankNameFragment fragment = new BankNameFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_bank_name, container, false);
        projectId = MADSurveyApp.getInstance().getProjectData().getId();
        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        edtBankName = (EditText) parent.findViewById(R.id.edtBankName);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtBankName.requestFocus();
                Utils.showKeyboard(getActivity(), true, edtBankName);
            }
        });
    }

    private void updateUIs(){
        edtBankName.setText("");

        ProjectData projectData = MADSurveyApp.getInstance().getProjectData();
        int currentBank = MADSurveyApp.getInstance().getBankNum();
        BankData bankData = bankDataHandler.get(projectId, currentBank);
        MADSurveyApp.getInstance().setBankData(bankData);
        int numBanks = projectData.getNumBanks();
        if(numBanks>0)
            txtSubTitle.setText(getString(R.string.bank_description_n_title, currentBank+1, numBanks));
        if(bankData != null)
            edtBankName.setText(bankData.getName());

        //Added by Alex - 2018/01/24
        if (!MADSurveyApp.getInstance().isEditMode() && currentBank > 0){
            ((BaseActivity) getActivity()).setBackable(false);
            parent.findViewById(R.id.btnBack).setVisibility(View.INVISIBLE);
        }
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        String bankName = edtBankName.getText().toString();
        if(bankName.equals("")) {
            showToast(getString(R.string.valid_msg_input_bank_name), Toast.LENGTH_SHORT);
            return;
        }
        int currentBank = MADSurveyApp.getInstance().getBankNum();
        BankData bankData = bankDataHandler.insertNewBankWithName(projectId, currentBank, bankName);
        if(bankData!=null){
            MADSurveyApp.getInstance().setBankData(bankData);
        }
        else{
            BankData bankData1 = MADSurveyApp.getInstance().getBankData();
            bankData1.setName(bankName);
            MADSurveyApp.getInstance().setBankData(bankData1);
            bankDataHandler.update(bankData1);
        }
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_BANK_NUMBER_CARS, "bank_number_cars");
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
