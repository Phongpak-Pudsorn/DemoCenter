package com.starvision.view.center.sub.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.starvision.luckygamesdk.databinding.LayoutExchangeItemBinding
import com.starvision.view.center.sub.models.SvSubExchangeModels
import kotlin.math.roundToInt

class SvAdapterExchangeSub(val context:Context, val list:ArrayList<SvSubExchangeModels.BankData>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val VIEW_TYPE_HEADER = 1
    val VIEW_TYPE_ITEM = 2
    class BankHolder(val bankBinding: LayoutExchangeItemBinding):RecyclerView.ViewHolder(bankBinding.root)

    override fun getItemViewType(position: Int): Int {
        return if (position==0){
            VIEW_TYPE_HEADER
        }else{
            VIEW_TYPE_ITEM
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = LayoutExchangeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BankHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is BankHolder){
            holder.bankBinding.TvNameBank.text = list[position].DatarowBank.Bank
            holder.bankBinding.TvBuy.text = decimals(list[position].DatarowBank.Bank_ExchangeRate[2].buy)
            holder.bankBinding.TvSell.text = decimals(list[position].DatarowBank.Bank_ExchangeRate[2].sell)
            try {
                val inputs = context.assets.open("bank/"+list[position].DatarowBank.Bank+".png")
                val img = Drawable.createFromStream(inputs,null)
                holder.bankBinding.IvFrag.setImageDrawable(img)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    override fun getItemCount(): Int = list.size

    private fun decimals(num: String): String {
        val dec = num.toDouble()
        val result = (dec * 100.0).roundToInt() / 100.0
        return "%.2f".format(result)
    }
}