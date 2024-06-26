package com.starvision.view.login

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.net.http.SslError
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnKeyListener
import android.view.ViewGroup
import android.webkit.*
import android.widget.CompoundButton
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.starvision.config.SvLogin
import com.starvision.centersdk.R
import com.starvision.centersdk.databinding.PageWebviewPolicyBinding

class SvWebViewPolicyDialogFragment : DialogFragment() {
    private val binding : PageWebviewPolicyBinding by lazy { PageWebviewPolicyBinding.inflate(layoutInflater) }
    private var className = 0

    private lateinit var mClickClose : ClickClose
    interface ClickClose{
        fun onClickClose()
        fun onNotAccept()
    }
    fun setClickClose(listener : ClickClose,Class : Int){
        mClickClose = listener
        className = Class
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog!!.window!!.setBackgroundDrawableResource(R.color.transparent)
        binding.cvAllow.isClickable = true
        if (className == 1){
            Log.e(javaClass.simpleName,"class 1")
            binding.lnAcceptPolicy.visibility = View.VISIBLE

            binding.cvAllow.setOnClickListener {
                binding.cvAllow.isClickable = false
                SvLogin.isFirstTime = true
                mClickClose.onClickClose()
                dialog!!.dismiss()
            }
        }else if (className == 2 ){
            dialog!!.setOnKeyListener { dialogInterface, i, keyEvent ->
                if (i==KeyEvent.KEYCODE_BACK){
                    mClickClose.onNotAccept()
                    true
                }else {
                    false
                }
            }
            Log.e(javaClass.simpleName,"class 2")
            binding.lnAcceptPolicy.visibility = View.VISIBLE
            binding.checkboxAcceptPolicy.visibility = View.GONE
            binding.cvRegister.visibility = View.GONE
            binding.imgBack.visibility = View.GONE
            binding.cvAllow.visibility = View.VISIBLE

            binding.cvAllow.setOnClickListener {
                binding.cvAllow.isClickable = false
                SvLogin.isFirstTime = true
                val guide = SvGuideDialogFragment()
                guide.setClickNext(object : SvGuideDialogFragment.ClickNext{
                    override fun onClickNext() {
                        dialog!!.dismiss()
                    }

                    override fun notAccept() {
                        mClickClose.onNotAccept()
                    }
                })
                guide.show(childFragmentManager,"guide")
            }
        }

        binding.webview.settings.javaScriptEnabled = true
        binding.webview.webChromeClient = WebChromeClient()
        binding.webview.webViewClient = WebViewClient()
        binding.webview.loadUrl("https://www.starvision.in.th/term/privacy.html")
        binding.webview.webViewClient = CustomWebViewClient()
        binding.imgBack.setOnClickListener {
            dialog!!.dismiss()
        }
        binding.editAccept.setOnClickListener {
            mClickClose.onClickClose()
            dialog!!.dismiss()
        }
        binding.editAccept.isEnabled = false
        binding.editAccept.setBackgroundColor(requireContext().getColor(R.color.grey_text))
        binding.checkboxAcceptPolicy.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                binding.editAccept.isEnabled = false
                binding.editAccept.setBackgroundColor(requireContext().getColor(R.color.grey_text))
            } else {
                binding.editAccept.isEnabled = true
                binding.editAccept.setBackgroundResource(R.drawable.btn_login)
            }
        }
        dialog!!.show()
    }
    inner class CustomWebViewClient : WebViewClient() {

        @SuppressLint("WebViewClientOnReceivedSslError")
        override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
            binding.webview.loadData(requireContext().getString(R.string.text_nocon)+" "+getSslErrorMessage(error), "text/html", "UTF-8")
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