package com.san.ipassmanager.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.signature.ObjectKey
import com.san.ipassmanager.R
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList

//General
inline fun <T> T.applyIf(condition: Boolean, block: T.() -> Unit): T {
    return if (condition) this.apply(block) else this
}

// LiveData
fun <T> MutableLiveData<List<T>>.add(item: T) {
    val updatedItems = ArrayList(this.value.orEmpty())
    updatedItems.add(item)
    this.value = updatedItems
}

fun <T> MutableLiveData<List<T>>.remove(item: T) {
    val updatedItems = ArrayList(this.value.orEmpty())
    updatedItems.remove(item)
    this.value = updatedItems
}

// View


fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeInVisible() {
    visibility = View.INVISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}

fun View.hideKeyboard() {
    val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(windowToken, 0)
}

fun View.getWidthInDp() = (width * resources.displayMetrics.density + 0.5f).toInt()


fun View.setPaddingInDp(left: Int, top: Int, right: Int, bottom: Int) {

    fun getDp(pixels: Int) = (pixels * resources.displayMetrics.density + 0.5f).toInt()

    setPadding(getDp(left), getDp(top), getDp(right), getDp(bottom))

}

//text view
fun TextView.setHtmlText(htmlText: String?) {

    htmlText?.let {

        this.text =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                Html.fromHtml(it, Html.FROM_HTML_MODE_COMPACT)

            } else {

                Html.fromHtml(it)
            }
    }
}

fun TextView.setTextColorRes(@ColorRes color: Int) = setTextColor(
    ContextCompat.getColor(context, color)
)


// String

fun String?.toDisplayDate() =
    try {
        LocalDate.parse(this, DateTimeFormatter.ISO_LOCAL_DATE)
            .format(DateTimeFormatter.ofPattern("dd-MM-uuuu"))
    } catch (e: Exception) {
        null
    }

fun String?.toDisplayTime() =
    try {
        LocalDate.parse(this, DateTimeFormatter.ISO_LOCAL_TIME)
            .format(DateTimeFormatter.ofPattern("hh:mm a"))
    } catch (e: Exception) {
        null
    }

