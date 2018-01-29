package com.plant.diaryapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.plant.diaryapp.R;
import com.plant.diaryapp.fragment.CardFragment;
import com.plant.diaryapp.utils.DateUtil;

import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;
import java.util.List;


public class HomeAct extends AppCompatActivity implements View.OnClickListener{

    private CircleImageView mLogo;
    private ViewPager mContainer;
    private FloatingActionButton mFab;
    private CardFragsAdapter mCardFragsAdapter;
    private List<Fragment> mFragmentList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);
        initViews();
        setupViews(DateUtil.getCurYear());
    }

    private void initViews(){
        Toolbar toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mLogo = findViewById(R.id.home_logo);
        mContainer = findViewById(R.id.home_view_pager);
        mFab = findViewById(R.id.home_fab);

        mLogo.setOnClickListener(this);
        mFab.setOnClickListener(this);
    }

    private void setupViews(int year){
        if (mFragmentList == null){
            mFragmentList = new ArrayList<>(12);
        }
        mFragmentList.clear();
        for (int i = 1; i <= 12; i++) {
            mFragmentList.add(CardFragment.newIns(year,i));
        }
        mCardFragsAdapter = new CardFragsAdapter(getSupportFragmentManager(),mFragmentList);
        mContainer.setAdapter(mCardFragsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_date){
            return true;
        }else if (item.getItemId() == R.id.action_search){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.home_logo){

        }else if (v.getId() == R.id.home_fab){

        }
    }
}
