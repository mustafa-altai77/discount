package com.example.discaount.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.discaount.R;
import com.example.discaount.databinding.DrugDetailsFragmentBinding;
import com.example.discaount.databinding.FragmentTextBinding;


public class TextFragment extends Fragment {

    String resone;
    TextView textView;
    FragmentTextBinding textBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        textBinding = FragmentTextBinding.inflate(inflater, container, false);
        resone = "";
        if (getArguments() != null)
            resone = getArguments().getString("drug");

        textBinding.textFt.setText(resone);
        return textBinding.getRoot();


    }
}