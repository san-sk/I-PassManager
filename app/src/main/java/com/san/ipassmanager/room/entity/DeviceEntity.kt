package com.san.ipassmanager.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tbl_device_credentials")
data class DeviceEntity(
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "device_name")
    var deviceName: String = "",
    @PrimaryKey @ColumnInfo(name = "user_name")
    var userName: String = "",
    @ColumnInfo(name = "password")
    var password: String = "",
    @ColumnInfo(name = "description")
    var description: String = ""

) : Serializable