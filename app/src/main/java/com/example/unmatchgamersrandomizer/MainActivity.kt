package com.example.unmatchgamersrandomizer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.unmatchgamersrandomizer.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
   //Для получения данных из BanActivity
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    var _binding: ActivityMainBinding?= null
    val binding
        get() = _binding ?: throw IllegalStateException("Binding for ActivityMainBinding null")
    val game = Unmatched()
    var basestr = ""
    var s = ""
    val ban_mask: MutableMap<String, List<String>> = mutableMapOf()


    //Добавление игрока
    private fun add_data(who_add:String){
        val alert_dialog = AlertDialog.Builder(this)
        val edTx = EditText(this)
        alert_dialog.setMessage(who_add)
        alert_dialog.setView(edTx)

        alert_dialog.setPositiveButton("Добавить"){
                dialog, which ->
            s = edTx.text.toString()
            if (s!="" && who_add == "Добавить персонажа"){
                game.add_pers(s)
                val added_pers = game.get_base()["Персонажи"]
                basestr = binding.pers.text.toString()
                basestr += "  " + added_pers?.size.toString()+" "
                basestr += added_pers?.lastOrNull()?.toString() + "\n"
                binding.pers.text = basestr
            }
            else if (s!="" && who_add == "Добавить карту"){
                game.add_maps(s)
                val added_map = game.get_base()["Карты"]
                basestr = binding.maps.text.toString()
                basestr += "  " + added_map?.size.toString()+" "
                basestr += added_map?.lastOrNull()?.toString() + "\n"
                binding.maps.text = basestr
            }
            else if (s!="" && who_add == "Добавить игрока"){
                game.add_gamers(s)
                val added_gamer = game.get_base()["Игроки"]
                basestr = binding.gamers.text.toString()
                basestr += "  " + added_gamer?.size.toString()+" "
                basestr += added_gamer?.lastOrNull()?.toString() + "\n"
                binding.gamers.text = basestr
            }
        }
        alert_dialog.setNegativeButton("Отмена"){
                dialog, _ ->
            dialog.dismiss()
        }
        alert_dialog.show()
    }
    //Функция обновления текста на главном экране
    private fun update_list(){
        var base_update = ""
        game.get_base()["Игроки"]?.forEachIndexed() { ind, strinlist ->
            base_update += "  ${ind + 1} $strinlist\n"
        }
        binding.gamers.text = base_update
        base_update = ""
        game.get_base()["Персонажи"]?.forEachIndexed() { ind, strinlist ->
            base_update += "  ${ind + 1} $strinlist\n"
        }
        binding.pers.text = base_update
        base_update = ""
        game.get_base()["Карты"]?.forEachIndexed() { ind, strinlist ->
            base_update += "  ${ind + 1} $strinlist\n"
        }
        binding.maps.text = base_update
    }


    fun startGenerate(accepted_map:Map<String, List<String>>):String {
        var generateData = ""
        val gamers = game.get_base()["Игроки"]?.toMutableList()
        val pers = game.get_base()["Персонажи"]?.toMutableList()
        val maps = game.get_base()["Карты"]?.toMutableList()

        if (gamers != null && pers != null && maps != null) {
            accepted_map["Игроки"]?.forEach() { value ->
                gamers.remove(value)
            }
            accepted_map["Персонажи"]?.forEach() { value ->
                pers.remove(value)
            }
            accepted_map["Карты"]?.forEach() { value ->
                maps.remove(value)
            }
            if (gamers.size<=pers.size) {
                while (gamers.isNotEmpty() == true) {
                    val gamer_rand = Random.nextInt(gamers.size)
                    val pers_rand = Random.nextInt(pers.size)
                    generateData += gamers[gamer_rand] + " играет за "
                    generateData += pers[pers_rand] + "\n"
                    gamers.removeAt(gamer_rand)
                    pers.removeAt(pers_rand)
                }
            } else{return "Игроков меньше чем персонажей!!!"}
        }

        if (gamers != null && maps!=null && pers != null && maps.size>0){
        return generateData + "\nКарта: "+ (maps.get(Random.nextInt(maps.size)))
        }
        return generateData + "\nКарта: НЕ НАЙДЕНА"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        update_list()
        binding.btnAddGamers.setOnClickListener(){
            add_data("Добавить игрока")
        }
        binding.btnAddPers.setOnClickListener(){
          add_data("Добавить персонажа")
        }
        binding.btnAddMaps.setOnClickListener(){
            add_data("Добавить карту")
        }


        //Возврат из BanActicity Данных
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val resultListGamersFromBan = data?.getStringArrayListExtra("Игроки")
                val resultListPersFromBan = data?.getStringArrayListExtra("Персонажи")
                val resultListMapsFromBan = data?.getStringArrayListExtra("Карты")
                if (resultListGamersFromBan != null)
                    ban_mask["Игроки"] = resultListGamersFromBan
                if (resultListPersFromBan != null)
                    ban_mask["Персонажи"] = resultListPersFromBan
                if (resultListMapsFromBan != null)
                    ban_mask["Карты"] = resultListMapsFromBan

            }
        }

        //Слушатель кнопки BAN
        binding.btnBan.setOnClickListener(){
            //Запускаем активность BanActivity
            val intent = Intent(this, BanActivity::class.java)
            val base_for_transfer = game.get_base()
            val bundle = Bundle()

            base_for_transfer.forEach { key, value ->
                bundle.putStringArrayList(key,  value as ArrayList<String>)
            }
            intent.putExtra("key_data", bundle) // Передача данных
            activityResultLauncher.launch(intent)

        }

        //Слушатель кнопки GENERATE
        binding.btnGenerate.setOnClickListener() {

            val test = startGenerate(ban_mask)
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Unmatch!!!")
                .setMessage(test)
                .setPositiveButton("ОК") { dialog, _ ->
                    dialog.dismiss()
                }

            val dialog = builder.create()

            // Установить, чтобы диалог не закрывался при нажатии вне его
            dialog.setCanceledOnTouchOutside(false)

            dialog.show()
        }




    }





}


