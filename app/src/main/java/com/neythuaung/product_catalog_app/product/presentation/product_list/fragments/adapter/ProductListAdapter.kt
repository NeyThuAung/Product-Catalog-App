package com.neythuaung.product_catalog_app.product.presentation.product_list.fragments.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neythuaung.product_catalog_app.R
import com.neythuaung.product_catalog_app.databinding.ProductItemCardViewBinding
import com.neythuaung.product_catalog_app.product.domain.entity.FavoriteProductEntity
import com.neythuaung.product_catalog_app.product.domain.entity.Product

class ProductListAdapter(
    private val productList: ArrayList<Product>,
    private val onProductClick: (Product) -> Unit,
    private val onFavouriteProductClick: (Product, Boolean) -> Unit,
) : RecyclerView.Adapter<ProductListAdapter.ProductListHolder>(), Filterable {

    private val fullList = ArrayList(productList)
    private var onEmptyState: ((Boolean) -> Unit)? = null

    fun setOnEmptyStateListener(listener: (Boolean) -> Unit) {
        onEmptyState = listener
    }

    private var favoriteIds: List<Int> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setFavorites(list: List<FavoriteProductEntity>) {
        favoriteIds = list.map { it.id }
        notifyDataSetChanged()
    }

    inner class ProductListHolder(
        private val binding: ProductItemCardViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.apply {

                Glide.with(itemView)
                    .load(product.thumbnail)
                    .placeholder(R.color.inactive)
                    .into(ivImage)

                val isFavorite = favoriteIds.contains(product.id!!.toInt())
                ivFavorite.setImageResource(
                    if (isFavorite) {
                        R.drawable.favourite_fill
                    } else {
                        R.drawable.favourite
                    }
                )

                tvName.text = product.title
                tvBrand.text = product.brand
                tvPrice.text = itemView.context.getString(R.string._1_s, product.price.toString())
                tvBrand.isVisible = !product.brand.isNullOrEmpty()

                root.setOnClickListener {
                    onProductClick(product)
                }

                ivFavorite.setOnClickListener {
                    onFavouriteProductClick(product, isFavorite)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListHolder {
        return ProductListHolder(
            ProductItemCardViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ProductListHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val query = constraint?.toString()?.lowercase()?.trim()

                val filtered = if (query.isNullOrEmpty()) {
                    fullList
                } else {
                    fullList.filter {
                        it.title!!.lowercase().contains(query) ||
                                it.brand?.lowercase()?.contains(query) == true
                    }
                }

                return FilterResults().apply {
                    values = filtered
                }
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                val filteredList = results?.values as? List<Product> ?: emptyList()

                productList.clear()
                productList.addAll(filteredList)

                notifyDataSetChanged()
                onEmptyState?.invoke(filteredList.isEmpty())
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList: List<Product>) {
        productList.clear()
        productList.addAll(newList)

        fullList.clear()
        fullList.addAll(newList)

        notifyDataSetChanged()
    }
}