package com.app.manqla.view.websites.onlineservices

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.manqla.R
import com.app.manqla.databinding.ArtcleItemBinding
import com.app.manqla.databinding.WebsiteItemBinding
import com.app.manqla.network.response.websites.Data
import com.squareup.picasso.Picasso


class WebsiteOnlineAdapter: RecyclerView.Adapter<WebsiteOnlineAdapter.ViewHolder>() {

    private val withdrawList: MutableList<Data> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val binding: WebsiteItemBinding = DataBindingUtil.inflate(layoutInflater!!, R.layout.website_item, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.binding.name.text = withdrawList[position].name.toString()
        viewHolder.binding.des.text = withdrawList[position].description.toString()
        Picasso.get().load(withdrawList[position].image).into(viewHolder.binding.image)
        viewHolder.binding.layoutItem.setOnClickListener {
            onItemClickListener?.setOnItemClickListener(withdrawList[position])
        }

    }

    override fun getItemCount() = withdrawList.size

    fun submitList(list: List<Data>?) {
        list?.let {
            withdrawList.addAll(it)
            notifyDataSetChanged()
        }
    }

    @Suppress("unused")
    fun clear() {
        withdrawList.clear()
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun setOnItemClickListener(movieId: Data)
    }

    class ViewHolder(val binding: WebsiteItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}