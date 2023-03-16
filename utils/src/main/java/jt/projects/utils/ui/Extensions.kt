package jt.projects.utils.ui

import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import jt.projects.utils.R


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

fun Activity.showToast(text: String) {
    Toast.makeText(
        this,
        text,
        Toast.LENGTH_SHORT
    ).show()
}

fun Activity.showNoInternetConnectionDialog() =
    showSnackbar(getString(R.string.dialog_message_device_is_offline))

/**
 * FRAGMENT EXT
 */
fun Fragment.showSnackbar(text: String) {
    Snackbar.make(
        requireActivity().findViewById(android.R.id.content),
        text,
        Snackbar.LENGTH_SHORT
    ).show()
}

fun Fragment.showNoInternetConnectionDialog() =
    showSnackbar(getString(R.string.dialog_message_device_is_offline))
