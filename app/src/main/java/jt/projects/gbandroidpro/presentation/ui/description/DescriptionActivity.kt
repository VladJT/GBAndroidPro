package jt.projects.gbandroidpro.presentation.ui.description

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import jt.projects.gbandroidpro.R
import jt.projects.model.data.DataModel


class DescriptionActivity : AppCompatActivity() {
    companion object {
        const val DATA_KEY = "f76a288a-5dcc-43f1-ba89-7fe1d53f63b0"

        fun getIntent(
            context: Context,
            data: DataModel
        ): Intent = Intent(context, DescriptionActivity::class.java).apply {
            putExtra(DATA_KEY, data)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // убираем splash screen
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            setTheme(jt.projects.core.R.style.Theme_GBAndroidPro)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        supportActionBar?.subtitle = getString(R.string.subtitle_description_activity)
        setActionbarHomeButtonAsUp()

        initFragment()
    }

    private fun initFragment() {
        supportFragmentManager.beginTransaction()
            .add(
                R.id.detailsFragmentContainer,
                DescriptionFragment().apply {
                    arguments = intent.extras
                }
            )
            .commitAllowingStateLoss()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setActionbarHomeButtonAsUp() {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}