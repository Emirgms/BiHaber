package com.devemir.bihaber.ui.bookmarks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.devemir.bihaber.adapter.BaseAdapter
import com.devemir.bihaber.data.model.Articles
import com.devemir.bihaber.databinding.FragmentBookmarksBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookmarksFragment : Fragment() {
    private lateinit var binding: FragmentBookmarksBinding
    private val viewModel  by viewModels<BookmarksViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSavedNews()
        initUIListeners()
    }

    private fun initUIListeners() {
        lifecycleScope.launch {
            viewModel.bookMarkState.collect {
                if (it.isLoading){
                    setLoadingScreen()
                    println("Loading")
                }else if (it.error.isNotEmpty()){
                    setErrorScreen()
                    println("Error")
                }else{
                    initRecycler(it.article)
                    println("Success ${it.article}")
                }
            }
        }
    }

    private fun setErrorScreen() {

    }

    private fun setLoadingScreen() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding= FragmentBookmarksBinding.inflate(inflater,container,false)
        return binding.root
    }
    private fun initRecycler(article: List<Articles>?) {

        article?.let {
            val adapter = BaseAdapter(
                onItemClickListener = object :((Articles) -> Unit) {
                    override fun invoke(article: Articles) {
                        if (article.url != null) {
                            val action =
                                BookmarksFragmentDirections.actionBookmarksFragmentToWebViewFragment(
                                    article
                                )
                            findNavController().navigate(action)
                        }
                    }
                },

                onBookmarkClickListener =
                    object :((Articles) -> Unit){
                        override fun invoke(article: Articles) {
                            article.url?.let {
                                if (viewModel.isBookmarked(article.url)) {
                                    viewModel.deleteBookmark(article)
                                } else {

                                }

                        }
                    }
                },
                onCheckBookmark = object : ((Articles) -> Boolean) {
                    var result = false
                    override fun invoke(article: Articles): Boolean {
                        article.url?.let {
                            result = viewModel.isBookmarked(article.url)
                        }
                        return result
                    }
                },
                articleList = it
            )


            binding.rvBookmarks.apply {
                this.adapter=adapter
                layoutManager= LinearLayoutManager(requireContext())
            }
        }

    }


}