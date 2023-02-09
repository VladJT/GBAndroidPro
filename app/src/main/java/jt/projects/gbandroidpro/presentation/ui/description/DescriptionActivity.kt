package jt.projects.gbandroidpro.presentation.ui.description

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
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
import jt.projects.gbandroidpro.model.domain.DataModel
import jt.projects.gbandroidpro.presentation.ui.dialogs.AlertDialogFragment
import jt.projects.gbandroidpro.utils.toOneString
import jt.projects.gbandroidpro.utils.ui.CoilImageLoader
import jt.projects.gbandroidpro.utils.ui.showSnackbar
import jt.projects.network.INetworkStatus
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named


class DescriptionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDescriptionBinding

    private val coilImageLoader: CoilImageLoader by inject()


    companion object {
        private const val DIALOG_FRAGMENT_TAG = "8c7dff51-9769-4f6d-bbee-a3896085e76e"
        private const val WORD_EXTRA = "f76a288a-5dcc-43f1-ba89-7fe1d53f63b0"

        fun getIntent(
            context: Context,
            data: DataModel
        ): Intent = Intent(context, DescriptionActivity::class.java).apply {
            putExtra(WORD_EXTRA, data)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.subtitle = "Карточка детализации"
        setActionbarHomeButtonAsUp()

        binding.descriptionScreenSwipeRefreshLayout.setOnRefreshListener {
            startLoadingOrShowError()
        }

        initButtonSound()
        setData()
    }

    fun initButtonSound() {
        binding.buttonSound.setOnClickListener {
            val soundUrl = extractData()?.meanings?.get(0)?.soundUrl
            if (soundUrl.isNullOrEmpty()) {
                showSnackbar("Нет ссылки для прослушивания")
            } else
                if (!getKoin().get<INetworkStatus>(named(NETWORK_SERVICE)).isOnline) {
                    showSnackbar("Отсутствует подключение к Интернет")
                } else {
                    val mp = MediaPlayer()
                    try {
                        mp.setDataSource(soundUrl)
                        mp.prepare()
                        mp.start()
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
        binding.transcription.text = data?.meanings?.get(0)?.transcription
        binding.descriptionTextview.text = data?.meanings?.toOneString()
        val imageLink = data?.meanings?.get(0)?.imageUrl
        if (imageLink.isNullOrBlank()) {
            stopRefreshAnimationIfNeeded()
        } else {
            useCoilToLoadPhoto(binding.descriptionImageview, imageLink)
            // usePicassoToLoadPhoto(binding.descriptionImageview, imageLink)
            //  useGlideToLoadPhoto(binding.descriptionImageview, imageLink)
        }
    }


    private fun startLoadingOrShowError() {
        if (getKoin().get<INetworkStatus>(named(NETWORK_SERVICE)).isOnline) {
            setData()
        } else {
            TODO()
//            AlertDialogFragment(
//                getString(R.string.dialog_title_device_is_offline),
//                getString(R.string.dialog_message_device_is_offline)
//            ).show(
//                supportFragmentManager, DIALOG_FRAGMENT_TAG
//            )
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


}