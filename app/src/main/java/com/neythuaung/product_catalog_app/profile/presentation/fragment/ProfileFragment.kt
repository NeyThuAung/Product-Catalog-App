package com.neythuaung.product_catalog_app.profile.presentation.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.neythuaung.product_catalog_app.MainActivity
import com.neythuaung.product_catalog_app.R
import com.neythuaung.product_catalog_app.core.presentation.dialog.ConfirmDialog
import com.neythuaung.product_catalog_app.core.utils.AppSharedPreference
import com.neythuaung.product_catalog_app.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(), ConfirmDialog.AlertButtonListener {

    private lateinit var binding: FragmentProfileBinding
    private val confirmDialog: ConfirmDialog by lazy {
        ConfirmDialog(requireActivity())
    }
    private val appSharedPreference: AppSharedPreference by lazy {
        AppSharedPreference(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mbLogout.setOnClickListener {
            confirmDialog.showDialog(
                getString(R.string.sure_to_log_out),
                getString(R.string.are_you_sure_you_want_to_log_out),
                img = R.drawable.ic_logout_red,
                tvOkayText = getString(R.string.logout),
                alertButtonListener = this@ProfileFragment
            )
        }
    }

    override fun onPositiveClick() {
        appSharedPreference.clear()

        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onCancelClick() {

    }
}