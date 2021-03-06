package com.codeskraps.talarkam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

public class PrefsActivity extends PreferenceActivity implements
		OnPreferenceClickListener, OnSharedPreferenceChangeListener {
	private static final String TAG = PrefsActivity.class.getSimpleName();
	private static final String FEEDBACK = "prefFeedBack";
	private static final String LANGUAGE = "lstLanguage";
	private static final String DONATION = "donation";

	private String[] aLanguages;
	private Preference prefFeedBack = null;
	private Preference donation = null;
	private SharedPreferences prefs = null;
	private ListPreference lstLanguage = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);

		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(this);

		setContentView(R.layout.prefs);
		addPreferencesFromResource(R.xml.prefs);

		prefFeedBack = (Preference) findPreference(FEEDBACK);
		lstLanguage = (ListPreference) findPreference(LANGUAGE);
		donation = (Preference) findPreference(DONATION);

		prefFeedBack.setOnPreferenceClickListener(this);
		donation.setOnPreferenceClickListener(this);

		int languageSelected = Integer.parseInt(prefs.getString(LANGUAGE, Integer.toString(0)));
		aLanguages = getResources().getStringArray(R.array.alanguages);

		lstLanguage.setSummary(aLanguages[languageSelected]);
	}

	@Override
	public boolean onPreferenceClick(Preference key) {

		if (key.equals(prefFeedBack)) {
			Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
	
			String aEmailList[] = { "codeskraps@gmail.com" };
	
			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
					"Talarkam - Feedback");
			emailIntent.setType("plain/text");
	
			startActivity(Intent.createChooser(emailIntent,
					"Send your feedback in:"));
			
		} else if (key.equals(donation)) {
			
			startActivity(new Intent(this, DonationActivity.class));
		}
		return true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		prefs.unregisterOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if (key.equals(LANGUAGE)) {
			
			int languageSelected = Integer.parseInt(prefs.getString(LANGUAGE, Integer.toString(0)));
			lstLanguage.setSummary(aLanguages[languageSelected]);
		}
	}
}
