package com.example.pexelsapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pexelsapp.R
import com.example.pexelsapp.data.models.NetworkPhoto
import com.example.pexelsapp.databinding.FragmentHomeBinding
import com.example.pexelsapp.domain.models.Featured
import com.example.pexelsapp.presentation.converters.ModelConverter
import com.example.pexelsapp.domain.models.Photo
import com.example.pexelsapp.presentation.adapters.FeaturedItemAdapter
import com.example.pexelsapp.presentation.adapters.PhotoItemsAdapter
import com.example.pexelsapp.presentation.utils.Constants
import com.example.pexelsapp.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var featuredAdapter: FeaturedItemAdapter
    private lateinit var curatedPhotosAdapter: PhotoItemsAdapter
    private val viewModel: HomeViewModel by viewModels()
    private val featuredList = mutableListOf<Featured>()
    private var detailPhoto: Photo? = null

    private fun updateFeaturedItem(position: Int, featured: Featured) {
        featuredList[position] = featured
        featuredAdapter.notifyItemChanged(position)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        loadingCase()
        setFragmentDetails()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        featuredAdapter = FeaturedItemAdapter()
        curatedPhotosAdapter = PhotoItemsAdapter()

        prepareFragment()
        tryAgainClicked()
        exploreClicked()
        featuredClicked()
    }

    private fun exploreClicked() {
        binding.exploreHomeButton.setOnClickListener {
            viewModel.getCuratedPhotos()
            observeLiveData(
                viewModel.curatedPhotosLiveData,
                { photoList ->
                    curatedPhotosAdapter.setPhotos(photosList = photoList as ArrayList<NetworkPhoto>)
                },
                Constants.CURATED_PHOTOS
            )
        }
    }

    private fun prepareFragment() {
        prepareFeaturedRecyclerView()
        viewModel.getFeatured()
        observeLiveData(
            viewModel.featuredLiveData,
            { featuredList ->
                featuredAdapter.setFeatured(featuredItemsList = featuredList as ArrayList<Featured>)
            },
            Constants.FEATURED_PHOTOS
        )

        prepareCuratedPhotosRecyclerView()
        viewModel.getCuratedPhotos()
        observeLiveData(
            viewModel.curatedPhotosLiveData,
            { photoList ->
                curatedPhotosAdapter.setPhotos(photosList = photoList as ArrayList<NetworkPhoto>)
            },
            Constants.CURATED_PHOTOS
        )

        curatedPhotoClicked()
        observeLiveData(
            viewModel.errorLiveData,
            { handleNetworkStub() },
            Constants.ERROR
        )

        searchPhotos()
        observeLiveData(
            viewModel.searchPhotosLiveData,
            { photoList ->
                curatedPhotosAdapter.setPhotos(photosList = photoList as ArrayList<NetworkPhoto>)
            },
            Constants.SEARCH_PHOTOS
        )
    }

    private fun tryAgainClicked() {
        binding.tryAgainTextView.setOnClickListener {
            prepareFragment()
        }
    }

    private fun setFragmentDetails() {
        val visibilityChangeListener = requireActivity() as? VisibilityChangeListener
        visibilityChangeListener?.changeVisibility(View.VISIBLE)
    }

    private fun searchPhotos() {
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    viewModel.searchPhotos(query)
                    observeLiveData(
                        viewModel.searchPhotosLiveData,
                        { photoList ->
                            curatedPhotosAdapter.setPhotos(photosList = photoList as ArrayList<NetworkPhoto>)
                        },
                        Constants.SEARCH_PHOTOS
                    )
                } else {
                    viewModel.getCuratedPhotos()
                    observeLiveData(
                        viewModel.curatedPhotosLiveData,
                        { photoList ->
                            curatedPhotosAdapter.setPhotos(photosList = photoList as ArrayList<NetworkPhoto>)
                        },
                        Constants.CURATED_PHOTOS
                    )
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    viewModel.searchPhotos(newText)
                    observeLiveData(
                        viewModel.searchPhotosLiveData,
                        { photoList ->
                            curatedPhotosAdapter.setPhotos(photosList = photoList as ArrayList<NetworkPhoto>)
                        },
                        Constants.SEARCH_PHOTOS
                    )
                } else {
                    viewModel.getCuratedPhotos()
                    observeLiveData(
                        viewModel.curatedPhotosLiveData,
                        { photoList ->
                            curatedPhotosAdapter.setPhotos(photosList = photoList as ArrayList<NetworkPhoto>)
                        },
                        Constants.CURATED_PHOTOS
                    )
                }
                return true
            }
        })
    }

    private fun prepareFeaturedRecyclerView() {
        binding.featuredRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = featuredAdapter
        }
    }

    private fun prepareCuratedPhotosRecyclerView() {
        binding.imagesRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = curatedPhotosAdapter
        }
    }

    private fun curatedPhotoClicked() {
        curatedPhotosAdapter.onItemClick = { photo ->
            detailPhoto = ModelConverter.networkPhotoToDomainModel(photo)
            val navController = requireActivity().findNavController(R.id.fragmentContainerView)
            val action = photo.src?.let {
                photo.liked?.let { flag ->
                    HomeFragmentDirections.actionHomeFragment2ToDetailsFragment22(
                        photo.photographer.toString(),
                        it.medium,
                        flag,
                        photo.id,
                        Constants.DEST_HOME
                    )
                }
            }
            if (action != null) {
                navController.navigate(action)
            }
        }
    }

    private fun featuredClicked() {
        featuredAdapter.onItemClick = { featured ->
            if (featured.selected == true) {
                featured.selected = false
                viewModel.getCuratedPhotos()

                observeLiveData(
                    viewModel.curatedPhotosLiveData,
                    { photoList ->
                        curatedPhotosAdapter.setPhotos(photosList = photoList as ArrayList<NetworkPhoto>)
                    },
                    Constants.CURATED_PHOTOS
                )

                binding.searchView.setQuery(Constants.SEARCH_VIEW_BASE_QUERY, false)
            } else {
                featured.selected = true
                viewModel.searchPhotos(featured.title)

                observeLiveData(
                    viewModel.curatedPhotosLiveData,
                    { photoList ->
                        curatedPhotosAdapter.setPhotos(photosList = photoList as ArrayList<NetworkPhoto>)
                    },
                    Constants.CURATED_PHOTOS
                )

                binding.searchView.setQuery(featured.title, true)
            }
            val position = featuredList.indexOf(featured)
            if (position != -1) {
                updateFeaturedItem(position, featured)
            }
        }
    }

    private fun <T> observeLiveData(
        liveData: LiveData<T>,
        onDataReceived: (T) -> Unit,
        dataType: String
    ) {
        liveData.observe(viewLifecycleOwner) { data ->
            when (dataType) {
                Constants.FEATURED_PHOTOS -> {
                    onResponseCase()
                    onDataReceived(data)
                }

                Constants.CURATED_PHOTOS -> {
                    if (data == null) {
                        handleDataNotReceived()
                    } else {
                        onResponseCase()
                        onDataReceived(data)
                        handleDataReceived()
                    }
                }

                Constants.ERROR -> {
                    handleNetworkStub()
                }

                Constants.SEARCH_PHOTOS -> {
                    onResponseCase()
                    if (data == null) {
                        handleDataNotReceived()
                    }
                    onDataReceived(data)
                    handleDataReceived()
                }
            }
        }
    }

    private fun handleNetworkStub() {
        with(binding){
            networkStubImage.visibility = View.VISIBLE
            tryAgainTextView.visibility = View.VISIBLE
        }
        loadingCase()
    }

    private fun handleDataNotReceived() {
        binding.noResultsFoundTextView.visibility = View.VISIBLE
        binding.exploreHomeButton.visibility = View.VISIBLE
    }

    private fun handleDataReceived() {
        binding.noResultsFoundTextView.visibility = View.INVISIBLE
        binding.exploreHomeButton.visibility = View.INVISIBLE
    }

    private fun loadingCase() {
        with(binding) {
            progressBar.visibility = View.VISIBLE
            featuredRecyclerView.visibility = View.INVISIBLE
            imagesRecyclerView.visibility = View.INVISIBLE
        }
    }

    private fun onResponseCase() {
        with(binding) {
            progressBar.visibility = View.INVISIBLE
            binding.noResultsFoundTextView.visibility = View.INVISIBLE
            binding.exploreHomeButton.visibility = View.INVISIBLE
            featuredRecyclerView.visibility = View.VISIBLE
            imagesRecyclerView.visibility = View.VISIBLE
            networkStubImage.visibility = View.INVISIBLE
            tryAgainTextView.visibility = View.INVISIBLE
        }
    }
}