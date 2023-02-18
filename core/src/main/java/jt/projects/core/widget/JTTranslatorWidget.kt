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
import kotlin.random.Random


class JTTranslatorWidget : AppWidgetProvider() {

    // вызывается при добавлении виджета
    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
    }

    override fun onUpdate(
        context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        val intent = Intent(context, JTTranslatorWidget::class.java).apply {
            action = INTENT_ACTION_WIDGET_CLICKED
        }
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)


        appWidgetIds.forEach { appWidgetId ->
            RemoteViews(context.packageName, R.layout.widget_layout).apply {
                setOnClickPendingIntent(R.id.appwidget_layout, pendingIntent)
                CoroutineScope(Dispatchers.IO).launch {
                    setTextViewText(R.id.appwidget_word, "123")
                    setTextViewText(R.id.appwidget_meanings, "1, 2, 3")
                    appWidgetManager.updateAppWidget(appWidgetId, this@apply)
                }
            }
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == INTENT_ACTION_WIDGET_CLICKED) {
            RemoteViews(context.packageName, R.layout.widget_layout).apply {
                CoroutineScope(Dispatchers.IO).launch {
                    setTextViewText(R.id.appwidget_word, Random.nextInt().toString())
                }
                return
            }
        }

        val data: DataModel? = intent.getParcelableExtra(WIDGET_DATA)

        val appWidgetManager = AppWidgetManager.getInstance(context)
        val appWidgetId = ComponentName(context, JTTranslatorWidget::class.java)

        RemoteViews(context.packageName, R.layout.widget_layout).apply {
            CoroutineScope(Dispatchers.IO).launch {

                data?.let { setTextViewText(R.id.appwidget_word, it.text) }
                data?.meanings?.let { setTextViewText(R.id.appwidget_meanings, it) }
                data?.imageUrl?.let {
                    try {
                        val bitmap = bitmapFromUri(it)
                        setImageViewBitmap(R.id.appwidget_image, bitmap)
                    } catch (e: Exception) {
                        Log.e("TAG", e.message.toString())
                    }
                }
                appWidgetManager.updateAppWidget(appWidgetId, this@apply)
            }

        }

    }

    //вызывается при изменении размеров  виджета
    override fun onAppWidgetOptionsChanged(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
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
        return BitmapFactory.decodeStream(input)
    }
}
