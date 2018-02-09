package com.mad.survey.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.mad.survey.R;

public class CarUnitSelectDialog extends Dialog {
	protected Context mContext;

    private View.OnClickListener mClickListener;

	public CarUnitSelectDialog(final Context context, View.OnClickListener listener) {
		super(context, R.style.MADAlertDialogStyle);
		this.mContext = context;
        this.mClickListener = listener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.dlg_car_unit_select);

        findViewById(R.id.btnKG).setOnClickListener(mClickListener);
        findViewById(R.id.btnLBS).setOnClickListener(mClickListener);
        findViewById(R.id.btnClose).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }});
    }
}
