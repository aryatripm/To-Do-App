package com.arya.todoapp.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arya.todoapp.Api.ApiClient;
import com.arya.todoapp.Api.TasksApi;
import com.arya.todoapp.Entity.Note;
import com.arya.todoapp.R;
import com.arya.todoapp.databinding.FragmentSecondBinding;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThirdFragment extends Fragment {

    private FragmentSecondBinding binding;
    private TasksApi api;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.fab.setOnClickListener(view1 -> addNewNote());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void addNewNote() {
        String title = binding.editTitle.getText().toString();
        String desc = binding.editDesc.getText().toString();

        Note note = new Note();
        note.setNote_title(title);
        note.setNote_desc(desc);

        add(note);
    }

    private void add(Note note) {
        api = ApiClient.getClient().create(TasksApi.class);
        api.addOneNote(note).enqueue(new Callback<Note>() {
            @Override
            public void onResponse(Call<Note> call, Response<Note> response) {
                Log.d(FirstFragment.TAG, response.toString());
                NavHostFragment.findNavController(ThirdFragment.this).navigate(R.id.action_ThirdFragment_to_FirstFragment);
            }

            @Override
            public void onFailure(Call<Note> call, Throwable t) {
                Log.d(FirstFragment.TAG, t.getLocalizedMessage());
            }
        });
    }
}