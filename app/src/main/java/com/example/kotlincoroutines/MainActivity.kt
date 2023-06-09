package com.example.kotlincoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val TAG = "MainActivity"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tvDummy = findViewById<TextView>(R.id.tvDummy)

        //Dispatchers.Main -> start coroutine in main thread -> useful for UI operations because you can only change the UI from main thread
        //Dispatchers.IO -> used for all kinds of data operation (networking, read/write to databases, read/write to files)
        //Dispatchers.Default -> used for doing complex and long running calculations that will block the main thread
        //Dispatchers.Unconfined -> it's not confined to a specific thread -> if you start a coroutine and unconfined that
        //causes a suspend function, it will stay in the thread that the suspend function resumed
        // GlobalScope.launch(newSingleThreadContext("MyNewThread")) {} -> making a new own thread

        //do network call and update the ui
        //ui only work on main thread
        //network calls shouldn't be on main thread
        //so we use Dispatchers.IO for network call
        //and switch to main thread by using withContext(Dispatchers.Main){}
        GlobalScope.launch(Dispatchers.IO) {
            Log.d(TAG, "Starting coroutines in thread ${Thread.currentThread().name}")
            val answer = doNetworkCall()
            withContext(Dispatchers.Main) {
                Log.d(TAG, "Setting text in thread ${Thread.currentThread().name}")
                tvDummy.text = answer
            }
        }
    }

    suspend fun doNetworkCall(): String {
        delay(5000L)
        return "This is the answer"
    }
}