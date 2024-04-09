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
import com.starvision.api.URL
import com.starvision.config.Login
import com.starvision.config.MD5
import com.starvision.config.ParamsData
import com.starvision.data.Const
import com.starvision.data.ParamUtil
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.PageRecoveryBinding
import com.starvision.view.center.models.DeleteAccountModels

class RecoveryFragment(private val bm: Bitmap) : Fragment() {
    private val binding : PageRecoveryBinding by lazy { PageRecoveryBinding.inflate(layoutInflater) }
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
            }else if(binding.editPassword.length() < 6){
                Toast.makeText(requireContext(),getString(R.string.text_alert_password_min), Toast.LENGTH_SHORT).show()
            }else{
                binding.progressBar3.visibility = View.VISIBLE
//                - acc_name
//                - password MD5
//                - type 1=ลบ 2=กู้
                val params = ParamUtil.ParamsUid
                params["acc_name"] = binding.editUsername.text.toString()
                params["password"] = MD5.CMD5(binding.editPassword.text.toString())
                params["type"] = "2"
                ParamsData(object : ParamsData.PostLoadListener{
                    override fun onSuccess(body: String) {
                        val jSon = Gson().fromJson(body, DeleteAccountModels::class.java)
                        val toast = Toast.makeText(requireContext(),jSon.message, Toast.LENGTH_SHORT)
                        toast.show()
                        handler.postDelayed({toast.cancel()},1000)
                        binding.progressBar3.visibility = View.GONE
                        mCloseListener.onClose()
                    }

                    override fun onFailed(t: Throwable) {
                        val toast = Toast.makeText(requireContext(),t.message, Toast.LENGTH_SHORT)
                        toast.show()
                        handler.postDelayed({toast.cancel()},1000)
                        binding.progressBar3.visibility = View.GONE
                    }

                }).postLoadData(URL.BASE_URL_SDK, URL.URL_DELETE_AND_RECOVERY,"",params)
            }
        }
        binding.btnBack.setOnClickListener {
            mCloseListener.onClose()
        }

    }
}