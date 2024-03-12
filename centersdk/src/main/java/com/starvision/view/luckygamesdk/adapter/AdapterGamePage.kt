package com.starvision.view.luckygamesdk.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.starvision.luckygamesdk.R
import com.starvision.view.luckygamesdk.view.WebViewPage

class AdapterGamePage(private val mActivity: Activity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_games, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is GameViewHolder){
//            Glide.with(mContext).load("").into(holder.image_game)
//            holder.name_game.text = ""
            holder.itemView.setOnClickListener {
                val intent = Intent(mActivity,WebViewPage::class.java)
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