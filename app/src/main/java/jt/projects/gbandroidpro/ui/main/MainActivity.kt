package jt.projects.gbandroidpro.ui.main

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import jt.projects.gbandroidpro.R
import jt.projects.gbandroidpro.databinding.ActivityMainBinding
import jt.projects.gbandroidpro.model.domain.AppState
import jt.projects.gbandroidpro.model.domain.DataModel
import jt.projects.gbandroidpro.presenter.MainPresenterImpl
import jt.projects.gbandroidpro.presenter.Presenter
import jt.projects.gbandroidpro.ui.base.BaseActivity
import jt.projects.gbandroidpro.ui.base.BaseView
import jt.projects.gbandroidpro.ui.search_dialog.OnSearchClickListener
import jt.projects.gbandroidpro.ui.search_dialog.SearchDialogFragment
import jt.projects.gbandroidpro.utils.BOTTOM_SHEET_FRAGMENT_DIALOG_TAG

/**
 *  мы получаем многослойную чистую архитектуру, где каждый слой занимается своими задачами:
● View отвечает только за отображение данных
● презентер решает, когда и что загружать и отображать
● интерактор содержит все возможные кейсы использования приложения (у нас пока только
загрузка данных)
● репозиторий отвечает за получение и хранение данных
● источник данных отвечает за получение данных из интернета или БД
● Retrofit обращается к серверу и получает от него ответ, который мы интерпретируем в виде
модели данных для нашего приложения

!! При этом ни один слой ничего не знает о нижележащих слоях и никак не завязан на платформу или
другие классы. Такой код легко читать и переиспользовать. В такой архитектуре сейчас создаётся
очень много проектов. Их легко поддерживать, расширять и тестировать.
 */
class MainActivity : BaseActivity<AppState>() {

    private lateinit var binding: ActivityMainBinding
    private var adapter: MainAdapter? = null

    private val onListItemClickListener = object : MainAdapter.OnListItemClickListener {
        override fun onItemClick(data: DataModel) {
            Toast.makeText(this@MainActivity, data.text, Toast.LENGTH_SHORT).show()
        }
    }

    override fun createPresenter(): Presenter<AppState, BaseView> {
        return MainPresenterImpl()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFabButton()
    }

    private fun initFabButton() {
        binding.searchFab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object : OnSearchClickListener {
                override fun onClick(searchWord: String) {
                    presenter.getData(searchWord, isOnline = true)
                }
            })
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
    }

    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val data = appState.data
                if (data.isNullOrEmpty()) {
                    showViewError(getString(R.string.empty_server_response_on_success))
                } else {
                    showViewSuccess()
                    if (adapter == null) {
                        binding.mainActivityRecyclerview.apply {
                            layoutManager = LinearLayoutManager(applicationContext)
                            adapter = MainAdapter(onListItemClickListener, data)
                        }

                    } else {
                        adapter?.setData(data)
                    }
                }

            }
            is AppState.Loading -> {
                showViewLoading()
                if (appState.progress != null) {
                    binding.progressBarHorizontal.visibility = VISIBLE
                    binding.progressBarRound.visibility = GONE
                    binding.progressBarHorizontal.progress = appState.progress
                } else {
                    binding.progressBarHorizontal.visibility = GONE
                    binding.progressBarRound.visibility = VISIBLE
                }
            }
            is AppState.Error -> {
                showViewError(appState.error.message)
            }
        }
    }

    private fun showViewSuccess() {
        binding.successLinearLayout.visibility = VISIBLE
        binding.loadingFrameLayout.visibility = GONE
        binding.errorLinearLayout.visibility = GONE
    }

    private fun showViewLoading() {
        binding.successLinearLayout.visibility = GONE
        binding.loadingFrameLayout.visibility = VISIBLE
        binding.errorLinearLayout.visibility = GONE
    }

    private fun showViewError(error: String?) {
        binding.errorTextview.text = error ?: getString(R.string.undefined_error)
        binding.reloadButton.setOnClickListener {
            presenter.getData("hi", true)
        }
        binding.successLinearLayout.visibility = GONE
        binding.loadingFrameLayout.visibility = GONE
        binding.errorLinearLayout.visibility = VISIBLE
    }
}