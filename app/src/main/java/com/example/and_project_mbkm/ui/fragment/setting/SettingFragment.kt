package com.example.and_project_mbkm.ui.fragment.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.and_project_mbkm.R
import com.example.and_project_mbkm.databinding.FragmentHomeBinding
import com.example.and_project_mbkm.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        var cv1 = binding.cv1
//        cv1.setElevation(8F)
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goToPremium()
    }

    private fun goToPremium(){
        binding.premium.setOnClickListener(){
            findNavController().navigate(R.id.action_settingFragment_to_premiumFragment2)
        }
    }


}