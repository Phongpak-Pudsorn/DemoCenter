package com.starvision.view.stavisions.adapter

import android.content.Context
import android.text.Html
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.starvision.data.SvConst
import com.starvision.luckygamesdk.databinding.ItemAppsBannerBinding
import com.starvision.view.center.models.SvCenterModels
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class SvAdapterImageSlide(context: Context, val bannerList:ArrayList<SvCenterModels.CenterData.PageData.BannerData>):RecyclerView.Adapter<SvAdapterImageSlide.ImageHolder>(){
    interface OnDataPass{
        fun passData(packName:String)
    }
    private var dataPasser: OnDataPass
    init {
        dataPasser = context as OnDataPass
    }
    class ImageHolder(val imgBinding:ItemAppsBannerBinding):RecyclerView.ViewHolder(imgBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val binding= ItemAppsBannerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ImageHolder(binding)
    }

    override fun getItemCount(): Int = bannerList.size

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
//        holder.imgBinding.tvContent.text = Html.fromHtml(bannerList[position].bannerappImgintroduce,0)
//        holder.imgBinding.tvContent.text = Html.fromHtml(getText(position,bannerList[position].bannerappId),Html.FROM_HTML_MODE_COMPACT)
        holder.imgBinding.root.setOnClickListener {
            if (SvConst.clickAble) {
                SvConst.clickAble = false
                dataPasser.passData(bannerList[position].bannerappLinkstoregoogle)
            }
        }
        when(bannerList[position].bannerappId){
            "1" ->{
                var luckynum = ""
                holder.imgBinding.tvTitle.visibility = View.VISIBLE
                holder.imgBinding.tvContent2.visibility = View.GONE
                holder.imgBinding.tvContent3.visibility = View.GONE
                holder.imgBinding.tvContent.textSize = 20f
                holder.imgBinding.tvContent.gravity = Gravity.NO_GRAVITY
                if (bannerList[position].bannerappdataintroduce[0].DatarLottoStatic.top_third!=""){
                    luckynum += "3ตัวบน   =   ${bannerList[position].bannerappdataintroduce[0].DatarLottoStatic.top_third}\n"
                }
                if (bannerList[position].bannerappdataintroduce[0].DatarLottoStatic.top_second!=""){
                    luckynum += "2ตัวบน   =   ${bannerList[position].bannerappdataintroduce[0].DatarLottoStatic.top_second}\n"
                }
                if (bannerList[position].bannerappdataintroduce[0].DatarLottoStatic.bottom_second!=""){
                    luckynum += "2ตัวล่าง  =   ${bannerList[position].bannerappdataintroduce[0].DatarLottoStatic.bottom_second}"
                }
                holder.imgBinding.tvTitle.text = "สำนัก : ${bannerList[position].bannerappdataintroduce[0].DatarLottoStatic.suggest_name}"
                holder.imgBinding.tvContent.text = luckynum
            }
            "2" ->{
                holder.imgBinding.tvTitle.visibility = View.VISIBLE
                holder.imgBinding.tvContent2.visibility = View.GONE
                holder.imgBinding.tvContent3.visibility = View.GONE
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale("th","TH"))
                val dateOnly = SimpleDateFormat("dd MMMM yyyy", Locale("th","TH"))
                val dateTime = dateFormat.parse(bannerList[position].bannerappdataintroduce[0].DatarCheckLotto.result_date)
                val strDate = dateOnly.format(dateTime)
                holder.imgBinding.tvTitle.text = "หวยงวด $strDate"
                holder.imgBinding.tvContent.textSize = 20f
                holder.imgBinding.tvContent.gravity = Gravity.CENTER
                holder.imgBinding.tvContent.text = "รางวัลที่หนึ่ง = ${bannerList[position].bannerappdataintroduce[0].DatarCheckLotto.first}\n\nเลขท้าย 2 ตัว = ${bannerList[position].bannerappdataintroduce[0].DatarCheckLotto.last_two}"
            }
            "3" ->{

            }
            "4" ->{
                holder.imgBinding.tvTitle.visibility = View.GONE
                holder.imgBinding.tvContent2.visibility = View.GONE
                holder.imgBinding.tvContent3.visibility = View.GONE
                holder.imgBinding.tvContent.gravity = Gravity.CENTER
                holder.imgBinding.tvContent.textSize = 30f
                val arr = bannerList[position].bannerappdataintroduce[0].DatarZodice.result_date.split(":").toTypedArray()
                holder.imgBinding.tvContent.text = arr[0]+"\n"+arr[1]
            }
            "5" ->{
                holder.imgBinding.tvTitle.visibility = View.VISIBLE
                holder.imgBinding.tvContent2.visibility = View.VISIBLE
                holder.imgBinding.tvContent3.visibility = View.VISIBLE
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("th","TH"))
                val dateOnly = SimpleDateFormat("dd MMMM yyyy  HH:mm ", Locale("th","TH"))
                val dateTime = dateFormat.parse(bannerList[position].bannerappdataintroduce[0].DataGb.Date)
                val strDate = dateOnly.format(dateTime)
                holder.imgBinding.tvContent.gravity = Gravity.NO_GRAVITY
                holder.imgBinding.tvContent2.gravity = Gravity.CENTER
                holder.imgBinding.tvContent3.gravity = Gravity.CENTER
                holder.imgBinding.tvContent.textSize = 20f
                holder.imgBinding.tvContent2.textSize = 20f
                holder.imgBinding.tvContent3.textSize = 20f
                holder.imgBinding.tvTitle.text = "ราคาทองวันที่ $strDate"
                holder.imgBinding.tvContent.text = "\nทองคำแท่ง\nทองรูปพรรณ"
                holder.imgBinding.tvContent2.text = "ซื้อ\n${bannerList[position].bannerappdataintroduce[0].DataGb.Buy}\n${bannerList[position].bannerappdataintroduce[1].DataGo.Buy}"
                holder.imgBinding.tvContent3.text = "ขาย\n${bannerList[position].bannerappdataintroduce[0].DataGb.Sell}\n${bannerList[position].bannerappdataintroduce[1].DataGo.Sell}"

            }
            "6" ->{
                holder.imgBinding.tvTitle.visibility = View.VISIBLE
                holder.imgBinding.tvContent2.visibility = View.VISIBLE
                holder.imgBinding.tvContent3.visibility = View.GONE
                holder.imgBinding.tvContent.gravity = Gravity.NO_GRAVITY
                holder.imgBinding.tvContent.textSize = 20f
                holder.imgBinding.tvContent2.textSize = 20f
                holder.imgBinding.tvTitle.text = bannerList[position].bannerappTitle
                var oil = ""
                var price = ""
                if (bannerList[position].bannerappdataintroduce.size>0) {
                    for (i in bannerList[position].bannerappdataintroduce.indices) {
                        if (i != 0){
                            oil +="\n"
                            price +="\n"
                        }
                        oil += bannerList[position].bannerappdataintroduce[i].DataPump.Oil
                        price += bannerList[position].bannerappdataintroduce[i].DataPump.priceOil
                    }
                }
                holder.imgBinding.tvContent.text = oil
                holder.imgBinding.tvContent2.text = price
            }
            "7" ->{
                holder.imgBinding.tvTitle.visibility = View.VISIBLE
                holder.imgBinding.tvContent2.visibility = View.VISIBLE
                holder.imgBinding.tvContent3.visibility = View.VISIBLE
                holder.imgBinding.tvContent.gravity = Gravity.NO_GRAVITY
                holder.imgBinding.tvContent2.gravity = Gravity.CENTER
                holder.imgBinding.tvContent3.gravity = Gravity.CENTER
                holder.imgBinding.tvContent.textSize = 20f
                holder.imgBinding.tvContent2.textSize = 20f
                holder.imgBinding.tvContent3.textSize = 20f
                var bank = "ธนาคาร\n"
                var buy = "ซื้อ\n"
                var sell = "ขาย\n"
                holder.imgBinding.tvTitle.text = bannerList[position].bannerappTitle
                if (bannerList[position].bannerappdataintroduce.size>0){
                    for (i in bannerList[position].bannerappdataintroduce.indices){
                        if (bannerList[position].bannerappdataintroduce[i].DataBank.Rate=="USD 1-2") {
                            if (bannerList[position].bannerappdataintroduce[i].DataBank.Buy!=""&&bannerList[position].bannerappdataintroduce[i].DataBank.Sell!="") {
                                if (i != 0) {
                                    bank += "\n"
                                    buy += "\n"
                                    sell += "\n"
                                }
                                bank += bannerList[position].bannerappdataintroduce[i].DataBank.Bank
                                buy += decimals(bannerList[position].bannerappdataintroduce[i].DataBank.Buy)
                                sell += decimals(bannerList[position].bannerappdataintroduce[i].DataBank.Sell)
                            }
                        }
                    }
                }
                holder.imgBinding.tvContent.text = bank
                holder.imgBinding.tvContent2.text = buy
                holder.imgBinding.tvContent3.text = sell
            }
        }
        Glide.with(holder.imgBinding.imageView).load(bannerList[position].bannerappImgbackground).centerCrop().into(holder.imgBinding.imageView)
    }
    private fun decimals(num: String): String {
        return try {
            val dec = num.toDouble()
            val result = (dec * 100.0).roundToInt() / 100.0
            "%.2f".format(result)
        }catch (e :java.lang.Exception){
            "-"
        }
    }

}