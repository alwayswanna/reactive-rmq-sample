package a.gleb.consumerapp.db.repository

import a.gleb.consumerapp.db.entity.UserMessageDAO
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserMessageRepository: CoroutineCrudRepository<UserMessageDAO, UUID>