package ua.kpi.comsys.ip8313

import android.content.Context
import java.io.IOException

class AssetsManager(private val context: Context) {
    fun getAssetData(filename: String): ByteArray? {
        val buffer: ByteArray
        try {
            val inputStream = context.assets.open(filename)
            val size = inputStream.available()
            buffer = ByteArray(size)
            inputStream.use { it.read(buffer) }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return buffer
    }

}