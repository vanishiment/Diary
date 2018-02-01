package com.plant.diaryapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.MenuItem;

import com.plant.diaryapp.R;
import com.plant.diaryapp.app.DiaryApp;
import com.plant.diaryapp.data.datasource.DiaryDataSource;
import com.plant.diaryapp.data.local.LocalDiaryDataSource;
import com.plant.diaryapp.data.model.Diary;
import com.plant.diaryapp.data.remote.RemoteDiaryDataSource;
import com.plant.diaryapp.data.repo.DiaryRepo;
import com.plant.diaryapp.ui.StateLayout;
import com.plant.diaryapp.ui.StateRecyclerView;
import com.plant.diaryapp.utils.AppExecutors;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SearchAct extends ToolbarAct implements SearchView.OnQueryTextListener, SearchView.OnSuggestionListener {

    private SearchView mSearchView;
    private SearchView.SearchAutoComplete mSearchAutoComplete;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private StateRecyclerView mRecyclerView;
    private DiaryListAct.DiaryListAdapter mAdapter;
    private List<Diary> mDiaryList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_search);
        initView();
    }

    private void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        }
        setDisplayShowTitleEnabled(false);
        mSearchView = findViewById(R.id.search_search_view);
        mSearchView.onActionViewExpanded();
        mSearchView.setQueryHint(getResources().getString(R.string.search_hint));
        mSearchAutoComplete = mSearchView.findViewById(R.id.search_src_text);
        mSearchAutoComplete.setTextColor(getResources().getColor(R.color.text_primary_color));
        mSearchAutoComplete.setHintTextColor(getResources().getColor(R.color.text_second_primary_color));
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setOnSuggestionListener(this);
        mSearchView.setOnSearchClickListener(v -> {
            CharSequence text = mSearchAutoComplete.getText();
            if (!TextUtils.isEmpty(text)){
                onQuery(text.toString());
            }
        });

        mSwipeRefreshLayout = findViewById(R.id.common_refresh_layout_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark,R.color.colorPrimary,R.color.colorAccent);
        StateLayout stateLayout = findViewById(R.id.common_refresh_layout_state_layout);
        mRecyclerView = findViewById(R.id.common_refresh_layout_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setStateLayout(stateLayout);
        mAdapter = new DiaryListAct.DiaryListAdapter(this,mDiaryList);
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setEnabled(false);
    }

    private void onQuery(String queryString){
        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(true));

        LocalDiaryDataSource local = LocalDiaryDataSource.getSource(DiaryApp.getDiaryDb().mDiaryDao(), AppExecutors.get());
        DiaryRepo repo = DiaryRepo.getRepo(local,new RemoteDiaryDataSource());
        repo.queryDiary(queryString, new DiaryDataSource.LoadDiaryListCallback() {
            @Override
            public void onDiaryListLoaded(List<Diary> diaryList) {
                mAdapter.replaceData(diaryList,true);
                endLoading();
            }

            @Override
            public void onDataNotAvailable() {
                mAdapter.replaceData(new ArrayList<>(),true);
                endLoading();
            }
        });
    }

    private void endLoading(){
        if (mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(false));
        }
        mSwipeRefreshLayout.setEnabled(false);
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    public boolean canBackPressed() {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onNavButtonClick();
        }
        return super.onOptionsItemSelected(item);
    }

    private void onNavButtonClick() {
        CharSequence text = mSearchAutoComplete.getText();
        if (!TextUtils.isEmpty(text)) {
            mSearchAutoComplete.setText("");
            mSearchAutoComplete.requestFocus();
            try {
                Method method = mSearchAutoComplete.getClass().getDeclaredMethod("setImeVisibility",boolean.class);
                method.setAccessible(true);
                method.invoke(mSearchAutoComplete,false);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            finish();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (!TextUtils.isEmpty(query)){
            onQuery(query);
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        return false;
    }

    @Override
    public boolean onSuggestionSelect(int position) {
        return false;
    }

    @Override
    public boolean onSuggestionClick(int position) {
        return false;
    }
}
