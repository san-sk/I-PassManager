package com.san.ipassmanager.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tbl_email_credentials")
data class EmailEntity(
    @PrimaryKey @ColumnInfo(name = "email_id")
    var emailId: String = "",
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "website_link")
    var websiteLink: String = "",
    @ColumnInfo(name = "user_name")
    var userName: String = "",
    @ColumnInfo(name = "password")
    var password: String = "",
    @ColumnInfo(name = "mobile_no")
    var mobileNo: String = "",
    @ColumnInfo(name = "description")
    var description: String = ""

):Serializable