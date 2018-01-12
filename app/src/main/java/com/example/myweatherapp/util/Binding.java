package com.example.myweatherapp.util;

import android.app.ActionBar;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

/**
 * Created by marco.t.leung on 12/1/2018.
 */

public class Binding {
    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView imageView, String url) {
        GlideApp.with(imageView.getContext())
                .load(url)
                .centerCrop()
                .into(imageView);
    }

}
