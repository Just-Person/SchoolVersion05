package com.example.version05;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragmentFriday#newInstance} factory method to
 * create an instance of this fragment.
 */

public class BlankFragmentFriday extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BlankFragmentFriday() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment5.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragmentFriday newInstance(String param1, String param2) {
        BlankFragmentFriday fragment = new BlankFragmentFriday();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView fr1=(TextView) view.findViewById(R.id.friday1);
        TextView fr2=(TextView) view.findViewById(R.id.friday2);
        TextView fr3=(TextView) view.findViewById(R.id.friday3);
        TextView fr4=(TextView) view.findViewById(R.id.friday4);
        TextView fr5=(TextView) view.findViewById(R.id.friday5);
        TextView fr6=(TextView) view.findViewById(R.id.friday6);
        TextView fr7=(TextView) view.findViewById(R.id.friday7);
        TextView fr8=(TextView) view.findViewById(R.id.friday8);
        String friday1 = getArguments().getString("friday1");
        String friday2 = getArguments().getString("friday2");
        String friday3 = getArguments().getString("friday3");
        String friday4 = getArguments().getString("friday4");
        String friday5 = getArguments().getString("friday5");
        String friday6 = getArguments().getString("friday6");
        String friday7 = getArguments().getString("friday7");
        String friday8 = getArguments().getString("friday8");
        fr1.setText(friday1);
        fr2.setText(friday2);
        fr3.setText(friday3);
        fr4.setText(friday4);
        fr5.setText(friday5);
        fr6.setText(friday6);
        fr7.setText(friday7);
        fr8.setText(friday8);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_blank_friday, container, false);

        return  view;
    }
}