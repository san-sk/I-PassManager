package com.san.ipassmanager.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tbl_server_credentials")
data class ServerEntity(
    @PrimaryKey @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "email_id")
    var emailId: String = "",
    @ColumnInfo(name = "website_link")
    var websiteLink: String = "",
    @ColumnInfo(name = "server_url")
    var serverUrl: String = "",
    @ColumnInfo(name = "user_name")
    var userName: String = "",
    @ColumnInfo(name = "password")
    var password: String = "",
    @ColumnInfo(name = "description")
    var description: String = ""

) : Serializable