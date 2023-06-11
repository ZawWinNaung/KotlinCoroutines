package com.example.kotlincoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val TAG = "MainActivity"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //This does not actually block the min thread, the UI will be still rendered
//        GlobalScope.launch(Dispatchers.Main) {
//            delay(1000L)
//        }

        //this block the main thread, the UI will be delayed
        Log.d(TAG, "Before run blocking")
        runBlocking {
            launch(Dispatchers.IO) {
                delay(3000L)
                Log.d(TAG, "Finished IO coroutine 1")
            }
            launch(Dispatchers.IO) {
                delay(3000L)
                Log.d(TAG, "Finished IO coroutine 2")
            }
            Log.d(TAG, "Start of run blocking")
            delay(5000L)
            Log.d(TAG, "End of run blocking")
        }
        Log.d(TAG, "After run blocking")

        //same result with above code, difference is we can call suspend functions
        //in runBlocking block
        //Thread.sleep(5000L)
    }
}