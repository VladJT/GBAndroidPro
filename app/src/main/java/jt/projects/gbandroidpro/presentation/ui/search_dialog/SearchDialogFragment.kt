package jt.projects.gbandroidpro.presentation.ui.search_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
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

    private var onSearchClickListener: OnSearchClickListener? = null

    // приходит из MainActivity
    internal fun setOnSearchClickListener(listener: OnSearchClickListener) {
        onSearchClickListener = listener
    }


    private val onSearchClick = View.OnClickListener {
        // отправляем результат в MainActivity
        onSearchClickListener?.onClick(binding.searchView.query.toString())
        dismiss()
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

        binding.searchButton.setOnClickListener(onSearchClick)

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
}