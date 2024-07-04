package com.application.snackhub.fragments.categories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.snackhub.R
import com.application.snackhub.SpacingDecorator.HorizantalSpacingItemDecorator
import com.application.snackhub.activities.ShoppingActivity
import com.application.snackhub.adapters.recyclerview.ProductsRecyclerAdapter
import com.application.snackhub.databinding.FragmentPizzaBinding
import com.application.snackhub.resource.Resource
import com.application.snackhub.util.Constants
import com.application.snackhub.viewmodel.shopping.ShoppingViewModel

class PizzaFragment : Fragment(R.layout.fragment_enlightening) {
    val TAG = "PizzaFragment"
    private lateinit var viewModel: ShoppingViewModel
    private lateinit var binding: FragmentPizzaBinding
    private lateinit var headerAdapter: ProductsRecyclerAdapter
    private lateinit var productsAdapter: ProductsRecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        headerAdapter = ProductsRecyclerAdapter()
        productsAdapter = ProductsRecyclerAdapter()
        viewModel = (activity as ShoppingActivity).viewModel

        viewModel.getAccessories()
        viewModel.getMostRequestedAccessories()

        Log.d("Test","pizza")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPizzaBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupHeaderRecyclerview()
        observeHeader()

        setupProductsRecyclerView()
        observeProducts()

        headerPaging()
        productsPaging()

        productsAdapter.onItemClick = { product ->
            val bundle = Bundle()
            bundle.putParcelable("product",product)
            bundle.putString("flag", Constants.PRODUCT_FLAG)
            Log.d("test",product.newPrice!!)

            findNavController().navigate(R.id.action_homeFragment_to_productPreviewFragment2,bundle)
        }

        headerAdapter.onItemClick = { product ->
            val bundle = Bundle()
            bundle.putParcelable("product",product)
            bundle.putString("flag", Constants.PRODUCT_FLAG)
            findNavController().navigate(R.id.action_homeFragment_to_productPreviewFragment2,bundle)
        }

    }

    private fun productsPaging() {
        binding.scrollTea.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (v!!.getChildAt(0).bottom <= (v.height + scrollY)) {
                viewModel.getAccessories(productsAdapter.differ.currentList.size)
            }
        })
    }

    private fun headerPaging() {
        binding.rvHeader.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollHorizontally(1) && dx != 0)
                    viewModel.getMostRequestedAccessories(headerAdapter.differ.currentList.size)

            }
        })
    }

    private fun observeProducts() {
        viewModel.pizza.observe(viewLifecycleOwner, Observer { response ->

            when (response) {
                is Resource.Loading -> {
                    showBottomLoading()
                    return@Observer
                }

                is Resource.Success -> {
                    hideBottomLoading()
                    productsAdapter.differ.submitList(response.data)
                    return@Observer
                }

                is Resource.Error -> {
                    hideBottomLoading()
                    Log.e(TAG, response.message.toString())
                    return@Observer
                }
            }
        })
    }

    private fun hideBottomLoading() {
        binding.progressbar2.visibility = View.GONE
    }

    private fun showBottomLoading() {
        binding.progressbar2.visibility = View.VISIBLE
    }

    private fun setupProductsRecyclerView() {
        binding.rvProducts.apply {
            adapter = productsAdapter
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        }
    }

    private fun observeHeader() {
        viewModel.mostRequestedAccessories.observe(viewLifecycleOwner, Observer { response ->

            when (response) {
                is Resource.Loading -> {
                    showTopLoading()
                    return@Observer
                }

                is Resource.Success -> {
                    hideTopLoading()
                    headerAdapter.differ.submitList(response.data)
                    return@Observer
                }

                is Resource.Error -> {
                    hideTopLoading()
                    Log.e(TAG, response.message.toString())
                    return@Observer
                }
            }
        })
    }

    private fun hideTopLoading() {
        binding.progressbar1.visibility = View.GONE
    }

    private fun showTopLoading() {
        binding.progressbar1.visibility = View.VISIBLE
    }

    private fun setupHeaderRecyclerview() {
        binding.rvHeader.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = headerAdapter
            addItemDecoration(HorizantalSpacingItemDecorator(100))
        }
    }

}