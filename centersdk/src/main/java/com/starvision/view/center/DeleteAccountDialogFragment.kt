package com.starvision.view.center

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
import com.starvision.api.URL
import com.starvision.config.Login
import com.starvision.config.MD5
import com.starvision.config.ParamsData
import com.starvision.data.Const
import com.starvision.data.ParamUtil
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.PageDeleteAccountBinding
import com.starvision.view.center.models.DeleteAccountModels
import com.starvision.view.login.LoginActivity

class DeleteAccountDialogFragment : DialogFragment() {
    private val binding : PageDeleteAccountBinding by lazy { PageDeleteAccountBinding.inflate(layoutInflater) }
    private val handler = Handler(Looper.getMainLooper())
    private val TAG = javaClass.simpleName

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
        dialog!!.setCancelable(false)
        dialog!!.show()
        binding.tvText.text = getString(R.string.text_alert_delete_account)+"\n"+"\n"+
                getString(R.string.text_alert_delete_account1)+"\n"+
        getString(R.string.text_alert_delete_account2)+"\n"+
        getString(R.string.text_alert_delete_account3)+"\n"
        binding.imgBack.setOnClickListener {
            dialog!!.dismiss()
        }
        binding.tvDelete.setOnClickListener {
            binding.tvDelete.isEnabled = false
            handler.postDelayed({binding.tvDelete.isEnabled = true},1000)
            if(!binding.checkBox.isChecked){
                val toast = Toast.makeText(requireContext(),getString(R.string.text_alert_delete_uncheck),Toast.LENGTH_SHORT)
                toast.show()
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({ toast.cancel() }, 1000)
            }else{
                val params = ParamUtil.ParamsUid
                params["acc_name"] = Login.UserName
                params["password"] = MD5.CMD5(Login.Password)
                params["type"] = "1"
                ParamsData(object : ParamsData.PostLoadListener{
                    override fun onSuccess(body: String) {
                        val jSon = Gson().fromJson(body,DeleteAccountModels::class.java)
                        val toast = Toast.makeText(requireContext(),jSon.message,Toast.LENGTH_SHORT)
                        toast.show()
                        handler.postDelayed({toast.cancel()},1000)
                        Login.isLogin = false
                        val intent = Intent(requireContext(),LoginActivity::class.java)
                        startActivity(intent)
                    }

                    override fun onFailed(t: Throwable) {
                        val toast = Toast.makeText(requireContext(),t.message,Toast.LENGTH_SHORT)
                        toast.show()
                        handler.postDelayed({toast.cancel()},1000)
                    }

                }).postLoadData(URL.BASE_URL_SDK,URL.URL_DELETE_AND_RECOVERY,"",params)
            }
        }

    }
}