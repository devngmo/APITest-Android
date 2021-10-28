package tml.tools.apitest.modules.apibrowser

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tml.tools.apitest.R
import tml.tools.apitest.databinding.ApibrowserListitemBinding
import tml.tools.apitest.domain.models.Api

class ApiListAdapter (val context: Context, var items: ArrayList<Api>, val itemClickListener: (position:Int) -> Unit) : RecyclerView.Adapter<ApiListAdapter.ApiItemViewHolder>() {
    class ApiItemViewHolder private constructor(private val binding: ApibrowserListitemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(api: Api) {
            binding.api = api
        }

        companion object {
            fun from(parent: ViewGroup): ApiItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ApibrowserListitemBinding.inflate(layoutInflater, parent, false)
                return ApiItemViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApiItemViewHolder {
        return ApiItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ApiItemViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener { itemClickListener(position) }
    }

    override fun getItemCount() = items. size
}