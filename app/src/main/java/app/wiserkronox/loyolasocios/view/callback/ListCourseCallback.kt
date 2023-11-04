package app.wiserkronox.loyolasocios.view.callback

import app.wiserkronox.loyolasocios.service.model.Course

interface ListCourseCallback {
    fun downloadDocument(course: Course)
}