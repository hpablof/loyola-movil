package app.wiserkronox.loyolasocios.service.model

data class CertificateRequestModel (
    val error: Boolean,
    val errorMessage:Array<String>,
    val errorCode:Number,
    val result: List<CertificateModel>
)