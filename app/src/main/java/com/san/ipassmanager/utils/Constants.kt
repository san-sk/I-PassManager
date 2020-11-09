package com.san.ipassmanager.utils

object Constants {

    const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    const val API_KEY = "^(DFD"

    enum class LIST_CATEGORIES {
        ALL,
        EMAIL,
        WEBSITE,
        BANK,
        NETWORK,
        OTTPLATFORM,
        SERVR,
        SOCIALMEDIA,
        OTHERS
    }


    const val DATABASE_NAME = "credentials.database"
    const val BACKUP_FILE_NAME ="IPass.backup"
    const val APP_NAME = "PasswordManager"
    const val THEME = "Theme"
    const val NOTIFICATION = "Notification"
    const val DRAWABLE_RES_ID = "drawable_res_id"

    //actions
    const val CREATE = "Create"
    const val READ = "Read"
    const val UPDATE = "Update"
    const val DELETE = "Delete"

    //account type
    const val LOCAL = "Local"
    const val ONLINE = "Online"

    const val BACKUP = "Backup"
    const val RESTORE = "Restore"


    //TAGS
    const val TAG = "PM"
    const val MESSAGE = "msg"
    const val ALERT = "Alert"

    //video call
    const val VIDEO_CONSULTATION_CHANNEL = "video_consultation"

    const val PROCESS_UDID = "test"
    const val MALE = "M"
    const val FEMALE = "F"
    const val OTHER = "O"
    const val UNKNOWN = "Unknown"
    const val Y = "Y"
    const val N = "N"


    //permissions
    const val TAKE_IMAGE = 124
    const val CHOOSE_IMAGE = 125
    const val CHOOSE_FILE = 126

    const val STORAGE_PERMISSION_ALL = 1
    val STORAGE_PERMISSIONS =
        arrayOf(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
        )

    val mimeTypes = arrayOf("application/pdf", "application/msword,text/plain")

    const val CALL_PERMISSION = 0
    val CALL_PERMISSIONS =
        arrayOf(
            android.Manifest.permission.CALL_PHONE,
            android.Manifest.permission.RECORD_AUDIO
        )
    const val CAMERA_PERMISSION_CODE = 100
    val CAMERA_PERMISSION = arrayOf(android.Manifest.permission.CAMERA)

    const val STORAGE_PERMISSION_CODE = 101

    const val ALL_PERMISSION_CODE = 5
    val ALL_PERMISSIONS =
        arrayOf(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.CALL_PHONE,
            android.Manifest.permission.RECORD_AUDIO
        )


}