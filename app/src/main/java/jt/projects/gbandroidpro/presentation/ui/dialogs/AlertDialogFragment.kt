package jt.projects.gbandroidpro.presentation.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import kotlinx.parcelize.Parcelize


class AlertDialogFragment() : AppCompatDialogFragment() {
    companion object {
        private const val DATA_EXTRA = "89cbce59-e28f-418f-b470-ff67125c2e2f"
        const val TAG = "MyFragmentDialog"

        fun newInstance(
            title: String,
            message: String,
            okPressed: (() -> Unit)? = null,
            cancelPressed: (() -> Unit)? = null
        ): AlertDialogFragment {
            val dialogFragment = AlertDialogFragment()
            val args = Bundle()
            val data = AlertDialogCallback(title, message, okPressed, cancelPressed)
            args.putParcelable(DATA_EXTRA, data)
            dialogFragment.arguments = args
            return dialogFragment
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val args = arguments?.getParcelable(DATA_EXTRA) as? AlertDialogCallback

        val title = args?.title
        val message = args?.message
        var okPressed: (() -> Unit)? = args?.okPressed
        var cancelPressed: (() -> Unit)? = args?.cancelPressed

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
            .setMessage(message)
            .setIcon(jt.projects.core.R.drawable.icon)
            .setPositiveButton("Да") { dialog, _ ->
                okPressed?.let { it() }
                dialog.dismiss()
            }
            .setNegativeButton("Нет") { dialog, _ ->
                cancelPressed?.let { it() }
                dialog.dismiss()
            }
            .setCancelable(false)
        return builder.create()
    }

    @Parcelize
    class AlertDialogCallback(
        val title: String,
        val message: String,
        val okPressed: (() -> Unit)?,
        val cancelPressed: (() -> Unit)?
    ) : Parcelable
}