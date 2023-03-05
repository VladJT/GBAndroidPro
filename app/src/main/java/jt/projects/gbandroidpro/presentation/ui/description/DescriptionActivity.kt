package jt.projects.gbandroidpro.presentation.ui.description

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import jt.projects.gbandroidpro.R
import jt.projects.gbandroidpro.databinding.ActivityDescriptionBinding
import jt.projects.gbandroidpro.di.NETWORK_SERVICE
import jt.projects.model.data.DataModel
import jt.projects.utils.network.INetworkStatus
import jt.projects.utils.ui.CoilImageLoader
import jt.projects.utils.ui.showNoInternetConnectionDialog
import jt.projects.utils.ui.showSnackbar
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named


class DescriptionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDescriptionBinding

    private val coilImageLoader: CoilImageLoader by inject()

    var mediaPlayer: MediaPlayer? = null
    var isPressed = false

    companion object {
        const val WORD_EXTRA = "f76a288a-5dcc-43f1-ba89-7fe1d53f63b0"

        fun getIntent(
            context: Context,
            data: DataModel
        ): Intent = Intent(context, DescriptionActivity::class.java).apply {
            putExtra(WORD_EXTRA, data)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // убираем splash screen
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            setTheme(jt.projects.core.R.style.Theme_GBAndroidPro)
        }
        super.onCreate(savedInstanceState)

        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.subtitle = "Карточка детализации"
        setActionbarHomeButtonAsUp()

        binding.descriptionScreenSwipeRefreshLayout.setOnRefreshListener {
            startLoadingOrShowError()
        }

        val soundUrl = extractData()?.soundUrl
        initButtonSound(soundUrl)
        setData()
    }

    private fun initButtonSound(soundUrl: String?) {
        binding.buttonSound.setOnClickListener {
            isPressed = true
            if (soundUrl.isNullOrEmpty()) {
                showSnackbar("Нет ссылки для прослушивания")
            } else
                if (!getKoin().get<INetworkStatus>(named(NETWORK_SERVICE)).isOnline()) {
                    showNoInternetConnectionDialog()
                } else {
                    try {
                        if (mediaPlayer == null) {
                            mediaPlayer = MediaPlayer()
                            mediaPlayer?.setDataSource(soundUrl)
                            mediaPlayer?.prepare()
                        }
                        mediaPlayer?.start()
                    } catch (e: Exception) {
                        showSnackbar("Error: Couldn't Play the Audio")
                    }
                }

        }
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

    private fun extractData(): DataModel? = intent.getParcelableExtra(WORD_EXTRA)

    private fun setData() {
        val data = extractData()
        binding.descriptionHeader.text = data?.text
        binding.transcription.text = data?.transcription
        binding.descriptionTextview.text = data?.meanings
        val imageLink = data?.imageUrl
        if (imageLink.isNullOrBlank()) {
            stopRefreshAnimationIfNeeded()
        } else {
            useCoilToLoadPhoto(binding.descriptionImageview, imageLink)
            // usePicassoToLoadPhoto(binding.descriptionImageview, imageLink)
            //  useGlideToLoadPhoto(binding.descriptionImageview, imageLink)
        }
    }


    private fun startLoadingOrShowError() {
        if (getKoin().get<INetworkStatus>(named(NETWORK_SERVICE)).isOnline()) {
            setData()
        } else {
            showSnackbar(getString(R.string.dialog_message_device_is_offline))
            stopRefreshAnimationIfNeeded()
        }
    }

    private fun stopRefreshAnimationIfNeeded() {
        if (binding.descriptionScreenSwipeRefreshLayout.isRefreshing) {
            binding.descriptionScreenSwipeRefreshLayout.isRefreshing = false
        }
    }

    private fun usePicassoToLoadPhoto(imageView: ImageView, imageLink: String) {
        Picasso.get().load("https:$imageLink").placeholder(R.drawable.ic_no_photo_vector).fit()
            .centerCrop().into(imageView, object : Callback {
                override fun onSuccess() {
                    stopRefreshAnimationIfNeeded()
                }

                override fun onError(e: Exception?) {
                    stopRefreshAnimationIfNeeded()
                    imageView.setImageResource(R.drawable.ic_load_error_vector)
                }
            })
    }

    private fun useCoilToLoadPhoto(imageView: ImageView, imageLink: String) {
        coilImageLoader.loadToRoundedCornersView(imageView, "https:$imageLink")
        // getKoin().get<CoilImageLoader>().loadToRoundedCornersView(imageView, "https:$imageLink")
    }

    private fun useGlideToLoadPhoto(imageView: ImageView, imageLink: String) {
        Glide.with(imageView)
            .load("https:$imageLink")
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    stopRefreshAnimationIfNeeded()
                    imageView.setImageResource(R.drawable.ic_load_error_vector)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    stopRefreshAnimationIfNeeded()
                    return false
                }
            }).apply(
                RequestOptions().placeholder(R.drawable.ic_no_photo_vector).centerCrop()
            ).into(imageView)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }

}