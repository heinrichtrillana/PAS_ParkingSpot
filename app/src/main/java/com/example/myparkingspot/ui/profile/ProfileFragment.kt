package com.example.myparkingspot.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.myparkingspot.R
import com.example.myparkingspot.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.actionBar?.setDisplayHomeAsUpEnabled(true);
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentProfileBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_profile, container, false)

        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        binding.profileViewModel = profileViewModel;

        //Al clickar en editar perfil, navego al fragmento correspondiente.
        binding.editProfileButton.setOnClickListener{
            this.findNavController().navigate(R.id.profile_to_edit_profile);
        }

        return binding.root
    }
}
