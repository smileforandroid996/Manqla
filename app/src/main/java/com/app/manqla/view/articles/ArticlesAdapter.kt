package com.app.manqla.view.articles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.manqla.R
import com.app.manqla.databinding.ArtcleItemBinding
import com.app.manqla.network.response.articles.Data
import com.squareup.picasso.Picasso


class ArticlesAdapter: RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {

    private val articlesList: MutableList<Data> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val binding: ArtcleItemBinding = DataBindingUtil.inflate(layoutInflater!!, R.layout.artcle_item, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.binding.name.text = articlesList[position].title
        viewHolder.binding.likes.text = "${articlesList[position].likeCount} اعجاب"

        Picasso.get().load(articlesList[position].image).into(viewHolder.binding.image)
        viewHolder.binding.layoutItem.setOnClickListener {
            onItemClickListener?.setOnItemClickListener(articlesList[position].id)
        }

    }

    override fun getItemCount() = articlesList.size

    fun submitList(list: List<Data>?) {
        list?.let {
            articlesList.addAll(it)
            notifyDataSetChanged()
        }
    }

    @Suppress("unused")
    fun clear() {
        articlesList.clear()
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun setOnItemClickListener(movieId: Int)
    }

    class ViewHolder(val binding: ArtcleItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}