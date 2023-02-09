package jt.projects.gbandroidpro.presentation.ui.search_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jt.projects.gbandroidpro.databinding.SearchDialogFragmentBinding

class SearchDialogFragment(private val onClose: (String?) -> Unit) : BottomSheetDialogFragment() {
    private var _binding: SearchDialogFragmentBinding? = null
    private val binding get() = _binding!!

    private var word: String? = null

    companion object {
        fun newInstance(onClose: (String?) -> Unit): SearchDialogFragment {
            return SearchDialogFragment(onClose)
        }
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

        binding.searchButton.setOnClickListener {
            word = binding.searchView.query.toString()
            dismiss()
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                binding.searchButton.isEnabled = newText.isNotEmpty()
                return true
            }
        })
    }

    override fun onDestroy() {
        onClose(word)
        super.onDestroy()
    }
}