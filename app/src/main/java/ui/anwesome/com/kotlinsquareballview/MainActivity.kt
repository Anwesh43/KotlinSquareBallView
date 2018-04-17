package ui.anwesome.com.kotlinsquareballview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.squareballview.SquareBallView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SquareBallView.create(this)
    }
}
