package com.example.unmatchgamersrandomizer

import kotlin.random.Random

class Unmatched{

    private val gamers_base: MutableList<String> = mutableListOf("Саша", "Руслан", "Вика", "Женя", "Ляйсан", "Артур")
    private val maps_base: MutableList<String> = mutableListOf("I", "II", "V", "VI", "VII")
    private val pers_base: MutableList<String> = mutableListOf(
        "Бигфут", "Робин Гуд",
        "Медуза", "Артур", "Алиса", "Синдбад",
        "Шерлок Холмс", "Хайд/Джекил", "Невидимка", "Дракула")
    fun get_base():Map<String, List<String>>{

        return mapOf("Игроки" to gamers_base,"Персонажи" to pers_base, "Карты" to maps_base)
    }
    fun add_gamers(names: String){
        gamers_base.add(names)
    }
    fun add_pers(names: String){
        pers_base.add(names)
    }

    fun add_maps(names: String){
        maps_base.add(names)
    }

}


