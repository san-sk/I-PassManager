package com.san.ipassmanager.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tbl_ott_credentials")
data class OTTEntity(
    @PrimaryKey @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "platform_name")
    var platformName: String = "",
    @ColumnInfo(name = "email_id")
    var emailId: String = "",
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

) : Serializable