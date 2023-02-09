package jt.projects.gbandroidpro.utils.ui

import android.app.Activity
import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(text: String) {
    Snackbar.make(this, text, Snackbar.LENGTH_SHORT).show()
}

fun Activity.showSnackbar(text: String) {
    Snackbar.make(
        this.findViewById(android.R.id.content),
        text,
        Snackbar.LENGTH_SHORT
    ).show()
}