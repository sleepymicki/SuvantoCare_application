package com.example.suvantocare_application

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.ImageCapture
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import com.example.suvantocare_application.databinding.FragmentCameraBinding
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

typealias LumaListener = (luma: Double) -> Unit

class CameraFragment : Fragment() {
    private var imageCapture: ImageCapture? = null

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    private lateinit var binding: FragmentCameraBinding

    private lateinit var thiscontext: Context
    private lateinit var bundle: Bundle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        super.onCreate(savedInstanceState)
        binding = FragmentCameraBinding.inflate(layoutInflater)
        val view = binding.root
        //setContentView(view)
        // Request camera permissions

        thiscontext = container!!.context

        // Set up the listener for take photo button
        binding.cameraButton.setOnClickListener { takePhoto() }

        outputDirectory = getOutputDirectory()

        cameraExecutor = Executors.newSingleThreadExecutor()

//        val rootView = inflater.inflate(R.layout.fragment_camera, container, false)
//
//        communicator = activity as Communicator
//        rootView.take_pic_button.setOnClickListener {
//            communicator.passData(rootView.viewFinder.text.toString())
//        }

        return view
    }

    private fun takePhoto() {
        binding.cameraButton.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_cameraFragment_to_addPatientFragment)
        }
    }

    private fun getOutputDirectory(): File {
        val mediaDir = activity?.externalMediaDirs?.firstOrNull()?.let {
            File(it, "SuvantoCare_app").apply { mkdirs() } }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else activity?.filesDir!!
    }
}