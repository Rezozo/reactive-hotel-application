package com.hotel.app.fcm.service

import com.hotel.app.fcm.models.PushRequest
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class PushService {
    private val logger = LoggerFactory.getLogger(PushService::class.java)

    private var fcmService: FCMService

    constructor(fcmService: FCMService) {
        this.fcmService = fcmService;
    }

    fun sendPushNotificationToToken(request: PushRequest) {
        try {
            fcmService.sendMessageToToken(request)
        } catch (e: Exception) {
            logger.error(e.message)
        }
    }
}