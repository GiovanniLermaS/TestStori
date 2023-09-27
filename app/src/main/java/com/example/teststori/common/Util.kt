package com.example.teststori.common

import com.google.firebase.Timestamp
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun convertIntToColombianMoney(int: Int): String {
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale("es", "CO"))
    return currencyFormat.format(int)
}

fun timestampToDate(timestamp: Timestamp?): Date {
    return Date((timestamp?.seconds ?: 1) * 1000)
}

fun formatDate(date: Date): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    return dateFormat.format(date)
}