// Generated by view binder compiler. Do not edit!
package com.example.quoridor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.quoridor.R;
import com.example.quoridor.ui.game.QuoridorBoard;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityLocalGameScreenBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final QuoridorBoard localGameBoard;

  @NonNull
  public final Button returnButton;

  @NonNull
  public final LinearLayout winnerCard;

  @NonNull
  public final TextView winnerText;

  private ActivityLocalGameScreenBinding(@NonNull RelativeLayout rootView,
      @NonNull QuoridorBoard localGameBoard, @NonNull Button returnButton,
      @NonNull LinearLayout winnerCard, @NonNull TextView winnerText) {
    this.rootView = rootView;
    this.localGameBoard = localGameBoard;
    this.returnButton = returnButton;
    this.winnerCard = winnerCard;
    this.winnerText = winnerText;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityLocalGameScreenBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityLocalGameScreenBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_local_game_screen, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityLocalGameScreenBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.localGameBoard;
      QuoridorBoard localGameBoard = ViewBindings.findChildViewById(rootView, id);
      if (localGameBoard == null) {
        break missingId;
      }

      id = R.id.returnButton;
      Button returnButton = ViewBindings.findChildViewById(rootView, id);
      if (returnButton == null) {
        break missingId;
      }

      id = R.id.winnerCard;
      LinearLayout winnerCard = ViewBindings.findChildViewById(rootView, id);
      if (winnerCard == null) {
        break missingId;
      }

      id = R.id.winnerText;
      TextView winnerText = ViewBindings.findChildViewById(rootView, id);
      if (winnerText == null) {
        break missingId;
      }

      return new ActivityLocalGameScreenBinding((RelativeLayout) rootView, localGameBoard,
          returnButton, winnerCard, winnerText);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}