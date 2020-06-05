package com.example.myparkingspot.ui.profile

import android.content.Context
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

        val sharedPref = activity?.getSharedPreferences(
            getString(R.string.user_preference_file), Context.MODE_PRIVATE) ?: return

        binding.saveProfile.setOnClickListener{

            // Guardar los datos tanto en el view model como en el shared preferences.
            profileViewModel.setName(binding.nameInput.text.toString())
            profileViewModel.setSurname(binding.surnameInput.text.toString())
            profileViewModel.setId(binding.idInput.text.toString())
            profileViewModel.setPhone(binding.phoneInput.text.toString())

            profileViewModel.setManufacturer(binding.manufacturerInput.text.toString())
            profileViewModel.setModel(binding.modelInput.text.toString())
            profileViewModel.setPlate(binding.plateInput.text.toString())

            with (sharedPref.edit()) {
                putString(getString(R.string.label_name), profileViewModel.name.value)
                putString(getString(R.string.label_family_name), profileViewModel.surname.value)
                putString(getString(R.string.label_id), profileViewModel.id.value)
                putString(getString(R.string.label_phone), profileViewModel.phone.value)

                putString(getString(R.string.label_manufacturer), profileViewModel.manufacturer.value)
                putString(getString(R.string.label_model), profileViewModel.model.value)
                putString(getString(R.string.label_plate), profileViewModel.plate.value)

                commit()
            }

            this.findNavController().navigate(R.id.edit_profile_to_profile);
            Snackbar.make(it, getString(R.string.snack_profile_saved), Snackbar.LENGTH_SHORT).show();
        }
    }
}