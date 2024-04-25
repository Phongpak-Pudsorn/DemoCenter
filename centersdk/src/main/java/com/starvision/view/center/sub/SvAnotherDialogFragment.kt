package com.starvision.view.center.sub

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.gson.Gson
import com.starvision.api.SvURL
import com.starvision.config.SvLogin
import com.starvision.config.SvMD5
import com.starvision.config.SvParamsData
import com.starvision.data.SvParamUtil
import com.starvision.centersdk.R
import com.starvision.centersdk.databinding.PageAnotherDialogBinding
import com.starvision.view.center.models.SvDeleteAccountModels
import com.starvision.view.login.SvLoginActivity


class SvAnotherDialogFragment : DialogFragment() {
    private val binding : PageAnotherDialogBinding by lazy { PageAnotherDialogBinding.inflate(layoutInflater) }
    private val handler = Handler(Looper.getMainLooper())
    private val TAG = javaClass.simpleName

    private lateinit var mClickListener : ClickListener
    interface ClickListener {
        fun onClickBack()
    }
    fun setClickListener(listener : ClickListener) {
        mClickListener = listener
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
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        dialog!!.window!!.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog!!.window!!.setBackgroundDrawableResource(R.color.transparent)
        bindingObject()
        dialog!!.show()
    }

    private fun bindingObject() {
        binding.tvConText2.visibility = View.GONE
        binding.tvConText.text = getString(R.string.text_ask_delete)
        binding.cvCancel.setOnClickListener { dialog!!.dismiss() }
        binding.cvOk.setOnClickListener {
                val params = SvParamUtil.ParamsUid
                params["acc_name"] = SvLogin.UserName
                params["password"] = SvMD5.CMD5(SvLogin.Password)
                params["type"] = "1"
                SvParamsData(object : SvParamsData.PostLoadListener {
                    override fun onSuccess(body: String) {
                        val jSon = Gson().fromJson(body, SvDeleteAccountModels::class.java)
                        val toast =
                            Toast.makeText(requireContext(), jSon.message, Toast.LENGTH_SHORT)
                        toast.show()
                        handler.postDelayed({ toast.cancel() }, 1000)
                        SvLogin.isLogin = false
                        val intent = Intent(requireContext(), SvLoginActivity::class.java)
                        startActivity(intent)
                        mClickListener.onClickBack()
                    }

                    override fun onFailed(t: Throwable) {
                        val toast = Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT)
                        toast.show()
                        handler.postDelayed({ toast.cancel() }, 1000)
                    }

                }).postLoadData(SvURL.BASE_URL_SDK, SvURL.URL_DELETE_AND_RECOVERY, "", params)
            }
    }
}