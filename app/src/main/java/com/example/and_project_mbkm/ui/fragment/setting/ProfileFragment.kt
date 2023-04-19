package com.example.and_project_mbkm.ui.fragment.setting

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.and_project_mbkm.R
import com.example.and_project_mbkm.databinding.FragmentProfileBinding
import com.example.and_project_mbkm.ui.fragment.setting.model.User
import com.example.and_project_mbkm.ui.activity.auth.AuthActivity
import com.example.and_project_mbkm.wrapper.Extension.loadImage
import com.example.and_project_mbkm.wrapper.Extension.showLongToast
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import dagger.hilt.android.AndroidEntryPoint

data class ProfileState(
    val isLoading: Boolean = false,
    val error: String = "",
    val user: User? = null,
    val result: Int? = null
)

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var user: User? = null
    private val permissions = listOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SettingViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            if (result != null) {
                requireContext().loadImage(result, binding.profileImage)
            }
        }

    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val bitmap = result.data?.extras?.get("data") as Bitmap
                requireContext().loadImage(bitmap, binding.profileImage)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logout()
//        getUserEmail()
//        updateUser()
        moveToHome()
        chooseImage()
    }

    private fun logout() {
        binding.btnLogout.setOnClickListener {
            viewModel.statusLogin(false)
            viewModel.getLoginStatus().observe(viewLifecycleOwner) {
                if (it == true) {
                    activity?.let { it ->
                        val intent = Intent(it, AuthActivity::class.java)
                        it.startActivity(intent)}
                } else {
                    requireContext()
                }
            }
            }

        }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun getUserData() {
        viewModel.userData.observe(viewLifecycleOwner) { result ->
            showUserData(result.user)
            user = result.user
        }
    }

    private fun showUserData(user: User?) {
        binding.apply {
            usernameEditText.setText(user?.username)
            fullNameEditText.setText(user?.name)
            dateOfBirthEditText.setText(user?.dateOfBirth)
            addressEditText.setText(user?.address)
            profileImage.load(user?.profilePhoto)
        }
    }


    private fun moveToHome() {
        binding.toolbarId.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }





    private fun chooseImage() {
        binding.profileImage.setOnClickListener {
            cameraCheckPermission()
        }
    }

    private fun showRotationalDialogForPermission() {
        AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
            .setMessage("Required permission for this feature")
            .setPositiveButton("Go to settings") { _, _ ->
                try {
                    val intent = Intent()
                    val uri = Uri.fromParts("package", requireActivity().packageName, null)

                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    intent.data = uri

                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
    }

    private fun cameraCheckPermission() {
        Dexter.withContext(requireContext())
            .withPermissions(permissions)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(reports: MultiplePermissionsReport?) {
                    reports?.let {
                        if (reports.areAllPermissionsGranted()) {
                            chooseImageDialog()
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                    showRotationalDialogForPermission()
                }
            })
            .onSameThread()
            .withErrorListener {
                requireContext().showLongToast(it.name)
            }
            .check()
    }

    private fun chooseImageDialog() {
        AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
            .setMessage("Choose an Image")
            .setPositiveButton("Gallery") { _, _ -> gallery() }
            .setNegativeButton("Camera") { _, _ -> camera() }
            .show()
    }

    private fun gallery() {
        requireActivity().intent.type = "image/*"
        galleryResult.launch("image/*")
    }

    private fun camera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { cameraResult.launch(it) }
    }
}