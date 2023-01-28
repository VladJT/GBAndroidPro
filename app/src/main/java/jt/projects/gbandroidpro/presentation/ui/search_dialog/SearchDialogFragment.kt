package jt.projects.gbandroidpro.presentation.ui.search_dialog

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jt.projects.gbandroidpro.databinding.SearchDialogFragmentBinding

class SearchDialogFragment : BottomSheetDialogFragment() {
    private var _binding: SearchDialogFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(): SearchDialogFragment {
            return SearchDialogFragment()
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            binding.searchButton.isEnabled = !binding.searchEditText.text.isNullOrEmpty()
        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }

    private var onSearchClickListener: OnSearchClickListener? = null

    private val onSearchButtonClickListener =
        View.OnClickListener {
            onSearchClickListener?.onClick(binding.searchEditText.text.toString())
            dismiss()
        }

    internal fun setOnSearchClickListener(listener: OnSearchClickListener) {
        onSearchClickListener = listener
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SearchDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchButton.setOnClickListener(onSearchButtonClickListener)
        binding.searchEditText.addTextChangedListener(textWatcher)
    }

    override fun onDestroyView() {
        onSearchClickListener = null
        super.onDestroyView()
    }
}