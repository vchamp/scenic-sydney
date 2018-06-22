package com.epam.scenicsydney.location.edit

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.epam.scenicsydney.R
import com.epam.scenicsydney.model.Note
import com.epam.scenicsydney.note.EditNoteFragment
import com.epam.scenicsydney.note.EditNoteViewModel
import kotlinx.android.synthetic.main.fragment_edit_location.*
import kotlinx.android.synthetic.main.item_note.view.*

/**
 * Fragment that allows to edit and delete a location, add notes. EditNoteFragment is opened from this fragment.
 *
 * Reacts to the save check command from the view model to handle back navigation by saving the title or discarding
 * changes.
 */
class EditLocationFragment : Fragment() {

    companion object {
        const val FRAGMENT_TAG = "edit_location"
        private const val ARG_LOCATION_ID = "location_id"
        private const val ARG_IS_NEW = "is_new"

        fun newInstance(locationId: Long, isNew: Boolean) = EditLocationFragment().apply {
            arguments = Bundle().apply {
                putLong(ARG_LOCATION_ID, locationId)
                putBoolean(ARG_IS_NEW, isNew)
            }
        }
    }

    private val locationId by lazy {
        arguments?.getLong(ARG_LOCATION_ID)
                ?: throw IllegalStateException("Location id not defined")
    }

    private val isNew by lazy {
        arguments?.getBoolean(ARG_IS_NEW) ?: false
    }

    private val viewModel: EditLocationViewModel by lazy {
        val activity = activity ?: throw IllegalStateException("Not attached")
        ViewModelProviders.of(activity,
                EditLocationViewModelFactory(locationId))
                .get(locationId.toString(), EditLocationViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_edit_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesRecyclerView.layoutManager = LinearLayoutManager(context)
        floatingActionButton.setOnClickListener { openAddNote() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getLocation().observe(this, Observer { location ->
            if (location != null) {
                coordsTextView.text = getString(R.string.coordinates, location.latitude, location.longitude)
                titleEditText.setText(location.name)
            }
        })

        viewModel.getNotes().observe(this, Observer { notes ->
            notesRecyclerView.adapter = NotesAdapter(notes ?: emptyList())
        })

        viewModel.getSaveCheckCommand().observe(this, Observer { saveCheck ->
            if (saveCheck == true) {
                // user wants to navigate back - handle the case when the title is empty or save the title otherwise
                val title = titleEditText.text.toString()
                if (title.isBlank()) {
                    if (isNew) {
                        confirmDeleteLocation(R.string.dismiss_new_location)
                    } else {
                        viewModel.close()
                    }
                } else {
                    viewModel.save(title)
                    viewModel.close()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        activity?.title = getString(R.string.location)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.edit_location, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete -> {
                val msgResId = if (isNew) R.string.dismiss_new_location else R.string.delete_location
                confirmDeleteLocation(msgResId)
            }
            else -> {
                super.onOptionsItemSelected(item)
                return false
            }
        }
        return true
    }

    private fun confirmDeleteLocation(@StringRes msgResId: Int) {
        context?.let { context ->
            AlertDialog.Builder(context)
                    .setMessage(msgResId)
                    .setPositiveButton(R.string.ok) { _, _ ->
                        viewModel.deleteLocation()
                        viewModel.close()
                    }.setNegativeButton(R.string.cancel, null)
                    .create().show()
        }
    }

    private fun openAddNote() {
        val fragment = EditNoteFragment()
        val fragmentManager = fragmentManager ?: return
        fragmentManager.beginTransaction().add(R.id.fragmentContainer, fragment).addToBackStack(null).commit()

        // the same view model is reused by this fragment and EditNoteFragment,
        // it allows to react here to the changes in the new note's live data
        val editNoteViewModel = ViewModelProviders.of(this).get(EditNoteViewModel::class.java)
        editNoteViewModel.setSelectedNote(Note(locationId))
        editNoteViewModel.getSelectedNote().observe(this, Observer { note ->
            if (note != null && !note.text.isBlank()) {
                viewModel.addNote(note)
                editNoteViewModel.getSelectedNote().removeObservers(this)
                fragmentManager.popBackStack()
            }
        })
    }

    inner class NotesAdapter(private val mNotes: List<Note>) : RecyclerView.Adapter<NoteViewHolder>() {
        override fun getItemCount(): Int = mNotes.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false)
            return NoteViewHolder(view)
        }

        override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
            holder.setText(mNotes[position].text)
        }
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView = itemView.textView

        fun setText(text: String) {
            textView.text = text
        }
    }
}