package com.neythuaung.product_catalog_app.product.presentation.product_detail.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.neythuaung.product_catalog_app.R
import com.neythuaung.product_catalog_app.databinding.FragmentProductDetailsBinding
import com.neythuaung.product_catalog_app.product.presentation.view_model.ProductViewModel

class ProductDetailsFragment : Fragment() {
    private lateinit var binding: FragmentProductDetailsBinding
    private val viewModel: ProductViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val product = viewModel.productDetail
        binding.apply {
            if (!product.images.isNullOrEmpty()) {
                Glide.with(root)
                    .load(product.images[0])
                    .placeholder(R.color.card_background_gray)
                    .into(ivImage)
            }

//            val isFavorite = favoriteIds.contains(product.id!!.toInt())
//            ivFavorite.setImageResource(
//                if (isFavorite) {
//                    R.drawable.favourite_fill
//                } else {
//                    R.drawable.favourite
//                }
//            )

            tvName.text = product.title
            tvBrand.text = product.brand
            tvPrice.text = requireContext().getString(R.string._1_s, product.price.toString())
            tvBrand.isVisible = !product.brand.isNullOrEmpty()
            tvDesc.text = product.description

            tvRating.text = product.rating.toString()
            tvReview.text = requireContext().getString(R.string._1_s_reviews, product.reviews!!.count().toString())

            ivBack.setOnClickListener {
                findNavController().popBackStack()
            }

        }
    }

}