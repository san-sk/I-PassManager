package com.san.ipassmanager.utils

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.pdf.PdfRenderer
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.squareup.moshi.Moshi
import com.yalantis.ucrop.UCrop
import java.io.File
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


object Utils {

    val moshi = Moshi.Builder().build()

    var fileNameDateFormat = SimpleDateFormat(
        "yyyyMMddHHmmss",
        Locale.getDefault()
    )

    var serverDateFormat = SimpleDateFormat(
        "yyyy-MM-dd",
        Locale.getDefault()
    )

    var displayDateTimeFormat = SimpleDateFormat(
        "dd-MM-yyyy hh:mm aa",
        Locale.getDefault()
    )

    var displayDateFormat = SimpleDateFormat(
        "dd-MM-yyyy",
        Locale.getDefault()
    )

    var serverDisplayDateFormat = SimpleDateFormat(
        "dd/MM/yyyy",
        Locale.getDefault()
    )

    var timestampFormat = SimpleDateFormat(
        "yyyy-MM-dd HH:mm:ss",
        Locale.getDefault()
    )

    var timestampFormat12h = SimpleDateFormat(
        "yyyy-MM-dd hh:mm aa",
        Locale.getDefault()
    )

    var timeFormat = SimpleDateFormat(
        "hh:mm aa",
        Locale.getDefault()
    )

    var displayTime24h = SimpleDateFormat(
        "HH:mm",
        Locale.getDefault()
    )

    var serverTime24h = SimpleDateFormat(
        "HH:mm:ss",
        Locale.getDefault()
    )

    private val MY_PERMISSIONS_REQUEST_STORAGE = 123
    private val perms = arrayOf("android.permission.READ_EXTERNAL_STORAGE")



    fun checkFileSize(file: File?, length: Long): Boolean =
        file != null && file.length() <= length


    val fileNameDateString: String
        get() = fileNameDateFormat.format(Calendar.getInstance().time)

    val todaysDateString: String
        get() = serverDateFormat.format(Calendar.getInstance().time)

    /*fun getErrorBody(response: Response<*>): ErrorBody {
        try {

            return Gson().fromJson(response.errorBody()?.charStream(), ErrorBody::class.java)
        } catch (e:JsonSyntaxException){
            return ErrorBody(500, "Server down for maintenance. Please try again later.")
        }
    }*/

