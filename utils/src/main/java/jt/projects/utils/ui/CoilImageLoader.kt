package jt.projects.utils.ui

import android.content.Context
import android.os.Build
import android.widget.ImageView
import coil.Coil
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.load
import jt.projects.utils.R


class CoilImageLoader {
    fun addExtendedDecoders(context: Context) {
        // для возможности загрузки изображений SVG & GIF через библиотеку COIL
        val imageLoader = ImageLoader.Builder(context)
            .components {
                //GIF
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
                //SVG
                add(SvgDecoder.Factory())
            }
            .build()
        Coil.setImageLoader(imageLoader)
    }


    fun loadToRoundedCornersView(imageView: ImageView, imageLink: String) {
        imageView.load(imageLink) {
            crossfade(1500)
            target(
                onStart = { imageView.setImageResource(R.drawable.ic_no_photo_vector) },
                onError = { imageView.setImageResource(R.drawable.ic_load_error_vector) },
                onSuccess = {
                    imageView.setImageDrawable(it)
                })
            val x = 50f
            transformations(coil.transform.RoundedCornersTransformation(x, x, x, x))
        }
    }


    fun loadToCircleView(imageView: ImageView, imageLink: String) {
        imageView.load(imageLink) {
            target(
                onStart = { imageView.setImageResource(R.drawable.ic_no_photo_vector) },
                onError = { imageView.setImageResource(R.drawable.ic_load_error_vector) },
                onSuccess = {
                    imageView.setImageDrawable(it)
                })
            transformations(coil.transform.CircleCropTransformation())
        }
    }

    fun cleanCache() {

    }

}