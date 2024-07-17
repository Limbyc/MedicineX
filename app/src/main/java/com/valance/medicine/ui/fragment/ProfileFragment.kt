package com.valance.medicine.ui.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.valance.medicine.R
import com.valance.medicine.databinding.ProfileFragmentBinding
import com.valance.medicine.domain.usecase.GetUserInfoUseCase
import com.valance.medicine.domain.usecase.SaveUserInfoUseCase
import com.valance.medicine.data.userInfoDataStore
import com.valance.medicine.ui.ImageHelper
import kotlinx.coroutines.launch
import java.io.IOException

class ProfileFragment : Fragment() {

    private lateinit var binding: ProfileFragmentBinding
    private lateinit var imageHelper: ImageHelper
    private lateinit var getUserInfoUseCase: GetUserInfoUseCase
    private lateinit var saveUserInfoUseCase: SaveUserInfoUseCase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfileFragmentBinding.inflate(inflater, container, false)
        imageHelper = ImageHelper()

        getUserInfoUseCase = GetUserInfoUseCase(requireContext().userInfoDataStore)
        saveUserInfoUseCase = SaveUserInfoUseCase(requireContext().userInfoDataStore)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            val userInfo = getUserInfoUseCase.execute()
            val userId = userInfo.id
            val userPhone = userInfo.userPhone

            activity?.runOnUiThread {
                binding.IdUser.text = "ID: $userId"
                binding.PhoneUser.text = "Phone: $userPhone"
            }

            if (userId != null && userPhone != null) {
                saveUserInfoToDataStore(userId, userPhone)
            }

            val bitmap = imageHelper.loadImageFromPath(requireContext())
            bitmap?.let { binding.UserPhoto.setImageBitmap(it) }
        }

        binding.cardView.setOnClickListener {
            pickPhoto()
        }

        binding.AddInfoAboutUser.setOnClickListener {
            findNavController().navigate(R.id.userInfoFragment)
        }
    }

    private fun pickPhoto() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_MEDIA_IMAGES
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.READ_MEDIA_IMAGES), 1)
        } else {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, 2)
        }
    }

    @Deprecated("Use pickPhotoWithPermissionCheck() instead.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            val pickedPhotoUri: Uri? = data.data
            pickedPhotoUri?.let { uri ->
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
                    binding.UserPhoto.setImageBitmap(bitmap)

                    val imagePath = imageHelper.saveImageToInternalStorage(requireContext(), bitmap)
                    imageHelper.saveImagePathToSharedPreferences(requireContext(), imagePath)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun saveUserInfoToDataStore(userId: String, phone: String) {
        lifecycleScope.launch {
            saveUserInfoUseCase.execute(userId, phone)
            Log.d("ProfileFragment", "Saved user info: ID: $userId, Phone: $phone")
        }
    }
}
