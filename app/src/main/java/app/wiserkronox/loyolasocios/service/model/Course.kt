package app.wiserkronox.loyolasocios.service.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "course")
class Course () {
    companion object {
        var DOWNLOAD_DATA_SERVER: String = "1"
    }

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    //Primer metodo de autentificacion
    @ColumnInfo
    var name: String = ""
    @ColumnInfo
    var photo: String = ""
    @ColumnInfo
    var start_date: String = ""
    @ColumnInfo
    var end_date: String = ""
    @ColumnInfo
    var schedule: String = ""
    @ColumnInfo
    var type: String = ""
    @ColumnInfo
    var location: String = ""
    @ColumnInfo
    var expositor: String = ""

    @ColumnInfo
    var url: String = ""
    @ColumnInfo
    var code: String = ""
    @ColumnInfo
    var password: String = ""

    @ColumnInfo
    var status: String = ""
    @ColumnInfo
    var document: String = ""
    @ColumnInfo
    var created_at: String = ""
    @ColumnInfo
    var updated_at: String = ""
    @ColumnInfo
    var deleted_at: String = ""
}