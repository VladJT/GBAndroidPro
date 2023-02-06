package jt.projects.gbandroidpro.presentation.ui.main

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import jt.projects.gbandroidpro.R
import jt.projects.gbandroidpro.databinding.ActivityMainBinding
import jt.projects.gbandroidpro.model.domain.AppState
import jt.projects.gbandroidpro.model.domain.DataModel
import jt.projects.gbandroidpro.presentation.ui.base.BaseActivity
import jt.projects.gbandroidpro.presentation.ui.search_dialog.OnSearchClickListener
import jt.projects.gbandroidpro.presentation.ui.search_dialog.SearchDialogFragment
import jt.projects.gbandroidpro.presentation.viewmodel.MainViewModel
import jt.projects.gbandroidpro.utils.BOTTOM_SHEET_FRAGMENT_DIALOG_TAG
import jt.projects.gbandroidpro.utils.Test
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

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
    // Теперь ViewModel инициализируется через функцию by viewModel()
    // Это функция, предоставляемая Koin из коробки через зависимость
    override val model: MainViewModel by viewModel()

    private lateinit var binding: ActivityMainBinding
    private var adapter: MainAdapter? = null

    private val onListItemClickListener = object : MainAdapter.OnListItemClickListener {
        override fun onItemClick(data: DataModel) {
            Toast.makeText(this@MainActivity, data.text, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        val session =
//            getKoin().createScope("main_activity_scope_ID", named("main_activity_scope_ID"))

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        initBtnPlus()
        initFabButton()

        binding.searchEditText.doOnTextChanged { text, start, before, count ->
            model.getData(text.toString())
        }

        test()
    }

    private fun initBtnPlus() {
        binding.btnPlus.setOnClickListener {
            model.counter.value = model.counter.value?.plus(1)
        }
    }


    private fun test() {
        val t = getKoin().get<Test> { parametersOf("Hello, world") }
        Toast.makeText(this, t.show(), Toast.LENGTH_SHORT).show()
    }

    private fun initViewModel() {
        // Убедимся, что модель инициализируется раньше View
        if (binding.mainActivityRecyclerview.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }

        model.liveDataForViewToObserve.observe(this, Observer<AppState> {
            renderData(it)
        })

        model.counter.observe(this) {
            binding.btnPlus.text = model.counter.value.toString()
        }
    }


    private fun initFabButton() {
        binding.searchFab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object : OnSearchClickListener {
                override fun onClick(searchWord: String) {
                    //model.getData(searchWord)
                    binding.searchEditText.setText(searchWord)
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
        binding.loadingFrameLayout.visibility = GONE
        binding.errorLinearLayout.visibility = GONE
    }

    private fun showViewLoading() {
        binding.loadingFrameLayout.visibility = VISIBLE
        binding.errorLinearLayout.visibility = GONE
    }

    private fun showViewError(error: String?) {
        binding.errorTextview.text = error ?: getString(R.string.undefined_error)
        binding.reloadButton.setOnClickListener {
            binding.searchEditText.setText("test")
        }
        binding.loadingFrameLayout.visibility = GONE
        binding.errorLinearLayout.visibility = VISIBLE
    }


//    var isOnline = true
//    private val receiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context?, intent: Intent) {
//            isOnline = intent.getBooleanExtra("STATUS", true)
//            Toast.makeText(this@MainActivity, "Internet: $isOnline", Toast.LENGTH_SHORT).show()
//        }
//    }

    override fun onStart() {
        super.onStart()
        //     startService(Intent(this, NetworkStatusService::class.java))
//        LocalBroadcastManager.getInstance(this)
//            .registerReceiver(receiver, IntentFilter(NETWORK_STATUS_INTENT_FILTER))
    }

    override fun onStop() {
        super.onStop()
//        LocalBroadcastManager.getInstance(this)
//            .unregisterReceiver(receiver)
//        stopService(Intent(this, NetworkStatusService::class.java))
    }
}