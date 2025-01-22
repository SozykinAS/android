package com.example.unmatchgamersrandomizer
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import com.example.unmatchgamersrandomizer.databinding.ActivityBanBinding

class BanActivity : AppCompatActivity() {
    private var _binding: ActivityBanBinding?= null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for ActivityMainBinding null")

    private val checkBoxList = mutableListOf<CheckBox>()
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

            mapofgamers?.forEach(){(key, value)->
                val textViewForLnView = TextView(this).apply {
                    text = "$key\n"
                    textSize = 45f
                    gravity = Gravity.START
                }
                lLayoutBan.addView(textViewForLnView)
                value?.forEach (){ str->
                    val checkBox = CheckBox(this)
                        checkBox.text = "${str}"
                        checkBox.textSize = 35f
                        checkBoxList.add(checkBox)
                        lLayoutBan.addView(checkBox)
                }
            }


        }


        binding.btnBack.setOnClickListener(){
            val check_Box_List :MutableList<String> = mutableListOf()
            checkBoxList.forEach(){check_box->
                if(check_box.isChecked){
                    check_Box_List.add(check_box.text.toString())

                }
                else{
                    check_Box_List.add("non")
                }
            }
            val returnIntent = Intent()
            returnIntent.putExtra("resultCheckBox", check_Box_List as ArrayList<String>)
            setResult(Activity.RESULT_OK, returnIntent)

            finish()
        }
    }



}