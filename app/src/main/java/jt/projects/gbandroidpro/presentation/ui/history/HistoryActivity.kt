package jt.projects.gbandroidpro.presentation.ui.history

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import jt.projects.core.BaseActivity
import jt.projects.gbandroidpro.R
import jt.projects.gbandroidpro.databinding.ActivityHistoryBinding
import jt.projects.gbandroidpro.presentation.ui.description.DescriptionActivity
import jt.projects.gbandroidpro.presentation.ui.dialogs.AlertDialogFragment
import jt.projects.model.data.AppState
import jt.projects.model.data.DataModel
import jt.projects.utils.ViewModelNotInitException
import jt.projects.utils.ui.showSnackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("Instantiatable")
class HistoryActivity : BaseActivity<AppState>() {

    override val model: HistoryViewModel by viewModel()

    private lateinit var binding: ActivityHistoryBinding
    private val historyAdapter: HistoryAdapter by lazy { HistoryAdapter(::onItemClick) }

    private fun onItemClick(data: DataModel) {
        startActivity(
            DescriptionActivity.getIntent(
                this,
                data
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.subtitle = getString(R.string.subtitle_history_activity)
        setActionbarHomeButtonAsUp()

        iniViewModel()
        initViews()

        // !! НЕ загружаем данные после поворота экрана
        if (savedInstanceState == null) {
            model.getData("")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.menu_clean_history -> {
                showDeleteDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showDeleteDialog() {
        AlertDialogFragment.newInstance(getString(R.string.dialog_clean_history_title),
            getString(R.string.dialog_clean_history_message),
            okPressed = {
                model.cleanHistory()
                showSnackbar(getString(R.string.dialog_clean_history_ok_pressed))
            },
            cancelPressed = {})
            .show(supportFragmentManager, AlertDialogFragment.TAG)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    private fun setActionbarHomeButtonAsUp() {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun iniViewModel() {
        if (binding.historyActivityRecyclerview.adapter != null) {
            throw ViewModelNotInitException
        }
        model.liveDataForViewToObserve.observe(this@HistoryActivity, Observer<AppState> {
            renderData(it)
        })
    }

    private fun initViews() {
        binding.historyActivityRecyclerview.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = historyAdapter
        }
    }

    // Вызовется из базовой Activity, когда данные будут готовы
    override fun setDataToAdapter(data: List<DataModel>) = historyAdapter.setData(data)

    override fun showViewSuccess() {
        binding.historyLoadingFrameLayout.visibility = View.GONE
    }

    override fun showViewLoading() {
        binding.historyLoadingFrameLayout.visibility = View.VISIBLE
    }

    override fun showViewError(error: String?) {
        binding.historyLoadingFrameLayout.visibility = View.GONE
    }
}