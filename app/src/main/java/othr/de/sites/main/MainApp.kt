package othr.de.sites.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import othr.de.sites.models.SiteJSONStore
import othr.de.sites.models.SiteStore
import othr.de.sites.models.firebase.SiteFireStore

class MainApp : Application(), AnkoLogger {

  lateinit var sites: SiteStore

  override fun onCreate() {
    super.onCreate()
    //sites = SiteJSONStore(applicationContext)
    sites = SiteFireStore(applicationContext)
    info("Site Application started")
  }
}

