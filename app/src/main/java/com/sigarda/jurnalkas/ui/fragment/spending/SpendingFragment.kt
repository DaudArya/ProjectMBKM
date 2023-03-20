package com.sigarda.jurnalkas.ui.fragment.spending

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sigarda.jurnalkas.R
import com.sigarda.jurnalkas.data.local.entity.TransactionEntity
import com.sigarda.jurnalkas.databinding.FragmentHomeBinding
import com.sigarda.jurnalkas.databinding.FragmentSpendingBinding
import com.sigarda.jurnalkas.ui.activity.main.TransactionViewModel
import com.sigarda.jurnalkas.ui.fragment.home.HomeViewModel
import com.sigarda.jurnalkas.wrapper.Constant
import com.sigarda.jurnalkas.wrapper.parseDouble
import com.sigarda.jurnalkas.wrapper.snack
import com.sigarda.jurnalkas.wrapper.transformIntoDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class SpendingFragment : Fragment() {

    private var _binding: FragmentSpendingBinding? = null
    private val binding get() = _binding!!

    private val transacationViewModel: TransactionViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSpendingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {

        val transactionTypeAdapter =
            ArrayAdapter(
                requireContext(),
                R.layout.item_autocomplete_layout,
                Constant.transactionType
            )
        val tagsAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_autocomplete_layout,
            Constant.transactionTags
        )

        with(binding) {
            // Set list to TextInputEditText adapter
            etTransactionType.setAdapter(transactionTypeAdapter)
            etTag.setAdapter(tagsAdapter)

            // Transform TextInputEditText to DatePicker using Ext function
            etWhen.transformIntoDatePicker(
                requireContext(),
                "dd/MM/yyyy",
                Date()
            )
            accept.setOnClickListener {
                binding.apply {
                    val (title, amount, transactionType, tag, date, note) = getTransactionContent()
                    // validate if transaction content is empty or not
                    when {
                        title.isEmpty() -> {
                            this.etTitle.error = "Title must not be empty"
                        }
                        amount.isNaN() -> {
                            this.etAmount.error = "Amount must not be empty"
                        }
                        transactionType.isEmpty() -> {
                            this.etTransactionType.error = "Transaction type must not be empty"
                        }
                        tag.isEmpty() -> {
                            this.etTag.error = "Tag must not be empty"
                        }
                        date.isEmpty() -> {
                            this.etWhen.error = "Date must not be empty"
                        }
                        else -> {
                            transacationViewModel.insertTransaction(getTransactionContent()).run {
                                binding.root.snack(
                                    string = R.string.success_expense_saved
                                )
                                findNavController().navigate(
                                    R.id.action_spendingFragment_to_homeFragment3
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getTransactionContent(): TransactionEntity = binding.let {
        val title = it.etTitle.text.toString()
        val amount = parseDouble(it.etAmount.text.toString())
        val transactionType = it.etTransactionType.text.toString()
        val tag = it.etTag.text.toString()
        val date = it.etWhen.text.toString()

        return TransactionEntity(title, amount, transactionType, tag, date)
    }

}
