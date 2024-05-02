package com.example.pexelsapp.presentation.fragments

import com.example.pexelsapp.data.ImageDownloader
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.pexelsapp.R
import com.example.pexelsapp.databinding.FragmentDetailsBinding
import com.example.pexelsapp.domain.models.Photo
import com.example.pexelsapp.domain.usecases.DownloadImageUseCase
import com.example.pexelsapp.presentation.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()
    private val detailsMvvm: DetailsViewModel by viewModels()
    private var photoToSave: Photo? = null

    @Inject
    lateinit var imageDownloader: ImageDownloader

    @Inject
    lateinit var downloadImageUseCase: DownloadImageUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)

        loadingCase()
        detailsMvvm.getPhotoDetail(args.photoId)
        observePhotoDetailsLiveData()

        setFragmentDetails()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onDownloadClicked()
        onBookmarkClicked()
        onBackClicked()
    }

    private fun onBackClicked() {
        binding.backButton.setOnClickListener {
            if (args.dest == "home") {
                navigateTo(R.id.action_detailsFragment2_to_homeFragment22)
            } else {
                navigateTo(R.id.action_detailsFragment2_to_bookmarksFragment)
            }

        }
    }

    private fun navigateTo(resId: Int) {
        val navController = requireActivity().findNavController(R.id.fragmentContainerView)
        navController.navigate(resId)
    }

    private fun onBookmarkClicked() {
        binding.addToBookmarkButton.setOnClickListener {
            if (photoToSave?.liked == false) {
                photoToSave?.let {
                    it.liked = true
                    detailsMvvm.insertPhoto(it)
                }
                binding.addToBookmarkButton.setImageResource(R.drawable.bookmark_button_active)
                Toast.makeText(context, "Photo was saved. Press again to unsave", Toast.LENGTH_SHORT)
                    .show()
            } else {
                photoToSave?.let {
                    it.liked = false
                    detailsMvvm.deletePhoto(it)
                }

                binding.addToBookmarkButton.setImageResource(R.drawable.bookmark_button_inactive)
                Toast.makeText(context, "Photo was unsaved", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun onDownloadClicked() {
        binding.downloadButton.setOnClickListener {
            val imageUrl = args.photoUrl
            lifecycleScope.launch {
                val bitmap = downloadImageUseCase(imageUrl)
            }
        }
    }

    private fun setFragmentDetails() {
        val photographer = args.photographer
        val photo = args.photoUrl

        val rootView = requireActivity().findViewById<ViewGroup>(android.R.id.content)
        val elementToChangeVisibility = rootView.findViewById<View>(R.id.bottomNavigationView)
        elementToChangeVisibility.visibility = View.INVISIBLE

        if (args.dest == "home") {
            binding.addToBookmarkButton.setImageResource(R.drawable.bookmark_button_inactive)
        } else {
            binding.addToBookmarkButton.setImageResource(R.drawable.bookmark_button_active)
        }

        if (photo != null) {
            Glide.with(binding.photoImageView)
                .load(photo)
                .into(binding.photoImageView)
        } else {
            photoIsNotFound()
        }

        binding.nameSurname.text = photographer.toString()

    }

    private fun observePhotoDetailsLiveData() {
        detailsMvvm.observePhotoDetailLiveData()
            .observe(
                viewLifecycleOwner
            ) { value ->
                onResponseCase()
                val photo = value
                photoToSave = photo
            }
    }

    private fun photoIsNotFound() {
        binding.imageNotFound.visibility = View.VISIBLE
        loadingCase()
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun loadingCase() {
        with(binding) {
            progressBar.visibility = View.VISIBLE
            cardView.visibility = View.INVISIBLE
            constraintLayout2.visibility = View.INVISIBLE
        }
    }

    private fun onResponseCase() {
        with(binding) {
            progressBar.visibility = View.INVISIBLE
            cardView.visibility = View.VISIBLE
            constraintLayout2.visibility = View.VISIBLE
            imageNotFound.visibility = View.INVISIBLE
        }
    }
}