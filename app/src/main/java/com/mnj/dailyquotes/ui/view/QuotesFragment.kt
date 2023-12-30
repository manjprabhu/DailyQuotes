package com.mnj.dailyquotes.ui.view

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.mnj.dailyquotes.R
import com.mnj.dailyquotes.databinding.FragmentFirstBinding
import com.mnj.dailyquotes.db.QuoteEntity
import com.mnj.dailyquotes.ui.viewmodel.QuotesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class QuotesFragment : Fragment() {

    private lateinit var _binding: FragmentFirstBinding
    private val viewModel by activityViewModels<QuotesViewModel>()
    private var quotes: QuoteEntity? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    //private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgressBar()
        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.quoteFlow.collect {
                        it.data?.let { today ->
                            quotes = today.copy()
                            hideProgressBar()
                            _binding.tvQuote.text = today.quote
                            _binding.tvAuthor.text = today.author
                        }
                    }
                }
            }

            launch {
                repeatOnLifecycle((Lifecycle.State.STARTED)) {
                    viewModel.saveQuoteFlow.collect {
                        if (it)
                            _binding.ivBookmark.setBackgroundResource(R.drawable.ic_bookmark_filled)
                    }
                }
            }
        }

        _binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        _binding.ivShareQuote.setOnClickListener {
            copyToClipboard()
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, pasteFromClipboard())
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        _binding.ivBookmark.setOnClickListener {
            quotes?.let { it1 -> viewModel.saveQuote(it1) }
        }
    }

    private fun copyToClipboard() {
        val textToCopy = _binding.tvQuote.text.toString()
        val clipboardManager = context?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", textToCopy)
        clipboardManager.setPrimaryClip(clipData)
    }

    private fun pasteFromClipboard(): String {
        val clipboardManager = context?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        return clipboardManager.primaryClip?.getItemAt(0)?.text.toString()
    }


    private fun hideProgressBar() {
        _binding.progressQuoteLoading.visibility = View.GONE
        _binding.ccQuotes.visibility = View.VISIBLE
        _binding.clShare.visibility = View.VISIBLE
    }

    private fun showProgressBar() {
        _binding.progressQuoteLoading.visibility = View.VISIBLE

    }

}