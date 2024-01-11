package com.devemir.bihaber.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.devemir.bihaber.adapter.BaseAdapter
import com.devemir.bihaber.data.model.Articles
import com.devemir.bihaber.databinding.FragmentSearchViewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchViewFragment : Fragment() {
    private lateinit var binding: FragmentSearchViewBinding
    private val viewModel by viewModels<SearchViewViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getSearchByInput()

    }


    fun getSearchByInput() {
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.getSearchNews(query)
                    lifecycleScope.launch {
                        viewModel.searchState.collect{
                            if(it.isLoading) {
                                setLoadingFunc()
                            } else if(it.error.isNotEmpty()) {
                                setErrorFunc()
                            } else {
                                initRecycler(it.article)
                            }
                        }
                    }

                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    private fun setErrorFunc() {

    }

    private fun setLoadingFunc() {

    }

    fun initRecycler(article:List<Articles>?) {
        article?.let {
            val adapter=BaseAdapter(
                articleList = it,
                onItemClickListener = object :((Articles) -> Unit ) {
                    override fun invoke(article: Articles) {
                        if(article.url !=null) {
                            val action=SearchViewFragmentDirections.actionSearchViewFragmentToWebViewFragment(article)
                            findNavController().navigate(action)
                        }

                    }

                },
                onBookmarkClickListener = object:((Articles)-> Unit){
                    override fun invoke(article: Articles) {
                        article.url?.let {
                            if(viewModel.isBookmarked(article.url)){
                                viewModel.deleteBookmark(article)
                                Toast.makeText(context,"Haber Silindi",Toast.LENGTH_SHORT).show()
                            } else {
                                viewModel.addBookmark(article)
                                Toast.makeText(context,"Haber Kaydedildi",Toast.LENGTH_SHORT).show()
                            }

                        }
                    }

                },
                onCheckBookmark = object : ((Articles) -> Boolean) {
                    var result = false
                    override fun invoke(article: Articles): Boolean {
                        article.url?.let {
                            result =  viewModel.isBookmarked(article.url)
                        }
                        return result
                    }

                }
            )

            binding.rvSearch.apply {
                this.adapter=adapter
                layoutManager=LinearLayoutManager(requireContext())
            }
        }

    }
}