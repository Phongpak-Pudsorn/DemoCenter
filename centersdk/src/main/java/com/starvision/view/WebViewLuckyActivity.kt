package com.starvision.view

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.net.http.SslError
import android.os.Bundle
import android.view.View
import android.webkit.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.PageWebviewBinding

class WebViewLuckyActivity : AppCompatActivity() {
    private val binding : PageWebviewBinding by lazy { PageWebviewBinding.inflate(layoutInflater) }
    private var callback : OnBackPressedCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
//        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
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

    inner class CustomWebViewClient : WebViewClient() {

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