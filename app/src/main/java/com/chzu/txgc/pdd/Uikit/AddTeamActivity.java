package com.chzu.txgc.pdd.Uikit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chzu.txgc.pdd.ContactHelper.TeamCreateHelper;
import com.chzu.txgc.pdd.R;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.business.contact.selector.activity.ContactSelectActivity;
import com.netease.nim.uikit.business.team.helper.TeamHelper;
import com.netease.nim.uikit.common.ToastHelper;

import java.util.ArrayList;

public class AddTeamActivity extends AppCompatActivity {
    ImageView addteam;
    LinearLayout linearlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_team);
        addteam=findViewById(R.id.addteam);
        linearlayout=findViewById(R.id.linearlayout);
        addteam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactSelectActivity.Option advancedOption = TeamHelper
                        .getCreateContactSelectOption(null, 50);
                NimUIKit.startContactSelector(AddTeamActivity.this, advancedOption,
                        2);
            }
        });
        linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactSelectActivity.Option advancedOption = TeamHelper
                        .getCreateContactSelectOption(null, 50);
                NimUIKit.startContactSelector(AddTeamActivity.this, advancedOption,
                        2);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {//startActivityForResult
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == 1) {
            final ArrayList<String> selected = data.getStringArrayListExtra(
                    ContactSelectActivity.RESULT_DATA);
            if (selected != null && !selected.isEmpty()) {
                TeamCreateHelper.createNormalTeam(this, selected, false, null);
            } else {
                ToastHelper.showToast(this, "请选择至少一个联系人！");
            }
        } else if (requestCode == 2) {
            final ArrayList<String> selected = data.getStringArrayListExtra(
                    ContactSelectActivity.RESULT_DATA);
            TeamCreateHelper.createAdvancedTeam(this, selected);
        }
    }
}
