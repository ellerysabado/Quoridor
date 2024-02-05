package com.example.quoridor.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.quoridor.R;

/**
 * Game chat fragment?
 *
 * @author Mazin Bashier
 * */
public class gameChatFragment extends Fragment implements View.OnClickListener {


    public static gameChatFragment newInstance() {
        gameChatFragment fragment = new gameChatFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

              return inflater.inflate(R.layout.fragment_game_chat, container, false);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), gameChat.class);

    }
}

