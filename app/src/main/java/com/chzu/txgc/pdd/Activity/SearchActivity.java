package com.chzu.txgc.pdd.Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chzu.txgc.pdd.R;
import com.chzu.txgc.pdd.Uikit.AdvancedTeamJoinActivity;
import com.netease.nim.uikit.api.wrapper.NimToolBarOptions;
import com.netease.nim.uikit.common.ToastHelper;
import com.netease.nim.uikit.common.activity.ToolBarOptions;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.common.ui.widget.ClearableEditTextWithIcon;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.team.TeamService;
import com.netease.nimlib.sdk.team.model.Team;

public class SearchActivity extends UI {
    private ClearableEditTextWithIcon searchEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setTitle(R.string.search_join_team);
        ToolBarOptions options = new NimToolBarOptions();
        options.titleId = R.string.search_join_team;
        setToolBar(R.id.toolbar, options);
        findViews();
        initActionbar();
    }
    private void findViews() {
        searchEditText = findViewById(R.id.team_search_edittext);
        searchEditText.setDeleteImage(R.drawable.nim_grey_delete_icon);
    }

    private void initActionbar() {
        TextView toolbarView = findView(R.id.action_bar_right_clickable_textview);
        toolbarView.setText(R.string.search);
        toolbarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(searchEditText.getText().toString())) {
                    ToastHelper.showToast(SearchActivity.this, R.string.not_allow_empty);
                } else {
                    queryTeamById();
                }
            }
        });
    }

    private void queryTeamById() {
        NIMClient.getService(TeamService.class).searchTeam(searchEditText.getText().toString()).setCallback(new RequestCallback<Team>() {
            @Override
            public void onSuccess(Team team) {
                updateTeamInfo(team);
            }

            @Override
            public void onFailed(int code) {
                if (code == ResponseCode.RES_TEAM_ENOTEXIST) {
                    ToastHelper.showToast(SearchActivity.this,"群号不存在");
                } else {
                    ToastHelper.showToast(SearchActivity.this, "查找群失败 " + code);
                }
            }

            @Override
            public void onException(Throwable exception) {
                ToastHelper.showToast(SearchActivity.this, "search team exception：" + exception.getMessage());
            }
        });
    }

    private void updateTeamInfo(Team team) {
        if (team.getId().equals(searchEditText.getText().toString())) {
            AdvancedTeamJoinActivity.start(this, team.getId());
        }
    }
}
