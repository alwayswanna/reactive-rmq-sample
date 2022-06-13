package a.gleb.publisherservice.model

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.http.HttpStatus

data class ResponseModel(
    @Schema(name = "Status", required = true, description = "Status code")
    val status: HttpStatus,
    @Schema(name = "Payload", required = true, description = "Description of response")
    val message: String,
    @Schema(name = "Attributes", required = false, description = "Additional attributes of response")
    val attributes: Map<String, String>?
)
