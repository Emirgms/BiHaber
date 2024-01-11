package com.devemir.bihaber.ui.new_detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.devemir.bihaber.R
import com.devemir.bihaber.databinding.FragmentWebViewBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WebViewFragment : Fragment() {
    private lateinit var binding: FragmentWebViewBinding
    private val args: com.devemir.bihaber.ui.new_detail.WebViewFragmentArgs by navArgs()
    private var url: String? = null
    private val viewModel: NewsDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        url=args.article.url
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        url?.let {
            binding.webView.settings.javaScriptEnabled = true
            setAppBar()
            setTopAppBarClickListeners()
            bindNewDetail()

        }
    }
    @SuppressLint("SetJavaScriptEnabled")
    private fun setAppBar() {
    viewModel.article.observe(viewLifecycleOwner) {article->
        if (article == null) {
           binding.webView.visibility=View.GONE
        } else {
            binding.newDetailToolbar.title = article.title
            binding.newDetailToolbar.subtitle = article.url
            binding.webView.visibility=View.VISIBLE
            binding.webView.apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                article.url?.let { articleUrl ->
                    loadUrl(articleUrl)
                }

            }
        }


    }

    }
    private fun setTopAppBarClickListeners() {
        binding.newDetailToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }



        binding.newDetailToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.share_icon -> {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    intent.putExtra(Intent.EXTRA_TEXT, viewModel.article.value?.url)
                    startActivity(Intent.createChooser(intent, "Share New"))
                    true
                }

                R.id.bookmark_icon -> {
                    if (viewModel.isBookmarked.value == true) {
                        menuItem.setIcon(R.drawable.bookmark)
                        viewModel.removeBookmark()
                    } else {
                        menuItem.setIcon(R.drawable.bookmark_clicked)
                        viewModel.addBookmark()
                    }
                    true
                }

                else -> false
            }
        }
    }
    private fun bindNewDetail() {
        arguments?.let {
            val args = WebViewFragmentArgs.fromBundle(it)
            viewModel.setArticle(args.article)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWebViewBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }


}