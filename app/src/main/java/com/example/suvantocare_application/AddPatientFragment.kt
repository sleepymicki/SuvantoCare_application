package com.example.suvantocare_application

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.suvantocare_application.databinding.FragmentAddPatientBinding

class AddPatientFragment : Fragment() {

    private var _binding: FragmentAddPatientBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddPatientBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.savePatientButton.setOnClickListener {
            var name = binding.editTextPatientName.text.toString()
            var birthday = binding.editTextPatientBirthday.text.toString()
            var address = binding.editTextPatientAddress.text.toString()
            var disease = binding.editTextPatientDisease.text.toString()

            Log.d("ADTECH", "$name $birthday $address $disease")

            sendFeedback(name, birthday, address, disease)
        }

        addPicture()
        savePatient()

        return root
    }

    private fun sendFeedback(name: String, birthday: String, address: String, disease: String) {

    }

    private fun addPicture() {
        binding.addImageButton.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_addPatientFragment_to_cameraFragment)
        }
    }

    private fun savePatient() {
        binding.savePatientButton.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_addPatientFragment_to_listPatientFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}