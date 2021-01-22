package com.tamboon.androidclient.view.donationCompleted

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.tamboon.androidclient.R
import com.tamboon.androidclient.databinding.FragmentDonationCompletedBinding


class DonationCompletedFragment : Fragment() {

    private val response: DonationCompletedFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var bindingUtil = DataBindingUtil.inflate<FragmentDonationCompletedBinding>(
            inflater,
            R.layout.fragment_donation_completed,
            container,
            false
        )
        var view = bindingUtil.root
        bindingUtil.response = response.theResponse
        bindingUtil.ivResponse.setImageResource(response.theResponse.icon)
        bindingUtil.bDone.setOnClickListener { button ->

            val navOptions = NavOptions.Builder().setPopUpTo(R.id.charityListFragment, true).build()
            Navigation.findNavController(button)
                .navigate(R.id.charityListFragment, null, navOptions)
        }
        return view
    }


}