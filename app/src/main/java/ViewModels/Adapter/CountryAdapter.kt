package ViewModels.Adapter

import Models.Country
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.quantusti.myapplication1.R
import androidx.databinding.DataBindingUtil
import com.quantusti.myapplication1.databinding.ItemCountryBinding

class CountryAdapter(
    countryList: List<Country>
) :
    RecyclerView.Adapter<CountryAdapter.MyViewHolder>() {

    private val _countryList: List<Country>
    private var _layoutInflater: LayoutInflater? = null

    init {
        _countryList = countryList
    }

    inner class MyViewHolder(val _binding: ItemCountryBinding) :
        RecyclerView.ViewHolder(_binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        if (_layoutInflater == null) {
            _layoutInflater = LayoutInflater.from(parent.context)
        }
        val binding = DataBindingUtil.inflate<ItemCountryBinding>(
            _layoutInflater!!,
            R.layout.item_country,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    //Bind object in the item layout
    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ){
        val country = _countryList[position]
        holder._binding.country = country

        holder._binding.textName.setText(country.name + ", " + country.region)
    }

    override fun getItemCount() : Int {
        return _countryList.size
    }
}