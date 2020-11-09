package com.san.ipassmanager.utils

import android.content.Context
import android.os.Environment
import android.util.Log
import com.google.android.gms.common.util.Base64Utils
import com.san.ipassmanager.room.entity.AllCredentialEntity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.File
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec


object BRUtils {


    fun createLocalBackupFile(context: Context): Boolean {

        var isNewFileCreated = false

        val backupFilePath =
            File("${context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)}/Backup/")
        backupFilePath.mkdirs()

        if (backupFilePath.exists()) {
            //isNewFileCreated = backupFilePath.mkdirs()
            isNewFileCreated = File("$backupFilePath/${Constants.BACKUP_FILE_NAME}").createNewFile()

        }

        return isNewFileCreated
    }

    fun backup(password: String, list: List<AllCredentialEntity>): String? {

        //createBackupFile(context)

        val dataListType =
            Types.newParameterizedType(List::class.java, AllCredentialEntity::class.java)
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val jsonAdapter: JsonAdapter<List<AllCredentialEntity>> =
            moshi.adapter(dataListType)

        /* return File("${context.filesDir}/Backup/Backup.json").apply {
         writeText(result)
     }*/

        val backupData = jsonAdapter.toJson(list)

        val encryptedData = encrypt(backupData, password + "12")

        Log.i("driveEncryptedData", encryptedData)
        return encryptedData

    }

    fun restore(password: String, encryptedString: String): List<AllCredentialEntity>? {


        val decryptedData = decrypt(encryptedString, password + "12")
        Log.i("driveDecryptedData", decryptedData)


        val dataListType =
            Types.newParameterizedType(List::class.java, AllCredentialEntity::class.java)
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val jsonAdapter: JsonAdapter<List<AllCredentialEntity>> =
            moshi.adapter(dataListType)

        return jsonAdapter.fromJson(decryptedData)


    }


    /**
     * aes encryption
     */
    fun encrypt(input: String, password: String): String {
        //1. Create a cipher object
        val cipher = Cipher.getInstance("AES")
        //2. Initialize cipher
        //The key you specified
        val keySpec = SecretKeySpec(password.toByteArray(), "AES")
        cipher.init(Cipher.ENCRYPT_MODE, keySpec)
        //3. Encryption and decryption
        val encrypt = cipher.doFinal(input.toByteArray());
        return Base64Utils.encode(encrypt)
    }

    /**
     * aes decryption
     */
    fun decrypt(input: String, password: String): String {
        //1. Create a cipher object
        val cipher = Cipher.getInstance("AES")
        //2. Initialize cipher
        //The key you specified
        val keySpec = SecretKeySpec(password.toByteArray(), "AES")
        cipher.init(Cipher.DECRYPT_MODE, keySpec)
        //3. Encryption and decryption
        val decrypt = cipher.doFinal(Base64Utils.decode(input))
        return String(decrypt)
    }


}