package com.example.buggyweather.network.exception

class RemoteException(msg: String): Throwable("Server error: $msg")