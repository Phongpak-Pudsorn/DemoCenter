package com.starvision.view.luckygamesdk.view

import android.annotation.SuppressLint
import android.net.http.SslError
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.FrameLayout
import androidx.fragment.app.DialogFragment
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.PageWebviewBinding

class WebViewPage(private val link : String) : DialogFragment() {
    private val binding : PageWebviewBinding by lazy { PageWebviewBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val displayMetrics = requireActivity().resources.displayMetrics
        val popupWidth = displayMetrics.widthPixels
        val popupHeight = displayMetrics.heightPixels
        val param = FrameLayout.LayoutParams(popupWidth, popupHeight)
//        param.gravity = Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL
//        val width = ViewGroup.LayoutParams.MATCH_PARENT
//        val height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog!!.window!!.setLayout(popupWidth, popupHeight)

        binding.mWebView.settings.javaScriptEnabled = true
        binding.mWebView.webChromeClient = WebChromeClient()
        binding.mWebView.webViewClient = WebViewClient()
        binding.mWebView.loadUrl(link)
        binding.mWebView.webViewClient = CustomWebViewClient()
        binding.mBtClosePopUp.setOnClickListener {
            dismiss()
        }
        dialog!!.show()
    }

    inner class CustomWebViewClient : WebViewClient() {

        @SuppressLint("WebViewClientOnReceivedSslError")
        override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
            binding.mWebView.loadData(requireContext().getString(R.string.text_nocon)+" "+getSslErrorMessage(error), "text/html", "UTF-8")
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
}