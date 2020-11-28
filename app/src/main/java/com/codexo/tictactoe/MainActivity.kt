package com.codexo.tictactoe

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}
