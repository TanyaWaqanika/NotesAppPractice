package com.plcoding.notespracticeapp.appStructure.domain.util

sealed class OrderType {
    data object Ascending: OrderType()
    data object Descending: OrderType()
}