package app.wiserkronox.loyolasocios.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class SocialResponsabilityModel {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo
    var year: String = ""

    @ColumnInfo
    var score_pdf:String = ""

    @ColumnInfo
    var report_pdf:String = ""

}