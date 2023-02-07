package jt.projects.gbandroidpro.utils.custom_view

import android.widget.ImageView
import coil.load
import jt.projects.gbandroidpro.R

class CoilImageLoader {

    fun loadToRoundedCornersView(imageView: ImageView, imageLink: String) {
        imageView.load(imageLink) {
            crossfade(true)
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