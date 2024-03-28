package com.starvision.view.center.sub

import android.app.AlertDialog
import android.content.DialogInterface
import android.net.http.SslError
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.SslErrorHandler
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.starvision.api.URL
import com.starvision.data.Const
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.PageZodiacSubBinding

class SubZodiacFragment:Fragment() {
    var loading = true
    val binding:PageZodiacSubBinding by lazy { PageZodiacSubBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startWebView(URL.zodiac)
        binding.cvMore.setOnClickListener {
            Const.openAnotherApp(requireContext(),getString(R.string.zodiac_package))
        }
    }
    private fun startWebView(url: String) {

        //Create new webview Client to show progress dialog
        //When opening a url or click on link
        binding.zodiacWeb.webViewClient = object : WebViewClient() {
//            var progressDialog = LoadingDialog.progressDialog(requireActivity())

            //If you will not use this method url links are opeen in new brower not in webview
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            //Show loader on url load
            override fun onLoadResource(view: WebView, url: String) {
                // in standard case YourActivity.this
                //                    progressDialog = ProgressDialog(requireActivity())
                //                    progressDialog!!.setMessage("Loading...")
                if (loading) {
//                    progressDialog.show()
                    binding.mProgressBar.visibility = View.VISIBLE
                }

            }

            override fun onPageFinished(view: WebView, url: String) {
                try {
                    //                if (progressDialog.isShowing()) {
                    //                    progressDialog.dismiss();
                    //                    progressDialog = null;
                    //                }
//                    progressDialog.dismiss()
//                    loading = false
                    binding.mProgressBar.visibility = View.GONE
                    loading = false
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }
            }

            override fun onReceivedSslError(
                view: WebView,
                handler: SslErrorHandler,
                error: SslError,
            ) {
//                Log.e("TAG", "onReceivedSslError")
                val builder = AlertDialog.Builder(requireActivity())
                val alertDialog = builder.create()
                var message = "Certificate error."
                when (error.primaryError) {
                    SslError.SSL_UNTRUSTED -> message = "The certificate authority is not trusted."
                    SslError.SSL_EXPIRED -> message = "The certificate has expired."
                    SslError.SSL_IDMISMATCH -> message = "The certificate Hostname mismatch."
                    SslError.SSL_NOTYETVALID -> message = "The certificate is not yet valid."
                }
                message += " Do you want to continue anyway?"
                alertDialog.setTitle("SSL Certificate Error")
                alertDialog.setMessage(message)
                alertDialog.setButton(
                    DialogInterface.BUTTON_POSITIVE, "OK"
                ) { dialog, which ->
//                    Log.d("CHECK", "Button ok pressed")
                    // Ignore SSL certificate errors
                    handler.proceed()
                }
                alertDialog.setButton(
                    DialogInterface.BUTTON_NEGATIVE, "Cancel"
                ) { dialog, which ->
//                    Log.d("CHECK", "Button cancel pressed")
                    handler.cancel()
                }
                alertDialog.show()
            }
        }

        // Javascript inabled on webview
        binding.zodiacWeb.settings.javaScriptEnabled = true

        // Other webview options
        // webView.getSettings().setLoadWithOverviewMode(true);
        // webView.getSettings().setUseWideViewPort(true);
        // webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        binding.zodiacWeb.isScrollbarFadingEnabled = false
        binding.zodiacWeb.settings.builtInZoomControls = true
        binding.zodiacWeb.settings.displayZoomControls = false
        binding.zodiacWeb.settings.allowFileAccess = true
        binding.zodiacWeb.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        //Load url in webview
        binding.zodiacWeb.loadUrl(url)
    }
}