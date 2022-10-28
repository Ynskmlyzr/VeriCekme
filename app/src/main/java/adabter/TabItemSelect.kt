package adabter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.yunuskemalyazar.vericekme.TabDetailScreenFragment
import model.Constant

class TabItemSelect (fragment : FragmentActivity,private var tabList : ArrayList<Pair<String,String>>) : FragmentStateAdapter (fragment) {
    override fun getItemCount(): Int {
        return tabList.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = TabDetailScreenFragment()
        fragment.arguments = Bundle().apply {
            putInt(Constant.TAB_POSITION,position)
            putSerializable(Constant.TAB_LİST,tabList)
        }
        return fragment
    }
}