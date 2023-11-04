package app.wiserkronox.loyolasocios.service.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CertificateDao {
    @Query("select * from Certificate order by year desc")
    fun getAllCertificates(): List<Certificate>

    @Query("delete from Certificate")
    suspend fun deleteAll()


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllCertificates(certificates: List<Certificate>): List<Long>
}
