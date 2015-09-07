package running.org.running;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
	private static final int MAX_TEMPO = 220;
	private static final int DEFAULT_TEMPO = 160;

	public static final String KEY_SPEED_SETTINGS = "listSpeedSettings";

	public static final String KEY_METRONOME_CATEGORY_SETTINGS = "metronomeCategorySettings";
	public static final String KEY_METRONOME_SETTINGS = "metronomeSettings";
	public static final String KEY_MODE_METRONOME_SETTINGS = "modeMetronomeSettings";
	public static final String KEY_STEPS_BY_MINUTE_SETTINGS = "stepsByMinuteSettings";
	public static final String KEY_STEPS_BY_MINUTE_TIME_SETTINGS = "stepsByMinuteTimeSettings";
	public static final String KEY_STEPS_BY_MINUTE_2ND_INTERVAL_SETTINGS = "stepsByMinuteSecondIntervalSettings";
	public static final String KEY_STEPS_BY_MINUTE_2ND_INTERVAL_TIME_SETTINGS = "stepsByMinuteSecondIntervalTimeSettings";
	public static final String KEY_REPEAT_METRONOME = "repeatMetronomeSettings";

	private ListPreference listSpeedSettings;

	private PreferenceCategory metronomeCategorySettings;
	private CheckBoxPreference metronomeSettings;
	private ListPreference modeMetronomeSettings;
	private ListPreference stepsByMinuteSettings;
	private ListPreference stepsByMinuteTimeSettings;
	private ListPreference stepsByMinuteSecondIntervalSettings;
	private ListPreference stepsByMinuteSecondIntervalTimeSettings;
	private ListPreference repeatMetronomeSettings;

	private Spinner stepsPerMinuteSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
		metronomeCategorySettings = (PreferenceCategory) findPreference(KEY_METRONOME_CATEGORY_SETTINGS);

		listSpeedSettings = (ListPreference)getPreferenceScreen().findPreference(KEY_SPEED_SETTINGS);
		metronomeSettings = (CheckBoxPreference)getPreferenceScreen().findPreference(KEY_METRONOME_SETTINGS);
		modeMetronomeSettings = (ListPreference)getPreferenceScreen().findPreference(KEY_MODE_METRONOME_SETTINGS);
		stepsByMinuteSettings = (ListPreference)getPreferenceScreen().findPreference(KEY_STEPS_BY_MINUTE_SETTINGS);
		stepsByMinuteTimeSettings = (ListPreference)getPreferenceScreen().findPreference(KEY_STEPS_BY_MINUTE_TIME_SETTINGS);
		stepsByMinuteSecondIntervalSettings = (ListPreference)getPreferenceScreen().findPreference(KEY_STEPS_BY_MINUTE_2ND_INTERVAL_SETTINGS);
		stepsByMinuteSecondIntervalTimeSettings = (ListPreference)getPreferenceScreen().findPreference(KEY_STEPS_BY_MINUTE_2ND_INTERVAL_TIME_SETTINGS);
		repeatMetronomeSettings = (ListPreference)getPreferenceScreen().findPreference(KEY_REPEAT_METRONOME);

		metronomeSettings.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				int modeMetronomeSetting = Integer.parseInt(modeMetronomeSettings.getValue().toString());

				if (newValue.toString().equals("true")) {
					metronomeCategorySettings.addPreference(modeMetronomeSettings);
					metronomeCategorySettings.addPreference(stepsByMinuteSettings);
					switch(modeMetronomeSetting) {
						case Constants.MODE_METRONOME_SETTING_CONTINUE: {
							metronomeCategorySettings.removePreference(stepsByMinuteTimeSettings);
							metronomeCategorySettings.removePreference(stepsByMinuteSecondIntervalSettings);
							metronomeCategorySettings.removePreference(stepsByMinuteSecondIntervalTimeSettings);
							metronomeCategorySettings.removePreference(repeatMetronomeSettings);
							break;
						}
						case Constants.MODE_METRONOME_SETTING_INTERVAL: {
							metronomeCategorySettings.addPreference(stepsByMinuteTimeSettings);
							metronomeCategorySettings.addPreference(stepsByMinuteSecondIntervalSettings);
							metronomeCategorySettings.addPreference(stepsByMinuteSecondIntervalTimeSettings);
							metronomeCategorySettings.addPreference(repeatMetronomeSettings);
							break;
						}
					}
					AppSettings.getInstance().putInt(AppSettings.METRONOME_SETTING, Constants.METRONOME_SETTING_ON);
				} else {
					metronomeCategorySettings.removePreference(modeMetronomeSettings);
					metronomeCategorySettings.removePreference(stepsByMinuteSettings);
					metronomeCategorySettings.removePreference(stepsByMinuteTimeSettings);
					metronomeCategorySettings.removePreference(stepsByMinuteSecondIntervalSettings);
					metronomeCategorySettings.removePreference(stepsByMinuteSecondIntervalTimeSettings);
					metronomeCategorySettings.removePreference(repeatMetronomeSettings);
					AppSettings.getInstance().putInt(AppSettings.METRONOME_SETTING, Constants.METRONOME_SETTING_OFF);
				}
				return true;
			}
		});

		//stepsByMinuteSettings.setOnPreferenceChangeListener(new Preference.OnPreferenceClickListener() {
		//	stepsByMinuteSettings.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			//@Override
			//public boolean onPreferenceChange(Preference preference, Object newValue) {
			//public boolean onPreferenceClick(Preference preference) {
			//stepsByMinuteSettings.getDialog().dismiss();

		//		String bpm[]=new String[MAX_TEMPO+1];
		//		for (int i=0; i<MAX_TEMPO+1; i++) bpm[i]= "" + i;
		//		bpm[0]="Silent";

			//stepsPerMinuteSpinner= (Spinner) findViewById(R.id.spinner_spm_metronome_i1);
			//ArrayAdapter<String> adapterSPMI1 = new ArrayAdapter<String>(SettingsActivity.this, android.R.layout.simple_spinner_item, bpm);
			//adapterSPMI1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			//stepsPerMinuteSpinner.setAdapter(adapterSPMI1);
			//stepsPerMinuteSpinner.setSelection(DEFAULT_TEMPO);
			//stepsPerMinuteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			//	@Override
			//	public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
			// your code here
			//	}

			//	@Override
			//	public void onNothingSelected(AdapterView<?> parentView) {
			// your code here
			//	}
		//		return true;
		//	}
			//return true;
		//});

		//stepsByMinuteTimeSettings.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
		//	@Override
		//	public boolean onPreferenceClick(Preference preference) {
		//		stepsByMinuteTimeSettings.getDialog().dismiss();
				//launch spinner/numberpicker/activity/dialog here
		//		return true;
		//	}
		//});

		//stepsByMinuteSecondIntervalSettings.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
		//	@Override
		//	public boolean onPreferenceClick(Preference preference) {
		//		stepsByMinuteSettings.getDialog().dismiss();
		//		//launch spinner/numberpicker/activity/dialog here
		//		return true;
		//	}
		//});

		//stepsByMinuteSecondIntervalTimeSettings.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
		//	@Override
		//	public boolean onPreferenceClick(Preference preference) {
		//		stepsByMinuteTimeSettings.getDialog().dismiss();
		//		//launch spinner/numberpicker/activity/dialog here
		//		return true;
		//	}
		//});

		//repeatMetronomeSettings.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
		//	@Override
		//	public boolean onPreferenceClick(Preference preference) {
		//		repeatMetronomeSettings.getDialog().dismiss();
				//launch spinner/numberpicker/activity/dialog here
		//		return true;
		//	}
		//});

		if (AppSettings.getInstance().getInt(AppSettings.METRONOME_SETTING)==Constants.METRONOME_SETTING_OFF) {
			metronomeSettings.setChecked(false);
			metronomeCategorySettings.removePreference(modeMetronomeSettings);
			metronomeCategorySettings.removePreference(stepsByMinuteSettings);
			metronomeCategorySettings.removePreference(stepsByMinuteTimeSettings);
			metronomeCategorySettings.removePreference(stepsByMinuteSecondIntervalSettings);
			metronomeCategorySettings.removePreference(stepsByMinuteSecondIntervalTimeSettings);
			metronomeCategorySettings.removePreference(repeatMetronomeSettings);
		} else {
			metronomeSettings.setChecked(true);
			int modeMetronomeSetting = Integer.parseInt(modeMetronomeSettings.getValue().toString());
			metronomeCategorySettings.addPreference(modeMetronomeSettings);
			metronomeCategorySettings.addPreference(stepsByMinuteSettings);
			switch(modeMetronomeSetting) {
				case Constants.MODE_METRONOME_SETTING_CONTINUE: {
					metronomeCategorySettings.removePreference(stepsByMinuteTimeSettings);
					metronomeCategorySettings.removePreference(stepsByMinuteSecondIntervalSettings);
					metronomeCategorySettings.removePreference(stepsByMinuteSecondIntervalTimeSettings);
					metronomeCategorySettings.removePreference(repeatMetronomeSettings);
					break;
				}
				case Constants.MODE_METRONOME_SETTING_INTERVAL: {
					metronomeCategorySettings.addPreference(stepsByMinuteTimeSettings);
					metronomeCategorySettings.addPreference(stepsByMinuteSecondIntervalSettings);
					metronomeCategorySettings.addPreference(stepsByMinuteSecondIntervalTimeSettings);
					metronomeCategorySettings.addPreference(repeatMetronomeSettings);
					break;
				}
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		// Setup the initial values
		listSpeedSettings.setSummary(listSpeedSettings.getEntry().toString());
		modeMetronomeSettings.setSummary(modeMetronomeSettings.getEntry().toString());
		stepsByMinuteSettings.setSummary(stepsByMinuteSettings.getEntry().toString());
		stepsByMinuteTimeSettings.setSummary(stepsByMinuteTimeSettings.getEntry().toString());
		stepsByMinuteSecondIntervalSettings.setSummary(stepsByMinuteSecondIntervalSettings.getEntry().toString());
		//stepsByMinuteSecondIntervalTimeSettings.setSummary(stepsByMinuteSecondIntervalTimeSettings.getEntry().toString());
		stepsByMinuteSecondIntervalTimeSettings.setSummary(stepsByMinuteSecondIntervalTimeSettings.getEntry());
		repeatMetronomeSettings.setSummary(repeatMetronomeSettings.getEntry());

		// Set up a listener whenever a key changes
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
		//AppSettings.getInstance().putInt(AppSettings.SPEED_PEACE_SETTING, Constants.SPEED_SETTING_KMH);
		//AppSettings.getInstance().putInt(AppSettings.METRONOME_SETTING, Constants.METRONOME_SETTING_OFF);
		//AppSettings.getInstance().putInt(AppSettings.MODE_METRONOME_SETTING, Constants.MODE_METRONOME_SETTING_CONTINUE);
		//AppSettings.getInstance().putInt(AppSettings.STEPS_BY_MINUTE_SETTING, Constants.STEPS_BY_MINUTE_SETTING);
		//AppSettings.getInstance().putInt(AppSettings.STEPS_BY_MINUTE_TIME_SETTING, Constants.STEPS_BY_MINUTE_TIME_SETTING);
		//AppSettings.getInstance().putInt(AppSettings.STEPS_BY_MINUTE_2ND_INTERVAL_SETTING, Constants.STEPS_BY_MINUTE_2ND_INTERVAL_SETTING);
		//AppSettings.getInstance().putInt(AppSettings.STEPS_BY_MINUTE_2ND_INTERVAL_TIME_SETTING, Constants.STEPS_BY_MINUTE_2ND_INTERVAL_TIME_SETTING);
		//AppSettings.getInstance().putInt(AppSettings.REPEAT_METRONOME_SETTING, Constants.REPEAT_METRONOME_SETTING);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// Unregister the listener whenever a key changes
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}

	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		// Set new summary, when a preference value changes
		if (key.equals(KEY_SPEED_SETTINGS)) {
			String s = listSpeedSettings.getValue().toString();
			int speedSetting = Integer.parseInt(s);
			listSpeedSettings.setSummary(listSpeedSettings.getEntry().toString());
			switch(speedSetting) {
				case R.id.unit_kmh: {
					AppSettings.getInstance().putInt(AppSettings.SPEED_PEACE_SETTING, Constants.SPEED_SETTING_KMH);
					break;
				}
				case R.id.unit_mk: {
					AppSettings.getInstance().putInt(AppSettings.SPEED_PEACE_SETTING, Constants.SPEED_SETTING_MIN_KM);
					break;
				}
			}
		}
		if (key.equals(KEY_MODE_METRONOME_SETTINGS)) {
			String s = modeMetronomeSettings.getValue().toString();
			int modeMetronomeSetting = Integer.parseInt(s);
			modeMetronomeSettings.setSummary(modeMetronomeSettings.getEntry().toString());
			switch(modeMetronomeSetting) {
				case Constants.MODE_METRONOME_SETTING_CONTINUE: {
					metronomeCategorySettings.removePreference(stepsByMinuteTimeSettings);
					metronomeCategorySettings.removePreference(stepsByMinuteSecondIntervalSettings);
					metronomeCategorySettings.removePreference(stepsByMinuteSecondIntervalTimeSettings);
					metronomeCategorySettings.removePreference(repeatMetronomeSettings);
					AppSettings.getInstance().putInt(AppSettings.MODE_METRONOME_SETTING, Constants.MODE_METRONOME_SETTING_CONTINUE);
					break;
				}
				case Constants.MODE_METRONOME_SETTING_INTERVAL: {
					metronomeCategorySettings.addPreference(stepsByMinuteTimeSettings);
					metronomeCategorySettings.addPreference(stepsByMinuteSecondIntervalSettings);
					metronomeCategorySettings.addPreference(stepsByMinuteSecondIntervalTimeSettings);
					metronomeCategorySettings.addPreference(repeatMetronomeSettings);
					AppSettings.getInstance().putInt(AppSettings.MODE_METRONOME_SETTING, Constants.MODE_METRONOME_SETTING_INTERVAL);
					break;
				}
			}
		}

		if (key.equals(KEY_STEPS_BY_MINUTE_SETTINGS)) {
			int stepsByMinuteSetting = Integer.parseInt(stepsByMinuteSettings.getValue());
			stepsByMinuteSettings.setSummary(stepsByMinuteSettings.getEntry().toString());
            AppSettings.getInstance().putInt(AppSettings.STEPS_BY_MINUTE_SETTING, stepsByMinuteSetting);
		}

		if (key.equals(KEY_STEPS_BY_MINUTE_TIME_SETTINGS)) {
			int stepsByMinuteTimeSetting = Integer.parseInt(stepsByMinuteTimeSettings.getValue());
			stepsByMinuteTimeSettings.setSummary(stepsByMinuteTimeSettings.getEntry().toString());
			AppSettings.getInstance().putInt(AppSettings.STEPS_BY_MINUTE_TIME_SETTING, stepsByMinuteTimeSetting);
		}

		if (key.equals(KEY_STEPS_BY_MINUTE_2ND_INTERVAL_SETTINGS)) {
			int stepsByMinuteSecondIntervalSetting = Integer.parseInt(stepsByMinuteSecondIntervalSettings.getValue());
			stepsByMinuteSecondIntervalSettings.setSummary(stepsByMinuteSecondIntervalSettings.getEntry().toString());
			AppSettings.getInstance().putInt(AppSettings.STEPS_BY_MINUTE_2ND_INTERVAL_SETTING, stepsByMinuteSecondIntervalSetting);
		}

		if (key.equals(KEY_STEPS_BY_MINUTE_2ND_INTERVAL_TIME_SETTINGS)) {
			int stepsByMinuteSecondIntervalTimeSetting = Integer.parseInt(stepsByMinuteSecondIntervalTimeSettings.getValue());
			stepsByMinuteSecondIntervalTimeSettings.setSummary(stepsByMinuteSecondIntervalTimeSettings.getEntry().toString());
			AppSettings.getInstance().putInt(AppSettings.STEPS_BY_MINUTE_2ND_INTERVAL_TIME_SETTING, stepsByMinuteSecondIntervalTimeSetting);
		}

		if (key.equals(KEY_REPEAT_METRONOME)) {
			int repeatMetronomeSetting = Integer.parseInt(repeatMetronomeSettings.getValue());
			repeatMetronomeSettings.setSummary(repeatMetronomeSettings.getEntry().toString());
			AppSettings.getInstance().putInt(AppSettings.REPEAT_METRONOME_SETTING, repeatMetronomeSetting);
		}
	}
}
