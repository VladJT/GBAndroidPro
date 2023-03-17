package jt.projects.gbandroidpro.presentation.ui.description


import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import jt.projects.gbandroidpro.R
import jt.projects.gbandroidpro.databinding.FragmentDescriptionBinding
import jt.projects.gbandroidpro.presentation.ui.description.DescriptionActivity.Companion.DATA_KEY
import jt.projects.model.data.DataModel
import jt.projects.utils.NETWORK_SERVICE
import jt.projects.utils.network.INetworkStatus
import jt.projects.utils.ui.CoilImageLoader
import jt.projects.utils.ui.showNoInternetConnectionDialog
import jt.projects.utils.ui.showSnackbar
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named


class DescriptionFragment : Fragment() {
    private var _binding: FragmentDescriptionBinding? = null
    private val binding get() = _binding!!

    private val coilImageLoader: CoilImageLoader by inject()

    var mediaPlayer: MediaPlayer? = null
    var isPressed = false

    private fun extractData(): DataModel? = arguments?.getParcelable(DATA_KEY)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDescriptionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                showSnackbar(getString(R.string.empty_audio_link))
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
                        showSnackbar(getString(R.string.error_play_audio))
                    }
                }
        }
    }

    private fun setData() {
        val data = extractData()
        binding.descriptionHeader.text = data?.text
        binding.transcription.text = data?.transcription
        binding.descriptionTextview.text = data?.meanings
        val imageLink = data?.imageUrl
        if (imageLink.isNullOrBlank()) {
            stopRefreshAnimationIfNeeded()
        } else {
            useCoilToLoadPhoto(_binding!!.descriptionImageview, imageLink)
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
                    target: Target<Drawable>?,
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
        _binding = null
    }
}