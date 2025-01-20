package com.example.unmatchgamersrandomizer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.unmatchgamersrandomizer.databinding.ActivityBanBinding
import com.example.unmatchgamersrandomizer.databinding.ActivityMainBinding

class BanActivity : AppCompatActivity() {
    var _binding: ActivityBanBinding?= null
    val binding
        get() = _binding ?: throw IllegalStateException("Binding for ActivityMainBinding null")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ban)
        _binding = ActivityBanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener(){
            //pass
        }
    }



}