package com.starvision.view.login

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.starvision.api.SvURL
import com.starvision.config.SvLogin
import com.starvision.config.SvMD5
import com.starvision.config.SvParamsData
import com.starvision.data.SvConst
import com.starvision.data.SvParamUtil
import com.starvision.centersdk.R
import com.starvision.centersdk.databinding.PageRegisterBinding
import com.starvision.view.login.models.SvLoginModels
import java.util.*

class SvRegisterFragment(private val bm: Bitmap) : Fragment() {
    private val binding : PageRegisterBinding by lazy { PageRegisterBinding.inflate(layoutInflater) }
    private val handler = Handler(Looper.getMainLooper())
    private val TAG = javaClass.simpleName

    private lateinit var mClickListener : ClickListener
    interface ClickListener {
        fun onClose()
        fun onSuccess()
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

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgLogo.setImageBitmap(bm)
        binding.cvRegister.setOnClickListener {
            binding.cvRegister.isEnabled = false
            handler.postDelayed({ binding.cvRegister.isEnabled = true },1000)
            if(binding.editUsername.length() < 6){
                val toast = Toast.makeText(requireContext(),getString(R.string.text_alert_user_min), Toast.LENGTH_SHORT)
                toast.show()
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({ toast.cancel() }, 1000)
            }else if(binding.editPassword.length() < 6){
                val toast = Toast.makeText(requireContext(),getString(R.string.text_alert_password_min), Toast.LENGTH_SHORT)
                toast.show()
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({ toast.cancel() }, 1000)
            }else if(binding.editConfirmPassword.length() < 6){
                val toast = Toast.makeText(requireContext(),getString(R.string.text_alert_password_not_same), Toast.LENGTH_SHORT)
                toast.show()
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({ toast.cancel() }, 1000)
            }else if(binding.editEmail.length() < 8){
                val toast = Toast.makeText(requireContext(),getString(R.string.text_alert_email_min), Toast.LENGTH_SHORT)
                toast.show()
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({ toast.cancel() }, 1000)
            }else if(!binding.checkboxAcceptPolicy.isChecked){
                val toast = Toast.makeText(requireContext(),getString(R.string.text_alert_policy_uncheck), Toast.LENGTH_SHORT)
                toast.show()
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({ toast.cancel() }, 1000)
            }else{
                val acc_name = binding.editUsername.text.toString().lowercase(Locale.getDefault())
                val password = SvMD5.CMD5(binding.editPassword.text.toString())
                val confirmpassword = SvMD5.CMD5(binding.editConfirmPassword.text.toString())
                val sign = SvMD5.CMD5("Starvision|$acc_name|Register|$password")
                val account_type = "s1"
                val ChannelId = "StarVision"
                val phonenumber = ""
                val hashMap = SvParamUtil.ParamsUid

                hashMap["acc_name"] = acc_name
                hashMap["password"] = password
                hashMap["confirmpassword"] = confirmpassword
                hashMap["sign"] = sign
                hashMap["account_type"] = account_type
                hashMap["ChannelId"] = ChannelId
                hashMap["phonenumber"] = phonenumber

                SvConst.loge(TAG,"params : $hashMap")

                SvParamsData(object : SvParamsData.PostLoadListener{
                    override fun onSuccess(body: String) {
                        try {
                            val jSon = Gson().fromJson(body, SvLoginModels::class.java)
                            if(jSon.message == "success"){
                                SvLogin.isRememberCheck = true
                                SvLogin.UserName = binding.editUsername.text.toString()
                                SvLogin.Password = binding.editPassword.text.toString()
                                SvLogin.UserID = jSon.userid
                                SvLogin.IDX = jSon.idx!!
                                SvLogin.isLogin = true
                                Toast.makeText(requireContext(),jSon.message,Toast.LENGTH_SHORT).show()
                                mClickListener.onSuccess()
                            }else{
                                Toast.makeText(requireContext(),jSon.message,Toast.LENGTH_SHORT).show()
                            }
                        }catch (e : Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onFailed(t: Throwable) {
                        SvConst.loge(TAG,"t : $t")
                    }
                }).postLoadData(SvURL.BASE_URL_SDK,SvURL.URL_REGISTER,"",hashMap)
            }
        }

        binding.tvPolicyRegister.setOnClickListener {
            binding.tvPolicyRegister.isEnabled = false
            handler.postDelayed({ binding.tvPolicyRegister.isEnabled = true },1000)
            val webview = SvWebViewPolicyDialogFragment()
            webview.setClickClose(object : SvWebViewPolicyDialogFragment.ClickClose{
                override fun onClickClose() {
                    binding.checkboxAcceptPolicy.isChecked = true
                }

                override fun onNotAccept() {
                }
            },1)
            webview.show(childFragmentManager,"policy")
        }

        binding.seePassword.setOnClickListener {
            if(binding.seePassword.drawable.constantState!! == requireActivity().getDrawable(R.drawable.baseline_eye_visibility_off_24)!!.constantState){
                binding.seePassword.setImageDrawable(requireActivity().getDrawable(R.drawable.baseline_eye_24_grey))
                binding.editPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }else{
                binding.seePassword.setImageDrawable(requireActivity().getDrawable(R.drawable.baseline_eye_visibility_off_24))
                binding.editPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        binding.seeConfirmPassword.setOnClickListener {
            if(binding.seeConfirmPassword.drawable.constantState!! == requireActivity().getDrawable(R.drawable.baseline_eye_visibility_off_24)!!.constantState){
                binding.seeConfirmPassword.setImageDrawable(requireActivity().getDrawable(R.drawable.baseline_eye_24_grey))
                binding.editConfirmPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }else{
                binding.seeConfirmPassword.setImageDrawable(requireActivity().getDrawable(R.drawable.baseline_eye_visibility_off_24))
                binding.editConfirmPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        binding.btnBack.setOnClickListener {
            mClickListener.onClose()
        }
    }

}