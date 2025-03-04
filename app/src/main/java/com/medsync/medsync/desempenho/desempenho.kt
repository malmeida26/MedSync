package com.medsync.medsync.desempenho

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.medsync.medsync.ui.theme.ui.theme.MedSyncTheme

class desempenho : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MedSyncTheme {

            }
        }
    }
}



