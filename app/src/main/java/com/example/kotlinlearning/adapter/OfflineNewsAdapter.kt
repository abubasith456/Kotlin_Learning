package com.example.kotlinlearning.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinlearning.databinding.ListViewItemsBinding
import com.example.kotlinlearning.databinding.ListViewOfflineDbBinding
import com.example.kotlinlearning.db.NewsEntity
import com.example.kotlinlearning.db.RoomAppDb
import com.example.kotlinlearning.model.Article

class OfflineNewsAdapter(
    var context: Context,
    var newsList: MutableList<NewsEntity> = mutableListOf<NewsEntity>()
) :
    RecyclerView.Adapter<OfflineNewsAdapter.OfflineNewsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OfflineNewsAdapter.OfflineNewsViewHolder {
        val layoutInflater = LayoutInflater.from(context.applicationContext)
        val listViewItemsBinding: ListViewOfflineDbBinding =
            ListViewOfflineDbBinding.inflate(layoutInflater, parent, false)
        return OfflineNewsViewHolder(listViewItemsBinding)
    }

    override fun onBindViewHolder(holder: OfflineNewsViewHolder, position: Int) {

        val news: NewsEntity = newsList.get(position)
        holder.listViewItemsBinding.newsEntity = news
        holder.listViewItemsBinding.linearLayoutDelete.setOnClickListener {
            try {
                val newsEntity = NewsEntity()
                newsEntity.id = newsList.get(position).id
                val userDao = RoomAppDb.getAppDatabase(context.applicationContext)?.userDao()
                userDao?.deleteNews(newsEntity)
                newsList.removeAt(position)
                notifyItemRemoved(position + 1)
//                notifyDataSetChanged()
                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
            } catch (exception: Exception) {
                Log.e("Offline error==> ", exception.message.toString())
            }
        }

    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    class OfflineNewsViewHolder(listViewItemsBinding: ListViewOfflineDbBinding) :
        RecyclerView.ViewHolder(listViewItemsBinding.root) {

        var listViewItemsBinding: ListViewOfflineDbBinding

        init {
            this.listViewItemsBinding = listViewItemsBinding
        }
    }

}