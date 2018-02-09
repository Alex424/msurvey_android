package com.mad.survey.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mad.survey.R;
import com.mad.survey.activities.BaseActivity;


public class SubmitProjectConfirmFragment extends BaseFragment implements View.OnClickListener {

    public static SubmitProjectConfirmFragment newInstance(){
        SubmitProjectConfirmFragment fragment = new SubmitProjectConfirmFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_submit_project_confirm, container, false);

        initView(parent);

        return parent;
    }

    private void initView(View parent){
        parent.findViewById(R.id.btnDone).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDone:
                ((BaseActivity) getActivity()).backForProjectDone();
                break;
        }
    }
}
