package com.hotel.app.fcm.models

data class PushRequest(val title: String, val message: String, val token: String)