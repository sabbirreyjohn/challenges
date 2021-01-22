package com.tamboon.androidclient.view.donation

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import com.tamboon.androidclient.R
import com.tamboon.androidclient.databinding.FragmentDonationBinding
import com.tamboon.androidclient.model.Payment
import com.tamboon.androidclient.view.charityList.CharityListFragmentDirections
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
                hideKeyboard(context)
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
                        .observe(viewLifecycleOwner, Observer {responseType->

                            if (responseType.succeed) {
                                donationViewModel.postDonationToServer(
                                    Donation(
                                        bindingUtil.tilName.text.toString(),
                                        responseType.message,
                                        bindingUtil.etAmount.text.toString().toInt()
                                    )
                                ).observe(viewLifecycleOwner, {updatedResponseType->
                                    if (updatedResponseType.succeed) {
                                        bindingUtil.progressBar.visibility = View.INVISIBLE
                                        updatedResponseType.message = "Donation process completed successfully"


                                    } else {
                                        bindingUtil.progressBar.visibility = View.INVISIBLE
                                        updatedResponseType.icon = R.drawable.ic_error
                                    }

                                    var action =
                                        DonationFragmentDirections.actionDonationFragmentToDonationCompletedFragment(updatedResponseType)
                                    findNavController().navigate(action)
                                })

                            } else {
                                bindingUtil.progressBar.visibility = View.INVISIBLE
                                responseType.icon = R.drawable.ic_error
                                var action =
                                    DonationFragmentDirections.actionDonationFragmentToDonationCompletedFragment(responseType)
                                findNavController().navigate(action)
                            }

                        })

                }
            }
        }
        return view
    }

    fun hideKeyboard(c: Context?) {
        val view: View? = (c as Activity).currentFocus
        if (view != null) {
            (c.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                view.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}