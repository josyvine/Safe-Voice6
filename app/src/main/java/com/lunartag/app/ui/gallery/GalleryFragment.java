package com.lunartag.app.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.lunartag.app.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Setup the RecyclerView with a GridLayoutManager to show 3 columns
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        binding.recyclerViewGallery.setLayoutManager(layoutManager);
    }

    @Override
    public void onResume() {
        super.onResume();
        // When the screen becomes visible, load the photos.
        loadPhotos();
    }

    private void loadPhotos() {
        // Show a loading indicator
        binding.progressBarGallery.setVisibility(View.VISIBLE);
        binding.recyclerViewGallery.setVisibility(View.GONE);
        binding.textNoPhotos.setVisibility(View.GONE);

        // This is where the logic will be added to:
        // 1. Query the local Room database to get a list of all saved photo records.
        // 2. This avoids hitting Firestore every time the user opens the gallery.
        // 3. Create and set a GalleryAdapter for the RecyclerView with the loaded data.
        // 4. Handle the UI states: show the list, show the "No photos" message, or hide the progress bar.
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Important to prevent memory leaks
    }
}
