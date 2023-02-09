package jt.projects.gbandroidpro.presentation.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import jt.projects.gbandroidpro.R
import jt.projects.gbandroidpro.databinding.ActivityMainBinding
import jt.projects.gbandroidpro.model.domain.AppState
import jt.projects.gbandroidpro.model.domain.DataModel
import jt.projects.gbandroidpro.presentation.ui.base.BaseActivity
import jt.projects.gbandroidpro.presentation.ui.dialogs.AlertDialogCallback
import jt.projects.gbandroidpro.presentation.ui.dialogs.AlertDialogFragment
import jt.projects.gbandroidpro.presentation.ui.dialogs.SearchDialogFragment
import jt.projects.gbandroidpro.presentation.viewmodel.MainViewModel
import jt.projects.gbandroidpro.utils.BOTTOM_SHEET_FRAGMENT_DIALOG_TAG
import jt.projects.gbandroidpro.utils.Test
import jt.projects.gbandroidpro.utils.ui.showSnackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.getKoin


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

    private val mainAdapter: MainAdapter by lazy { MainAdapter(::onItemClick) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        val session =
//            getKoin().createScope("main_activity_scope_ID", named("main_activity_scope_ID"))

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            subtitle = "Главное окно"
        }

        initViewModel()
        initFabButton()
        initRecView()

        binding.searchEditText.doOnTextChanged { text, start, before, count ->
            model.getData(text.toString())
        }

        test()
    }

    private fun initRecView() {
        binding.mainActivityRecyclerview.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = mainAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                AlertDialogFragment.newInstance(
                    AlertDialogCallback(getString(R.string.dialog_title_device_is_offline),
                        getString(R.string.dialog_message_device_is_offline),
                        { showSnackbar("ok") })
                ).show(supportFragmentManager, AlertDialogFragment.TAG)
                // startActivity(Intent(this, HistoryActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
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
    }


    private fun initFabButton() {
        binding.searchFab.setOnClickListener {
            setBlur(binding.root, true)

            val onSearchDialogClose: (String?) -> Unit = { word: String? ->
                if (word != null) binding.searchEditText.setText(word)
                setBlur(binding.root, false)
            }

            val searchDialogFragment = SearchDialogFragment.newInstance(onSearchDialogClose)

            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
    }


    override fun showViewSuccess() {
        binding.loadingFrameLayout.visibility = View.GONE
        binding.errorLinearLayout.visibility = View.GONE
    }

    override fun showViewLoading() {
        binding.loadingFrameLayout.visibility = View.VISIBLE
        binding.errorLinearLayout.visibility = View.GONE
    }

    override fun showViewError(error: String?) {
        binding.errorTextview.text = error ?: getString(R.string.undefined_error)
        binding.reloadButton.setOnClickListener {
            binding.searchEditText.setText("test")
        }
        binding.loadingFrameLayout.visibility = View.GONE
        binding.errorLinearLayout.visibility = View.VISIBLE
    }

    override fun setDataToAdapter(data: List<DataModel>) = mainAdapter.setData(data)
}