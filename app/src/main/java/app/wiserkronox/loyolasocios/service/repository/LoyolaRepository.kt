package app.wiserkronox.loyolasocios.service.repository

import androidx.annotation.WorkerThread
import app.wiserkronox.loyolasocios.service.model.*
import kotlinx.coroutines.flow.Flow
import org.jetbrains.annotations.NotNull

class LoyolaRepository(
    private val userDao: UserDao,
    private val assemblyDao: AssemblyDao,
    private val courseDao: CourseDao,
    private val certificateDao: CertificateDao
) {

    val allUsers: Flow<List<User>> = userDao.getUsers()

    @WorkerThread
    suspend fun insert(user: User): Long {
        return userDao.insert( user )
    }

    @WorkerThread
    suspend fun update2(user: User): Int {
        return userDao.update2( user )
    }

    /*fun insert2(user: User): Flow<Long>{
        return userDao.insert2( user )
    }*/

    @WorkerThread
    suspend fun deleteAll(){
        return userDao.deleteAll()
    }


    fun getUserByEmail(email: String): User {
        return userDao.getUserByEmail( email )
    }

    fun getUserEmailPassword(email: String, password: String): User {
        return userDao.getUserByEmailPassword( email, password )
    }

    /*fun getUserEmail2(email: String): Flow<User> {
        return userDao.getUserByEmail2( email )
    }*/

    fun getUserByOauthUid(oauthUid: String): User {
        return userDao.getUserByOauth_uid( oauthUid)
    }

    @WorkerThread
    suspend fun update(user: User){
        userDao.update(user)
    }

    /*************************************************************************************
     * Funciones para la instancia de la asamblea
     */
    val allAssemblys: Flow<List<Assembly>> = assemblyDao.getAssemblys()
    fun getAllAssembly(): List<Assembly> {
        return assemblyDao.getAllAssemblys()
    }

    @WorkerThread
    suspend fun insertAssembly(assembly: Assembly): Long {
        return assemblyDao.insert( assembly )
    }

    @WorkerThread
    suspend fun insertAllAssembly(assemblys: List<Assembly>): List<Long>{
        return assemblyDao.insertAll( assemblys )
    }

    @WorkerThread
    suspend fun deleteAllAssemblys(){
        assemblyDao.deleteAll()
    }

    fun getAllAssemblysStatus(status: String): List<Assembly> {
        return assemblyDao.getAllAssemblysStatus(status)
    }
/*************************************************************************************
 * Funciones para la instancia de cursos
 */
    @WorkerThread
    suspend fun updateCourse(course: Course): Int {
        return courseDao.updateC( course )
    }
    val allCourses: Flow<List<Course>> = courseDao.getCourses()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAllCourses(): List<Course> {
        return courseDao.getAllCourses()
    }

    @WorkerThread
    suspend fun insertAllCourses(courses: List<Course>): List<Long>{
        return courseDao.insertAllCourses( courses )
    }

    @WorkerThread
    suspend fun deleteAllCourses(){
        courseDao.deleteAll()
    }
    fun getAllCoursesStatus(status: String): List<Course> {
        return courseDao.getAllCoursesStatus(status)
    }

    /*************************************************************************************
     * Funciones para la instancia de certificados
     */
    @Suppress("RedundantSuspendModifier")
    suspend fun getAllCertificates():List<Certificate> {
        return  certificateDao.getAllCertificates()
    }

    @WorkerThread
    suspend fun insertAllCertificates(certificates: List<Certificate>): List<Long>{
        return certificateDao.insertAllCertificates(certificates)
    }

    @WorkerThread
    suspend fun deleteAllCertificates(){
        certificateDao.deleteAll()
    }
}