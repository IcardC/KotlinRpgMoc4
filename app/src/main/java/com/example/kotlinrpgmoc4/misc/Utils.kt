package com.example.kotlinrpgmoc4.misc

class Utils {
    companion object {
        fun waitingThread() {
            for (i in 0..5) {
                print(". ")
                Thread.sleep(300)
            }
        }

        fun waitingThread(seconds: Int) {
            for (i in 0 until seconds) {
                Thread.sleep(1000)
            }
        }
    }

}