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
import com.mad.survey.models.ProjectData;
import com.mad.survey.utils.ConversionUtils;
import com.mad.survey.utils.Utils;

public class BankNotesFragment extends BaseFragment implements View.OnClickListener,OnFragmentResumedListener {

    private TextView txtSubTitle;
    private EditText edtBankNotes;

    public static BankNotesFragment newInstance(){
        BankNotesFragment fragment = new BankNotesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_bank_notes, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        edtBankNotes = (EditText) parent.findViewById(R.id.edtBankNotes);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtBankNotes.requestFocus();
                Utils.showKeyboard(getActivity(), true, edtBankNotes);
            }
        });
    }

    private void updateUIs(){
        ProjectData projectData = MADSurveyApp.getInstance().getProjectData();
        int currentBank = MADSurveyApp.getInstance().getBankNum();
        int numBanks = projectData.getNumBanks();
        if(numBanks>0)
            txtSubTitle.setText(getString(R.string.bank_description_n_title, currentBank+1, numBanks));
        edtBankNotes.setText(MADSurveyApp.getInstance().getBankData().getNotes());
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        String notes = edtBankNotes.getText().toString();
        int bankId = MADSurveyApp.getInstance().getBankData().getId();
        MADSurveyApp.getInstance().getBankData().setNotes(notes);

        //------------------set floorNum = 0--------------------
        MADSurveyApp.getInstance().setFloorNum(0);
        //------------------------------------------------------

        bankDataHandler.update(MADSurveyApp.getInstance().getBankData());
        ((BaseActivity) getActivity()).replacePhotosFragment(bankId,GlobalConstant.PROJECT_PHOTO_TYPE_BANK, 0, "");

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
