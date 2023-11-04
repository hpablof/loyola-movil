package app.wiserkronox.loyolasocios.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Certificate {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo
    var year:String = ""

    @ColumnInfo
    var number:Int = 0

    @ColumnInfo
    var opening_date:String = String()

    @ColumnInfo
    var amount: Int = 0

    @ColumnInfo
    var cost: Double = 0.0

    @ColumnInfo
    var state: String = ""


}