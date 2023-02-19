package jt.projects.core

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.View
import jt.projects.core.databinding.LoadingLayoutBinding
import jt.projects.core.splash_screen.AnimatedSplashScreen
import jt.projects.core.splash_screen.showSplashScreen
import jt.projects.model.data.AppState
import jt.projects.model.data.DataModel
import jt.projects.utils.network.OnlineLiveData
import jt.projects.utils.ui.showNoInternetConnectionDialog
import org.koin.androidx.scope.ScopeActivity

private const val DIALOG_FRAGMENT_TAG = "74a54328-5d62-46bf-ab6b-cbf5d8c79522"

abstract class BaseActivity<T : AppState> : ScopeActivity() {

    protected var isNetworkAvailable: Boolean = true

    val baseBinding: LoadingLayoutBinding by lazy { LoadingLayoutBinding.inflate(layoutInflater) }

    // В каждой Активити будет своя ViewModel, которая наследуется от BaseViewModel
    abstract val model: BaseViewModel<T>

    override fun onCreate(savedInstanceState: Bundle?) {
        // убираем splash screen
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            setTheme(R.style.Theme_GBAndroidPro)
        }
        super.onCreate(savedInstanceState)
        subscribeToNetworkChange()
    }

    private fun subscribeToNetworkChange() {
        OnlineLiveData(this).observe(this@BaseActivity) {
            isNetworkAvailable = it
            if (!isNetworkAvailable) {
                showNoInternetConnectionDialog()
                supportActionBar?.apply {
                    title = applicationInfo.loadLabel(packageManager).toString()
                }
            } else {
                supportActionBar?.apply {
                    title = title.toString() + " ⚡"
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isNetworkAvailable) {
            showNoInternetConnectionDialog()
        }
    }

    protected fun renderData(appState: T) {
        when (appState) {
            is AppState.Success -> {
                val data = appState.data
                if (data.isNullOrEmpty()) {
                    showViewError(getString(R.string.empty_server_response_on_success))
                    setDataToAdapter(listOf())
                } else {
                    showViewSuccess()
                    setDataToAdapter(data)
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                appState.progress?.let { onLoadingProgressChange(it) }
            }
            is AppState.Error -> {
                showViewError(appState.error.message)
            }
        }
    }

    open fun onLoadingProgressChange(value: Int) {
        baseBinding.progressBarHorizontal.progress = value
    }

    open fun showViewSuccess() {
        //    baseBinding.loadingFrameLayout.visibility = View.GONE
    }

    open fun showViewLoading() {
        //    baseBinding.loadingFrameLayout.visibility = View.VISIBLE
    }

    open fun showViewError(error: String?) {
        //   baseBinding.loadingFrameLayout.visibility = View.GONE
    }

    fun setBlur(view: View, isBlur: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val blurEffect = RenderEffect.createBlurEffect(
                15f, 0f,
                Shader.TileMode.CLAMP
            )
            if (isBlur) {
                view.setRenderEffect(blurEffect)
            } else {
                view.setRenderEffect(null)
            }
        }
    }

    private fun isDialogNull(): Boolean {
        return supportFragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG) == null
    }

    // Объявим абстрактный метод и будем вызывать его в renderData, когда данные
    // будут готовы для отображения
    abstract fun setDataToAdapter(data: List<DataModel>)
}