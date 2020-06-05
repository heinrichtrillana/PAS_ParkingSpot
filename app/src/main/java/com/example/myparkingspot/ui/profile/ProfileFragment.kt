package com.example.myparkingspot.ui.profile

import android.content.Context
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
    private lateinit var binding : FragmentProfileBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_profile, container, false)

        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        profileViewModel = activity?.run {
            ViewModelProviders.of(this).get(ProfileViewModel::class.java);
        }?: throw Exception("Invalid Activity")

        val sharedPref = activity?.getSharedPreferences(
            getString(R.string.user_preference_file), Context.MODE_PRIVATE) ?: return

        binding.profileViewModel = profileViewModel;
        //Rellenar el view model con los datos del Shared Preferences.
        profileViewModel.setName(sharedPref.getString(getString(R.string.label_name),"")!!)
        profileViewModel.setSurname(sharedPref.getString(getString(R.string.label_family_name),"")!!)
        profileViewModel.setId(sharedPref.getString(getString(R.string.label_id),"5")!!)
        profileViewModel.setPhone(sharedPref.getString(getString(R.string.label_phone),"4")!!)

        profileViewModel.setManufacturer(sharedPref.getString(getString(R.string.label_manufacturer),"3")!!)
        profileViewModel.setModel(sharedPref.getString(getString(R.string.label_model),"2")!!)
        profileViewModel.setPlate(sharedPref.getString(getString(R.string.label_plate),"1")!!)

        //Al clickar en editar perfil, navego al fragmento correspondiente.
        binding.editProfileButton.setOnClickListener{
            this.findNavController().navigate(R.id.profile_to_edit_profile);
        }
    }
}
