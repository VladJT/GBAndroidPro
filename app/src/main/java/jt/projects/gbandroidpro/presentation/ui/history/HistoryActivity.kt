package jt.projects.gbandroidpro.presentation.ui.history

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import jt.projects.gbandroidpro.R
import jt.projects.gbandroidpro.databinding.ActivityHistoryBinding
import jt.projects.gbandroidpro.model.domain.AppState
import jt.projects.gbandroidpro.model.domain.DataModel
import jt.projects.gbandroidpro.presentation.ui.base.BaseActivity
import jt.projects.gbandroidpro.presentation.viewmodel.HistoryViewModel
import jt.projects.gbandroidpro.utils.ui.showSnackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryActivity : BaseActivity<AppState>() {


    override val model: HistoryViewModel by viewModel()

    private lateinit var binding: ActivityHistoryBinding
    private val historyAdapter: HistoryAdapter by lazy { HistoryAdapter(::onItemClick) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.subtitle = "История запросов"
        setActionbarHomeButtonAsUp()

        iniViewModel()
        initViews()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.menu_history -> {
                showDeleteDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDeleteDialog() {
        model.cleanHistory()
        showSnackbar("Данные истории очищены")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    private fun setActionbarHomeButtonAsUp() {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // Сразу запрашиваем данные из локального репозитория
    override fun onResume() {
        super.onResume()
        model.getData("")
    }

    private fun iniViewModel() {
        if (binding.historyActivityRecyclerview.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
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
}