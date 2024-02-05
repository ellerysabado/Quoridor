package com.example.quoridor.ui.gameHistory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quoridor.R;


/**
 * @author Mazin Bashier
*
* */
public class fragment extends Fragment {

    public void fragment() {
    }

    public static fragment newInstance() {
        fragment fragment = new fragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }
}
