package com.example.version05;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragmentMonday#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragmentMonday extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BlankFragmentMonday() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragmentMonday newInstance(String param1, String param2) {
        BlankFragmentMonday fragment = new BlankFragmentMonday();
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
        TextView mon1=(TextView) view.findViewById(R.id.monday1);
        TextView mon2=(TextView) view.findViewById(R.id.monday2);
        TextView mon3=(TextView) view.findViewById(R.id.monday3);
        TextView mon4=(TextView) view.findViewById(R.id.monday4);
        TextView mon5=(TextView) view.findViewById(R.id.monday5);
        TextView mon6=(TextView) view.findViewById(R.id.monday6);
        TextView mon7=(TextView) view.findViewById(R.id.monday7);
        TextView mon8=(TextView) view.findViewById(R.id.monday8);
        String monday1 = getArguments().getString("monday1");
        String monday2 = getArguments().getString("monday2");
        String monday3 = getArguments().getString("monday3");
        String monday4 = getArguments().getString("monday4");
        String monday5 = getArguments().getString("monday5");
        String monday6 = getArguments().getString("monday6");
        String monday7 = getArguments().getString("monday7");
        String monday8 = getArguments().getString("monday8");
        mon1.setText(monday1);
        mon2.setText(monday2);
        mon3.setText(monday3);
        mon4.setText(monday4);
        mon5.setText(monday5);
        mon6.setText(monday6);
        mon7.setText(monday7);
        mon8.setText(monday8);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_blank_monday, container, false);

        return  view;
    }
}