    fun hideKeyboardFrom(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /* fun getSpannedFromHtmlString(htmlString: String?): Spanned {
         return htmlString?.let {

             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                 Html.fromHtml(it, Html.FROM_HTML_MODE_COMPACT)

             } else {

                 Html.fromHtml(it)
             }
         } ?: "".toSpanned()
     }*/

    fun showDialog(
        activity: Activity,
        dialogFragment: DialogFragment,
        fragmentManager: FragmentManager,
        tag: String
    ) {


        dialogFragment.isCancelable = false
        dialogFragment.show(fragmentManager, tag)

        fragmentManager.executePendingTransactions()

        val window = dialogFragment.dialog?.window
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val width = (activity.resources.displayMetrics.widthPixels * 0.90).toInt()
        //int height = (int)(getResources().getDisplayMetrics().heightPixels*0.90);
        window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    /* fun showMessageDialog(context: Context, message: String, tag:String) {

         val fragmentActivity = context as FragmentActivity

         val bundle = Bundle()
         bundle.putString("message", message)

         val genericMessageDialogFragment = progressDialogFragment()
         genericMessageDialogFragment.arguments = bundle
         genericMessageDialogFragment.isCancelable = false
         genericMessageDialogFragment.show(fragmentActivity.supportFragmentManager, tag)

         fragmentActivity.supportFragmentManager.executePendingTransactions()

         val window = genericMessageDialogFragment.dialog?.window
         window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

         val width = (fragmentActivity.resources.displayMetrics.widthPixels * 0.90).toInt()
         //int height = (int)(getResources().getDisplayMetrics().heightPixels*0.90);
         window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)

     }*/


    fun getDisplayDateFromTimestamp(timestamp: String): String {

        val date: Date = try {

            timestampFormat.parse(timestamp)

        } catch (e: ParseException) {

            Calendar.getInstance().time
        }

        return displayDateFormat.format(date)

    }

    fun getDisplayTime24hFromServerTime(serverTime: String): String {
        var date: Date

        try {

            date = serverTime24h.parse(serverTime)

        } catch (e: ParseException) {

            date = Calendar.getInstance().time
        }

        return displayTime24h.format(date)

    }

    fun getTimeFromLong(longTime: Long): String {
        val date = Date()
        date.time = longTime
        return timeFormat.format(date)

    }

    fun getDisplayDateFromLong(longTime: Long): String {
        val date = Date()
        date.time = longTime
        return displayDateFormat.format(date)

    }

    fun getFormattedString(unformattedString: String?): String {

        return if (unformattedString == null || unformattedString.trim { it <= ' ' }.isEmpty())
            "N/A"
        else
            unformattedString
    }

    fun getDisplayDateString(unformattedDateString: String): String {
        var date: Date

        try {
            date = serverDateFormat.parse(unformattedDateString)
        } catch (e: ParseException) {
            date = Calendar.getInstance().time
        }

        return displayDateFormat.format(date)
    }

    fun checkIfValidDates(fromDateString: String, toDateString: String): Boolean {

        var fromDate: Date? = null
        var toDate: Date? = null
        try {
            fromDate = displayDateFormat.parse(fromDateString)
            toDate = displayDateFormat.parse(toDateString)

        } catch (e: ParseException) {
            e.printStackTrace()
        }

        assert(fromDate != null)
        return fromDate!!.before(toDate)
    }


    fun isValid(string: String?): Boolean {

        return !(string == null || string.trim() == "null" || string.isBlank())
    }

    fun isValid(int: Int?): Boolean {

        return !(int == null || int == 0)
    }

    fun isValid(list: List<Any>?): Boolean {

        return !(list == null || list.isEmpty())
    }

    fun isValidEmail(target: String): Boolean {
        return !TextUtils.isEmpty(target.trim { it <= ' ' }) && Patterns.EMAIL_ADDRESS.matcher(
            target
        ).matches()
    }

    fun loge(message: String?) {
        Log.e("${Constants.TAG} ->", message.orEmpty())
    }

    fun logd(message: String?) {
        Log.d("${Constants.TAG} ->", message.orEmpty())
    }

    fun getProgDialog(context: Context): ProgressDialog {
        val progDialog = ProgressDialog(context)
        progDialog.setMessage("Loading...")
        progDialog.isIndeterminate = false
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progDialog.setCancelable(false)
        return progDialog
    }


    fun hasPermissions(context: Context, vararg permissions: String): Boolean {
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }

        return true
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    fun checkFilePermission(context: Context): Boolean {
        val currentAPIVersion = Build.VERSION.SDK_INT
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        context as Activity,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                ) {
                    val alertBuilder = AlertDialog.Builder(context)
                    alertBuilder.setCancelable(true)
                    alertBuilder.setTitle("Permission necessary")
                    alertBuilder.setMessage("Storage permission is necessary")
                    alertBuilder.setPositiveButton(android.R.string.yes) { dialog, which ->
                        ActivityCompat.requestPermissions(
                            context,
                            perms,
                            MY_PERMISSIONS_REQUEST_STORAGE
                        )
                    }
                    val alert = alertBuilder.create()
                    alert.show()

                } else {
                    ActivityCompat.requestPermissions(
                        context,
                        perms,
                        MY_PERMISSIONS_REQUEST_STORAGE
                    )
                }
                return false
            } else {
                return true
            }
        } else {
            return true
        }
    }

    fun defaultString(string: String?): String {
        return if (isValid(string))
            string!!
        else
            ""
    }

    fun amPmConverter(time: String?): String {
        val sdf = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
        val formatter = SimpleDateFormat("hh:mm aa", Locale.getDefault())
        return formatter.format(sdf.parse(time))
    }

    fun getTodayDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val date = dateFormat.format(calendar.time)
        return date
    }


    fun datePicker(context: Context): String { // Get Current Date
        val c = Calendar.getInstance()
        val mYear = c[Calendar.YEAR]
        val mMonth = c[Calendar.MONTH]
        val mDay = c[Calendar.DAY_OF_MONTH]
        var selectedDate: String = getTodayDate()

        context?.let {
            val datePickerDialog = DatePickerDialog(
                it,
                OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    selectedDate =
                        year.toString() + "-" + (monthOfYear + 1).toString() + "-" + dayOfMonth.toString()
                }, mYear, mMonth, mDay
            )
            datePickerDialog.show()
        }
        return selectedDate
    }


    fun isTimeAfter(startTime: Date?, endTime: Date): Boolean {
        return !endTime.before(startTime)
    }

    fun checkIfValidTimes(startTime: Date?, endTime: Date): Int {

        return when (startTime!!.compareTo(endTime)) {
            0 ->//startTime and endTime not **Equal**
                0

            1 ->//endTime is **Greater** then startTime
                1

            -1 ->//startTime is **Greater** then endTime
                -1

            else -> 2
        }

    }



    fun checkIfValidDate(startDate: String, endDate: String): Int {

        var fromDate: Date? = null
        var toDate: Date? = null

        fromDate = serverDisplayDateFormat.parse(startDate)
        toDate = serverDisplayDateFormat.parse(endDate)

        return when {
            fromDate > toDate -> {
                return 1
            }
            fromDate < toDate -> {
                return -1
            }
            fromDate.compareTo(toDate) == 0 -> {
                return 0
            }
            else -> 2
        }

    }

    fun getDayName(inputDate: String): String {
        //input format 2020-05-08
        val dateParts: List<String> = inputDate.split("-")
        val year = dateParts[0].toInt()
        val monthOfYear = dateParts[1].toInt()
        val dayOfMonth = dateParts[2].toInt()

        val date = Date(year, monthOfYear - 1, dayOfMonth - 1)
        val simpledateformat = SimpleDateFormat("EEEE")
        return simpledateformat.format(date)
    }



    fun clearBubbleSharedPrefHints(context: Context) {
        val preferences: SharedPreferences =
            context.getSharedPreferences("BubbleShowCasePrefs", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }


    fun isHeadsetOn(context: Context): Boolean {
        val am: AudioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return am.isWiredHeadsetOn() || am.isBluetoothScoOn() || am.isBluetoothA2dpOn()
        } else {
            val devices: Array<AudioDeviceInfo> =
                am.getDevices(AudioManager.GET_DEVICES_OUTPUTS)
            for (i in devices.indices) {
                val device: AudioDeviceInfo = devices[i]
                if (device.type == AudioDeviceInfo.TYPE_WIRED_HEADSET ||
                    device.type == AudioDeviceInfo.TYPE_WIRED_HEADPHONES ||
                    device.type == AudioDeviceInfo.TYPE_BLUETOOTH_A2DP ||
                    device.type == AudioDeviceInfo.TYPE_BLUETOOTH_SCO
                ) {
                    return true
                }
            }
        }
        return false
    }


    fun getTimePart(time: String?): Int? {

        return when (time?.take(2)!!.toInt()) {
            in 0..11 -> {
                1 //morning
            }
            in 12..17 -> {
                2 //afternoon
            }
            in 18..21 -> {
                3 //evening
            }
            in 22..24 -> {
                4 //night
            }
            else -> {
                0
            }
        }

    }

    fun renderPdf(uri:Uri, context: Context): List<Bitmap> {
        val bitmaps = arrayListOf<Bitmap>()
        context.contentResolver.openFileDescriptor(uri, "r")?.let {
            // create a new renderer
            /*val fileDescriptor =
                ParcelFileDescriptor.open(documentFile, ParcelFileDescriptor.MODE_READ_ONLY)*/
            // create a new renderer
            val renderer = PdfRenderer(it)

            // let us just render all pages

            // let us just render all pages

            val pageCount = renderer.pageCount
            for (i in 0 until pageCount) {
                val page: PdfRenderer.Page = renderer.openPage(i)
                val mBitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
                // say we render for showing on the screen
                page.render(mBitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

                // do stuff with the bitmap
                bitmaps.add(mBitmap)

                // close the page
                page.close()
            }

            // close the renderer
            renderer.close()

        }

        return bitmaps
    }



    fun getRandomColorCode(): Int {
        val random = Random()
        return Color.argb(
            255,
            random.nextInt(256),
            random.nextInt(256),
            random.nextInt(256)
        )
    }


    fun cropWithUCrop(context: Context?, uri: Uri, fragment: Fragment) {
        context?.let {
            UCrop.of(uri, Uri.fromFile(createImageFile(it)))
                .withAspectRatio(1f, 1f)
                .withMaxResultSize(300, 300)
                .start(it, fragment)
        }
    }


    @Throws(IOException::class)
    fun createImageFile(context: Context?): File {
        // Create an image file name
        val timeStamp: String = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        //val storageDir: File? = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES) // to save in /data/data/apa_name/files
        val storageDir: File? = context?.cacheDir // to save in app cache dir


        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir/* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            var currentPhotoUri = this.toUri()
        }
    }


    //views

    fun makeVisible(views: List<View>) {
        views.forEach {
            it.visibility = View.VISIBLE
        }

    }

    fun makeInVisible(views: List<View>) {
        views.forEach {
            it.visibility = View.INVISIBLE
        }

    }

    fun makeGone(views: List<View>) {
        views.forEach {
            it.visibility = View.GONE
        }

    }

}

