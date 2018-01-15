package com.plant.diary.ui.settings;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import com.plant.diary.R;

import static android.content.Context.CLIPBOARD_SERVICE;

public class SettingFragmentPreference extends PreferenceFragmentCompat {

  public SettingFragmentPreference() {
  }

  @Override public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    addPreferencesFromResource(R.xml.setting_preference);

    findPreference("night_model").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
      @Override public boolean onPreferenceChange(Preference preference, Object newValue) {
        if ((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
            == Configuration.UI_MODE_NIGHT_YES) {
          AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {

          AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        getActivity().getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
        getActivity().recreate();
        return true;
      }
    });

    findPreference("download_diary").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
      @Override public boolean onPreferenceClick(Preference preference) {

        return true;
      }
    });

    findPreference("rate").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
      @Override public boolean onPreferenceClick(Preference preference) {
        try {
          Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
          Intent intent = new Intent(Intent.ACTION_VIEW,uri);
          intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex){

        }
        return true;
      }
    });

    findPreference("feedback").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
      @Override public boolean onPreferenceChange(Preference preference, Object newValue) {
        try{
          Uri uri = Uri.parse(getString(R.string.sendto));
          Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
          intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_topic));
          intent.putExtra(Intent.EXTRA_TEXT,
              getString(R.string.device_model) + Build.MODEL + "\n"
                  + getString(R.string.sdk_version) + Build.VERSION.RELEASE + "\n"
                  + getString(R.string.settings_app_version));
          startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex){

        }
        return true;
      }
    });

    findPreference("coffee").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
      @Override public boolean onPreferenceChange(Preference preference, Object newValue) {
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        dialog.setTitle(R.string.donate);
        dialog.setMessage(getString(R.string.donate_content));
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.positive), new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
            // 将指定账号添加到剪切板
            // add the alipay account to clipboard
            ClipboardManager manager = (ClipboardManager) getContext().getSystemService(CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("text", getString(R.string.donate_account));
            manager.setPrimaryClip(clipData);

          }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.negative), new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {

          }
        });
        dialog.show();
        return true;
      }
    });
  }

}
