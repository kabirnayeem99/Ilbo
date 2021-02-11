package io.github.kabirnayeem99.ilbo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.with
import io.github.kabirnayeem99.ilbo.R
import io.github.kabirnayeem99.ilbo.models.Article
import io.github.kabirnayeem99.ilbo.utils.AppGlideModule
import kotlinx.android.synthetic.main.*
import kotlinx.android.synthetic.main.item_article_preview.view.*


class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {
    lateinit var context: Context

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivArticleImage = itemView.ivArticleImage
        val tvSource = itemView.tvSource
        val tvTitle = itemView.tvTitle
        val tvDescription = itemView.tvDescription
        val tvPublishedAt = itemView.tvPublishedAt

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
        val article = differ.currentList[0]

        article.apply {
            Glide.with(context)
                .load(article.urlToImage)
                .into(holder.ivArticleImage)

            holder.tvSource.text = article.source.name
            holder.tvDescription.text = article.description
            holder.tvPublishedAt.text = article.publishedAt
            holder.tvTitle.text = article.title
            setOnItemClickListener {
                onItemClickListener?.let { it(article) }
            }

        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listner: (Article) -> Unit) {
        onItemClickListener = listner
    }
}