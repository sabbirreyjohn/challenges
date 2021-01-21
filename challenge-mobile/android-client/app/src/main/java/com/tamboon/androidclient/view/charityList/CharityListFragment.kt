package com.tamboon.androidclient.view.charityList

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import co.omise.android.ui.CreditCardActivity
import co.omise.android.ui.OmiseActivity
import com.tamboon.androidclient.BuildConfig
import com.tamboon.androidclient.R
import com.tamboon.androidclient.databinding.FragmentCharityListBinding

class CharityListFragment : Fragment(), CharityAdapter.OnRecycleViewItemClickListener {

    private val TAG = "CharityListFragment"
    private var binding: FragmentCharityListBinding? = null
    private val charityViewModel: CharityViewModel by viewModels()
    private var charityAdapter: CharityAdapter? = null
    private val REQUEST_CC: Int = 100
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_charity_list, container, false)
        binding = FragmentCharityListBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.rcv?.itemAnimator = DefaultItemAnimator()
        binding?.rcv?.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )

        loadCharities()
    }

    private fun loadCharities() {
        Log.d(TAG, "loadFromServer: Loading...")
        charityViewModel.getCharities().observe(viewLifecycleOwner, Observer { list ->
            if (!list.isNullOrEmpty()) {
                Log.d(TAG, "loadFromServer: Loaded from server")
                loadRecyclerView(list)
            } else {

                Log.d(TAG, "loadFromServer: failed")
            }
        })
    }

    private fun loadRecyclerView(charities: List<Charity>) {
        Log.d(TAG, "loadRecyclerView: Loading...")
        charityAdapter = CharityAdapter(context, charities)
        binding?.rcv?.adapter = charityAdapter
        charityAdapter?.setOnRecycleViewItemClickListener(this)
    }

    override fun onRecycleViewItemClick(v: View?, charity: Charity) {

        Log.d(TAG, "loadRecyclerView: " + charity.name)
        var action =
            CharityListFragmentDirections.actionCharityListFragmentToDonationFragment(charity)
        findNavController().navigate(action)

    }


}