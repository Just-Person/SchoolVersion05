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
 * Use the {@link BlankFragmentWednesday#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragmentWednesday extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BlankFragmentWednesday() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment3.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragmentWednesday newInstance(String param1, String param2) {
        BlankFragmentWednesday fragment = new BlankFragmentWednesday();
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
        TextView wed1=(TextView) view.findViewById(R.id.wednesday1);
        TextView wed2=(TextView) view.findViewById(R.id.wednesday2);
        TextView wed3=(TextView) view.findViewById(R.id.wednesday3);
        TextView wed4=(TextView) view.findViewById(R.id.wednesday4);
        TextView wed5=(TextView) view.findViewById(R.id.wednesday5);
        TextView wed6=(TextView) view.findViewById(R.id.wednesday6);
        TextView wed7=(TextView) view.findViewById(R.id.wednesday7);
        TextView wed8=(TextView) view.findViewById(R.id.wednesday8);
        String wednesday1 = getArguments().getString("wednesday1");
        String wednesday2 = getArguments().getString("wednesday2");
        String wednesday3 = getArguments().getString("wednesday3");
        String wednesday4 = getArguments().getString("wednesday4");
        String wednesday5 = getArguments().getString("wednesday5");
        String wednesday6 = getArguments().getString("wednesday6");
        String wednesday7 = getArguments().getString("wednesday7");
        String wednesday8 = getArguments().getString("wednesday8");
        wed1.setText(wednesday1);
        wed2.setText(wednesday2);
        wed3.setText(wednesday3);
        wed4.setText(wednesday4);
        wed5.setText(wednesday5);
        wed6.setText(wednesday6);
        wed7.setText(wednesday7);
        wed8.setText(wednesday8);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_blank_wednesday, container, false);

        return  view;
    }
}