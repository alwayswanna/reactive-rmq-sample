package a.gleb.receiverservice.model

import com.fasterxml.jackson.annotation.JsonProperty

data class RequestModel(
    @JsonProperty("author") val author: String,
    @JsonProperty("message") val message: String,
    @JsonProperty("attributes") var attributes: Map<String, String>?
)
