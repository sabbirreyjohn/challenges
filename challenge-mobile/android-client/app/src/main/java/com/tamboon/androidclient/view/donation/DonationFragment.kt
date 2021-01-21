package com.tamboon.androidclient.view.donation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import com.tamboon.androidclient.R
import com.tamboon.androidclient.databinding.FragmentDonationBinding
import com.tamboon.androidclient.model.Payment
import com.tamboon.androidclient.view.charityList.CharityViewModel


class DonationFragment : Fragment(R.layout.fragment_donation) {

    private val charity: DonationFragmentArgs by navArgs()
    private val donationViewModel: DonationViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var bindingUtil = DataBindingUtil.inflate<FragmentDonationBinding>(
            inflater,
            R.layout.fragment_donation,
            container,
            false
        )
        var view = bindingUtil.root
        bindingUtil.charity = charity.selectedCharity
        Picasso.get().load(charity.selectedCharity.logo_url).into(bindingUtil.ivLogo)
        bindingUtil.btnSubmitPayment.setOnClickListener { button ->

            if (bindingUtil.etAmount.text!!.isEmpty() || bindingUtil.tilCreditCardNumber.text!!.isEmpty() ||
                bindingUtil.tilName.text!!.isEmpty() || bindingUtil.tilExpirationDate.text!!.isEmpty() || bindingUtil.tilCvv.text!!.isEmpty()
            ) {
                Toast.makeText(activity, "Please fill all the required fields", Toast.LENGTH_LONG)
                    .show()
            } else {
                bindingUtil.progressBar.visibility = View.VISIBLE
                var expiryDate = bindingUtil.tilExpirationDate.text.toString().split("/")
                if (expiryDate.size < 2) {
                    Toast.makeText(activity, "Invalid Expire Date", Toast.LENGTH_LONG)
                        .show()

                } else {
                    var payment = Payment(
                        bindingUtil.etAmount.text.toString().toInt(),
                        bindingUtil.tilName.text.toString(),
                        bindingUtil.tilCreditCardNumber.text.toString(),
                        expiryDate[0].toInt(),
                        expiryDate[1].toInt() + 2000,
                        bindingUtil.tilCvv.text.toString()
                    )

                    donationViewModel.sendDonationToOmise(payment)
                        .observe(viewLifecycleOwner, Observer {
                            bindingUtil.progressBar.visibility = View.INVISIBLE
                            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
                        })

                }
            }
        }
        return view
    }
}