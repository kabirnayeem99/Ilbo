package io.github.kabirnayeem99.ilbo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.github.kabirnayeem99.ilbo.R
import io.github.kabirnayeem99.ilbo.models.Article
import kotlinx.android.synthetic.main.item_article_preview.view.*


class NewsAdapter() : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivArticleImage: ImageView = itemView.ivArticleImage
        val tvSource: TextView = itemView.tvSource
        val tvTitle: TextView = itemView.tvTitle
        val tvDescription: TextView = itemView.tvDescription
        val tvPublishedAt: TextView = itemView.tvPublishedAt

    }

    private var differCallBack = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_article_preview,
                parent,
                false,
            )
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]

        holder.itemView.apply {
            Glide.with(this)
                .load(article.urlToImage)
                .into(ivArticleImage)

            tvSource.text = article.source.name
            tvDescription.text = article.description
            tvPublishedAt.text = article.publishedAt
            tvTitle.text = article.title
            setOnItemClickListener {
                onItemClickListener?.let { it(article) }
            }

        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    private fun setOnItemClickListener(listner: (Article) -> Unit) {
        onItemClickListener = listner
    }
}