package othr.de.sites.models.firebase

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.jetbrains.anko.AnkoLogger
import othr.de.sites.helpers.readImageFromPath
import othr.de.sites.models.SiteModel
import othr.de.sites.models.SiteStore
import java.io.ByteArrayOutputStream
import java.io.File

class SiteFireStore(val context: Context) : SiteStore, AnkoLogger {

  val sites = ArrayList<SiteModel>()
  private lateinit var userId: String
  private lateinit var db: DatabaseReference
  private lateinit var st: StorageReference

  override fun findAll(): List<SiteModel> {
    return sites
  }

  override fun findById(id: Long): SiteModel? {
    val foundSite: SiteModel? = sites.find { p -> p.id == id }
    return foundSite
  }

  override fun create(site: SiteModel) {
    val key = db.child("users").child(userId).child("sites").push().key
    site.fbId = key!!
    sites.add(site)
    db.child("users").child(userId).child("sites").child(key).setValue(site)
    if((site.images.length) > 0 && (site.images[0] != 'h')) {
      updateImage(site)
    }
  }

  override fun update(site: SiteModel) {
    val foundSite: SiteModel? = sites.find { p -> p.fbId == site.fbId }
    if (foundSite != null) {
      foundSite.name = site.name
      foundSite.description = site.description
      foundSite.images = site.images
      foundSite.latitude = site.latitude
      foundSite.longitude = site.longitude
      foundSite.date_visited = site.date_visited
      foundSite.visited = site.visited
      foundSite.description = site.description
      foundSite.additionalInfo = site.additionalInfo
      foundSite.rating = site.rating
      foundSite.favorite = site.favorite
    }

    db.child("users").child(userId).child("sites").child(site.fbId).setValue(site)
    if((site.images.length) > 0 && (site.images[0] != 'h')) {
      updateImage(site)
    }
  }

  fun updateImage(site: SiteModel) {
    if(site.images != "") {
      val fileName = File(site.images)
      val imageName = fileName.name

      val imageRef = st.child(userId + '/' + imageName)
      val baos = ByteArrayOutputStream()
      val bitmap = readImageFromPath(context, site.images)

      bitmap?.let {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100,baos)
        val data = baos.toByteArray()
        val uploadTask = imageRef.putBytes(data)
        uploadTask.addOnFailureListener {
          println(it.message)
        }.addOnSuccessListener { taskSnapshot ->
          taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
            site.images = it.toString()
            db.child("users").child(userId).child("sites").child(site.fbId).setValue(site)
          }
        }
      }

    }
  }

  override fun delete(site: SiteModel) {
    //TODO DELETE PICTURES FROM STORAGE
    if(site.images == "_") {

      val imageName =File(site.images).nameWithoutExtension
      println(imageName)

      st.child(userId + '/' + imageName).delete().addOnSuccessListener {
        //
      }.addOnFailureListener {
        Log.d("StorageFailure","Image is not deleted! " + it.message)
      }
    }
    db.child("users").child(userId).child("sites").child(site.fbId).removeValue()
    sites.remove(site)
  }

  override fun clear() {
    sites.clear()
  }

  fun fetchSites(sitesReady: () -> Unit) {
    val valueEventListener = object : ValueEventListener {
      override fun onCancelled(error: DatabaseError) {
      }

      override fun onDataChange(dataSnapshot: DataSnapshot) {
        dataSnapshot.children.mapNotNullTo(sites) { it.getValue<SiteModel>(SiteModel::class.java) }
        sitesReady()
      }
    }
    userId = FirebaseAuth.getInstance().currentUser!!.uid
    db = FirebaseDatabase.getInstance().reference
    st = FirebaseStorage.getInstance().reference
    clear()
    db.child("users").child(userId).child("sites").addListenerForSingleValueEvent(valueEventListener)
  }
}