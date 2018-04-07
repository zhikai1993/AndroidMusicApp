package com.android.flashbackmusic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class AddMusic extends LinearLayout {
    Context context;

    public AddMusic(Context context, AttributeSet attr) {
        super(context, attr);
        this.context = context;
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.add_music, this);
        //this.display(false);
    }

    public void display(boolean display) {
        if (display)  {
            display();
        } else {
            hide();
        }

    }

    public void hide() {
        this.setVisibility(LinearLayout.GONE);
    }

    public void display() {
        this.setVisibility(LinearLayout.VISIBLE);
    }

    public EditText getAddMusicTextEdit() { return this.findViewById(R.id.add_music_textedit); }
    public Button getAddMusicButton() { return this.findViewById(R.id.add_music_button); }
}
