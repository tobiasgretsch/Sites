package othr.de.sites.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_site_list_view.*
import othr.de.sites.R

class SiteListView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site_list_view)
        setSupportActionBar(listToolbar)
    }
}
