package othr.de.sites.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class SiteModel(
  
    var id: Long = 0,
    var name: String = "",
    var description: String = "",
    var images: String = "",
    var longtitue: Double = 0.0,
    var latitute: Double = 0.0,
    var visited: Boolean = false,
    var date_visited: String = ""

) : Parcelable

data class User(
  var email: String = "",
  var password: String = ""
  //speichern der Userdaten über die besuchten Sites
)