package com.san.ipassmanager.room.dao


import androidx.lifecycle.LiveData
import androidx.room.*
import com.san.ipassmanager.room.entity.BankEntity

@Dao
interface BankDao {

    @Query("SELECT * from tbl_bank_credentials ORDER BY name ASC")
    fun getBankCredentials(): LiveData<List<BankEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBankCredential(bankEntity: BankEntity)

    @Update()
    suspend fun updateBankCredential(bankEntity: BankEntity)

    @Query("DELETE FROM tbl_bank_credentials")
    suspend fun deleteAll()

    @Query("SELECT * FROM tbl_bank_credentials WHERE account_no = :acNo")
    fun getCredential(acNo: String): LiveData<BankEntity>

    @Delete()
    suspend fun deleteBankCredential(bankEntity: BankEntity)

}