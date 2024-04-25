package com.starvision.view

import android.annotation.SuppressLint
import android.net.http.SslError
import android.os.Bundle
import android.view.View
import android.webkit.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.starvision.data.SvConst
import com.starvision.centersdk.R
import com.starvision.centersdk.databinding.PageWebviewBinding


class SvWebViewLuckyActivity : AppCompatActivity() {
    private val binding : PageWebviewBinding by lazy { PageWebviewBinding.inflate(layoutInflater) }
    private val TAG = javaClass.simpleName
    private var callback : OnBackPressedCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        hideStatusBar()
        val bundle = intent.extras
        val link = bundle!!.getString("link")

        binding.btnBack.setOnClickListener { finish() }
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.mWebView.canGoBack()) {
                    binding.mWebView.goBack()
                }else{
                    finish()
                }
            }
        }
        this.onBackPressedDispatcher.addCallback(this,callback!!)

        binding.mBtGoForword.setOnClickListener {
            if (binding.mWebView.canGoForward())
                binding.mWebView.goForward()
        }
        binding.mBtGoBack.setOnClickListener {
            if (binding.mWebView.canGoBack())
                binding.mWebView.goBack()
        }

        binding.mWebView.settings.javaScriptEnabled = true
        binding.mWebView.webChromeClient = WebChromeClient()
        binding.mWebView.webViewClient = WebViewClient()
        binding.mWebView.loadUrl(link.toString())
        binding.mWebView.webViewClient = CustomWebViewClient()
        binding.mBtGoHome.setOnClickListener { binding.mWebView.loadUrl(link.toString()) }

    }
    private fun hideStatusBar() {
        WindowCompat.getInsetsController(window,window.decorView).apply {
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            hide(WindowInsetsCompat.Type.statusBars())
        }
    }
    inner class CustomWebViewClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            SvConst.loge(TAG,"url : "+ request!!.url)
            return false
        }

        @SuppressLint("WebViewClientOnReceivedSslError")
        override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
            binding.mWebView.loadData(getString(R.string.text_nocon)+" "+getSslErrorMessage(error), "text/html", "UTF-8")
        }

        private fun getSslErrorMessage(error: SslError): String = when (error.primaryError) {
            SslError.SSL_DATE_INVALID -> "The certificate date is invalid."
            SslError.SSL_EXPIRED -> "The certificate has expired."
            SslError.SSL_IDMISMATCH -> "The certificate hostname mismatch."
            SslError.SSL_INVALID -> "The certificate is invalid."
            SslError.SSL_NOTYETVALID -> "The certificate is not yet valid"
            SslError.SSL_UNTRUSTED -> "The certificate is untrusted."
            else -> "SSL Certificate error."
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            binding.mPbLoad.visibility = View.GONE
            if (binding.mWebView.canGoForward()) {
                binding.mBtGoForword.visibility = View.VISIBLE
            } else {
                binding.mBtGoForword.visibility = View.INVISIBLE
            }
            if (binding.mWebView.canGoBack()) {
                binding.mBtGoBack.visibility = View.VISIBLE
            } else {
                binding.mBtGoBack.visibility = View.INVISIBLE
            }
            super.onPageFinished(view, url)
        }
    }

    override fun onDestroy() {
        callback!!.remove()
        super.onDestroy()
    }
}