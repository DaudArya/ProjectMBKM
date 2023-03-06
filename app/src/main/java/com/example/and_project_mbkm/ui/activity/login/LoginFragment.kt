package com.example.and_project_mbkm.ui.activity.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.and_project_mbkm.R
import com.example.and_project_mbkm.data.network.model.auth.login.email.LoginRequestBody
import com.example.and_project_mbkm.databinding.FragmentLoginBinding
import com.example.and_project_mbkm.ui.activity.main.MainActivity
import com.example.and_project_mbkm.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.getLoginStatus().observe(viewLifecycleOwner) {
//            if (it == true) {
//                activity?.let { it ->
//                    val intent = Intent(it, MainActivity::class.java)
//                    it.startActivity(intent)}
//            } else {
//                requireContext()
//            }
//        }
        observeDataLogin()

        binding.toRegisterButton.setOnClickListener { openRegister() }
        binding.loginButton.setOnClickListener (){
            loginUser()
        }
    }



    private fun loginUser() {
        if (validateInput()) {

            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            binding.emailEditText.isEnabled = false
            binding.passwordEditText.isEnabled = false
            viewModel.login(parseFormIntoEntity(email, password))

        }
    }


    private fun observeDataLogin() {
        viewModel.postLoginUserResponse.observe(viewLifecycleOwner) {
            binding.emailEditText.isEnabled = true
            binding.passwordEditText.isEnabled = true
            when (it) {
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "${it.data?.metadata?.message}", Toast.LENGTH_LONG).show()
                    Log.d("loginResponse", it.data.toString())
                    viewModel.statusLogin(true)
                    navigateToHome()
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Login Gagal Silahkan Periksa Jaringan Internet Anda", Toast.LENGTH_LONG).show()

                }
                is Resource.Empty -> {
                    Toast.makeText(requireContext(), "Empty", Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }
        //datastore
        viewModel.getUserLoginStatus().observe(viewLifecycleOwner) {
            Log.d("getlogin", it.toString())
            if (it) {
                navigateToHome()
            }
        }
    }

    private fun navigateToHome() {
        viewModel.setUserLogin(true)
        activity?.let { it ->
            val intent = Intent(it, MainActivity::class.java)
            it.startActivity(intent)}
    }



    private fun parseFormIntoEntity(email: String, password: String): LoginRequestBody {
        return LoginRequestBody(email, password)
    }


    private fun validateInput(): Boolean {
        var isValid = true
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()

        if (email.isEmpty()) {
            isValid = false
            binding.emailEditText.error = "Email must not be empty"
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isValid = false
            binding.emailEditText.error = "Invalid email"
        }
        if (password.isEmpty()) {
            isValid = false
            Toast.makeText(requireContext(), "Password must not be empty", Toast.LENGTH_SHORT)
                .show()
        }
        if (password.length < 6) {
            isValid = false
            Toast.makeText(
                requireContext(),
                "Password should be at least 6 characters",
                Toast.LENGTH_SHORT
            ).show()
        }
        return isValid
    }

    private fun openRegister() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}














