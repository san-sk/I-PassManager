package com.san.ipassmanager.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.san.ipassmanager.room.Converters
import com.san.ipassmanager.room.dao.*
import com.san.ipassmanager.room.entity.*

//EmailEntity::class, DeviceEntity::class, BankEntity::class,
//        NetworkEntity::class, OTTEntity::class, ServerEntity::class,
//        SocialMediaEntity::class


@Database(
    entities = [AllCredentialEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class CredentialsDatabase : RoomDatabase() {

    abstract fun credentialDao(): AllCredentialDao
    /*abstract fun emailDao(): EmailDao
    abstract fun bankDao(): BankDao
    abstract fun deviceDao(): DeviceDao
    abstract fun networkDao(): NetworkDao
    abstract fun ottDao(): OTTDao
    abstract fun serverDao(): ServerDao
    abstract fun socialMediaDao(): SocialMediaDao*/


    /* private class CredentialsDatabaseCallback(
         private val scope: CoroutineScope
     ) : RoomDatabase.Callback() {

         override fun onOpen(db: SupportSQLiteDatabase) {
             super.onOpen(db)
             INSTANCE?.let { database ->
                 scope.launch {
                     val emailDao = database.emailDao()

                     // Delete all content here.
                     emailDao.deleteAll()

                     // Add sample words.
                     val c = EmailEntity(
                         "san@g.com",
                         "san",
                         "san.com",
                         "san_sk",
                         "san",
                         "8807519080",
                         "test"
                     )
                     emailDao.insertCredentials(c)

                 }
             }
         }
     }

     companion object {
         // Singleton prevents multiple instances of database opening at the
         // same time.
         @Volatile
         private var INSTANCE: CredentialsDatabase? = null

         fun getDatabase(context: Context, scope: CoroutineScope): CredentialsDatabase {
             val tempInstance = INSTANCE
             if (tempInstance != null) {
                 return tempInstance
             }
             synchronized(this) {
                 val instance = Room.databaseBuilder(
                     context.applicationContext,
                     CredentialsDatabase::class.java,
                     "credentials_database"
                 )
                     .addCallback(CredentialsDatabaseCallback(scope))
                     .build()
                 INSTANCE = instance
                 return instance
             }
         }
     }*/
}