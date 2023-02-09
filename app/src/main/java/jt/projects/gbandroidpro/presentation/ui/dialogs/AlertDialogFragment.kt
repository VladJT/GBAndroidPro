package jt.projects.gbandroidpro.presentation.ui.dialogs

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import jt.projects.gbandroidpro.R
import kotlinx.parcelize.Parcelize

@Parcelize
class AlertDialogCallback(
    val title: String,
    val message : String,
    val okPressed: (()->Unit)?
    ) : Parcelable

class AlertDialogFragment() : AppCompatDialogFragment() {

    companion object {
        private const val TITLE_EXTRA = "89cbce59-e28f-418f-b470-ff67125c2e2f"
        const val TAG = "MyFragmentDialog"

        fun newInstance(data: AlertDialogCallback): AlertDialogFragment {
            val dialogFragment = AlertDialogFragment()
            val args = Bundle()
            args.putParcelable(TITLE_EXTRA, data)
            dialogFragment.arguments = args
            return dialogFragment
        }


    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val args = arguments?.getParcelable(TITLE_EXTRA) as? AlertDialogCallback
       // getParcelableExtra(TITLE_EXTRA, AlertDialogCallback::class.java)

        val title = args?.title
        val message = args?.message
        var okPressed: (()->Unit)? = args?.okPressed

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
            .setMessage(message)
            .setIcon(R.drawable.icon)
            .setPositiveButton("ОК") { dialog, _ ->
                okPressed?.let { it() }
                dialog.dismiss()
            }
            .setCancelable(false)
        return builder.create()
    }

}