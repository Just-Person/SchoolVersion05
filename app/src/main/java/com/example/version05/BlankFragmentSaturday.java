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
 * Use the {@link BlankFragmentSaturday#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragmentSaturday extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BlankFragmentSaturday() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment6.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragmentSaturday newInstance(String param1, String param2) {
        BlankFragmentSaturday fragment = new BlankFragmentSaturday();
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
        TextView sa1=(TextView) view.findViewById(R.id.saturday1);
        TextView sa2=(TextView) view.findViewById(R.id.saturday2);
        TextView sa3=(TextView) view.findViewById(R.id.saturday3);
        TextView sa4=(TextView) view.findViewById(R.id.saturday4);
        TextView sa5=(TextView) view.findViewById(R.id.saturday5);
        TextView sa6=(TextView) view.findViewById(R.id.saturday6);
        TextView sa7=(TextView) view.findViewById(R.id.saturday7);
        TextView sa8=(TextView) view.findViewById(R.id.saturday8);
        String saturday1 = getArguments().getString("saturday1");
        String saturday2 = getArguments().getString("saturday2");
        String saturday3 = getArguments().getString("saturday3");
        String saturday4 = getArguments().getString("saturday4");
        String saturday5 = getArguments().getString("saturday5");
        String saturday6 = getArguments().getString("saturday6");
        String saturday7 = getArguments().getString("saturday7");
        String saturday8 = getArguments().getString("saturday8");
        sa1.setText(saturday1);
        sa2.setText(saturday2);
        sa3.setText(saturday3);
        sa4.setText(saturday4);
        sa5.setText(saturday5);
        sa6.setText(saturday6);
        sa7.setText(saturday7);
        sa8.setText(saturday8);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_blank_saturday, container, false);

        return  view;
    }
}