package com.devemir.bihaber.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.paging.PagingData
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.devemir.bihaber.R
import com.devemir.bihaber.data.model.Articles
import com.devemir.bihaber.databinding.ItemRowBinding
import com.devemir.bihaber.util.getImageFromUrl
import com.devemir.bihaber.util.parseISODate

class BaseAdapter(
    var onItemClickListener: ((Articles) -> Unit)? = null,
    var articleList: List<Articles>,
    var onBookmarkClickListener: ((Articles) -> Unit)? = null,
    var onCheckBookmark : ((Articles) -> Boolean)? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


  inner  class RecyclerViewHolder(private val binding: ItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Articles?) {
            val articles: Articles? = item
            articles?.title.let { binding.newsTitle.text = it.toString() }
            articles?.description?.let { binding.newsDescp.text = it }
            binding.authorName.text = articles?.author ?: "Unknown"
            articles?.publishedAt.let {
                if (it != null) {
                    binding.authDate.text = it.parseISODate()
                }
            }
            articles?.urlToImage?.let { imagetoUrl ->
                binding.ivNews.getImageFromUrl(imagetoUrl)
            }
            articles?.let {
                if (onItemClickListener != null) {
                    binding.cardView.setOnClickListener { _ ->
                        onItemClickListener?.invoke(it)
                    }
                }
                if (onBookmarkClickListener != null) {
                    binding.bookmarkBtn.setOnClickListener { _ ->
                        onBookmarkClickListener?.invoke(it)
                    }
                }
            }
        }
      fun checkBookmarkIcon(currentArticle: Articles) {
        val isBookmarked=onCheckBookmark?.invoke(currentArticle)
          if (isBookmarked !=null && isBookmarked) {
              binding.bookmarkBtn.icon = AppCompatResources.getDrawable(
                  binding.root.context,
                  R.drawable.bookmark_clicked
              )
          } else {
              binding.bookmarkBtn.icon =
                  AppCompatResources.getDrawable(binding.root.context, R.drawable.bookmark)
          }

      }

      fun bookMarkButonClicked(currentArticle: Articles, notifyCallBack: () -> Unit) {
          binding.bookmarkBtn.setOnClickListener {
              if (currentArticle.isBookmarked) {
                  binding.bookmarkBtn.icon = AppCompatResources.getDrawable(
                      binding.root.context,
                      R.drawable.bookmark_clicked
                  )
              } else {
                  binding.bookmarkBtn.icon =
                      AppCompatResources.getDrawable(binding.root.context, R.drawable.bookmark)
              }
              notifyCallBack()
              onBookmarkClickListener?.invoke(currentArticle)
          }
      }



    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as RecyclerViewHolder).bind(articleList[position])
        holder.checkBookmarkIcon(articleList[position])
        holder.bookMarkButonClicked(articleList[position]) {
            notifyItemChanged(position)
        }

    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Articles>() {
            override fun areItemsTheSame(oldItem: Articles, newItem: Articles): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Articles, newItem: Articles): Boolean {
                return oldItem == newItem
            }
        }
    }

}