package othr.de.sites.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import othr.de.sites.models.SiteJSONStore
import othr.de.sites.models.SiteStore

class MainApp : Application(), AnkoLogger {

  lateinit var sites: SiteStore

  override fun onCreate() {
    super.onCreate()
    sites = SiteJSONStore(applicationContext)
    info("Site Application started")
  }
}

