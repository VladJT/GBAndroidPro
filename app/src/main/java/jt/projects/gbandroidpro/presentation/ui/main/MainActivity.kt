package jt.projects.gbandroidpro.presentation.ui.main

import android.annotation.SuppressLint
import android.appwidget.AppWidgetManager
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jt.projects.core.BaseActivity
import jt.projects.core.splash_screen.AnimatedSplashScreen
import jt.projects.core.splash_screen.showSplashScreen
import jt.projects.core.widget.JTTranslatorWidget
import jt.projects.gbandroidpro.BuildConfig
import jt.projects.gbandroidpro.R
import jt.projects.gbandroidpro.databinding.ActivityMainBinding
import jt.projects.gbandroidpro.di.getWordFromSharedPref
import jt.projects.gbandroidpro.others.Test
import jt.projects.gbandroidpro.presentation.ui.description.DescriptionActivity
import jt.projects.gbandroidpro.presentation.ui.history.HistoryActivity
import jt.projects.model.data.AppState
import jt.projects.model.data.DataModel
import jt.projects.utils.FAKE
import jt.projects.utils.LOG_TAG
import jt.projects.utils.ViewModelNotInitException
import jt.projects.utils.WIDGET_DATA
import jt.projects.utils.ui.showSnackbar
import jt.projects.utils.ui.showToast
import jt.projects.utils.ui.viewById
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.getKoin
import java.util.*


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


@SuppressLint("Instantiatable")
class MainActivity : BaseActivity<AppState>() {
    companion object {
        const val REQUEST_CODE_VOICE = 10
    }

    var lastResultCount = 0

    // override val model: MainViewModel by scope.inject() // привязана к жизненному циклу Activity
    override val model: MainViewModel by viewModel() // НЕ привязана к жизненному циклу Activity

    private val testScope by lazy { getKoin().createScope("", named("test_scope")) }

    private lateinit var binding: ActivityMainBinding

    private val mainAdapter: MainAdapter by lazy { MainAdapter(::onItemClick) }

    private fun onItemClick(data: DataModel) {
        startActivity(
            DescriptionActivity.getIntent(
                this,
                data
            )
        )

        val appWidgetManager = AppWidgetManager.getInstance(this)
        val appWidgetId = ComponentName(this, JTTranslatorWidget::class.java)
        val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE).apply {
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetId)
            putExtra(WIDGET_DATA, data)
        }
        sendBroadcast(intent)
    }


    override fun onLoadingProgressChange(value: Int) {
        super.onLoadingProgressChange(value)
        binding.progressBarHorizontal.progress = value
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState == null)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                showSplashScreen { AnimatedSplashScreen() }
            } else {
                Thread.sleep(1000)
            }

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            subtitle = getString(R.string.subtitle_main_activity)
        }

        initViewModel()
        initFabButton()
        initRecView()

        binding.searchEditText.doOnTextChanged { text, start, before, count ->
            model.getData(text.toString())
        }

        //test()
        if (BuildConfig.TYPE == FAKE) {
            showToast(BuildConfig.VERSION_NAME)
        }
        binding.searchEditText.setText(getWordFromSharedPref())
    }

    private fun initRecView() {
        val mainActivityRecyclerview by viewById<RecyclerView>(viewId = R.id.main_activity_recyclerview)
        mainActivityRecyclerview?.layoutManager = LinearLayoutManager(applicationContext)
        mainActivityRecyclerview?.adapter = mainAdapter
//        binding.mainActivityRecyclerview.apply {
//            layoutManager = LinearLayoutManager(applicationContext)
//            adapter = mainAdapter
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                startActivity(Intent(this, HistoryActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun test() {
        val t = testScope.get<Test> { parametersOf("Hello, world: ") }
        println(theme)
        Toast.makeText(this, t.show(), Toast.LENGTH_SHORT).show()
    }

    private fun initViewModel() {
        // Убедимся, что модель инициализируется раньше View
        if (binding.mainActivityRecyclerview.adapter != null) {
            throw ViewModelNotInitException
        }

        model.liveDataForViewToObserve.observe(this, Observer<AppState> {
            renderData(it)
        })
    }


    private fun initFabButton() {
        binding.searchFab.setOnClickListener {
            try {
                setBlur(binding.root, true)
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                intent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                startActivityForResult(intent, REQUEST_CODE_VOICE)
            } catch (e: ActivityNotFoundException) {
                Log.e(LOG_TAG, e.message.toString())
                showSnackbar(getString(R.string.no_app_for_voice_input))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        setBlur(binding.root, false)
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            when (requestCode) {
                REQUEST_CODE_VOICE -> {
                    val text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    binding.searchEditText.setText(text!![0])
                }
            }
        }
    }


    override fun showViewSuccess() {
        binding.mainLoadingFrameLayout.visibility = View.GONE
        binding.errorLinearLayout.visibility = View.GONE
    }

    override fun showViewLoading() {
        binding.mainLoadingFrameLayout.visibility = View.VISIBLE
        binding.errorLinearLayout.visibility = View.GONE
    }

    override fun showViewError(error: String?) {
        binding.mainLoadingFrameLayout.visibility = View.GONE
        binding.errorTextview.text = error ?: getString(R.string.undefined_error)
        binding.reloadButton.setOnClickListener {
            binding.searchEditText.setText("word")
        }
        binding.errorLinearLayout.visibility = View.VISIBLE
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        mainAdapter.setData(data)
        lastResultCount = data.size
    }

    override fun onDestroy() {
        testScope.close()// если не закрыть, будет выбрасываться исключение при пересоздании Activity
        super.onDestroy()
    }
}