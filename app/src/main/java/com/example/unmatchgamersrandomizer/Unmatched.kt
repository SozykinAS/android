package com.example.unmatchgamersrandomizer

import kotlin.random.Random
class Unmatched{

    private val gamers_base: MutableList<String> = mutableListOf("Саша", "Руслан", "Вика", "Женя")
    private val maps: MutableList<String> = mutableListOf("I", "II", "V", "VI")
    private val pers_base: MutableList<String> = mutableListOf(
        "Бигфут", "Робин Гуд",
        "Медуза", "Артур", "Алиса", "Синдбад",
        "Шерлок Холмс", "Хайд/Джекил", "Невидимка", "Дракула")
    fun get_base():Map<String, List<String>>{

        return mapOf("Игроки" to gamers_base,"Персонажи" to pers_base, "Карты" to maps)
    }
    fun add_gamers(names: List<String>){
        gamers_base.addAll(names)
    }
    fun add_maps(names: List<String>){
        maps.addAll(names)
    }
    fun add_pers(names: List<String>){
        pers_base.addAll(names)
    }
    fun startGame() {
        val gamers = ArrayList(gamers_base)
        val pers = ArrayList(pers_base)
        while (gamers.isNotEmpty()) {
            var inkr = Random.nextInt(0, gamers.size)
            print(gamers[inkr] + " ".repeat(8 - gamers[inkr].length))
            gamers.removeAt(inkr)
            inkr = Random.nextInt(0, pers.size)
            println("-  ${pers[Random.nextInt(0, pers.size)]}")
            pers.removeAt(inkr)
        }
        println("Играем на поле " + maps[Random.nextInt(0, maps.size)])
    }
}

