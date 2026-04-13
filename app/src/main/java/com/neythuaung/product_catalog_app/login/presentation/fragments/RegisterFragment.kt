package com.neythuaung.product_catalog_app.login.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.neythuaung.product_catalog_app.core.utils.isStrongPassword
import com.neythuaung.product_catalog_app.core.utils.isValidEmail
import com.neythuaung.product_catalog_app.databinding.FragmentRegisterBinding
import com.neythuaung.product_catalog_app.login.presentation.utils.goToProductActivity


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var email: String
    private lateinit var password: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
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

            etPassword.doAfterTextChanged {
                if (it.toString().isNotEmpty()) {
                    tilConfirmPassword.isErrorEnabled = false
                    tilConfirmPassword.error = null
                }
            }
        }
    }

    private fun onClickEvents() {

        binding.apply {
            ivBack.setOnClickListener {
                findNavController().navigateUp()
            }

            mbSignUp.setOnClickListener {
                email = etEmail.text.toString()
                password = etConfirmPassword.text.toString()

                if (checkAllField()) {
                    requireContext().goToProductActivity()
                    requireActivity().finish()
//                signUpUserAccount(email, password)
                }
            }

            mbSignUpWithGoogle.setOnClickListener {
                requireContext().goToProductActivity()
                requireActivity().finish()
            }

            llAlreadyAccount.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun checkAllField(): Boolean {
        var isValid = true

        binding.apply {
            if (etEmail.text.toString().isEmpty()) {
                tilEmail.isErrorEnabled = true
                tilEmail.error = "Enter email address."
                isValid = false
            } else if (!etEmail.text.toString().isValidEmail()) {
                tilEmail.isErrorEnabled = true
                tilEmail.error = "Enter a valid email address."
                isValid = false
            } else {
                tilEmail.isErrorEnabled = false
            }

            if (etPassword.text.toString().isEmpty()) {
                tilPassword.isErrorEnabled = true
                tilPassword.error = "Enter passwrod."
                isValid = false
            } else if (etPassword.text.toString().trim().length <= 6) {
                tilPassword.isErrorEnabled = true
                tilPassword.error = "Password should be at least 8 characters long."
                isValid = false
            } else if (!isStrongPassword(etPassword.text.toString())) {
                tilPassword.isErrorEnabled = true
                tilPassword.error = "Password should contain A-Z, a-z, 0-9,\n" +
                        "Special characters (e.g., !, @, #, \$, %, ^, &, *, etc.)"
                isValid = false
            } else {
                tilPassword.isErrorEnabled = false
            }

            if (etConfirmPassword.text.toString().isEmpty()) {
                tilConfirmPassword.isErrorEnabled = true
                tilConfirmPassword.error = "This is required field."
                isValid = false
            } else if (etConfirmPassword.text.toString() != etPassword.text.toString()) {
                tilConfirmPassword.isErrorEnabled = true
                tilConfirmPassword.error = "Password does not match."
                isValid = false
            } else {
                tilConfirmPassword.isErrorEnabled = false
            }
        }

        return isValid

    }


}