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
import com.mnj.dailyquotes.ui.viewmodel.QuotesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class QuotesFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val viewModel by activityViewModels<QuotesViewModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgressBar()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.quoteFlow.collect {

                    it.data?.let { today ->
                        hideProgressBar()
                        _binding?.tvQuote?.text = today.quote
                        _binding?.tvAuthor?.text = today.author
                    }
                    println("==>> Quote of the day is :${it.data?.quote}")
                }
            }
        }

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.ivShareQuote.setOnClickListener {
            copyToClipboard()
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, pasteFromClipboard())
                type= "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent,null)
            startActivity(shareIntent)
        }
    }

    private fun copyToClipboard() {
        val textToCopy = binding.tvQuote.text.toString()
        val clipboardManager = context?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", textToCopy)
        clipboardManager.setPrimaryClip(clipData)
    }

    private fun pasteFromClipboard(): String {
        val clipboardManager = context?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        return clipboardManager.primaryClip?.getItemAt(0)?.text.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun hideProgressBar() {
        _binding?.progressQuoteLoading?.visibility = View.GONE
        _binding?.ccQuotes?.visibility = View.VISIBLE
        _binding?.clShare?.visibility = View.VISIBLE
    }

    private fun showProgressBar() {
        _binding?.progressQuoteLoading?.visibility = View.VISIBLE

    }

}