package com.example.unmatchgamersrandomizer
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import com.example.unmatchgamersrandomizer.databinding.ActivityBanBinding

class BanActivity : AppCompatActivity() {


    private var _binding: ActivityBanBinding?= null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for ActivityMainBinding null")

    private val checkBoxList = mutableMapOf<String, MutableList<CheckBox>>(
        "Игроки" to mutableListOf(), "Персонажи" to mutableListOf(),"Карты" to mutableListOf())
    private val checkBoxMapTransfer = mutableMapOf<String, MutableList<String>>(
        "Игроки" to mutableListOf(), "Персонажи" to mutableListOf(),"Карты" to mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ban)

        _binding = ActivityBanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val lLayoutBan = binding.lLayoutBan

        val receivedBundle: Bundle? = intent.extras

        if (receivedBundle != null) {

            val arr_ban = receivedBundle.getBundle("key_data")
            val mapofgamers = mapOf("Игроки" to arr_ban?.getStringArrayList("Игроки"),
                                    "Персонажи" to arr_ban?.getStringArrayList("Персонажи"),
                                    "Карты" to arr_ban?.getStringArrayList("Карты"))

            // Цикл формирования checkBoks
            mapofgamers?.forEach(){(key, value)->
                val textViewForLnView = TextView(this).apply {
                    text = "$key"
                    textSize = 30f
                    gravity = Gravity.START
                }
                lLayoutBan.addView(textViewForLnView)
                value?.forEach (){ str->
                    val checkBox = CheckBox(this).apply {
                        text = "${str}"
                        textSize = 25f
                    }
                    checkBoxList[key]?.add(checkBox)
                    lLayoutBan.addView(checkBox)
                }
            }



        }

        //Слушатель на нажатую кнопку назад
        binding.btnBack.setOnClickListener(){
            val returnIntent = Intent()
            checkBoxList.forEach() { key_cb, check_box_list ->
                check_box_list.forEach() { check_box->
                    if (check_box.isChecked) {
                        checkBoxMapTransfer[key_cb]?.add(check_box.text.toString())
                    }
                }
                returnIntent.putExtra(key_cb, checkBoxMapTransfer[key_cb] as ArrayList<String>)
            }


            setResult(Activity.RESULT_OK, returnIntent)

            finish()

        }
    }



}