package com.starvision.view.login

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.starvision.config.SvLogin
import com.starvision.centersdk.R
import com.starvision.centersdk.databinding.PageGuideBinding

class SvGuideDialogFragment:DialogFragment() {
    val binding:PageGuideBinding by lazy { PageGuideBinding.inflate(layoutInflater) }

    private lateinit var mClickNext : ClickNext
    interface ClickNext{
        fun onClickNext()
    }
    fun setClickNext(listener : ClickNext){
        mClickNext = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog!!.window!!.setBackgroundDrawableResource(R.color.transparent)
        dialog!!.setCancelable(false)
        val bm = getBitmapFromAsset("logo_starvision.png")
        binding.imgLogo.setImageBitmap(bm)
        binding.buttonNext.setOnClickListener {
            SvLogin.guideFirstTime = true
            mClickNext.onClickNext()
            dialog!!.dismiss()
        }
        dialog!!.show()
    }
    private fun getBitmapFromAsset(strPic: String): Bitmap {
        val assetManager = requireActivity().assets
        val iStr = assetManager.open(strPic)
        return BitmapFactory.decodeStream(iStr)
    }
}