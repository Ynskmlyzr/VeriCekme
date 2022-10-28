package com.yunuskemalyazar.vericekme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yunuskemalyazar.vericekme.databinding.ActivityProductDetailBinding
import model.Constant

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding
    private var productDetailUrl : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productDetailUrl=intent.getStringExtra("tab")

        binding.webView.loadUrl(Constant.BASE_URL + productDetailUrl)

    }
}