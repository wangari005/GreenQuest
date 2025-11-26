package com.greenquest.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import dagger.hilt.android.AndroidEntryPoint
import com.greenquest.app.ui.theme.GreenQuestTheme
import com.greenquest.app.navigation.AppNavHost

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GreenQuestTheme {
                Surface {
                    AppNavHost()
                }
            }
        }
    }
}
