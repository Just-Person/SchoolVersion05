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
 * Use the {@link BlankFragmentTuesday#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragmentTuesday extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BlankFragmentTuesday() {
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
    public static BlankFragmentTuesday newInstance(String param1, String param2) {
        BlankFragmentTuesday fragment = new BlankFragmentTuesday();
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
        TextView tu1=(TextView) view.findViewById(R.id.tuesday1);
        TextView tu2=(TextView) view.findViewById(R.id.tuesday2);
        TextView tu3=(TextView) view.findViewById(R.id.tuesday3);
        TextView tu4=(TextView) view.findViewById(R.id.tuesday4);
        TextView tu5=(TextView) view.findViewById(R.id.tuesday5);
        TextView tu6=(TextView) view.findViewById(R.id.tuesday6);
        TextView tu7=(TextView) view.findViewById(R.id.tuesday7);
        TextView tu8=(TextView) view.findViewById(R.id.tuesday8);
        String tuesday1 = getArguments().getString("tuesday1");
        String tuesday2 = getArguments().getString("tuesday2");
        String tuesday3 = getArguments().getString("tuesday3");
        String tuesday4 = getArguments().getString("tuesday4");
        String tuesday5 = getArguments().getString("tuesday5");
        String tuesday6 = getArguments().getString("tuesday6");
        String tuesday7 = getArguments().getString("tuesday7");
        String tuesday8 = getArguments().getString("tuesday8");
        tu1.setText(tuesday1);
        tu2.setText(tuesday2);
        tu3.setText(tuesday3);
        tu4.setText(tuesday4);
        tu5.setText(tuesday5);
        tu6.setText(tuesday6);
        tu7.setText(tuesday7);
        tu8.setText(tuesday8);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_blank_tuesday, container, false);

        return  view;
    }
}