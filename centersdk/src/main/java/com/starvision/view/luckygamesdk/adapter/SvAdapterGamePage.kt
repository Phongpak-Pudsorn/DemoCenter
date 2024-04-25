package com.starvision.view.luckygamesdk.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.starvision.centersdk.R
import com.starvision.view.SvWebViewLuckyActivity

class SvAdapterGamePage(private val mActivity: Activity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_games, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is GameViewHolder){
            //รอดึงข้อมูล จาก api

//            Glide.with(mContext).load("").into(holder.image_game)
//            holder.name_game.text = ""

            holder.itemView.setOnClickListener {
//                val uid = appPref.getPreferences(mActivity,AppPreferencesLogin.KEY_PREFS_USERID,"")
//                val token = ""
//                val lan = "thai_th"

                val intent = Intent(mActivity, SvWebViewLuckyActivity::class.java)
                intent.putExtra("link", "https://luckygame.in.th/")
                mActivity.startActivity(intent)
            }

        }
    }

    override fun getItemCount(): Int {
        return 10
    }
    inner class GameViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val image_game = view.findViewById<ImageView>(R.id.image_game)
        val name_game = view.findViewById<TextView>(R.id.name_game)
    }
}