package com.starvision.view.center

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.starvision.api.URL
import com.starvision.config.*
import com.starvision.data.AppPreferencesLogin
import com.starvision.data.Const
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.PageFullProfileBinding
import com.starvision.view.center.models.ProfileModels
import com.starvision.view.login.LoginActivity
import retrofit2.http.Body
import java.security.SecureRandom
import java.security.spec.KeySpec
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class ProfileDialogFragment : DialogFragment() {
    private val binding : PageFullProfileBinding by lazy { PageFullProfileBinding.inflate(layoutInflater) }
    private val TAG = javaClass.simpleName
    private val appPrefe = AppPreferencesLogin

    private lateinit var mClickListener : ClickListener
    interface ClickListener {
        fun onLogout()
        fun onCancel()
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
        dialog!!.setOnCancelListener {
            mClickListener.onCancel()
        }
    }

    private fun bindingObject(){
        binding.tvName.text = Login.getName
        binding.tvCoin.text = Login.getCoin
        binding.tvIdx.text = "idx : "+Login.getIDX
        Glide.with(requireContext()).load(Login.getAvatar).into(binding.imgProfile)
        binding.tvLogout.setOnClickListener {
            mClickListener.onLogout()
            appPrefe.setPreferences(requireContext(),AppPreferencesLogin.KEY_PREFS_LOGIN,false)
            appPrefe.setPreferences(requireContext(),AppPreferencesLogin.KEY_PREFS_NAME, "")
            appPrefe.setPreferences(requireContext(),AppPreferencesLogin.KEY_PREFS_AVATAR,"")
            appPrefe.setPreferences(requireContext(),AppPreferencesLogin.KEY_PREFS_COIN,"")
            val intent = Intent(requireContext(),LoginActivity::class.java)
            startActivity(intent)
        }
    }

}