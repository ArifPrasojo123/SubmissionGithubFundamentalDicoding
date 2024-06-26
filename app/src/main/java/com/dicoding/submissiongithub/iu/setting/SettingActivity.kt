package com.dicoding.submissiongithub.iu.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.dicoding.submissiongithub.R
import com.dicoding.submissiongithub.iu.ViewModelFactory
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingActivity : AppCompatActivity() {

    private val settingViewModel by viewModels<SettingViewModel> {
        ViewModelFactory.getInstance(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
        }
    }
}