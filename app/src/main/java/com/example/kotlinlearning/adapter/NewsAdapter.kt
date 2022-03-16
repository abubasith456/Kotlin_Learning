package com.example.kotlinlearning.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinlearning.R
import com.example.kotlinlearning.databinding.ListViewItemsBinding
import com.example.kotlinlearning.fragment.NewsDetailsFragment
import com.example.kotlinlearning.fragment.NewsFragment
import com.example.kotlinlearning.model.Article

class NewsAdapter(var context: Context, var newsList: List<Article>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(context.applicationContext)
        val listViewItemsBinding: ListViewItemsBinding =
            ListViewItemsBinding.inflate(layoutInflater, parent, false)
        return NewsViewHolder(listViewItemsBinding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        val article: Article = newsList.get(position)
        holder.listViewItemsBinding.articles = article
        holder.listViewItemsBinding.executePendingBindings()
        holder.listViewItemsBinding.constarinLayout.setOnClickListener {

            val fragment = NewsDetailsFragment(
                article.title,
                article.description,
                article.content,
                article.urlToImage,
                article.url,
                article.author,
                article.publishedAt
            )
            val transaction =
                (context as AppCompatActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayoutContainer, fragment)
            transaction.addToBackStack(null)
            transaction.commit()

        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    class NewsViewHolder(listViewItemsBinding: ListViewItemsBinding) :
        RecyclerView.ViewHolder(listViewItemsBinding.root) {

        var listViewItemsBinding: ListViewItemsBinding


        init {
            this.listViewItemsBinding = listViewItemsBinding
        }
    }

}