package com.example.buggyweather.core.network.exception

class RemoteException(msg: String): Throwable("Server error: $msg")