package com.san.ipassmanager.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tbl_bank_credentials")
data class BankEntity(
    @PrimaryKey @ColumnInfo(name = "account_no")
    var accountNo: String = "",
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "bank_name")
    var bankName: String = "",
    @ColumnInfo(name = "ac_holder_name")
    var acHolderName: String = "",
    @ColumnInfo(name = "website_link")
    var websiteLink: String = "",
    @ColumnInfo(name = "IFSC")
    var ifsc: String = "",
    @ColumnInfo(name = "MICR")
    var micr: String = "",
    @ColumnInfo(name = "card_no")
    var cardNo: String = "",
    @ColumnInfo(name = "card_type")
    var cardType: String = "",
    @ColumnInfo(name = "card_valid_from")
    var cardValidFrom: String = "",
    @ColumnInfo(name = "card_valid_to")
    var cardValidTo: String = "",
    @ColumnInfo(name = "card_cvv_no")
    var cardCvvNo: String = "",
    @ColumnInfo(name = "mobile_no")
    var mobileNo: String = "",
    @ColumnInfo(name = "description")
    var description: String = ""

) : Serializable