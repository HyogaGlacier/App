package com.example.hyoga.splitthecost;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;

public class SettingPrefActivity extends android.app.Activity {
    static public final String PREF_KEY_FRACTION="key_fraction";
    static public final String PREF_KEY_ROUNDUP="key_roundup";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //PrefFragment
        getFragmentManager().beginTransaction().replace(
                android.R.id.content, new PrefFragment()).commit();
    }

    //PrefFragment Class
    public static class PrefFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.setting_pref);

            //Summary
            setSummaryFraction();
        }

        //register listener
        @Override
        public void onResume() {
            super.onResume();
            SharedPreferences sp = getPreferenceScreen().getSharedPreferences();
            sp.registerOnSharedPreferenceChangeListener(listener);
        }

        //unregister listener
        @Override
        public void onPause() {
            super.onPause();
            SharedPreferences sp = getPreferenceScreen().getSharedPreferences();
            sp.unregisterOnSharedPreferenceChangeListener(listener);
        }

        //renew Summary when preference is changed.
        private OnSharedPreferenceChangeListener listener =
                new OnSharedPreferenceChangeListener() {
                    @Override
                    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                        if (key.equals(PREF_KEY_FRACTION)) {
                            setSummaryFraction();
                        }
                    }
                };

        //Summary of Fraction
        private void setSummaryFraction(){
            ListPreference prefFraction=(ListPreference)findPreference(PREF_KEY_FRACTION);
            prefFraction.setSummary(prefFraction.getEntry());
        }
    }
}
