package othr.de.sites.models.json

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import othr.de.sites.helpers.exists
import othr.de.sites.helpers.read
import othr.de.sites.helpers.write
import othr.de.sites.models.SiteModel
import othr.de.sites.models.SiteStore
import java.util.*

val JSON_FILE = "sites.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<SiteModel>>() {}.type

fun generateRandomId(): Long {
  return Random().nextLong()
}

class SiteJSONStore : SiteStore, AnkoLogger {
  override fun clear() {
    //
  }

  val context: Context
  var sites = mutableListOf<SiteModel>()

  constructor(context: Context) {
    this.context = context
    if (exists(context, JSON_FILE)) deserialize()
  }

  override fun findAll(): List<SiteModel> {
    return sites
  }

  override fun create(site: SiteModel) {
    site.id = generateRandomId()
    sites.add(site)
    serialize()
  }

  override fun update(site: SiteModel) {
    val siteList = findAll()
    var foundSite: SiteModel? = siteList.find { s -> s.id == site.id }

    if (foundSite != null) {
      foundSite.images = site.images
      foundSite.name = site.name
      foundSite.description = site.description
      foundSite.latitude = site.latitude
      foundSite.longitude = site.longitude
      foundSite.visited = site.visited
      foundSite.additionalInfo = site.additionalInfo
    }
    serialize()
  }

  override fun delete(site: SiteModel) {
    sites.remove(site)
    serialize()
  }

  override fun findById(id: Long): SiteModel? {
    val foundSite: SiteModel? = sites.find { s -> s.id == id }
    return foundSite
  }

  private fun serialize() {
    val jsonString = gsonBuilder.toJson(sites, listType)
    write(context, JSON_FILE, jsonString)
  }

  private fun deserialize() {
    val jsonString = read(context, JSON_FILE)
    sites = Gson().fromJson(jsonString, listType)
  }
}