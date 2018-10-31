package othr.de.sites.main

class MainApp : Application(), AnkoLogger {

    //lateinit var sites : SiteStore

    override fun onCreate() {
        super.onCreate()
        //sites = SiteJSONStore(applicationContext)
        info("Site Application started")
    }
}

