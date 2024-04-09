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

class DeleteAccountDialogFragment : DialogFragment() {
    private val binding : PageDeleteAccountBinding by lazy { PageDeleteAccountBinding.inflate(layoutInflater) }
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
        binding.imgClose.setOnClickListener {
            dismiss()
        }
        binding.tvDelete.setOnClickListener {
            if(!binding.checkBox.isChecked){
                val toast = Toast.makeText(requireContext(),getString(R.string.text_alert_delete_uncheck),Toast.LENGTH_SHORT)
                toast.show()
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({ toast.cancel() }, 1000)
            }else{
//                - acc_name
//                - password MD5
//                - type 1=ลบ 2=กู้
                val params = ParamUtil.ParamsUid
                params["acc_name"] = Login.UserName
                params["password"] = MD5.CMD5(Login.Password)
                params["type"] = "1"
                ParamsData(object : ParamsData.PostLoadListener{
                    override fun onSuccess(body: String) {
                        val jSon = Gson().fromJson(body,DeleteAccountModels::class.java)
                        Const.loge(TAG,"jSon : "+jSon.message)
                        val intent = Intent(requireContext(),Login::class.java)
                        startActivity(intent)
                    }

                    override fun onFailed(t: Throwable) {

                    }

                }).postLoadData(URL.BASE_URL_SDK,URL.URL_DELETE_AND_RECOVERY,"",params)
            }
        }

    }
}