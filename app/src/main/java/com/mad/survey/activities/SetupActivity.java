package com.mad.survey.activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.mad.survey.R;
import com.mad.survey.globals.GlobalConstant;
import com.mad.survey.preferences.AppPreference;
import com.mad.survey.utils.Utils;


public class SetupActivity extends BaseActivity implements View.OnClickListener{

    private EditText edtName, edtEmail, edtCompany, edtPhone, edtState;
    private CheckBox chkCentimeters, chkInches;
    private int units = GlobalConstant.PROJECT_SETTING_UNITS_INCHES;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        edtName = (EditText) findViewById(R.id.edtName);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtCompany = (EditText) findViewById(R.id.edtCompany);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtState = (EditText) findViewById(R.id.edtState);
        chkCentimeters = (CheckBox) findViewById(R.id.chkCentimeters);
        chkInches = (CheckBox) findViewById(R.id.chkInches);

        findViewById(R.id.btnBack).setOnClickListener(this);
        findViewById(R.id.btnSave).setOnClickListener(this);
        findViewById(R.id.btnInchesCheck).setOnClickListener(this);
        findViewById(R.id.btnCentimetersCheck).setOnClickListener(this);


        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtName.requestFocus();
                Utils.showKeyboard(SetupActivity.this, true, edtName);
            }
        });

        loadData();
    }

    private void loadData(){
        edtName.setText(AppPreference.getStringPrefValue(this, AppPreference.PREF_KEY_YOUR_NAME));
        edtEmail.setText(AppPreference.getStringPrefValue(this, AppPreference.PREF_KEY_YOUR_EMAIL));
        edtCompany.setText(AppPreference.getStringPrefValue(this, AppPreference.PREF_KEY_YOUR_COMPANY));
        edtPhone.setText(AppPreference.getStringPrefValue(this, AppPreference.PREF_KEY_YOUR_PHONE));
        edtState.setText(AppPreference.getStringPrefValue(this, AppPreference.PREF_KEY_YOUR_STATE_CODE));
        units = AppPreference.getIntPrefValue(this, AppPreference.PREF_KEY_UNITS_LABEL);
        if(units == GlobalConstant.PROJECT_SETTING_UNITS_CENTIMETERS)
            chkCentimeters.setChecked(true);
        else {
            units = GlobalConstant.PROJECT_SETTING_UNITS_INCHES;
            chkInches.setChecked(true);
        }

    }

    private void saveData(){
        String name = edtName.getText().toString();
        String email = edtEmail.getText().toString();
        String company = edtCompany.getText().toString();
        String phone = edtPhone.getText().toString();
        String state = edtState.getText().toString();


        AppPreference.setSharedPrefValue(this, AppPreference.PREF_KEY_YOUR_NAME, name);
        AppPreference.setSharedPrefValue(this, AppPreference.PREF_KEY_YOUR_EMAIL, email);
        AppPreference.setSharedPrefValue(this, AppPreference.PREF_KEY_YOUR_COMPANY, company);
        AppPreference.setSharedPrefValue(this, AppPreference.PREF_KEY_YOUR_PHONE, phone);
        AppPreference.setSharedPrefValue(this, AppPreference.PREF_KEY_YOUR_STATE_CODE, state);
        AppPreference.setSharedPrefValue(this, AppPreference.PREF_KEY_UNITS_LABEL, units);

        finish();
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnSave:
                saveData();
                break;
            case R.id.btnCentimetersCheck:
                units = GlobalConstant.PROJECT_SETTING_UNITS_CENTIMETERS;
                chkInches.setChecked(false);
                chkCentimeters.setChecked(true);
                break;
            case R.id.btnInchesCheck:
                units = GlobalConstant.PROJECT_SETTING_UNITS_INCHES;
                chkInches.setChecked(true);
                chkCentimeters.setChecked(false);
                break;
        }
    }
}
