package com.example.buggyweather.core.network.exception

class RemoteException(msg: String = "unknown"): Throwable("Server error: $msg")