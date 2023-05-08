package a.gleb.consumerapp.db.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*


@Table("user_message")
data class UserMessageDAO(
    @field:Id
    var id: UUID? = null,
    var username: String,
    var message: String? = null
)
