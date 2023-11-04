package app.wiserkronox.loyolasocios.view.ui.home

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.wiserkronox.loyolasocios.R
import app.wiserkronox.loyolasocios.service.LoyolaApplication
import app.wiserkronox.loyolasocios.service.Util
import app.wiserkronox.loyolasocios.service.Util.Companion.getOutputDirectory
import app.wiserkronox.loyolasocios.service.model.Course
import app.wiserkronox.loyolasocios.service.repository.CourseRest
import app.wiserkronox.loyolasocios.service.repository.LoyolaService
import app.wiserkronox.loyolasocios.view.adapter.AdapterCourse
import app.wiserkronox.loyolasocios.view.callback.ListCourseCallback
import app.wiserkronox.loyolasocios.view.ui.HomeActivity
import app.wiserkronox.loyolasocios.view.ui.MainActivity
import com.android.volley.Request
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ListCourseFragment : Fragment(), ListCourseCallback {

    private  lateinit var loader: ProgressBar
    private  lateinit var recycler: RecyclerView

    companion object {
        internal const val TAG = "CourseFragment"
        private const val CREATE_FILE = 1
    }
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_list_course, container, false)

        loader = root.findViewById(R.id.progress_course)
        recycler = root.findViewById(R.id.recyclerCourse)



        GlobalScope.launch {
            val courses = LoyolaApplication.getInstance()?.repository?.getAllCourses()
            Log.d(TAG, "" + courses?.size);
        }

        if( (activity as HomeActivity).isOnline() ) {
            getUpdateData()
        } else {
            Toast.makeText(context, "En este momento no existen cursos", Toast.LENGTH_SHORT).show()
            ( activity as HomeActivity).goHome()
        }
        return root
    }

    fun getUpdateData(){
        activity?.let{
            val courseRest = CourseRest(it)
            val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.POST, courseRest.getCourseURL(),
                    null,
                    { response ->
                        Log.d(ListCourseFragment.TAG, "Response is: ${response.toString()}")
                        if (response.getBoolean("success")) {
                            Log.d(ListCourseFragment.TAG, "Exito")
                            loader.visibility = ProgressBar.INVISIBLE
                            if (response.has("courses")) {
                                val courses = response.getJSONArray("courses")
                                if (courses.length() > 0) {
                                    val courseRest = CourseRest(it)
                                    insertCourses(courseRest.getCoursesList(courses))
                                } else {
                                    Log.d(ListCourseFragment.TAG, "No existen cursos")
                                }
                            }
                        } else {
                            (activity as HomeActivity).showMessage(response.getString("reason"))
                        }
                    },
                    { error ->
                        Log.e(ListCourseFragment.TAG, error.toString())
                        error.printStackTrace()
                    }
            )

            // Add the request to the RequestQueue.
            LoyolaService.getInstance(it).addToRequestQueue(jsonObjectRequest)


        }
    }
    fun insertCourses(courses: List<Course>){
        GlobalScope.launch {
            LoyolaApplication.getInstance()?.repository?.deleteAllCourses()
            LoyolaApplication.getInstance()?.repository?.insertAllCourses(courses)

            val actives = LoyolaApplication.getInstance()?.repository?.getAllCoursesStatus("activo")

            actives?.forEach{ course ->
                activity?.let{
                    if(!course.photo.equals("")) {
                        downloadPictureCourse(getOutputDirectory(activity as AppCompatActivity).toString(), course.photo, course)
                    }
                }
            }
        }
    }

    fun populateCourseList(courses: List<Course>){

        Handler(Looper.getMainLooper()).post {
            val recycler_adap = AdapterCourse(courses, this  )
            val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
            recycler.setLayoutManager(layoutManager)
            recycler.setAdapter(recycler_adap)
        }
    }

    /*************************************************************************************/
    //Funciones para guardar la foto del curso
    /*************************************************************************************/

    fun backUpdate(course: Course){
        GlobalScope.launch {
            LoyolaApplication.getInstance()?.repository?. updateCourse(course)
            val actives = LoyolaApplication.getInstance()?.repository?.getAllCoursesStatus("activo")
            actives?.let {
                populateCourseList(actives)
            }
        }
    }

    fun downloadPictureCourse(output_directory: String, url_photo: String, course: Course){
        val imageRequest = ImageRequest(
                url_photo,
                { bitmap -> // response listener
                    Log.d(MainActivity.TAG, "Descargando Imagen")
                    Log.d(MainActivity.TAG, "URL: " + url_photo)
                    Log.d(MainActivity.TAG, "filename: " + url_photo.substring(url_photo.lastIndexOf("/") + 1))

                    val photo = Util.saveImage(bitmap, output_directory +"/"+ url_photo.substring(url_photo.lastIndexOf("/") + 1));
                    photo?.let {
                        course.photo = Uri.fromFile(it).toString()
                        backUpdate(course)
                    }
                },
                0, // max width
                0, // max height
                ImageView.ScaleType.CENTER_CROP, // image scale type
                Bitmap.Config.ARGB_8888, // decode config
                { error -> // error listener
                    Log.d(MainActivity.TAG, error.message.toString())
                    Log.d(MainActivity.TAG, url_photo)
                }
        )

        activity?.let {
                LoyolaService.getInstance(it.baseContext).addToRequestQueue(imageRequest)
        }
    }
    override fun downloadDocument(course: Course) {
        (activity as HomeActivity).downloadDocumentCourse(getOutputDirectory(activity as AppCompatActivity).toString(), course.document, course)
    }
}

