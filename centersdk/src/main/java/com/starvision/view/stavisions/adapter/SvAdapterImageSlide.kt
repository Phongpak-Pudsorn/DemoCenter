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
                holder.imgBinding.tvTitle.visibility = View.VISIBLE
                holder.imgBinding.tvContent2.visibility = View.GONE
                holder.imgBinding.tvContent3.visibility = View.GONE
                holder.imgBinding.tvContent.textSize = 20f
                holder.imgBinding.tvContent.gravity = Gravity.NO_GRAVITY
                holder.imgBinding.tvTitle.text = "สำนัก : ${bannerList[position].bannerappdataintroduce[0].DatarLottoStatic.suggest_name}"
                holder.imgBinding.tvContent.text = "3ตัวบน  = ${bannerList[position].bannerappdataintroduce[0].DatarLottoStatic.top_third}\n2ตัวบน  = ${bannerList[position].bannerappdataintroduce[0].DatarLottoStatic.top_second}\n2ตัวล่าง = ${bannerList[position].bannerappdataintroduce[0].DatarLottoStatic.bottom_second}"
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
                holder.imgBinding.tvTitle.text = "ราคาทองวันที่ $strDate"
                holder.imgBinding.tvContent.text = "\nทองคำแท่ง\nทองรูปพรรณ"
                holder.imgBinding.tvContent2.text = "ซื้อ\n${bannerList[position].bannerappdataintroduce[0].DataGb.Buy}\n${bannerList[position].bannerappdataintroduce[1].DataGo.Buy}"
                holder.imgBinding.tvContent3.text = "ขาย\n${bannerList[position].bannerappdataintroduce[0].DataGb.Sell}\n${bannerList[position].bannerappdataintroduce[1].DataGo.Sell}"

            }
            "6" ->{
                holder.imgBinding.tvContent2.visibility = View.VISIBLE
                holder.imgBinding.tvContent3.visibility = View.GONE
                holder.imgBinding.tvContent.gravity = Gravity.NO_GRAVITY
                holder.imgBinding.tvContent.textSize = 20f
                holder.imgBinding.tvTitle.text = bannerList[position].bannerappTitle
                var oil = ""
                var price = ""
                if (bannerList[position].bannerappdataintroduce.size>0) {
                    for (i in bannerList[position].bannerappdataintroduce.indices) {
                        oil += bannerList[position].bannerappdataintroduce[i].DataPump.Oil+"\n"
                        price += bannerList[position].bannerappdataintroduce[i].DataPump.priceOil+"\n"
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
                var bank = "ธนาคาร\n"
                var buy = "ซื้อ\n"
                var sell = "ขาย\n"
                holder.imgBinding.tvTitle.text = bannerList[position].bannerappTitle
                if (bannerList[position].bannerappdataintroduce.size>0){
                    for (i in bannerList[position].bannerappdataintroduce.indices){
                        if (bannerList[position].bannerappdataintroduce[i].DataBank.Rate=="USD 1-20") {
                            bank += bannerList[position].bannerappdataintroduce[i].DataBank.Bank+"\n"
                            buy += bannerList[position].bannerappdataintroduce[i].DataBank.Buy+"\n"
                            sell += bannerList[position].bannerappdataintroduce[i].DataBank.Sell+"\n"
                        }
                    }
                }
                holder.imgBinding.tvContent.text = bank
                holder.imgBinding.tvContent2.text = buy
                holder.imgBinding.tvContent3.text = sell
            }
        }
        Glide.with(holder.imgBinding.imageView).load(bannerList[position].bannerappImgbackground).into(holder.imgBinding.imageView)
    }
    private fun getText(position: Int, id:String): String {
        when(id){
            "1" ->{
                return "<div style='text-align:center'><h4>สำนัก : ${bannerList[position].bannerappdataintroduce[0].DatarLottoStatic.suggest_name}</h4><br>" +
                        "<p>เลขหน้า 3ตัว :&ensp;${bannerList[position].bannerappdataintroduce[0].DatarLottoStatic.top_third}</p><br>" +
                        "<p>เลขหน้า 2ตัว :&ensp;${bannerList[position].bannerappdataintroduce[0].DatarLottoStatic.top_second}</p><br>" +
                        "<p>เลขท้าย 2ตัว :&ensp;${bannerList[position].bannerappdataintroduce[0].DatarLottoStatic.bottom_second}</p></div>"
            }
            "2" ->{
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale("th","TH"))
                val dateOnly = SimpleDateFormat("dd MMMM yyyy", Locale("th","TH"))
                val dateTime = dateFormat.parse(bannerList[position].bannerappdataintroduce[0].DatarCheckLotto.result_date)
                val strDate = dateOnly.format(dateTime)
                return "<div style='text-align:center'><h4>หวยงวด ${strDate}</h4></div><br>" +
                        "<div style='text-align:center'><h5>รางวัลที่หนึ่ง = ${bannerList[position].bannerappdataintroduce[0].DatarCheckLotto.first}</h5></div><br>" +
                        "<div style='text-align:center'><h6>เลขท้าย 2 ตัว = ${bannerList[position].bannerappdataintroduce[0].DatarCheckLotto.last_two}</h6></div>"
            }
            "3" ->{
                return ""
            }
            "4" ->{
                return "<div style='text-align:center'><p>${bannerList[position].bannerappdataintroduce[0].DatarZodice.result_date}</p></div>"
            }
            "5" ->{
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("th","TH"))
                val dateOnly = SimpleDateFormat("dd MMMM yyyy  HH:mm ", Locale("th","TH"))
                val dateTime = dateFormat.parse(bannerList[position].bannerappdataintroduce[0].DataGb.Date)
                val strDate = dateOnly.format(dateTime)
                return "<html><body><div style='text-align:center'><h5>ราคาทองวันที่ ${strDate}</h5></div><br>" +
                        "<h6>ประเภทของทอง&emsp;&emsp;ซื้อ&emsp;&emsp;&emsp;ขาย</h6><br>"+
                        "<h6>ทองคำแท่ง&ensp;&emsp;&emsp;${bannerList[position].bannerappdataintroduce[0].DataGb.Buy}&emsp; ${bannerList[position].bannerappdataintroduce[0].DataGb.Sell}</h6><br>" +
                        "<h6>ทองรูปพรรณ&ensp;&emsp;${bannerList[position].bannerappdataintroduce[1].DataGo.Buy}&emsp; ${bannerList[position].bannerappdataintroduce[1].DataGo.Sell}</h6>"
            }
            "6" ->{
                var oilHtml = "<div style='text-align:center'><h4>${bannerList[position].bannerappTitle}</h4></div><br>"
                if (bannerList[position].bannerappdataintroduce.size>0){
                    oilHtml += "<ui>"
                    for (i in bannerList[position].bannerappdataintroduce.indices){
                        oilHtml +="<li>${bannerList[position].bannerappdataintroduce[i].DataPump.Oil}&emsp;&emsp;${bannerList[position].bannerappdataintroduce[i].DataPump.priceOil}</li>"
                    }
                    oilHtml += "</ui>"
                }
                return oilHtml
            }
            "7" ->{
                var exchange = "<div style='text-align:center'><h4>${bannerList[position].bannerappTitle}</h4></div><br>"
                if (bannerList[position].bannerappdataintroduce.size>0){
                    exchange += "<ui>"
                    for (i in bannerList[position].bannerappdataintroduce.indices){
                        if (bannerList[position].bannerappdataintroduce[i].DataBank.Rate=="USD 1-20") {
                            exchange += "<li>${bannerList[position].bannerappdataintroduce[i].DataBank.Bank}</li>"
                        }
                    }
                    exchange += "</ui>"
                }
                return exchange
            }
        }
        return ""
    }
}