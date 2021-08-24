package com.arya.todoapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arya.todoapp.Entity.Note;
import com.arya.todoapp.Fragment.FirstFragment;
import com.arya.todoapp.R;
import com.arya.todoapp.databinding.ItemNoteBinding;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private final List<Note> notes;
    private final NoteItemListener listener;

    public NotesAdapter(List<Note> notes, NoteItemListener listener) {
        this.notes = notes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setBinding(notes.get(position));
        holder.itemView.setOnClickListener(view -> {
            holder.binding.materialCheckBox.performClick();
        });
        holder.binding.materialCheckBox.setOnClickListener(view -> listener.NoteClicked(notes.get(position)));
        holder.binding.noteEdit.setOnClickListener(view -> listener.EditClicked(notes.get(position)));
        holder.binding.noteDelete.setOnClickListener(view -> listener.DeleteClicked(notes.get(position)));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemNoteBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemNoteBinding.bind(itemView);
        }

        public void setBinding(Note note) {
            binding.noteTitle.setText(note.getNote_title());
            binding.noteDesc.setText(note.getNote_desc());
            binding.materialCheckBox.setChecked(note.getCompleted());
        }
    }

    public interface NoteItemListener{
        void NoteClicked(Note note);
        void EditClicked(Note note);
        void DeleteClicked(Note note);
    }
}
