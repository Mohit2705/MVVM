package com.example.msharma.dashlite.util

import com.example.msharma.dashlite.data.LatLong
import org.mockito.Mockito

object MockHelper {

    inline fun <reified T : Any> any() = Mockito.any(LatLong::class.java) ?: LatLong(0.0, 0.0)

}