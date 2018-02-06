package com.plant.diaryapp.fragment;

import com.google.gson.Gson;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.widget.Toast;

import com.plant.diaryapp.R;
import com.plant.diaryapp.app.DiaryApp;
import com.plant.diaryapp.data.datasource.DiaryDataSource;
import com.plant.diaryapp.data.local.LocalDiaryDataSource;
import com.plant.diaryapp.data.model.Diary;
import com.plant.diaryapp.data.remote.RemoteDiaryDataSource;
import com.plant.diaryapp.data.repo.DiaryRepo;
import com.plant.diaryapp.support.permission.MPermission;
import com.plant.diaryapp.support.permission.callback.PermissionCallback;
import com.plant.diaryapp.utils.AppExecutors;
import com.plant.diaryapp.utils.ToastUtil;
import com.plant.diaryapp.widget.DialogFragExtend;

import org.json.JSONArray;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SettingFrag extends PreferenceFragmentCompat {

    public SettingFrag() {
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.setting_preference);

//    findPreference("night_model").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//      @Override public boolean onPreferenceChange(Preference preference, Object newValue) {
//        if ((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
//            == Configuration.UI_MODE_NIGHT_YES) {
//          AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        } else {
//
//          AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        }
//        getActivity().getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
//        getActivity().recreate();
//        return true;
//      }
//    });

        findPreference("download_diary").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                exportFile();
                return true;
            }
        });

        findPreference("rate").setOnPreferenceClickListener(preference -> {
            Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            if (null != intent.resolveActivity(getActivity().getPackageManager())) {
                startActivity(intent);
            } else {
                ToastUtil.show(getActivity(),R.string.no_app_store, Toast.LENGTH_SHORT);
            }

            return true;
        });

        findPreference("feedback").setOnPreferenceClickListener(preference -> {
            Uri uri = Uri.parse(getString(R.string.sendto));
            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_topic));
            intent.putExtra(Intent.EXTRA_TEXT,
                    getString(R.string.device_model) + Build.MODEL + "\n"
                            + getString(R.string.sdk_version) + Build.VERSION.RELEASE + "\n"
                            + getString(R.string.settings_app_version));
            if (null != intent.resolveActivity(getActivity().getPackageManager())) {
                startActivity(intent);
            } else {
                ToastUtil.show(getActivity(),R.string.no_email_app, Toast.LENGTH_SHORT);
            }
            return true;
        });


//    findPreference("coffee").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//      @Override public boolean onPreferenceChange(Preference preference, Object newValue) {
//        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
//        dialog.setTitle(R.string.donate);
//        dialog.setMessage(getString(R.string.donate_content));
//        dialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.positive), new DialogInterface.OnClickListener() {
//          @Override public void onClick(DialogInterface dialog, int which) {
//            // 将指定账号添加到剪切板
//            // add the alipay account to clipboard
//            ClipboardManager manager = (ClipboardManager) getContext().getSystemService(CLIPBOARD_SERVICE);
//            ClipData clipData = ClipData.newPlainText("text", getString(R.string.donate_account));
//            manager.setPrimaryClip(clipData);
//
//          }
//        });
//        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.negative), new DialogInterface.OnClickListener() {
//          @Override public void onClick(DialogInterface dialog, int which) {
//
//          }
//        });
//        dialog.show();
//        return true;
//      }
//    });
    }

    private void exportFile(){
        showExportDialog(false);
        Runnable exportFile = ()->{
            List<Diary> diaryList = DiaryApp.getDiaryDb().mDiaryDao().queryAllDiary();
            if (!diaryList.isEmpty()){
                Gson gson = new Gson();
                String json = gson.toJson(diaryList);
                prepareFile(json);
            }else {
                hideExportDialog();
            }
        };

        AppExecutors.get().diskIO().execute(exportFile);
    }

    private void prepareFile(String json){
        Runnable reqPermission = ()->{
            MPermission.getInstance().request(SettingFrag.this, new PermissionCallback() {
                @Override
                public void onGranted() {
                    mkFile(json);
                }

                @Override
                public void onDenied(List<String> perms) {
                    hideExportDialog();
                }
            },new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"});
        };
        AppExecutors.get().mainThread().execute(reqPermission);
    }

    private void mkFile(String json){
        Runnable mkFile = ()->{
            try {
                String rootDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "LittleDiary" + File.separator;
                File fileDir = new File(rootDir);
                if (!fileDir.exists()){
                    fileDir.mkdirs();
                }
                File file = new File(fileDir,"diary_note.json");
                if (!file.exists()){
                    file.createNewFile();
                }else {
                    file.delete();
                    file.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(json.getBytes());
                fos.close();
                showExportDialog(true);
            } catch (IOException e) {
                e.printStackTrace();
                hideExportDialog();
            }
        };
        AppExecutors.get().diskIO().execute(mkFile);
    }

    private void showExportDialog(boolean exportSuccess){

        Runnable uiR = ()->{
            DialogFragExtend dialogFragExtend = (DialogFragExtend) getChildFragmentManager().findFragmentByTag("DialogFragExtend");
            if (dialogFragExtend == null){
                dialogFragExtend = new DialogFragExtend();
                getChildFragmentManager().beginTransaction().add(dialogFragExtend,"DialogFragExtend").commit();
            }
            if (exportSuccess){
                dialogFragExtend.showExportSuccess();
            }
        };

        AppExecutors.get().mainThread().execute(uiR);
    }

    private void hideExportDialog(){
        Runnable uiR = () -> {
            DialogFragExtend dialogFragExtend = (DialogFragExtend) getChildFragmentManager().findFragmentByTag("DialogFragExtend");
            if (dialogFragExtend != null){
                dialogFragExtend.dismiss();
            }
        };
        AppExecutors.get().mainThread().execute(uiR);
    }
}
