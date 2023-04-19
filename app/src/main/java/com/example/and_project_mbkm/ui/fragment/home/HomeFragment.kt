package com.example.and_project_mbkm.ui.fragment.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.and_project_mbkm.R
import com.example.and_project_mbkm.databinding.FragmentHomeBinding
import com.example.and_project_mbkm.ui.activity.Settings.SettingActivity
import com.example.and_project_mbkm.ui.activity.splash.SplashActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goToSetting()
        goToSpend()
    }

    private fun goToSpend(){
        binding.profileAdd.setOnClickListener(){
            findNavController().navigate(R.id.action_homeFragment3_to_spendingFragment)
        }
    }


    private fun goToSetting (){
        binding.setting.setOnClickListener(){
            findNavController().navigate(R.id.action_homeFragment3_to_settingFragment)
            }
        }
    }
