@file:OptIn(ExperimentalMaterial3Api::class)

package com.valance.medicine.ui.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.valance.medicine.data.userInfoDataStore
import com.valance.medicine.domain.usecase.GetUserInfoUseCase
import com.valance.medicine.domain.usecase.SaveUserInfoUseCase
import com.valance.medicine.ui.ImageHelper
import com.valance.petproject.ui.navigation.BottomNav
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var imageHelper: ImageHelper
    private lateinit var getUserInfoUseCase: GetUserInfoUseCase
    private lateinit var saveUserInfoUseCase: SaveUserInfoUseCase

    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    private var userId by mutableStateOf<String?>(null)
    private var userPhone by mutableStateOf<String?>(null)
    private var imageUri by mutableStateOf<Uri?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imageHelper = ImageHelper()
        getUserInfoUseCase = GetUserInfoUseCase(requireContext().userInfoDataStore)
        saveUserInfoUseCase = SaveUserInfoUseCase(requireContext().userInfoDataStore)

        pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data
                uri?.let { processImageUri(it) }
            }
        }

        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openGallery()
            } else {
                Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

        lifecycleScope.launch {
            val userInfo = getUserInfoUseCase.execute()
            userId = userInfo.id
            userPhone = userInfo.userPhone
            loadImageAsync()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
//                ProfileScreen()
            }
        }
    }

    @Composable
    fun ProfileScreen(
        imageUri: Uri?,
        userId: String?,
        userPhone: String?,
        onPickImage: () -> Unit,
        onNavigateToUserInfo: () -> Unit
    ) {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Profile") })
            },
            bottomBar = {
                BottomNav()
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    Card(
                        modifier = Modifier
                            .size(100.dp)
                            .clickable { onPickImage() },
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        imageUri?.let {
                            Box(modifier = Modifier.background(Color.Gray).fillMaxSize()) {
                                Text("Image", modifier = Modifier.align(Alignment.Center))
                            }
                        } ?: run {
                            Box(modifier = Modifier.background(Color.Gray).fillMaxSize()) {
                                Text("Add Photo", modifier = Modifier.align(Alignment.Center))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "User ID: ${userId ?: "N/A"}")
                    Text(text = "Phone: ${userPhone ?: "N/A"}")

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(onClick = { onNavigateToUserInfo() }) {
                        Text("Add Info About User")
                    }
                }
            }
        }
    }


    private fun pickImage() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED -> {
                openGallery()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageLauncher.launch(intent)
    }

    private fun processImageUri(uri: Uri) {
        lifecycleScope.launch {
            val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
            val imagePath = imageHelper.saveImageToInternalStorage(requireContext(), bitmap)
            imageHelper.saveImagePathToProto(requireContext(), imagePath) { success ->
                val message = if (success) "Image path saved successfully" else "Failed to save image path"
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
            imageUri = uri // Update the image URI state
        }
    }

    private fun loadImageAsync() {
        imageHelper.loadImageFromPath(requireContext()) { bitmap ->
            lifecycleScope.launch(Dispatchers.Main) {

            }
        }
    }
}


