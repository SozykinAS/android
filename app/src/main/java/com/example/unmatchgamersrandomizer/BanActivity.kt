package com.example.unmatchgamersrandomizer
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.GridLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.unmatchgamersrandomizer.databinding.ActivityBanBinding

class BanActivity : AppCompatActivity() {
    private var _binding: ActivityBanBinding?= null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for ActivityMainBinding null")

    private val buttonList = mutableListOf<Button>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ban)
        _binding = ActivityBanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gridLayout = binding.gridLayout
        val receivedBundle: Bundle? = intent.extras
        if (receivedBundle != null) {

            val arr_ban = receivedBundle.getBundle("key_data")
            val gamers = arr_ban?.getStringArrayList("Игроки")

                gamers?.forEachIndexed() { ind, value->

                    val button = CheckBox(this)
                    button.text = "${value}"
                    button.textSize = 20f
                    buttonList.add(button)


                    val params = GridLayout.LayoutParams()
                    params.rowSpec = GridLayout.spec(ind)
                    params.columnSpec = GridLayout.spec(0)
                    params.setMargins(5, 5, 5, 5)
                    gridLayout.addView(button, params)

                }



        }


        binding.btnBack.setOnClickListener(){
            //pass
            finish()
        }
    }



}