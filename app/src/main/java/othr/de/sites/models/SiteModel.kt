package othr.de.sites.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SiteModel(

  var id: Long = 0,
  var name: String = "",
  var description: String = "",
  var images: String = "",
  var latitute: Double = 49.0193773,
  var longtitue: Double = 12.0985301,
  var zoom: Float = 16F,
  var visited: Boolean = false,
  var date_visited: String = "",
  var additionalInfo: String = ""

) : Parcelable

@Parcelize
data class Location(
  var longtitue: Double = 0.0,
  var latitute: Double = 0.0,
  var zoom: Float = 0F
) : Parcelable

@SuppressWarnings
@Parcelize
data class User(
  var email: String = "",
  var password: String = "",
  var sites: List<SiteModel>
) :Parcelable