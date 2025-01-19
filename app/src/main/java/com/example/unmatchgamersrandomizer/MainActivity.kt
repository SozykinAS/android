package com.example.unmatchgamersrandomizer

import android.app.Activity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.unmatchgamersrandomizer.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {
    var _binding: ActivityMainBinding?= null
    val binding
        get() = _binding ?: throw IllegalStateException("Binding for ActivityMainBinding null")
    val game = Unmatched()
    var basestr = ""
    var s = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        game.get_base()["Игроки"]?.forEachIndexed() { ind, strinlist ->
                basestr += "  ${ind + 1} $strinlist\n"
        }
        binding.gamers.text = basestr
        basestr = ""
        game.get_base()["Персонажи"]?.forEachIndexed() { ind, strinlist ->
            basestr += "  ${ind + 1} $strinlist\n"
        }
        binding.pers.text = basestr
        basestr = ""
        game.get_base()["Карты"]?.forEachIndexed() { ind, strinlist ->
            basestr += "  ${ind + 1} $strinlist\n"
        }
        binding.maps.text = basestr
        basestr = ""

        binding.btnAddGamers.setOnClickListener(){
            val alert_dialog = AlertDialog.Builder(this)
            val edTx = EditText(this)
            alert_dialog.setMessage("Добавить нового игрока")
            alert_dialog.setView(edTx)

            alert_dialog.setPositiveButton("Добавить"){
                dialog, which ->
                s = edTx.text.toString()
                if (s!=""){
                game.add_gamers(listOf(s))
                val added_gamer = game.get_base()["Игроки"]
                basestr = binding.gamers.text.toString()
                basestr += "  " + added_gamer?.size.toString()+" "
                basestr += added_gamer?.lastOrNull()?.toString() + "\n"
                binding.gamers.text = basestr

            }

            }
            alert_dialog.setNegativeButton("Отмена"){
                    dialog, which ->
                dialog.dismiss()
            }
            alert_dialog.show()


            //s = binding.edText.text.toString()





        }

    }
}

private fun AlertDialog.Builder.setPositiveButton(s: String, function: () -> Unit) {

}
