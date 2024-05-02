package com.example.pexelsapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pexelsapp.R
import com.example.pexelsapp.databinding.FragmentHomeBinding
import com.example.pexelsapp.domain.models.Featured
import com.example.pexelsapp.domain.models.Photo
import com.example.pexelsapp.presentation.adapters.FeaturedItemAdapter
import com.example.pexelsapp.presentation.adapters.PhotoItemsAdapter
import com.example.pexelsapp.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var detailPhoto: Photo
    @Inject
    lateinit var featuredAdapter: FeaturedItemAdapter
    @Inject
    lateinit var curatedPhotosAdapter: PhotoItemsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        prepareFragment()
        tryAgainClicked()
        exploreClicked()
        featuredClicked()
    }

    private fun exploreClicked() {
        binding.explore.setOnClickListener {
            viewModel.getCuratedPhotos()
            observeCuratedPhotosLiveData()
        }
    }

    private fun prepareFragment() {
        prepareFeaturedRecyclerView()
        viewModel.getFeatured()
        observeFeaturedLiveData()

        prepareCuratedPhotosRecyclerView()
        viewModel.getCuratedPhotos()
        observeCuratedPhotosLiveData()
        curatedPhotoClicked()
        observeErrorLiveData()

        searchPhotos()
        observeSearchPhotosLiveData()
    }

    private fun tryAgainClicked() {
        binding.tryAgainTextView.setOnClickListener {
            prepareFragment()
        }
    }

    private fun setFragmentDetails() {
        val rootView = requireActivity().findViewById<ViewGroup>(android.R.id.content)
        val elementToChangeVisibility = rootView.findViewById<View>(R.id.bottomNavigationView)
        elementToChangeVisibility.visibility = View.VISIBLE
    }
    
    private fun searchPhotos() {
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.searchPhotos(query)
                    observeSearchPhotosLiveData()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    viewModel.searchPhotos(newText)
                    observeSearchPhotosLiveData()
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
            detailPhoto = photo
            val navController = requireActivity().findNavController(R.id.fragmentContainerView)
            val action = photo.src?.let {
                photo.liked?.let { it1 ->
                    HomeFragmentDirections.actionHomeFragment2ToDetailsFragment22(
                        photo.photographer.toString(),
                        it.medium,
                        it1,
                        photo.id,
                        "home"
                    )
                }
            }
            if (action != null) {
                navController.navigate(action)
            }
        }
    }

    private fun featuredClicked() {
        featuredAdapter.onItemClick = {featured->
            if(featured.selected == true){
                featured.selected  = false
                viewModel.getCuratedPhotos()
                observeCuratedPhotosLiveData()
                binding.searchView.setQuery("", false)
            }else{
                featured.selected = true
                viewModel.searchPhotos(featured.title)
                observeCuratedPhotosLiveData()
                binding.searchView.setQuery(featured.title, true)
            }
            featuredAdapter.notifyDataSetChanged()
        }
    }

    private fun observeFeaturedLiveData() {
        viewModel.observeFeaturedLiveData().observe(
            viewLifecycleOwner
        ) { featuredList ->
            onResponseCase()
            featuredAdapter.setFeatured(featuredItemsList = featuredList as ArrayList<Featured>)
        }
    }

    private fun observeCuratedPhotosLiveData() {
        viewModel.observeCuratedPhotosLiveData().observe(
            viewLifecycleOwner
        ) { photoList ->
            if (photoList.isNullOrEmpty()) {
                handleDataNotReceived()
            } else {
                onResponseCase()
                curatedPhotosAdapter.setPhotos(photosList = photoList as ArrayList<Photo>)
            }
        }
    }

    private fun observeErrorLiveData() {
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            handleNetworkStub()
        }
    }

    private fun handleNetworkStub() {
        binding.layoutNetworkStub.visibility = View.VISIBLE
        loadingCase()
    }

    private fun handleDataNotReceived(){
        binding.noResultsFound.visibility = View.VISIBLE
    }

    private fun observeSearchPhotosLiveData() {
        viewModel.observeSearchPhotosLiveData().observe(
            viewLifecycleOwner
        ) {photoList->
            onResponseCase()
            if(photoList.isEmpty()){
                binding.noResultsFound.visibility = View.VISIBLE
            }
            curatedPhotosAdapter.setPhotos(photosList = photoList as ArrayList<Photo>)
        }
    }

    private fun loadingCase() {
        with(binding){
            progressBar.visibility = View.VISIBLE
            featuredRecyclerView.visibility = View.INVISIBLE
            imagesRecyclerView.visibility = View.INVISIBLE
        }
    }

    private fun onResponseCase() {
        with(binding){
            progressBar.visibility = View.INVISIBLE
            noResultsFound.visibility = View.INVISIBLE
            featuredRecyclerView.visibility = View.VISIBLE
            imagesRecyclerView.visibility = View.VISIBLE
            layoutNetworkStub.visibility = View.INVISIBLE
        }
    }

}