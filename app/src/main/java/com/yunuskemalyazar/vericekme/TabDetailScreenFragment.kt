package com.yunuskemalyazar.vericekme

import adabter.DataExtractionAdabter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.yunuskemalyazar.vericekme.databinding.FragmentTabDetailScreenBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import model.Constant
import model.MarketProductData
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class TabDetailScreenFragment : Fragment() {

    private lateinit var binding: FragmentTabDetailScreenBinding
    private var productList : ArrayList<MarketProductData> = arrayListOf()
    private var position = 0
    private var tabList : ArrayList<Pair<String,String>> = arrayListOf()
    private var dataExtractionAdabter = DataExtractionAdabter()

    private var productName : ArrayList<String> = arrayListOf()
    private var productImage : ArrayList<String> = arrayListOf()
    private var productPrice : ArrayList<String> = arrayListOf()
    private var productDetail : ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position =it.get(Constant.TAB_POSITION) as Int
            tabList = it.get(Constant.TAB_LİST) as ArrayList<Pair<String, String>>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTabDetailScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            recycler.layoutManager = GridLayoutManager(requireContext(),3)
            recycler.adapter = dataExtractionAdabter

        }

        GlobalScope.launch {
            try {
                var doc : Document = Jsoup.connect(Constant.BASE_URL + tabList.get(position).second).get()
                activity?.runOnUiThread {
                    for (div in doc.select(".product-card .product-detail .product-name")){
                        productName.add(div.text())
                    }
                    for (div in doc.select(".product-card .product-image img")){
                        productImage.add(div.attr("data-src").toString())
                    }
                    for (div in doc.select(".product-card .product-detail .price-box")){
                        productPrice.add(div.text())
                    }
                    for (div in doc.select(".product-card a")){
                        productDetail.add(div.attr("href"))
                    }
                }
            }
            catch (e : Exception){
                Log.i("Jsoup", e.message.toString())
                e.printStackTrace()
            }
            activity?.runOnUiThread {
                for(x in 0..productDetail.size){
                    productList.add(MarketProductData(productName = productName.getOrNull(x), productImage = productImage.getOrNull(x), productPrice = productPrice.getOrNull(x), productDetail = productDetail.getOrNull(x)))
                }
                dataExtractionAdabter.fillProductList(productList)
            }
        }

        dataExtractionAdabter.productClick = { product ->

            Toast.makeText(requireContext(),"${product.productName} ürününüz sepete eklenmiştir. Sepet tutarınız ${product.productPrice}",Toast.LENGTH_LONG).show()
        }

        dataExtractionAdabter.productLayoutClick = { url ->

            val intent = Intent(requireContext(),ProductDetailActivity::class.java)
            intent.putExtra("tab",url)
            startActivity(intent)
        }
    }
}