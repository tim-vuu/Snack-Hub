package com.application.snackhub.fragments.applunch

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.application.snackhub.R
import com.application.snackhub.databinding.FragmentFirstScreenBinding
import com.application.snackhub.util.Constants.Companion.SHOULD_SHOW
import com.application.snackhub.util.Constants.Companion.SPLASH_SHARED_PREF

class FirstScreenFragment : Fragment() {
    private lateinit var binding: FragmentFirstScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val shouldShowIntroductionFragment =
            requireActivity().getSharedPreferences(SPLASH_SHARED_PREF, Context.MODE_PRIVATE) //changed from activty to requierdActivty
                .getBoolean(SHOULD_SHOW, true)


        if (!shouldShowIntroductionFragment)
            findNavController().navigate(R.id.action_firstScreenFragment_to_secondScreenFragment)

        else
            binding.btnFirstscreen.setOnClickListener {

                findNavController().navigate(R.id.action_firstScreenFragment_to_secondScreenFragment)

                val sharedPref =
                    requireActivity().getSharedPreferences(SPLASH_SHARED_PREF, Context.MODE_PRIVATE)
                sharedPref.edit().putBoolean(SHOULD_SHOW, false).apply()
            }
    }
}