fun String?.toDisplayDateTime() =
    try {
        LocalDateTime.parse(this, DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss"))
            .format(DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm:ss"))
    } catch (e: Exception) {
        null
    }


// Collection
operator fun <T> Collection<T>.plus(element: T): List<T> {
    val result = ArrayList<T>(size + 1)
    result.addAll(this)
    result.add(element)
    return result
}

// NavController

/**
 * Makes sure the action is known to the current destination before navigating
 */
fun NavController.navigateSafely(navDirections: NavDirections) {
    try {
        currentDestination?.getAction(navDirections.actionId)
            ?.let { navigate(navDirections) }
    } catch (e: IllegalArgumentException) {
        Log.e(this::class.simpleName, "Multiple navigation attempts handled.")
    }
}

//export recycler view images

fun getRecyclerViewScreenshot(view: RecyclerView): Bitmap? {
    val size = view.adapter!!.itemCount
    val holder = view.adapter!!.createViewHolder(view, 0)
    view.adapter!!.onBindViewHolder(holder, 0)
    holder.itemView.measure(
        View.MeasureSpec.makeMeasureSpec(
            view.width,
            View.MeasureSpec.EXACTLY
        ),
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    )
    holder.itemView.layout(
        0,
        0,
        holder.itemView.measuredWidth,
        holder.itemView.measuredHeight
    )
    val bigBitmap = Bitmap.createBitmap(
        view.measuredWidth, holder.itemView.measuredHeight * size,
        Bitmap.Config.ARGB_8888
    )
    val bigCanvas = Canvas(bigBitmap)
    bigCanvas.drawColor(Color.WHITE)
    val paint = Paint()
    var iHeight = 0
    holder.itemView.isDrawingCacheEnabled = true
    holder.itemView.buildDrawingCache()
    bigCanvas.drawBitmap(holder.itemView.drawingCache, 0f, iHeight.toFloat(), paint)
    holder.itemView.isDrawingCacheEnabled = false
    holder.itemView.destroyDrawingCache()
    iHeight += holder.itemView.measuredHeight
    for (i in 1 until size) {
        view.adapter!!.onBindViewHolder(holder, i)
        holder.itemView.isDrawingCacheEnabled = true
        holder.itemView.buildDrawingCache()
        bigCanvas.drawBitmap(holder.itemView.drawingCache, 0f, iHeight.toFloat(), paint)
        iHeight += holder.itemView.measuredHeight
        holder.itemView.isDrawingCacheEnabled = false
        holder.itemView.destroyDrawingCache()
    }
    return bigBitmap
}


// ImageView
fun ImageView.loadImage(
    url: String?,
    errorDrawableResId: Int = R.drawable.ic_logo,
    isFullSize: Boolean = false,
    isCircleCrop: Boolean = false
) {
    val requestBuilder = Glide.with(context).load(Uri.parse(url.orEmpty())).signature(
        ObjectKey(url!!.substringAfterLast("?"))
    )
    loadImage(
        this, requestBuilder, errorDrawableResId, isFullSize, isCircleCrop
    )

}


fun ImageView.loadImage(
    drawableResId: Int,
    errorDrawableResId: Int = R.drawable.ic_logo,
    isFullSize: Boolean = false,
    isCircleCrop: Boolean = false
) {
    val requestBuilder = Glide.with(context).load(drawableResId)
    loadImage(this, requestBuilder, errorDrawableResId, isFullSize, isCircleCrop)
}

fun ImageView.loadImage(
    file: File?,
    errorDrawableResId: Int = R.drawable.ic_logo,
    isFullSize: Boolean = false,
    isCircleCrop: Boolean = false
) {
    val requestBuilder = Glide.with(context).load(Uri.fromFile(file))
    loadImage(this, requestBuilder, errorDrawableResId, isFullSize, isCircleCrop)
}

fun ImageView.loadImage(
    uri: Uri,
    errorDrawableResId: Int = R.drawable.ic_logo,
    isFullSize: Boolean = false,
    isCircleCrop: Boolean = false
) {
    val requestBuilder = Glide.with(context).load(uri)
    loadImage(this, requestBuilder, errorDrawableResId, isFullSize, isCircleCrop)
}

private fun loadImage(
    imageView: ImageView,
    requestBuilder: RequestBuilder<Drawable>,
    errorDrawableResId: Int = R.drawable.ic_logo,
    isFullSize: Boolean = false,
    isCircleCrop: Boolean = false
) {

    /* val circularProgressDrawable by lazy {
         KoinJavaComponent.getKoin().get<CircularProgressDrawable>()
     }*/

    requestBuilder
        .placeholder(R.drawable.ic_logo)
        .thumbnail(0.25f)
        .error(
            Glide.with(imageView.context).load(errorDrawableResId)
                .applyIf(isCircleCrop) { circleCrop() }
                .applyIf(isFullSize) {
                    apply(
                        RequestOptions()
                            .format(DecodeFormat.PREFER_ARGB_8888)
                            .override(Target.SIZE_ORIGINAL)
                    )
                }
        )
        .applyIf(isCircleCrop) { circleCrop() }
        .applyIf(isFullSize) {
            apply(
                RequestOptions()
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .override(Target.SIZE_ORIGINAL)
            )
        }
        .into(imageView)

}


//check and open pdf file

/*fun checkAndOpenFile(context: Context?, uri: Uri, transitionView: ImageView?) {

    context?.let {
        val inflater = LayoutInflater.from(it)
        *//* val frescoView: View =
             inflater.inflate(R.layout.pdf_overlay_view, null)
 *//*
        val imageList = Utils.renderPdf(uri, it)

        StfalconImageViewer.Builder(context, imageList,
            ImageLoader<Bitmap> { imageView, bitmap ->
                imageView.setImageBitmap(bitmap)
            })
            .withBackgroundColor(Color.WHITE)
            .withStartPosition(0)
            .applyIf(transitionView != null) { withTransitionFrom(transitionView) }
            //.withOverlayView(frescoView)
            *//*.withImageChangeListener {
                tv_pov_page_no?.text = "$it/$imageList.size.toString()"
                tv_pov_title?.text = appointmentDetail.appointmentDate
                fab_pov_share?.setOnClickListener {
                    IntentUtils.intentShareFile(requireActivity(), file)
                }
            }*//*
            .show()

    }


}*/
