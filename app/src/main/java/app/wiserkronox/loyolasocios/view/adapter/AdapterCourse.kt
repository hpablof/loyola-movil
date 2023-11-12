package app.wiserkronox.loyolasocios.view.adapter


import android.content.Context
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


class AdapterCourse
    (
        private val context: Context,
        private val dataset: List<Course>
    ):RecyclerView.Adapter<AdapterCourse.CourseViewHolder>() {
    class CourseViewHolder
        (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val number_c: TextView = itemView.findViewById(R.id.curso_number)
        val name_c: TextView = itemView.findViewById(R.id.text_name_c)
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
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseViewHolder {
        val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_course, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = dataset[position]
        holder.number_c.text = (position + 1).toString()
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
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}