package gun0912.tedimagepicker.model

import android.net.Uri
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

/**
 * Represents a media item, which can be either an image or a video.
 *
 * @property albumName The name of the album the media item belongs to.
 * @property uri The URI of the media item.
 * @property dateAddedSecond The timestamp representing when the media item was added, in seconds since the epoch. (GMT Based)
 */
internal sealed class Media(
    open val albumName: String,
    open val uri: Uri,
    open val dateAddedSecond: Long,
) {
    data class Image(
        override val albumName: String,
        override val uri: Uri,
        override val dateAddedSecond: Long,
    ) : Media(albumName, uri, dateAddedSecond)

    data class Video(
        override val albumName: String,
        override val uri: Uri,
        override val dateAddedSecond: Long,
        val duration: Long,
    ) : Media(albumName, uri, dateAddedSecond){

        val durationText: String = duration.let { durationMills ->
            val hours = TimeUnit.MILLISECONDS.toHours(durationMills)
            val dateFormatPattern =
                if (hours > 0) {
                    "HH:mm:ss"
                } else {
                    "mm:ss"
                }
            SimpleDateFormat(dateFormatPattern, Locale.getDefault())
                .apply {
                    timeZone = TimeZone.getTimeZone("GMT")
                }
                .format(Date(durationMills))
        }
    }
}
