package com.kurokiji.gss.mainfragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kurokiji.gss.R;
import com.kurokiji.gss.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    TextView userNameTextView;
    TextView userEmailTextView;
    TextView userIpTextView;
    ImageView userProfileImageView;
    MainActivity localMainActivity;


    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        userNameTextView = view.findViewById(R.id.userName);
        userEmailTextView = view.findViewById(R.id.userEmail);
        userIpTextView = view.findViewById(R.id.userIp);
        userProfileImageView = view.findViewById(R.id.profilePic);
        localMainActivity = (MainActivity) getActivity();
        userNameTextView.setText(localMainActivity.currentUser.getUsername());
        userEmailTextView.setText(localMainActivity.currentUser.getEmail());
        String ipData = localMainActivity.currentUser.getUserIpData();
        if (ipData != null) {
            userIpTextView.setText(ipData);
        }
        String profileUri = localMainActivity.currentUser.getUriPath();
        if(profileUri != null){
            userProfileImageView.setImageURI(Uri.parse(profileUri));
        } else {
            userProfileImageView.setImageResource(R.drawable.profile_emptyprofile);
        }
        return view;
    }
}