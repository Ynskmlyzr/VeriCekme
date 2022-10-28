package adabter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yunuskemalyazar.vericekme.databinding.ProductListRecyclerViewBinding
import model.MarketProductData

class DataExtractionAdabter : RecyclerView.Adapter<DataExtractionAdabter.ListHolder>() {

    private var productList : ArrayList<MarketProductData> = arrayListOf()
    var productClick : (MarketProductData) -> Unit = {}
    var productLayoutClick : (String?) -> Unit = {}

    inner class ListHolder(val binding: ProductListRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(productData: MarketProductData){
            binding.apply {

                tvProductName.text=productData.productName
                tvProductPrice.text=productData.productPrice
                //tvProductDetail.text=productData.productDetail
                Glide.with(itemView).load(productData.productImage).into(imgProductImage)

                ivAddIcon.setOnClickListener {
                    productClick(productData)
                }

                productLayout.setOnClickListener {
                    productLayoutClick(productData.productDetail)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {

        val binding = ProductListRecyclerViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListHolder(binding)

    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int {
        return productList.size-1
    }

    fun fillProductList(list: ArrayList<MarketProductData>){
        productList.addAll(list)
        notifyDataSetChanged()
    }
}