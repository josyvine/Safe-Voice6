package com.safevoice.app.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.safevoice.app.KycActivity;
import com.safevoice.app.R;
import com.safevoice.app.databinding.FragmentHomeBinding;
import com.safevoice.app.services.VoiceRecognitionService;

/**
 * The fragment for the "Home" screen.
 * It provides controls to start/stop the listening service and to verify the user's identity.
 */
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment using view binding.
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up the click listener for the service toggle button.
        binding.buttonToggleService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isServiceRunning()) {
                    stopVoiceService();
                } else {
                    startVoiceService();
                }
                // Update the UI immediately after the button is clicked.
                updateServiceStatusUI();
            }
        });

        // Set up the click listener for the identity verification button.
        binding.buttonVerifyIdentity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    Intent intent = new Intent(getActivity(), KycActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Update the UI every time the fragment becomes visible to ensure it's up-to-date.
        updateServiceStatusUI();
        updateVerificationStatusUI();
    }

    /**
     * Checks the status of the VoiceRecognitionService and updates the UI elements accordingly.
     */
    private void updateServiceStatusUI() {
        if (isServiceRunning()) {
            binding.textServiceStatus.setText(R.string.home_status_listening);
            binding.buttonToggleService.setText(R.string.home_stop_service_button);
        } else {
            binding.textServiceStatus.setText(R.string.home_status_stopped);
            binding.buttonToggleService.setText(R.string.home_start_service_button);
        }
    }

    /**
     * Checks the user's verification status from Firebase and updates the UI.
     */
    private void updateVerificationStatusUI() {
        // TODO: In a later step, this will be updated to read a custom "verifiedName"
        // field from the user's Firestore document.
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null && currentUser.getDisplayName() != null && !currentUser.getDisplayName().isEmpty()) {
            // For now, we use the Google display name as a placeholder for verification.
            String statusText = getString(R.string.home_verification_status_verified, currentUser.getDisplayName());
            binding.textVerificationStatus.setText(statusText);
            binding.buttonVerifyIdentity.setVisibility(View.GONE);
        } else {
            binding.textVerificationStatus.setText(R.string.home_verification_status);
            binding.buttonVerifyIdentity.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Starts the background voice recognition service.
     */
    private void startVoiceService() {
        if (getActivity() != null) {
            Intent serviceIntent = new Intent(getActivity(), VoiceRecognitionService.class);
            getActivity().startService(serviceIntent);
        }
    }

    /**
     * Stops the background voice recognition service.
     */
    private void stopVoiceService() {
        if (getActivity() != null) {
            Intent serviceIntent = new Intent(getActivity(), VoiceRecognitionService.class);
            getActivity().stopService(serviceIntent);
        }
    }

    /**
     * A simple method to check if the service is running.
     * This relies on a static variable in the service class itself.
     *
     * @return true if the service is currently running, false otherwise.
     */
    private boolean isServiceRunning() {
        return VoiceRecognitionService.isServiceRunning;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Clean up the binding object to prevent memory leaks.
        binding = null;
    }
                                                       }
