package com.epam.scenicsydney.note

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.epam.scenicsydney.R
import com.epam.scenicsydney.location.edit.EditLocationFragment
import kotlinx.android.synthetic.main.fragment_edit_note.*

/**
 * Fragment for specifying a note text. The note is saved by clicking the floating action button.
 */
class EditNoteFragment : Fragment() {

    // view model must be provided by the parent fragment
    private val viewModel: EditNoteViewModel by lazy {
        val parentFragment = fragmentManager?.findFragmentByTag(EditLocationFragment.FRAGMENT_TAG)
                ?: throw IllegalStateException("No parent fragment")
        ViewModelProviders.of(parentFragment).get(EditNoteViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_edit_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        floatingActionButton.setOnClickListener { viewModel.setText(editText.text.toString()) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }
}