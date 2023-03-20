package com.sigarda.jurnalkas.ui.fragment.setting

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.sigarda.jurnalkas.R
import com.sigarda.jurnalkas.databinding.FragmentProfileBinding
import com.sigarda.jurnalkas.model.User
import com.sigarda.jurnalkas.ui.activity.auth.AuthActivity
import com.sigarda.jurnalkas.ui.fragment.login.LoginViewModel
import com.sigarda.jurnalkas.ui.activity.main.MainActivity
import com.sigarda.jurnalkas.wrapper.Extension.loadImage
import com.sigarda.jurnalkas.wrapper.Extension.showLongToast
import com.sigarda.jurnalkas.wrapper.Resource
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

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

    private var image_uri: Uri? = null
    private var imageFile: File? = null
    private var imageMultiPart: MultipartBody.Part? = null

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SettingViewModel by viewModels()
    private val userViewModel: LoginViewModel by viewModels()

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
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeGet()
        moveToHome()
//        chooseImage()
        getProfile()
        updateProfile()
        logout()
    }

    private fun observeGet(){
        viewModel.GetProfileUserResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success ->{
                    Log.d("GetUserProfileResponse", it.data.toString())
                    binding.apply {
                        usernameEditText.setText("${it.data?.data?.username.toString()}")
                        fullNameEditText.setText("${it.data?.data?.name.toString()}")

                        Glide.with(requireContext())
                            .load(it.data?.data?.avatar)
                            .circleCrop()
                            .into(binding.profileImage)
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Reload Gagal : ObserveGet", Toast.LENGTH_LONG).show()

                }
                is Resource.Empty-> {
                    Toast.makeText(requireContext(), "Field : Empty", Toast.LENGTH_LONG).show()
                }
                else -> {}
            }

        }
    }

    private fun getProfile() {
        userViewModel.getDataStoreToken().observe(viewLifecycleOwner) {
            viewModel.GetProfileUser("Bearer $it")
        }
        viewModel.data.observe(viewLifecycleOwner) {
            binding.apply {
                if (it != null) {
                    usernameEditText.setText("${it.data?.username.toString()}")
                    fullNameEditText.setText("${it.data?.name.toString()}")

                    Glide.with(requireContext())
                        .load(it.data?.avatar)
                        .circleCrop()
                        .into(binding.profileImage)
                }
            }
        }
        userViewModel.postLoginUserResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success ->{
                    val token = "${it.data?.response?.token}"
                    if (token != ""){
                        Log.d("TokenResponse", it.data.toString())
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Reload Gagal : GetProfile", Toast.LENGTH_LONG).show()
                }
                is Resource.Empty-> {
                    Toast.makeText(requireContext(), "Field : Empty", Toast.LENGTH_LONG).show()
                }

                else -> {} }
        }}

    private fun updateProfile(){
        userViewModel.getDataStoreToken().observe(viewLifecycleOwner) {
            viewModel.GetProfileUser("Bearer $it")
        }

        binding.updateButton.setOnClickListener {

            val username = binding.usernameEditText.text.toString().trim()
                .toRequestBody("multipart/form-data".toMediaType())
            val fullName = binding.fullNameEditText.text.toString().trim()
                .toRequestBody("multipart/form-data".toMediaType())
            userViewModel.getDataStoreToken().observe(viewLifecycleOwner) {
                viewModel.updateUser(fullName,username,imageMultiPart!!,"Bearer $it")
            }
            Toast.makeText(requireContext(), "Update Success", Toast.LENGTH_SHORT).show()
            activity?.let { it ->
                val intent = Intent(it, MainActivity::class.java)
                it.startActivity(intent)}
        }

        viewModel.data.observe(viewLifecycleOwner) {
            binding.apply {
                if (it != null) {
                    fullNameEditText.setText(it.data?.name)
                    usernameEditText.setText(it.data?.username)
                }
            }
        }
        openGallery()
    }

    private fun chooseImage() {
        binding.profileImage.setOnClickListener {
            cameraCheckPermission()
        }
    }

    private fun moveToHome() {
        binding.toolbarId.setNavigationOnClickListener {
            activity?.let { it ->
                val intent = Intent(it, MainActivity::class.java)
                it.startActivity(intent)}
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

    fun openGallery() {
        binding.buttonPicture.setOnClickListener {
            changePicture.launch("image/*")
        }
    }

    private val changePicture =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                val contentResolver: ContentResolver = requireContext().contentResolver
                val type = contentResolver.getType(it)
                image_uri = it

                val fileNameimg = "${System.currentTimeMillis()}.png"
                binding.profileImage.setImageURI(it)
                Toast.makeText(requireContext(), "$image_uri", Toast.LENGTH_SHORT).show()

                val tempFile = File.createTempFile("OtakKanan-", fileNameimg, null)
                imageFile = tempFile
                val inputstream = contentResolver.openInputStream(uri)
                tempFile.outputStream().use { result ->
                    inputstream?.copyTo(result)
                }
                val requestBody: RequestBody = tempFile.asRequestBody(type?.toMediaType())
                imageMultiPart =
                    MultipartBody.Part.createFormData("image", tempFile.name, requestBody)
            }
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
