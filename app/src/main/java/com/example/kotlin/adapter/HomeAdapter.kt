package com.example.kotlin.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.R
import com.example.kotlin.activity.QListActivity
import com.example.kotlin.data.AlgorithmData
import com.example.kotlin.data.Data
import com.example.kotlin.data.HawkConfig
import com.example.kotlin.data.QAData
import com.example.kotlin.viewholder.MyViewHolder
import com.orhanobut.hawk.Hawk

class HomeAdapter(private val context: Context, private val dataList: ArrayList<Data>) :
    RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_items, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val hobby = dataList[position]
        holder.setData(hobby, position)
        holder.setOnListener(object : HolderListener {
            override fun delete() {
                dataList.removeAt(position)
                notifyDataSetChanged()
            }

            override fun jump() {
                when (dataList[position].title) {
                    "一进二" -> context.startActivity(Intent(context, QListActivity::class.java).putExtra("intent", "one_to_two.json"))
                    "整体操作" -> context.startActivity(Intent(context, QListActivity::class.java).putExtra("intent", "thought.json"))
                    "恢复问答" -> {
                        Toast.makeText(context, "已刷新", Toast.LENGTH_SHORT).show()
                        Hawk.put(HawkConfig.QA, QAData.data)
                    }
                    "恢复算法" -> {
                        Toast.makeText(context, "已刷新", Toast.LENGTH_SHORT).show()
                        Hawk.put(HawkConfig.AlgorithmQA, AlgorithmData.data)
                    }

                }

            }
        })
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    interface HolderListener {
        fun delete()
        fun jump()
    }

}