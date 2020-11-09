package com.san.ipassmanager.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tbl_all_credentials")
data class AllCredentialEntity(

    //common
    @PrimaryKey @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "credential_type")
    var credentialType: String? = null,
    @ColumnInfo(name = "user_name")
    var userName: String? = null,
    @ColumnInfo(name = "password")
    var password: String? = null,
    @ColumnInfo(name = "mobile_no")
    var mobileNo: String? = null,
    @ColumnInfo(name = "website_link")
    var websiteLink: String? = null,
    @ColumnInfo(name = "description")
    var description: String? = null,

    //bank
    @ColumnInfo(name = "account_no")
    var accountNo: String? = null,
    @ColumnInfo(name = "bank_name")
    var bankName: String? = null,
    @ColumnInfo(name = "ac_holder_name")
    var acHolderName: String? = null,
    @ColumnInfo(name = "IFSC")
    var ifsc: String? = null,
    @ColumnInfo(name = "MICR")
    var micr: String? = null,
    @ColumnInfo(name = "card_no")
    var cardNo: String? = null,
    @ColumnInfo(name = "card_type")
    var cardType: String? = null,
    @ColumnInfo(name = "card_valid_from")
    var cardValidFrom: String? = null,
    @ColumnInfo(name = "card_valid_to")
    var cardValidTo: String? = null,
    @ColumnInfo(name = "card_cvv_no")
    var cardCvvNo: String? = null,


    //device
    @ColumnInfo(name = "device_name")
    var deviceName: String? = null,

    //email
    @ColumnInfo(name = "email_id")
    var emailId: String? = null,

    //network
    @ColumnInfo(name = "wifi_ssid")
    var ssid: String? = null,

    //OTT
    @ColumnInfo(name = "platform_name")
    var platformName: String? = null,

    //server
    @ColumnInfo(name = "server_url")
    var serverUrl: String? = null,

    //social media
    @ColumnInfo(name = "social_media_platform_name")
    var smPlatformName: String? = null

) : Serializable