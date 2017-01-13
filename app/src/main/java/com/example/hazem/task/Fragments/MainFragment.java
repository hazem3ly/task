package com.example.hazem.task.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hazem.task.Data.DataBaseAdapter;
import com.example.hazem.task.Data.User;
import com.example.hazem.task.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    TextView tv;
    DataBaseAdapter dataBaseAdapter;
    String email="";
    String password="";

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        tv = (TextView) rootView.findViewById(R.id.toolbar_title);

        Bundle data = getArguments();
        email = data.getString("email");
        password = data.getString("password");

        dataBaseAdapter = new DataBaseAdapter(getContext());

        User user = dataBaseAdapter.logedUserData(email,password);
        tv.setText(user.getName());


        return rootView;
    }
}

