package com.app.manqla.view.mobadrat.onlineservice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.manqla.R
import com.app.manqla.databinding.ArtcleItemBinding
import com.app.manqla.databinding.OnlineServiceItemBinding
import com.app.manqla.network.response.mubadraat.Data
import com.squareup.picasso.Picasso


class OnlineServicesAdapter: RecyclerView.Adapter<OnlineServicesAdapter.ViewHolder>() {

    private val withdrawList: MutableList<Data> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val binding: OnlineServiceItemBinding = DataBindingUtil.inflate(layoutInflater!!, R.layout.online_service_item, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.binding.title.text = withdrawList[position].title.toString()
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

    class ViewHolder(val binding: OnlineServiceItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}