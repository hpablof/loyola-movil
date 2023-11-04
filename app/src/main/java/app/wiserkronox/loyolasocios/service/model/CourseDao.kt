package app.wiserkronox.loyolasocios.service.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Query("select * from course  order by created_at")
    fun getCourses(): Flow<List<Course>>

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateC(course: Course): Int

    @Update
    suspend fun update(course: Course?)

    @Query("select * from course order by created_at")
    fun getAllCourses(): List<Course>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(course: Course): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllCourses(course: List<Course>): List<Long>

    @Query("delete from course")
    suspend fun deleteAll()

    @Query("select * from Course where status = :status order by created_at")
    fun getAllCoursesStatus(status: String): List<Course>

}