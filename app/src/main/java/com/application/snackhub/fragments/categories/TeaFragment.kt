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
import com.application.snackhub.databinding.FragmentTeaBinding
import com.application.snackhub.resource.Resource
import com.application.snackhub.util.Constants
import com.application.snackhub.viewmodel.shopping.ShoppingViewModel

class TeaFragment : Fragment(R.layout.fragment_burger) {
    private lateinit var viewModel: ShoppingViewModel
    private lateinit var binding: FragmentTeaBinding
    private lateinit var mostOrderedTeasAdapter: ProductsRecyclerAdapter
    private lateinit var teaAdapter: ProductsRecyclerAdapter
    private val TAG = "TeaFragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mostOrderedTeasAdapter = ProductsRecyclerAdapter()
        teaAdapter = ProductsRecyclerAdapter()
        viewModel = (activity as ShoppingActivity).viewModel

        viewModel.getMostRequestedTeas()
        viewModel.getTeaProduct()
        Log.d("test","teaFragment")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentTeaBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMostOrderedTeaRecyclerView()
        observeMostOrderedTea()

        setupTeaRecyclerView()
        observeTea()

        mostRequestedTeaPaging()
        teaPaging()

        teaAdapter.onItemClick = { product ->
            val bundle = Bundle()
            bundle.putParcelable("product",product)
            bundle.putString("flag", Constants.PRODUCT_FLAG)

            findNavController().navigate(R.id.action_homeFragment_to_productPreviewFragment2,bundle)
        }

        mostOrderedTeasAdapter.onItemClick = { product ->
            val bundle = Bundle()
            bundle.putParcelable("product",product)
            bundle.putString("flag", Constants.PRODUCT_FLAG)
            findNavController().navigate(R.id.action_homeFragment_to_productPreviewFragment2,bundle)
        }

    }

    private fun teaPaging() {
        binding.scrollTea.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (v!!.getChildAt(0).bottom <= (v.height + scrollY)) {
                viewModel.getTeaProduct(teaAdapter.differ.currentList.size)
            }
        })
    }

    private fun mostRequestedTeaPaging() {
        binding.rvHeader.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollHorizontally(1) && dx != 0)
                    viewModel.getMostRequestedTeas(mostOrderedTeasAdapter.differ.currentList.size)

            }
        })
    }

    private fun observeTea() {
        viewModel.tea.observe(viewLifecycleOwner, Observer { response ->

            when (response) {
                is Resource.Loading -> {
                    showBottomLoading()
                    return@Observer
                }

                is Resource.Success -> {
                    hideBottomLoading()
                    teaAdapter.differ.submitList(response.data)
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

    private fun setupTeaRecyclerView() {
        binding.rvProducts.apply {
            adapter = teaAdapter
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        }
    }

    private fun observeMostOrderedTea() {
        viewModel.mostRequestedTea.observe(viewLifecycleOwner, Observer { response ->

            when (response) {
                is Resource.Loading -> {
                    showTopLoading()
                    return@Observer
                }

                is Resource.Success -> {
                    hideTopLoading()
                    mostOrderedTeasAdapter.differ.submitList(response.data)
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

    private fun setupMostOrderedTeaRecyclerView() {
        binding.rvHeader.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = mostOrderedTeasAdapter
            addItemDecoration(HorizantalSpacingItemDecorator(100))
        }
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    override fun onResume() {
        super.onResume()


    }

}