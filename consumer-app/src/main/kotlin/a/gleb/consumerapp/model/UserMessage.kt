package a.gleb.consumerapp.model

import kotlinx.serialization.Serializable

@Serializable
data class UserMessage(
    var username: String,
    var message: String
)