package com.example.pexelsapp.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pexelsapp.R
import com.example.pexelsapp.data.models.DBPhoto
import com.example.pexelsapp.databinding.ActivityMainBinding
import com.example.pexelsapp.databinding.FragmentBookmarksBinding
import com.example.pexelsapp.domain.models.Photo
import com.example.pexelsapp.presentation.adapters.BookmarkItemAdapter
import com.example.pexelsapp.presentation.utils.Constants
import com.example.pexelsapp.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BookmarksFragment : Fragment() {

    private lateinit var binding: FragmentBookmarksBinding
    private lateinit var bookmarkPhotosAdapter: BookmarkItemAdapter
    private val viewModel: HomeViewModel by viewModels()
    private var detailPhoto: DBPhoto? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookmarksBinding.inflate(inflater, container, false)

        setFragmentDetails()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookmarkPhotosAdapter = BookmarkItemAdapter()

        prepareCuratedPhotosRecyclerView()
        observeBookmarkLiveData()
        bookmarksPhotoClicked()
        exploreClicked()
    }

    private fun exploreClicked() {
        binding.exploreButton.setOnClickListener {
            val action = BookmarksFragmentDirections.actionBookmarksFragmentToHomeFragment2()
            val navController = requireActivity().findNavController(R.id.fragmentContainerView)
            navController.navigate(action)
        }
    }

    private fun setFragmentDetails() {
        val visibilityChangeListener = requireActivity() as? VisibilityChangeListener
        visibilityChangeListener?.changeVisibility(View.VISIBLE)
    }

    private fun prepareCuratedPhotosRecyclerView() {
        binding.bookmarkRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = bookmarkPhotosAdapter
        }
    }

    private fun bookmarksPhotoClicked() {
        bookmarkPhotosAdapter.onItemClick = { photo ->
            detailPhoto = photo
            val navController = requireActivity().findNavController(R.id.fragmentContainerView)
            val action = photo.src?.let {
                photo.liked?.let { flag ->
                    BookmarksFragmentDirections.actionBookmarksFragmentToDetailsFragment2(
                        photo.photographer.toString(),
                        it.medium,
                        flag,
                        photo.id,
                        Constants.DEST_BOOKMARK
                    )
                }
            }
            if (action != null) {
                navController.navigate(action)
            }
        }
    }

    private fun observeBookmarkLiveData() {
        viewModel.bookmarkLiveData.observe(viewLifecycleOwner, Observer { photos ->
            bookmarkPhotosAdapter.setPhotos(photosList = photos as ArrayList<DBPhoto>)
            if (photos.isEmpty()) {
                binding.anythingHaventSaved.visibility = View.VISIBLE
            } else {
                binding.anythingHaventSaved.visibility = View.INVISIBLE
            }
        })
    }
}