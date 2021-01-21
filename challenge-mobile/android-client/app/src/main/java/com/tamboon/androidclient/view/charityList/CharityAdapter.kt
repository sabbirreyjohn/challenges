package com.tamboon.androidclient.view.charityList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tamboon.androidclient.R
import com.tamboon.androidclient.databinding.RowCharityBinding

class CharityAdapter(val context: Context?, val charities: List<Charity>) :
    RecyclerView.Adapter<CharityAdapter.TheViewHolder>() {

    val inflater = LayoutInflater.from(context)

    inner class TheViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var binding = RowCharityBinding.bind(itemView)

        init {
            binding.rl.setOnClickListener { view ->

                listener?.onRecycleViewItemClick(view, charities.get(adapterPosition))
            }
        }

        fun bindCharity(charity: Charity) {
            binding.tvName.text = charity.name
            binding.tvId.text = charity.id.toString()
            Picasso.get().load(charity.logo_url).into(binding.ivLogo)
        }
    }

    var listener: OnRecycleViewItemClickListener? = null

    fun setOnRecycleViewItemClickListener(listener: OnRecycleViewItemClickListener?) {
        this.listener = listener
    }

    interface OnRecycleViewItemClickListener {
        fun onRecycleViewItemClick(v: View?, charity: Charity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheViewHolder {
        val view = inflater.inflate(R.layout.row_charity, parent, false)
        return TheViewHolder(view)
    }

    override fun onBindViewHolder(holder: TheViewHolder, position: Int) {
        var charity = charities.get(position)
        holder.bindCharity(charity)
    }

    override fun getItemCount(): Int {
        return charities.size
    }
}