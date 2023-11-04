package app.wiserkronox.loyolasocios.service.repository

import android.content.Context
import app.wiserkronox.loyolasocios.R
import app.wiserkronox.loyolasocios.service.model.Course
import org.json.JSONArray
import org.json.JSONException

class CourseRest (val context: Context){
    companion object{
        var GET_COURSES = "get_course.php"
    }

    fun getCourseURL(): String {
        return context.getString(R.string.host_service)+
                context.getString(R.string.home_service)+
                GET_COURSES
    }

    fun getCoursesList(jCourses: JSONArray): List<Course>{
        var listCourse : ArrayList<Course> = arrayListOf()
        try{
            for ( i in 0..jCourses.length()-1 ){
                val j_course = jCourses.getJSONObject(i)
                var course = Course()

                if( j_course.has("photo") && !j_course.getString("photo").equals("null"))
                course.photo = context.getString(R.string.host_service)+
                        context.getString(R.string.home_aplication)+
                        context.getString(R.string.dir_photo_course)+
                        j_course.getLong("id")+"/"+
                        j_course.getString("photo")

                course.id = j_course.getLong("id")
                course.name = j_course.getString("name")
                course.start_date = j_course.getString("start_date")
                course.end_date = j_course.getString("end_date")
                course.schedule = j_course.getString("schedule")
                course.status = j_course.getString("status")
                course.type = j_course.getString("type")
                course.expositor = j_course.getString("expositor")

                course.url = j_course.getString("url")
                course.code = j_course.getString("code")
                course.password = j_course.getString("password")
                course.location = j_course.getString("location")

                if( j_course.has("document") && !j_course.getString("document").equals("null"))
                    course.document = context.getString(R.string.host_service)+
                            context.getString(R.string.home_aplication)+
                            context.getString(R.string.dir_photo_course)+
                            j_course.getLong("id")+"/"+
                            j_course.getString("document")

                listCourse.add(course)
            }
            return listCourse

        } catch (j_error: JSONException){
            j_error.printStackTrace()
            return listCourse
        }
    }
}