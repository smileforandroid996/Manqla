package com.app.manqla.view.mobadrat.mobadratstudent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.manqla.R
import com.app.manqla.databinding.ArtcleItemBinding
import com.app.manqla.databinding.MobardratStudentsItemBinding
import com.app.manqla.network.response.mubadraat.Data
import com.app.manqla.network.response.mubadraat.Invitations
import com.squareup.picasso.Picasso


class MobadratStudentAdapter: RecyclerView.Adapter<MobadratStudentAdapter.ViewHolder>() {

    private val withdrawList: MutableList<Data> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val binding: MobardratStudentsItemBinding = DataBindingUtil.inflate(layoutInflater!!, R.layout.mobardrat_students_item, parent, false)

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

    class ViewHolder(val binding: MobardratStudentsItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}