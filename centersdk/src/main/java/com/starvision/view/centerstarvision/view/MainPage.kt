package com.starvision.view.centerstarvision.view

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.starvision.luckygamesdk.databinding.MainPageBinding

class MainPage: AppCompatActivity() {
    val binding: MainPageBinding by lazy { MainPageBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(binding.root)
    }
}