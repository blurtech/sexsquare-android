package tech.blur.sexsquare

import android.os.Bundle
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import tech.blur.sexsquare.databinding.ActivityMainBinding
import tech.blur.sexsquare.ui.dashboard.DashboardFragment
import tech.blur.sexsquare.ui.map.MapFragment
import tech.blur.sexsquare.ui.notifications.NotificationsFragment
import kotlin.reflect.KClass

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MainContentFragmentPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_map -> {
                    viewPager_content_Main.setCurrentItem(0, false)
                }
                R.id.navigation_feed -> {
                    viewPager_content_Main.setCurrentItem(1, false)
                }
                R.id.navigation_profile -> {
                    viewPager_content_Main.setCurrentItem(2, false)
                }
            }
            return@setOnNavigationItemSelectedListener true
        }

        adapter = MainContentFragmentPagerAdapter(
            supportFragmentManager,
            MapFragment::class,
            DashboardFragment::class,
            NotificationsFragment::class
        )

        viewPager_content_Main.apply {
            offscreenPageLimit = (this@MainActivity).adapter.count
            adapter = (this@MainActivity).adapter
        }
    }

    private class MainContentFragmentPagerAdapter(
        fragmentManager: FragmentManager,
        vararg fragmentTypes: KClass<out Fragment>
    ) : FragmentStatePagerAdapter(fragmentManager) {

        private val fragmentModels: List<Item> = fragmentTypes.map { Item(it) }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val fragment = super.instantiateItem(container, position) as Fragment
            fragmentModels[position].fragment = fragment
            return fragment
        }

        override fun destroyItem(container: ViewGroup, position: Int, fragment: Any) {
            fragmentModels[position].fragment = null
            super.destroyItem(container, position, fragment)
        }

        override fun getItem(position: Int): Fragment =
            fragmentModels[position].fragmentType.java.newInstance()

        override fun getCount() = fragmentModels.size

        fun getFragment(position: Int) = fragmentModels[position].fragment!!

        private class Item(val fragmentType: KClass<out Fragment>, var fragment: Fragment? = null)
    }
}
