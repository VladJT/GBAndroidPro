package jt.projects.core.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import jt.projects.core.R
import jt.projects.model.data.DataModel
import jt.projects.utils.INTENT_ACTION_WIDGET_CLICKED
import jt.projects.utils.WIDGET_DATA
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL


class JTTranslatorWidget : AppWidgetProvider() {

    // вызывается при добавлении виджета
    override fun onEnabled(context: Context) {
        super.onEnabled(context)
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
    }

    //вызывается при изменении размеров  виджета
    override fun onAppWidgetOptionsChanged(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
    }

    override fun onUpdate(
        context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
//        appWidgetIds.forEach { appWidgetId ->
//            RemoteViews(context.packageName, R.layout.widget_layout).apply {
//
//                CoroutineScope(Dispatchers.IO).launch {
//                    setTextViewText(R.id.appwidget_word, Random.nextInt().toString())
//                    setTextViewText(R.id.appwidget_meanings, "1, 2, 3")
//                    appWidgetManager.updateAppWidget(appWidgetId, this@apply)
//                }
//            }
//        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val appWidgetId = ComponentName(context, JTTranslatorWidget::class.java)

        if (intent.action == INTENT_ACTION_WIDGET_CLICKED) {
            RemoteViews(context.packageName, R.layout.widget_layout).apply {
                CoroutineScope(Dispatchers.IO).launch {
                    setTextViewText(R.id.appwidget_word, "Random.nextInt().toString()")
                    appWidgetManager.updateAppWidget(appWidgetId, this@apply)
                }
                return
            }
        }

        val _intent = Intent(context, JTTranslatorWidget::class.java).apply {
            action = INTENT_ACTION_WIDGET_CLICKED
        }
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, _intent, PendingIntent.FLAG_IMMUTABLE)

        val data: DataModel? = intent.getParcelableExtra(WIDGET_DATA)

        RemoteViews(context.packageName, R.layout.widget_layout).apply {
            //setOnClickPendingIntent(R.id.appwidget_layout, pendingIntent)

            CoroutineScope(Dispatchers.IO).launch {
                data?.let {
                    renderData(it)
                    appWidgetManager.updateAppWidget(appWidgetId, this@apply)
                }
            }
        }
    }

    private fun RemoteViews.renderData(data: DataModel) {
        setTextViewText(R.id.appwidget_word, data.text)
        setTextViewText(R.id.appwidget_meanings, data.meanings)
        data.imageUrl.let {
            try {
                val bitmap = bitmapFromUri(it)
                setImageViewBitmap(R.id.appwidget_image, bitmap)
            } catch (e: Exception) {
                Log.e("TAG", e.message.toString())
            }
        }
    }

    private fun bitmapFromUri(it: String): Bitmap? {
        val index = it.indexOf("https://", 0, true)
        val src = it.substring(index)
        val url = URL(src)
        val connection: HttpURLConnection =
            url.openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()
        val input = connection.inputStream
        //Bitmap b = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)
        //profileImage.setImageBitmap(Bitmap.createScaledBitmap(b, 120, 120, false));
        val bitmap =  BitmapFactory.decodeStream(input)
        return Bitmap.createScaledBitmap(bitmap, 300,300,false)
    }
}
