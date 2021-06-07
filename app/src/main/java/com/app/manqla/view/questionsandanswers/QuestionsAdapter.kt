package com.app.manqla.view.questionsandanswers

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import com.app.manqla.R
import com.app.manqla.network.response.questions.Row


class QuestionsAdapter: BaseExpandableListAdapter() {

    private val rows: MutableList<Row> = ArrayList()

    override fun getGroupCount(): Int {
        return rows.size
    }

    override fun getChildrenCount(i: Int): Int {
        return 1
    }

    override fun getGroup(i: Int): String {
        return rows[i].question!!
    }

    override fun getChild(i: Int, i1: Int): Row {
        return rows[i]
    }

    override fun getGroupId(i: Int): Long {
        return i.toLong()
    }

    override fun getChildId(i: Int, i1: Int): Long {
        return i1.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, view: View?, viewGroup: ViewGroup?): View? {
        var view: View? = view
        val headerTitle = getGroup(groupPosition) as String
        if (view == null) {
            val layoutInflater = viewGroup!!.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = layoutInflater.inflate(R.layout.question_list_item, null)
        }
        val arrow: ImageView = view?.findViewById(R.id.iv_arrow)!!
        val itemName: TextView = view.findViewById(R.id.tv_gove_name)
        itemName.text = headerTitle
        if (isExpanded) {
            itemName.setTextColor(viewGroup?.context!!.resources.getColor(R.color.colorPrimary))
            arrow.setImageDrawable(viewGroup.context!!.getDrawable(R.drawable.ic_arrow_up))
        } else {
            itemName.setTextColor(viewGroup?.context!!.resources.getColor(R.color.text_black))
            arrow.setImageDrawable(viewGroup.context!!.getDrawable(R.drawable.ic_arrow_down))
        }
        return view
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, b: Boolean, view: View?, viewGroup: ViewGroup?): View? {
        var view: View? = view
        val childText = getChild(groupPosition, childPosition) as Row
        if (view == null) {
            val layoutInflater =
                viewGroup?.context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = layoutInflater.inflate(R.layout.awnser_list_item, null)
        }

        val answer: TextView = view?.findViewById(R.id.answer)!!
        val date: TextView = view.findViewById(R.id.date)
        answer.text = childText.answer
        date.text = childText.createdAt

        return view
    }

    override fun isChildSelectable(i: Int, i1: Int): Boolean {
        return true
    }

    fun submitList(newRows: List<Row>?) {
        newRows?.let {
            rows.addAll(it)
            notifyDataSetChanged()
        }
    }

    @Suppress("unused")
    fun clear() {
        rows.clear()
        notifyDataSetChanged()
    }
}