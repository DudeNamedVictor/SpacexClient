package com.example.spacexclient_spirin.utils


class DataParser(private val months: Array<String>) {
    fun parseData(data: String) = getDay(data) + " " + getMonth(data) + ", " + getYear(data)

    private fun getDay(data: String) = data.removeRange(0, 8).toInt().toString()

    private fun getMonth(data: String): String {
        var month = data.removeRange(0, 5)
        month = month.removeRange(2, month.length)

        return months[month.toInt() - 1]
    }

    private fun getYear(data: String) = data.removeRange(4, data.length)

}