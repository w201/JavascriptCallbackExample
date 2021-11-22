package com.codium.javascripttest

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.webkit.JavascriptInterface
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.codium.javascripttest.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private inner class JavaScriptInterface {
        @JavascriptInterface
        fun close() {
            Toast.makeText(this@MainActivity, "Callback from HTML to close the App", Toast.LENGTH_LONG).show()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.btnOut.setOnClickListener {
            Toast.makeText(this, "Click outside webview", Toast.LENGTH_LONG).show()
        }

        binding.wv.run {
            settings.javaScriptEnabled = true
            addJavascriptInterface(JavaScriptInterface(), "Android")
            val data = context.assets.open("index.html").bufferedReader().readText()
            loadData(data,"text/html", "utf-8")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}