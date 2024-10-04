package com.kodiiiofc.urbanuniversity.database

import android.content.Intent
import android.os.Bundle
import android.view.AttachedSurfaceControl.OnBufferTransformHintChangedListener
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startBTN = findViewById<Button>(R.id.btn_start)
        startBTN.setOnClickListener {
            val intent = Intent(this, DatabaseActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}