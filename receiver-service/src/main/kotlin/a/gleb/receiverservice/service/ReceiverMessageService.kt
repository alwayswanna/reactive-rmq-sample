package a.gleb.receiverservice.service

import a.gleb.receiverservice.model.RequestModel

interface ReceiverMessageService {

    suspend fun proceed(message: RequestModel)
}