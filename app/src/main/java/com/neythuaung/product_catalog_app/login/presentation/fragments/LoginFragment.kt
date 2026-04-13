package com.neythuaung.product_catalog_app.login.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.neythuaung.product_catalog_app.R
import com.neythuaung.product_catalog_app.config.StorageConfig
import com.neythuaung.product_catalog_app.core.utils.AppSharedPreference
import com.neythuaung.product_catalog_app.core.utils.showCustomSnackBar
import com.neythuaung.product_catalog_app.databinding.FragmentLoginBinding
import com.neythuaung.product_catalog_app.login.presentation.LoginViewModel
import com.neythuaung.product_catalog_app.login.presentation.utils.goToProductActivity
import kotlinx.coroutines.flow.collectLatest


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by activityViewModels()
    private val appSharedPreference: AppSharedPreference by lazy {
        AppSharedPreference(requireContext())
    }
    private lateinit var username: String
    private lateinit var password: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doAfterTextChange()
        onClickEvents()
    }

    private fun doAfterTextChange() {

        binding.apply {
            etEmail.doAfterTextChanged {
                if (it.toString().isNotEmpty()) {
                    tilEmail.isErrorEnabled = false
                    tilEmail.error = null
                }
            }

            etPassword.doAfterTextChanged {
                if (it.toString().isNotEmpty()) {
                    tilPassword.isErrorEnabled = false
                    tilPassword.error = null
                }
            }
        }
    }

    private fun onClickEvents() {
        binding.apply {
            tvForgetPassword.setOnClickListener {

            }

            mbSignUpWithGoogle.setOnClickListener {
            }

            mbLogin.setOnClickListener {
                username = binding.etEmail.text.toString()
                password = binding.etPassword.text.toString()

                if (isValidateAllField()) {
                    viewModel.login(username, password, onError = {
                        showCustomSnackBar(binding.root, "$it . Please try again.", true)
                        binding.apply {
                            mbLogin.text = "Login"
                            progressLoad.visibility = View.INVISIBLE
                        }
                    })
                    logIn()
                }
            }

            llRegisterHere.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }

    }

    private fun logIn() {
        lifecycleScope.launchWhenStarted {
            viewModel.loginState.collectLatest { state ->
                when {
                    state.isLoading -> {
                        binding.apply {
                            mbLogin.text = null
                            progressLoad.visibility = View.VISIBLE
                        }
                    }

                    else -> {
                        Log.d("LJKJL", "logIn: ${state.success}")
                        appSharedPreference.saveString(
                            StorageConfig.TOKEN,
                            state.success.accessToken.toString()
                        )
                        appSharedPreference.saveString(
                            StorageConfig.REFRESH_TOKEN,
                            state.success.refreshToken.toString()
                        )
                        requireContext().goToProductActivity()
                        requireActivity().finish()
                    }
                }
            }
        }
    }


    private fun isValidateAllField(): Boolean {
        var isValid = true

        binding.apply {
            if (etEmail.text.toString().isEmpty()) {
                tilEmail.isErrorEnabled = true
                tilEmail.error = "Enter username."
                isValid = false
            } else {
                tilEmail.isErrorEnabled = false
            }

            if (etPassword.text.toString().isEmpty()) {
                tilPassword.isErrorEnabled = true
                tilPassword.error = "Enter passwrod."
                isValid = false
            } else {
                tilPassword.isErrorEnabled = false
            }
        }

        return isValid

    }

}