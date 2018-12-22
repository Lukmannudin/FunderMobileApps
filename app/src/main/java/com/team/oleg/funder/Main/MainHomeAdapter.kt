
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class MainHomeAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
    private var mFragmentList : MutableList<Fragment> = mutableListOf()

    override fun getItem(position: Int): Fragment {
        return mFragmentList.get(position)
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }


    fun addFragment(fragment: Fragment) {
        mFragmentList.add(fragment)
    }

}