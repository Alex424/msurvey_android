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
import com.mad.survey.models.ProjectData;
import com.mad.survey.utils.ConversionUtils;
import com.mad.survey.utils.Utils;

public class BankNumberRisersFragment extends BaseFragment implements View.OnClickListener,OnFragmentResumedListener {

    private TextView txtSubTitle;
    private EditText edtNumberRisers;

    public static BankNumberRisersFragment newInstance(){
        BankNumberRisersFragment fragment = new BankNumberRisersFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_bank_number_risers, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        edtNumberRisers = (EditText) parent.findViewById(R.id.edtNumberRisers);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtNumberRisers.requestFocus();
                Utils.showKeyboard(getActivity(), true, edtNumberRisers);
            }
        });
    }

    private void updateUIs(){
        edtNumberRisers.setText("");
        ProjectData projectData = MADSurveyApp.getInstance().getProjectData();
        int currentBank = MADSurveyApp.getInstance().getBankNum();
        int numBanks = projectData.getNumBanks();
        if(numBanks>0)
            txtSubTitle.setText(getString(R.string.bank_description_n_title, currentBank+1, numBanks));
        if(MADSurveyApp.getInstance().getBankData().getNumOfRiser()>0)
            edtNumberRisers.setText(MADSurveyApp.getInstance().getBankData().getNumOfRiser()+"");
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        int numRisers = ConversionUtils.getIntegerFromEditText(edtNumberRisers);
        if (numRisers <= 0){
            showToast(getString(R.string.valid_msg_input_risers_count), Toast.LENGTH_SHORT);
            return;
        }

        MADSurveyApp.getInstance().getBankData().setNumOfRiser(numRisers);
        bankDataHandler.update(MADSurveyApp.getInstance().getBankData());
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_BANK_NOTES, "bank_notes");
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
