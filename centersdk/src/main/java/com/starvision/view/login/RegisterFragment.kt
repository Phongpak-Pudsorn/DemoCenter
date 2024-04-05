package com.starvision.view.login

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
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
import com.starvision.api.URL
import com.starvision.config.Login
import com.starvision.config.MD5
import com.starvision.config.ParamsData
import com.starvision.data.Const
import com.starvision.data.ParamUtil
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.PageRegisterBinding
import com.starvision.view.login.models.LoginModels
import java.util.*

class RegisterFragment(private val bm: Bitmap) : Fragment() {
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
                val password = MD5.CMD5(binding.editPassword.text.toString())
                val confirmpassword = MD5.CMD5(binding.editConfirmPassword.text.toString())
                val sign = MD5.CMD5("Starvision|$acc_name|Register|$password")
                val account_type = "s1"
                val ChannelId = "StarVision"
                val phonenumber = ""
                val hashMap = ParamUtil.ParamsUid

                hashMap["acc_name"] = acc_name
                hashMap["password"] = password
                hashMap["confirmpassword"] = confirmpassword
                hashMap["sign"] = sign
                hashMap["account_type"] = account_type
                hashMap["ChannelId"] = ChannelId
                hashMap["phonenumber"] = phonenumber

                Const.loge(TAG,"params : $hashMap")

                ParamsData(object : ParamsData.PostLoadListener{
                    override fun onSuccess(body: String) {
                        try {
                            val jSon = Gson().fromJson(body, LoginModels::class.java)
                            if(jSon.message == "success"){
                                Login.isRememberCheck = true
                                Login.UserName = binding.editUsername.text.toString()
                                Login.Password = binding.editPassword.text.toString()
                                Login.UserID = jSon.userid
                                Login.IDX = jSon.idx!!
                                Login.isLogin = true
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
                        Const.loge(TAG,"t : $t")
                    }
                }).postLoadData(URL.BASE_URL_SDK,URL.URL_REGISTER,"",hashMap)
            }
        }

        binding.tvPolicyRegister.setOnClickListener {
            binding.tvPolicyRegister.isEnabled = false
            handler.postDelayed({ binding.tvPolicyRegister.isEnabled = true },1000)
            val webview = WebViewPolicyDialogFragment()
            webview.setClickClose(object : WebViewPolicyDialogFragment.ClickClose{
                override fun onClickClose() {
                    binding.checkboxAcceptPolicy.isChecked = true
                }
            })
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

        binding.seePasswordCon.setOnClickListener {
            if(binding.seePasswordCon.drawable.constantState!! == requireActivity().getDrawable(R.drawable.baseline_eye_visibility_off_24)!!.constantState){
                binding.seePasswordCon.setImageDrawable(requireActivity().getDrawable(R.drawable.baseline_eye_24_grey))
                binding.editConfirmPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }else{
                binding.seePasswordCon.setImageDrawable(requireActivity().getDrawable(R.drawable.baseline_eye_visibility_off_24))
                binding.editConfirmPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        binding.btnBack.setOnClickListener {
            mClickListener.onClose()
        }
    }

}