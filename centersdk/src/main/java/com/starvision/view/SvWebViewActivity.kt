package com.starvision.view

import android.annotation.SuppressLint
import android.net.http.SslError
import android.os.Bundle
import android.view.View
import android.webkit.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.PageNewsBinding

class SvWebViewActivity : AppCompatActivity() {
    private val binding : PageNewsBinding by lazy { PageNewsBinding.inflate(layoutInflater) }
    private var callback : OnBackPressedCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        val bundle = intent.extras
        val link = bundle!!.getString("link")
        val title = bundle.getString("title")
        binding.tvTitle.text = title
        binding.tvTitle.isSelected = true

        binding.topBar.visibility = View.VISIBLE
        binding.imgBack.setOnClickListener { finish() }
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


        binding.mWebView.settings.javaScriptEnabled = true
        binding.mWebView.webChromeClient = WebChromeClient()
        binding.mWebView.webViewClient = WebViewClient()
        binding.mWebView.loadUrl(link.toString())
        binding.mWebView.webViewClient = CustomWebViewClient()
//        binding.mBtGoHome.setOnClickListener { binding.mWebView.loadUrl(link.toString()) }

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
            super.onPageFinished(view, url)
        }
    }

    override fun onDestroy() {
        callback!!.remove()
        super.onDestroy()
    }
}