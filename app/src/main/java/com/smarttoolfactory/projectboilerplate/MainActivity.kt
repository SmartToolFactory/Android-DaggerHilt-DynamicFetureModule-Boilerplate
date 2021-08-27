package com.smarttoolfactory.projectboilerplate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.MutableLiveData
import com.smarttoolfactory.core.util.observe
import com.smarttoolfactory.projectboilerplate.ui.theme.SampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔥 Opens MainFragment which uses avController to navigate to dynamic feature module
        setContentView(R.layout.activity_main)

//        setContent {
//            SampleTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(color = MaterialTheme.colors.background) {
//                    Greeting("Android")
//                }
//            }
//        }

        val data = MutableLiveData<String>()
        observe(data) {
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SampleTheme {
        Greeting("Android")
    }
}
