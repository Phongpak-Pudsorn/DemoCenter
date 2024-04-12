package com.starvision.view.login

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.starvision.api.SvURL
import com.starvision.config.SvMD5
import com.starvision.config.SvParamsData
import com.starvision.data.SvConst
import com.starvision.data.SvParamUtil
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.PageForgotBinding
import com.starvision.view.login.models.SvForgotModels

class SvForgotFragment(private val bm: Bitmap) : Fragment() {
    private val binding : PageForgotBinding by lazy { PageForgotBinding.inflate(layoutInflater) }
    private val handler = Handler(Looper.getMainLooper())
    private val TAG = javaClass.simpleName

    private lateinit var mCloseListener : CloseListener
    interface CloseListener {
        fun onClose()
    }
    fun setCloseListener(listener : CloseListener) {
        mCloseListener = listener
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
        binding.imgLogo.setImageBitmap(bm)
        binding.cvSent.setOnClickListener {
            binding.cvSent.isEnabled = false
            handler.postDelayed({ binding.cvSent.isEnabled = true },1000)
            if(binding.editEmail.length() < 6){
                Toast.makeText(requireContext(),getString(R.string.text_alert_email_min), Toast.LENGTH_SHORT).show()
            }else if(binding.editUsername.length() < 6){
                Toast.makeText(requireContext(),getString(R.string.text_alert_user_min), Toast.LENGTH_SHORT).show()
            }else{
                binding.progressBar3.visibility = View.VISIBLE
                val acc_name = binding.editUsername.text.toString()
                val email = binding.editEmail.text.toString()
                val sign = SvMD5.CMD5("Starvision|${acc_name}|ForgetPass|${email}")
                val params = SvParamUtil.ParamsUid
                params["acc_name"] = acc_name
                params["email"] = email
                params["sign"] = sign

                SvConst.loge(TAG,"params : $params")
                SvConst.loge(TAG,"acc_name : $acc_name")
                SvConst.loge(TAG,"email : $email")

                SvParamsData(object : SvParamsData.PostLoadListener{
                    override fun onSuccess(body: String) {
                        val json = Gson().fromJson(body,SvForgotModels::class.java)
                        if(json.code == "1101"){
                            val toast = Toast.makeText(requireContext(),getString(R.string.text_alert_recovery), Toast.LENGTH_SHORT)
                            toast.show()
                            handler.postDelayed({toast.cancel()},1000)
                        }else{
                            val toast = Toast.makeText(requireContext(),json.message, Toast.LENGTH_SHORT)
                            toast.show()
                            handler.postDelayed({toast.cancel()},1000)
                        }
                        binding.progressBar3.visibility = View.GONE
                        SvConst.loge(TAG,"body : $body")
                    }

                    override fun onFailed(t: Throwable) {
                        t.printStackTrace()
                        binding.progressBar3.visibility = View.GONE
                    }

                }).postLoadData(SvURL.BASE_URL_SDK,SvURL.URL_FORGOTPASS,"",params)
            }
        }
        binding.btnBack.setOnClickListener {
            mCloseListener.onClose()
        }

    }
}