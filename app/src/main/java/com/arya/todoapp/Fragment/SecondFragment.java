package com.arya.todoapp.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.arya.todoapp.Api.ApiClient;
import com.arya.todoapp.Api.TasksApi;
import com.arya.todoapp.Entity.Note;
import com.arya.todoapp.MainActivity;
import com.arya.todoapp.R;
import com.arya.todoapp.databinding.FragmentSecondBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private TasksApi api;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Note note = SecondFragmentArgs.fromBundle(getArguments()).getEditNote();

        binding.fab.setOnClickListener(view1 -> updateNote(note));

        binding.editTitle.setText(note.getNote_title());
        binding.editDesc.setText(note.getNote_desc());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateNote(Note note) {
        String title = binding.editTitle.getText().toString();
        String desc = binding.editDesc.getText().toString();

        note.setNote_title(title);
        note.setNote_desc(desc);

        update(note);
    }

    private void update(Note note) {
        api = ApiClient.getClient().create(TasksApi.class);
        api.updateCompleted(note.get_id(), note).enqueue(new Callback<Note>() {
            @Override
            public void onResponse(Call<Note> call, Response<Note> response) {
                Log.d(FirstFragment.TAG, response.toString());
                NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_FirstFragment);
            }

            @Override
            public void onFailure(Call<Note> call, Throwable t) {
                Log.d(FirstFragment.TAG, t.getLocalizedMessage());
            }
        });
    }

}