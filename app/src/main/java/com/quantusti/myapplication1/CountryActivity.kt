package com.quantusti.myapplication1

import ViewModels.CountryViewModels
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.quantusti.myapplication1.databinding.ActivityCountryBinding

class CountryActivity : AppCompatActivity() {
    private var _country: CountryViewModels? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country)

        val _binding = DataBindingUtil.setContentView<ActivityCountryBinding>(this, R.layout.activity_country)
        _country = CountryViewModels(this, _binding)
        _binding.countryModel = _country

        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = this.getColor(R.color.colorBackgroud);
    }
}