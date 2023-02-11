package jt.projects.gbandroidpro.presentation.ui.base

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import jt.projects.gbandroidpro.R
import jt.projects.gbandroidpro.databinding.LoadingLayoutBinding
import jt.projects.gbandroidpro.di.NETWORK_SERVICE
import jt.projects.gbandroidpro.presentation.ui.description.DescriptionActivity
import jt.projects.gbandroidpro.presentation.viewmodel.BaseViewModel
import jt.projects.model.data.AppState
import jt.projects.model.data.DataModel
import jt.projects.utils.network.INetworkStatus
import jt.projects.utils.ui.showNoInternetConnectionDialog
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

private const val DIALOG_FRAGMENT_TAG = "74a54328-5d62-46bf-ab6b-cbf5d8c79522"

abstract class BaseActivity<T : AppState> : AppCompatActivity() {

    val baseBinding: LoadingLayoutBinding by lazy { LoadingLayoutBinding.inflate(layoutInflater) }

    private val networkStatus: INetworkStatus by inject(named(NETWORK_SERVICE))

    fun onItemClick(data: DataModel) {
        startActivity(
            DescriptionActivity.getIntent(
                this@BaseActivity,
                data
            )
        )
    }

    // В каждой Активити будет своя ViewModel, которая наследуется от BaseViewModel
    abstract val model: BaseViewModel<T>

    override fun onResume() {
        super.onResume()
       // baseBinding = LoadingLayoutBinding.inflate(layoutInflater)
        if (!networkStatus.isOnline && isDialogNull()) {
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
//                if (appState.progress != null) {
//                    binding.progressBarHorizontal.visibility = VISIBLE
//                    binding.progressBarRound.visibility = GONE
//                    binding.progressBarHorizontal.progress = appState.progress
//                } else {
//                    binding.progressBarHorizontal.visibility = GONE
//                    binding.progressBarRound.visibility = VISIBLE
//                }
            }
            is AppState.Error -> {
                showViewError(appState.error.message)
            }
        }
    }

    open fun showViewSuccess() {
        baseBinding.loadingFrameLayout.visibility = View.GONE
    }

    open fun showViewLoading() {
        baseBinding.loadingFrameLayout.visibility = View.VISIBLE
    }

    open fun showViewError(error: String?) {
        baseBinding.loadingFrameLayout.visibility = View.GONE
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