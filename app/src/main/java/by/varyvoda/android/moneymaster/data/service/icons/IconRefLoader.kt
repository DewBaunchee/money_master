package by.varyvoda.android.moneymaster.data.service.icons

import by.varyvoda.android.moneymaster.data.model.icon.IconRef

interface IconRefLoader {

    fun load(name: String): IconRef?
}