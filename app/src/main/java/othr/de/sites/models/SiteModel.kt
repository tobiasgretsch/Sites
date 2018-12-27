package othr.de.sites.models

import android.os.Parcelable

import kotlinx.android.parcel.Parcelize

@Parcelize
data class SiteModel(
  /*@PrimaryKey(autoGenerate = true)*/
  var id: Long = 0,
  var fbId: String = "",
  var name: String = "",
  var description: String = "",
  var images: String = "",
  var latitude: Double = 49.0193773,
  var longitude: Double = 12.0985301,
  var zoom: Float = 16F,
  var visited: Boolean = false,
  var date_visited: String = "",
  var additionalInfo: String = ""
) : Parcelable

@Parcelize
data class Location(
  var longtitue: Double = 0.0,
  var latitute: Double = 0.0,
  var zoom: Float = 0F,
  var titel: String = ""
) : Parcelable