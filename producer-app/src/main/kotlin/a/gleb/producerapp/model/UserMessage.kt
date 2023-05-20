package a.gleb.producerapp.model

import kotlinx.serialization.Serializable

@Serializable
data class UserMessage(
    private var username: String,
    private var message: String?
)
