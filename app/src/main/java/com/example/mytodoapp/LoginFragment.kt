package com.example.mytodoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.example.mytodoapp.databinding.FragmentLoginBinding
import com.example.mytodoapp.entities.User
import com.example.mytodoapp.models.UserModel
import com.example.mytodoapp.prefdata.LoginPreference
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private val modelView : UserModel by activityViewModels()
    private lateinit var binding : FragmentLoginBinding
    private lateinit var loginPreference: LoginPreference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginPreference = LoginPreference(requireContext())
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        modelView.errorMessageLD.observe(viewLifecycleOwner){
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        }
        binding.loginButton.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()
            modelView.login(email,password){
                lifecycle.coroutineScope.launch {
                    loginPreference.setLoginStatus(true, it, requireContext())
                    findNavController().popBackStack()
                }
            }
        }

        binding.registerButton.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()
            val user = User(
                email = email,
                password = password
            )
            modelView.register(user){
                lifecycle.coroutineScope.launch {
                    loginPreference.setLoginStatus(true, it, requireContext())
                    findNavController().popBackStack()
                }
            }
        }
        return binding.root
    }

}