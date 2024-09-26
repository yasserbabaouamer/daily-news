package owner.yacer.mynewsapp.activities

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home_page.*
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import owner.yacer.mynewsapp.fragments.FavoriteFragment.Companion.adapter
import owner.yacer.mynewsapp.fragments.*
import owner.yacer.mynewsapp.models.Article
import owner.yacer.mynewsapp.utils.BottomNavCallback
import owner.yacer.mynewsapp.adapters.FavoriteNewsAdapter
import owner.yacer.mynewsapp.R
import owner.yacer.mynewsapp.utils.AppUtils
import java.util.*


class HomeActivity : AppCompatActivity() {
    private var auth = Firebase.auth
    var user = auth.currentUser
    var db = Firebase.firestore
    lateinit var progressBoxHome: ProgressDialog
    var callback = object : BottomNavCallback {
        override fun update() {
            setUpBottomNavigationView()
        }
    }

    companion object {
        lateinit var news:List<Article>
    }

    lateinit var toggleBtn: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        // Set the user profile picture
        val headerView = nav_bar.getHeaderView(0)
        val imgView = headerView.findViewById<ImageView>(R.id.iv_user)
        if (user != null && user?.photoUrl != null) {
            Glide.with(this).load(user!!.photoUrl).into(imgView)
        }

        progressBoxHome = ProgressDialog(this)
        with(progressBoxHome) {
            setMessage("يرجى الإنتظار...")
            setCancelable(false)
        }
        setUpNavigationDrawer()
        setUpBottomNavigationView()

        // Fetch favorites from firebase
        lifecycleScope.async {
            getFavoriteNews()
        }

        setFragment(HomeFragment())
    }

    private fun getFavoriteNews() {
        lifecycleScope.launch {
            db.collection("root").document(user!!.uid).get().addOnCompleteListener { task ->
                if (task.isSuccessful && task.result.data != null) {
                    val articleList = LinkedList<Article>()
                    val result = task.result.data as HashMap<String, Any>
                    Log.e("msg", result.toString())
                    for (entry: Map.Entry<String, Any> in result.entries) {
                        val map = entry.value as HashMap<String, Any>
                        val article = AppUtils.mapperToArticle(map)
                        articleList.add(article)
                    }
                    FavoriteNewsAdapter.articles = articleList
                    adapter.notifyDataSetChanged()
                }
            }.await()
        }
    }

    private fun setUpBottomNavigationView() {
        val user = auth.currentUser
        bottomNavigationView2.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.miHome -> {
                    setFragment(HomeFragment())
                }
                R.id.miFavorite -> if (user != null) {
                    setFragment(FavoriteFragment())
                } else {
                    setFragment(FavFragment_notSignIN())
                }
                R.id.miAccount -> if (user != null) {
                    setFragment(AccountFragment(callback))
                } else {
                    setFragment(SignInFragment(callback))
                }
            }
            true
        }
    }

    private fun setUpNavigationDrawer() {
        nav_bar.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.miSetting ->
                    Toast.makeText(
                        applicationContext,
                        "ستتوفر بعض الإعدادات عند إطلاق التطبيق ان شاء الله ..",
                        Toast.LENGTH_SHORT
                    ).show()
                R.id.miHelp -> Toast.makeText(
                    applicationContext,
                    "سيتوفر الدعم الفني عند إطلاق التطبيق ان شاء الله ..",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.miFollowUs -> {
                    var uri = Uri.parse("https://www.linkedin.com/in/yacer-babaouamer-7b12b1229/")
                    Intent(Intent.ACTION_VIEW, uri).also {
                        startActivity(it)
                    }
                }
            }
            true
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flfragment, fragment)
            commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggleBtn.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }


}