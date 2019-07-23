
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MainHomeAdapter(manager: androidx.fragment.app.FragmentManager) : androidx.fragment.app.FragmentPagerAdapter(manager) {
    private var mFragmentList : MutableList<androidx.fragment.app.Fragment> = mutableListOf()

    override fun getItem(position: Int): androidx.fragment.app.Fragment {
        return mFragmentList.get(position)
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }


    fun addFragment(fragment: androidx.fragment.app.Fragment) {
        mFragmentList.add(fragment)
    }

}