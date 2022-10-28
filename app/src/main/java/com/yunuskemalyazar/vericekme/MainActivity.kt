package com.yunuskemalyazar.vericekme

import adabter.TabItemSelect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.yunuskemalyazar.vericekme.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import model.Constant
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var nameList: ArrayList<Pair<String,String>> = arrayListOf()
    private lateinit var tabItem : TabItemSelect

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            GlobalScope.launch {
                try {
                    val doc : Document = Jsoup.connect(Constant.BASE_URL).get()
                    runOnUiThread{
                     for (div in doc.select(".category-list a")){
                         nameList.add(Pair(div.text(),div.attributes()["href"]))
                     }
                        nameList.forEach {
                            tblTablo.addTab(tblTablo.newTab().setText(it.first))
                        }
                    }

                }catch (e : Exception){
                    e.printStackTrace()
                }
            }
            runOnUiThread{
                tabItem= TabItemSelect(this@MainActivity,nameList)
                pager2.adapter=tabItem

                TabLayoutMediator(tblTablo,pager2){ tab,position ->
                    tab.text=nameList.get(position).first
                }.attach()
            }
        }
    }
}