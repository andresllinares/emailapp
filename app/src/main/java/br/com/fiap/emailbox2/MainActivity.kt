package br.com.fiap.emailbox2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.fiap.emailbox2.ui.theme.EmailBox2Theme
import br.com.fiap.emailboxapp.screens.EmailInbox

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmailBox2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                EmailInbox()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun EmailInboxPreview() {
    Surface (
        modifier = Modifier.fillMaxSize()
    ) {
        EmailInbox()    }
}