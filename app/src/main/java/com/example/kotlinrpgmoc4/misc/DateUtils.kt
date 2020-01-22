package com.example.kotlinrpgmoc4.misc

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {

  companion object {
    fun calendarToStringTime(calendar: Calendar): String {
      val dateFormat = SimpleDateFormat("HH:mm", Locale.FRANCE)
      return dateFormat.format(calendar.time)
    }
  }

}