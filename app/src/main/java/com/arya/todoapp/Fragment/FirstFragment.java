package com.arya.todoapp.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.arya.todoapp.Adapter.NotesAdapter;
import com.arya.todoapp.Api.ApiClient;
import com.arya.todoapp.Api.TasksApi;
import com.arya.todoapp.Entity.Note;
import com.arya.todoapp.R;
import com.arya.todoapp.databinding.FragmentFirstBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FirstFragment extends Fragment {

    public static final String TAG = "coba";
    private FragmentFirstBinding binding;
    private NotesAdapter adapter;
    private final ArrayList<Note> notes = new ArrayList<>();
    private final NoteListener listener = new NoteListener();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new NotesAdapter(notes, listener);
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
        binding.rvNotes.setAdapter(adapter);
        binding.rvNotes.setLayoutManager(manager);

        binding.fab.setOnClickListener(view1 -> {
            NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_ThirdFragment);
        });

        fetchData();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        notes.removeAll(notes);
    }

    private void fetchData() {
        TasksApi api = ApiClient.getClient().create(TasksApi.class);

        api.getAll().enqueue(new Callback<List<Note>>() {
            @Override
            public void onResponse(Call<List<Note>> call, Response<List<Note>> response) {
                notes.addAll(response.body());
                adapter.notifyItemChanged(notes.size());
            }

            @Override
            public void onFailure(Call<List<Note>> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class NoteListener implements NotesAdapter.NoteItemListener{

        TasksApi api = ApiClient.getClient().create(TasksApi.class);

        @Override
        public void NoteClicked(Note note) {
            note.setCompleted(!note.getCompleted());
            api.updateCompleted(note.get_id(), note).enqueue(new Callback<Note>() {
                @Override
                public void onResponse(Call<Note> call, Response<Note> response) {
                    Log.d(TAG, response.body().toString());
                }

                @Override
                public void onFailure(Call<Note> call, Throwable t) {
                    Log.d(TAG, t.getLocalizedMessage());
                }
            });
        }

        @Override
        public void EditClicked(Note note) {
            toEdit(note);
        }

        @Override
        public void DeleteClicked(Note note) {
            api.deleteById(note.get_id()).enqueue(new Callback<Note>() {
                @Override
                public void onResponse(Call<Note> call, Response<Note> response) {
                    Log.d(TAG, response.toString());
                    notes.removeAll(notes);
                    fetchData();
                }

                @Override
                public void onFailure(Call<Note> call, Throwable t) {
                    Log.d(TAG, t.getLocalizedMessage());
                }
            });
        }

        public void toEdit(Note note) {
            FirstFragmentDirections.ActionFirstFragmentToSecondFragment toSecondFragment = FirstFragmentDirections.actionFirstFragmentToSecondFragment(note);
            toSecondFragment.setEditNote(note);
            NavHostFragment.findNavController(FirstFragment.this).navigate(toSecondFragment);
        }
    }

}