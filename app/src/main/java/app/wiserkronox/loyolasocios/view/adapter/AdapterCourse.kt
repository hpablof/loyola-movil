package app.wiserkronox.loyolasocios.view.adapter


import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.wiserkronox.loyolasocios.R
import app.wiserkronox.loyolasocios.service.model.Course
import app.wiserkronox.loyolasocios.view.callback.ListCourseCallback


class AdapterCourse internal constructor(private val courseList: List<Course>,val mCallBack: ListCourseCallback) :
        RecyclerView.Adapter<AdapterCourse.CourseViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCourse.CourseViewHolder {
        val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_course, parent, false)
        return AdapterCourse.CourseViewHolder(view)
    }

    fun setUpImage(holder: AdapterCourse.CourseViewHolder, bmp: Bitmap){
        holder.photo_course.setImageBitmap(bmp)
    }

    override fun onBindViewHolder(holder: AdapterCourse.CourseViewHolder, position: Int) {
        val course = courseList[position]
        if( !course.photo.startsWith("http") && !course.photo.equals("")) {
            holder.photo_course.visibility = View.VISIBLE
            holder.photo_course.setImageURI(Uri.parse(course.photo))
        } else {
            holder.photo_course.visibility = View.GONE
        }
        holder.name_c.text = course.name
        holder.date_s.text = course.start_date
        holder.date_e.text = course.end_date
        holder.expositor.text = course.expositor
        if(!course.schedule.isEmpty()){
            holder.schedule.text = course.schedule
        }
        holder.type.text = course.type
        if(course.type.equals("Virtual")) {
            holder.cont_locate.visibility = View.GONE
            holder.url.text = course.url
            holder.code.text = course.code
            holder.password.text = course.password

        } else {
            holder.locate.text = course.location
            holder.cont_password.visibility = View.GONE
            holder.cont_codes.visibility = View.GONE
            holder.cont_url.visibility = View.GONE
        }

        if( !course.document.isEmpty() ) {
            holder.download_pdf.visibility = Button.VISIBLE
            holder.download_pdf.setOnClickListener {
                mCallBack.downloadDocument( courseList[position] )
            }
        } else {
            holder.download_pdf.visibility = Button.GONE
        }
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name_c: TextView = itemView.findViewById(R.id.text_name_c)
        val photo_course: ImageView = itemView.findViewById(R.id.photo_course)
        val date_s: TextView = itemView.findViewById(R.id.text_date_s)
        val date_e: TextView = itemView.findViewById(R.id.text_date_e)
        val expositor: TextView = itemView.findViewById(R.id.text_expositor)
        val schedule: TextView = itemView.findViewById(R.id.text_horario)
        val type: TextView = itemView.findViewById(R.id.text_type)
        val locate: TextView = itemView.findViewById(R.id.text_locate)
        val url: TextView = itemView.findViewById(R.id.text_url)
        val code: TextView = itemView.findViewById(R.id.text_code)
        val password: TextView = itemView.findViewById(R.id.text_password)
        val cont_locate: LinearLayout = itemView.findViewById(R.id.cont_location)
        val cont_password: LinearLayout = itemView.findViewById(R.id.cont_password)
        val cont_codes: LinearLayout = itemView.findViewById(R.id.cont_code)
        val cont_url: LinearLayout = itemView.findViewById(R.id.cont_url)
        val download_pdf: Button = itemView.findViewById(R.id.pdf_curso)
    }
}