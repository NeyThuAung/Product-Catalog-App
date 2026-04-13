package com.neythuaung.product_catalog_app.product.presentation.favourite_products.fragments.adapter

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

class FavouriteProductListAdapter(
    private val productList: ArrayList<FavoriteProductEntity>,
    private val onFavouriteProductClick: (FavoriteProductEntity) -> Unit,
) : RecyclerView.Adapter<FavouriteProductListAdapter.ProductListHolder>() {

    inner class ProductListHolder(
        private val binding: ProductItemCardViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: FavoriteProductEntity) {
            binding.apply {

                Glide.with(itemView)
                    .load(product.thumbnail)
                    .placeholder(R.color.inactive)
                    .into(ivImage)

                ivFavorite.setImageResource(
                    R.drawable.favourite_fill
                )

                tvName.text = product.title
                tvBrand.text = product.brand
                tvPrice.text = itemView.context.getString(R.string._1_s, product.price.toString())
                tvBrand.isVisible = !product.brand.isNullOrEmpty()

                ivFavorite.setOnClickListener {
                    onFavouriteProductClick(product)
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

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList: List<FavoriteProductEntity>) {
        productList.clear()
        productList.addAll(newList)

        notifyDataSetChanged()
    }
}