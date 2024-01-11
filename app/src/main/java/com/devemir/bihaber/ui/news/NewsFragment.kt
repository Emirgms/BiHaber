package com.devemir.bihaber.ui.news


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels


import androidx.lifecycle.lifecycleScope

import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState

import androidx.recyclerview.widget.LinearLayoutManager


import com.devemir.bihaber.adapter.NewsPagingDataAdapter
import com.devemir.bihaber.data.model.Articles
import com.devemir.bihaber.databinding.FragmentNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

import kotlinx.coroutines.launch



@AndroidEntryPoint
class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private val newsViewModel: NewsViewModel by viewModels()
    private lateinit var pagingDataAdapter: NewsPagingDataAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pagingDataAdapter = NewsPagingDataAdapter()

    }

    private fun getNewsState() {
        lifecycleScope.launch {
            newsViewModel.pagingNews.collectLatest { pagingData ->
                pagingDataAdapter.submitData(pagingData)
            }
        }
        lifecycleScope.launch {
            pagingDataAdapter.loadStateFlow.collectLatest { loadState ->
                if (loadState.refresh is LoadState.Loading) {
                    showProgressBar()
                } else {
                    hideProgressBar()
                }
            }
        }
    }


    private fun initRecycler() {
        pagingDataAdapter.onItemClickListener = object : ((Articles) -> Unit) {
            override fun invoke(article: Articles) {
                article.url?.let {
                    val action =
                        NewsFragmentDirections.actionNewsFragmentToWebViewFragment(article)
                    findNavController().navigate(action)
                }

            }
        }
        pagingDataAdapter.onBookmarkClickListener = object : ((Articles) -> Unit) {
            override fun invoke(article: Articles) {
                article.url?.let {
                    if (newsViewModel.isBookmarked(article.url)) {
                        article.isBookmarked = false
                        newsViewModel.deleteBookmark(article)

                        Toast.makeText(requireContext(), "Haber silindi", Toast.LENGTH_SHORT).show()
                    } else {
                        article.isBookmarked = true
                        newsViewModel.addBookmark(article)
                        Toast.makeText(requireContext(), "Haber kaydedildi", Toast.LENGTH_SHORT)
                            .show()
                    }

                }
            }
        }
        pagingDataAdapter.onCheckBookmark = object : ((Articles) -> Boolean) {
            var result = false
            override fun invoke(article: Articles): Boolean {
                article.url?.let {
                    result = newsViewModel.isBookmarked(article.url)
                }
                return result
            }


        }
        binding.rvNews.apply {
            this.adapter = pagingDataAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun hideProgressBar() {
        binding.newsPb.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.newsPb.visibility = View.VISIBLE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        getNewsState()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(inflater)

        // Inflate the layout for this fragment
        return binding.root
    }

}


