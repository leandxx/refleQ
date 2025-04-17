package com.example.refleq

import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.refleq.ui.theme.RefleQTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RefleQTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WebViewScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun WebViewScreen(modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView?, request: android.webkit.WebResourceRequest?): Boolean {
                        val url = request?.url.toString()
                        return !url.startsWith("http://refleqtions.ct.ws")
                    }
                }

                webChromeClient = WebChromeClient() // For JS dialogs, alerts

                val webSettings = settings

                @Suppress("SetJavaScriptEnabled") // Suppress lint warning
                webSettings.javaScriptEnabled = true

                webSettings.domStorageEnabled = true
                webSettings.javaScriptCanOpenWindowsAutomatically = true
                webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

                // ✅ Accept cookies (needed for chatbot, sessions, etc.)
                CookieManager.getInstance().setAcceptCookie(true)
                CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)

                // Load your trusted URL
                loadUrl("http://refleqtions.ct.ws/Refleqtions/welcome.php")
            }
        }
    )
}
