package com.example.kotlincoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val TAG = "MainActivity"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val job = GlobalScope.launch(Dispatchers.Default) {
            //repeat the code
//            repeat(5) {
//                Log.d(TAG, "Coroutine is still working...")
//                delay(1000L)
//            }

            Log.d(TAG, "Starting long running calculation...")

            //automatic cancellation, no need to use job.cancel if the calculation function
            //is in the withTimeOut block.It will be automatically cancelled after 1 seconds.
            withTimeout(1000L) {
                for (i in 30..40) {
                    //to check the job is canceled or not
                    if (isActive) {
                        Log.d(TAG, "Result for i = $i : ${fib(i)}")
                    }
                }
            }
            Log.d(TAG, "Ending long running calculation...")
        }

        //block the thread until the above coroutine is finished
//        runBlocking {
//            //wait for it to finish by using join
//            //job.join()
//            delay(1000L)
//            job.cancel()
//            Log.d(TAG, "Canceled job!")
//        }
    }

    fun fib(n: Int): Long {
        return if (n == 0) 0
        else if (n == 1) 1
        else fib(n - 1) + fib(n - 2)
    }
}