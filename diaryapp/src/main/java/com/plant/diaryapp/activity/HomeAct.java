package com.plant.diaryapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.plant.diaryapp.R;
import com.plant.diaryapp.app.DiaryApp;
import com.plant.diaryapp.data.datasource.DiaryDataSource;
import com.plant.diaryapp.data.local.LocalDiaryDataSource;
import com.plant.diaryapp.data.model.Diary;
import com.plant.diaryapp.data.remote.RemoteDiaryDataSource;
import com.plant.diaryapp.data.repo.DiaryRepo;
import com.plant.diaryapp.fragment.CardFragment;
import com.plant.diaryapp.utils.AppExecutors;
import com.plant.diaryapp.utils.DateUtil;
import com.plant.diaryapp.widget.DatePickerDialog;

import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;
import java.util.List;


public class HomeAct extends AppCompatActivity implements View.OnClickListener,DatePickerDialog.OnDateClickListener{

    private CircleImageView mLogo;
    private TextView mTitle;
    private ViewPager mContainer;
    private FloatingActionButton mFab;
    private CardFragsAdapter mCardFragsAdapter;
    private List<Fragment> mFragmentList;

    private boolean mToday = false;
    private Diary mDiary;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.blue_primary_dark));
        initViews();
        setHomeTitle(String.valueOf(DateUtil.getCurYear()));
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
        mTitle = findViewById(R.id.home_title);
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
    protected void onResume() {
        super.onResume();
        LocalDiaryDataSource local = LocalDiaryDataSource.getSource(DiaryApp.getDiaryDb().mDiaryDao(),AppExecutors.get());
        DiaryRepo repo = DiaryRepo.getRepo(local,new RemoteDiaryDataSource());
        repo.getDiary(DateUtil.getCurYear(), DateUtil.getCurMonth(), DateUtil.getCurDay(), new DiaryDataSource.GetDiaryCallback() {
            @Override
            public void onDiaryLoaded(Diary diary) {
                mToday = true;
                mDiary = diary;
                mFab.setImageResource(R.drawable.ic_today_white_24dp);
            }

            @Override
            public void onDataNotAvailable() {
                mToday = false;
                mFab.setImageResource(R.drawable.ic_add_white_24dp);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_date){
            show(getSupportFragmentManager());
            return true;
        }else if (item.getItemId() == R.id.action_search){
            launchAct(SearchAct.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.home_logo){
            launchAct(SettingsAct.class);
        }else if (v.getId() == R.id.home_fab){
//            addDiary();
            Intent intent;
            if (mToday){
                intent = new Intent(this,DiaryAct.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(DiaryAct.DIARY,mDiary);
                intent.putExtras(bundle);
            }else {
                intent = new Intent(this,EditAct.class);
            }
            intent.putExtra(EditAct.YEAR,DateUtil.getCurYear());
            intent.putExtra(EditAct.MONTH,DateUtil.getCurMonth());
            intent.putExtra(EditAct.DAY,DateUtil.getCurDay());
            startActivity(intent);
        }
    }

    private void addDiary(){
        LocalDiaryDataSource local = LocalDiaryDataSource.getSource(DiaryApp.getDiaryDb().mDiaryDao(), AppExecutors.get());
        DiaryRepo repo = DiaryRepo.getRepo(local,new RemoteDiaryDataSource());
        Diary diary = new Diary();
        diary.setYear(2018);
        diary.setMonth(1);
        diary.setDay(31);
        diary.setWeek(3);
        diary.setWeather("Snow");
        diary.setTitle("白雪皑皑");
        diary.setContent("今天下雪了！");
        repo.insertDiary(diary);

        Diary diary1 = new Diary();
        diary1.setYear(2018);
        diary1.setMonth(1);
        diary1.setDay(30);
        diary1.setWeek(2);
        diary1.setWeather("Snow");
        diary1.setTitle("白雪皑皑");
        diary1.setContent("今天下雪了！");
        repo.insertDiary(diary1);
    }

    private void launchAct(Class<? extends Activity> clz){
        Intent intent = new Intent(this,clz);
        startActivity(intent);
    }

    @Override
    public void onItemClick(String item) {
        dismiss(getSupportFragmentManager());
        setHomeTitle(item);
        setupViews(Integer.parseInt(item));
    }

    private void setHomeTitle(String title){
        String appName = getResources().getString(R.string.app_name);
        mTitle.setText(String.format("%s·%s", appName, title));
    }

    private void resetData(int year){
        if (mFragmentList == null){
            mFragmentList = new ArrayList<>(12);
        }
        mFragmentList.clear();
        mCardFragsAdapter.notifyDataSetChanged();
        for (int i = 1; i <= 12; i++) {
            mFragmentList.add(CardFragment.newIns(year,i));
        }
        mCardFragsAdapter.notifyDataSetChanged();
    }

    public void show(FragmentManager fm){
        DatePickerDialog dialog = new DatePickerDialog();
        dialog.show(fm,"DatePickerDialog");
    }

    public void dismiss(FragmentManager fm){
        DatePickerDialog dialog = (DatePickerDialog) fm.findFragmentByTag("DatePickerDialog");
        if (dialog != null){
            dialog.dismiss();
        }
    }
}
