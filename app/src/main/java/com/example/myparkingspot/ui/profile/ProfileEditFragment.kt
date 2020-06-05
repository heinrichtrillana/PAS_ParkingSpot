package com.example.myparkingspot.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.myparkingspot.R
import com.example.myparkingspot.databinding.FragmentEditProfileBinding
import com.google.android.material.snackbar.Snackbar

class ProfileEditFragment : Fragment(){

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var binding : FragmentEditProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_edit_profile, container, false)

        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        profileViewModel = activity?.run {
            ViewModelProviders.of(this).get(ProfileViewModel::class.java);
        }?: throw Exception("Invalid Activity")

        binding.profileViewModel = profileViewModel;

        binding.saveProfile.setOnClickListener{

            Log.i("probando", binding.probandoxd.text.toString())
            profileViewModel.setName(binding.probandoxd.text.toString())
            Log.i("probando", profileViewModel.name.value )

            this.findNavController().navigate(R.id.edit_profile_to_profile);
            Snackbar.make(it, "SAVED", Snackbar.LENGTH_SHORT).show();
        }
    }
}