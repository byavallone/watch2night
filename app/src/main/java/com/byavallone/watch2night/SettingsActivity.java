package com.byavallone.watch2night;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Settings activity will be used to add user preferences to the app
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public static class MoviesPreferenceFragment extends PreferenceFragment implements  Preference.OnPreferenceChangeListener{

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            Preference sortBy = findPreference(getString(R.string.settings_sort_key));
            bindPreferenceSummaryToValue(sortBy);
        }

        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceValue = preferences.getString(preference.getKey(), getString(R.string.sort_most_popular_index));
            onPreferenceChange(preference, preferenceValue);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {

            String index = String.valueOf(value.toString());
            String summary;
            // checking the proper value to update in the summary
            if(index.equalsIgnoreCase(getString(R.string.sort_most_popular_index))){
                summary = getString(R.string.sort_most_popular_label);
            }else{
                summary = getString(R.string.sort_highest_rate_label);
            }
            preference.setSummary(summary);
            return true;
        }
    }
}
