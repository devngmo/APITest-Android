package tml.tools.apitest.application.common.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tml.tools.apitest.R
import tml.tools.apitest.databinding.PropertylistItemBinding
import tml.tools.apitest.domain.models.Api
import tml.tools.apitest.domain.models.KeyValuePair

class KVPListAdapter (val context: Context, var items: ArrayList<KeyValuePair>,
                      val itemClickListener: (position:Int) -> Unit)
    : RecyclerView.Adapter<KVPListAdapter.KVPItemViewHolder>() {

    class KVPItemViewHolder private constructor(private val binding: PropertylistItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: KeyValuePair) {
            binding.kvp = item
        }

        companion object {
            fun from(parent: ViewGroup): KVPItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PropertylistItemBinding.inflate(layoutInflater, parent, false)
                return KVPItemViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KVPItemViewHolder {
        return KVPItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: KVPItemViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener { itemClickListener(position) }
    }

    override fun getItemCount() = items. size

    fun update(newItems: java.util.ArrayList<KeyValuePair>) {
        this.items = newItems
        notifyDataSetChanged()
    }
}