package com.mad.survey.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.mad.survey.R;
import com.mad.survey.globals.GlobalConstant;
import com.mad.survey.models.ProjectData;

public class ItemDeleteDialog extends Dialog {
	protected Context mContext;

    private View.OnClickListener mClickListener;

    private String strBankInfo,strFloorInfo,strItemDesc;
    private TextView txtBankInfo, txtFloorInfo, txtItemDesc;

	public ItemDeleteDialog(final Context context, String bankInfo,String floorInfo,String itemDesc, View.OnClickListener listener) {
		super(context, R.style.MADAlertDialogStyle);
		this.mContext = context;
        this.mClickListener = listener;
        strBankInfo = bankInfo;
        strFloorInfo =floorInfo;
        strItemDesc = itemDesc;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.dlg_existing_item_delete);

        txtBankInfo = (TextView) findViewById(R.id.txtBankInfo);
        txtFloorInfo = (TextView) findViewById(R.id.txtFloorInfo);
        txtItemDesc = (TextView) findViewById(R.id.txtItemDesc);

        findViewById(R.id.btnYes).setOnClickListener(mClickListener);
        findViewById(R.id.btnNo).setOnClickListener(mClickListener);
        findViewById(R.id.btnClose).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }});

        txtBankInfo.setText(strBankInfo);
        txtFloorInfo.setText(strFloorInfo);
        txtItemDesc.setText(strItemDesc);
	}
}
