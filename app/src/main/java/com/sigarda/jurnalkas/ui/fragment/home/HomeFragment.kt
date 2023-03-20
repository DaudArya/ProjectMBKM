package com.sigarda.jurnalkas.ui.fragment.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sigarda.jurnalkas.R
import com.sigarda.jurnalkas.adapter.transaction.TransactionAdapter
import com.sigarda.jurnalkas.data.local.entity.TransactionEntity
import com.sigarda.jurnalkas.databinding.FragmentHomeBinding
import com.sigarda.jurnalkas.ui.activity.Settings.SettingActivity
import com.sigarda.jurnalkas.ui.activity.main.TransactionViewModel
import com.sigarda.jurnalkas.wrapper.hide
import com.sigarda.jurnalkas.wrapper.rupiah
import com.sigarda.jurnalkas.wrapper.show
import com.sigarda.jurnalkas.wrapper.snack
import com.sigarda.jurnalkas.wrapper.viewState.ViewState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {


    private lateinit var transactionAdapter: TransactionAdapter
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goToCalendar()
        goToSetting()
        setupRV()
        initViews()
        observeFilter()
        observeTransaction()
        swipeToDelete()
    }

    private fun observeFilter() = with(binding) {
        lifecycleScope.launchWhenCreated {
            transacationViewModel.transactionFilter.collect { filter ->
                when (filter) {
                    "Overall" -> {
                        totalIncomExpense.show()
                        incomeCardView.totalTitle.text = getString(R.string.text_total_income)
                        expenseCardView.totalTitle.text = getString(R.string.text_total_expense)
                        expenseCardView.totalIcon.setImageResource(R.drawable.ic_expense)
                    }
                    "Income" -> {
                        totalmoney.text =
                            getString(R.string.text_total_income)
                        totalIncomExpense.hide()
                    }
                    "Expense" -> {
                        totalmoney.text =
                            getString(R.string.text_total_expense)
                        totalIncomExpense.hide()
                    }
                }
                transacationViewModel.getAllTransaction(filter)
            }
        }
    }

    private fun setupRV() = with(binding) {
        transactionAdapter = TransactionAdapter()
        transactionRv.apply {
            adapter = transactionAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }


    private fun swipeToDelete() {
        // init item touch callback for swipe action
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // get item position & delete notes
                val position = viewHolder.adapterPosition
                val transaction = transactionAdapter.differ.currentList[position]
                val transactionItem = TransactionEntity(
                    transaction.title,
                    transaction.amount,
                    transaction.transactionType,
                    transaction.tag,
                    transaction.date,
                    transaction.createdAt,
                    transaction.id
                )
                transacationViewModel.deleteTransaction(transactionItem)
                Snackbar.make(
                    binding.root,
                    getString(R.string.success_transaction_delete),
                    Snackbar.LENGTH_LONG
                )
                    .apply {
                        setAction(getString(R.string.text_undo)) {
                            transacationViewModel.insertTransaction(
                                transactionItem
                            )
                        }
                        show()
                    }
            }
        }

        // attach swipe callback to rv
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.transactionRv)
        }
    }

    private fun onTotalTransactionLoaded(transaction: List<TransactionEntity>) = with(binding) {
        val (totalIncome, totalExpense) = transaction.partition { it.transactionType == "Income" }
        val income = totalIncome.sumOf { it.amount }
        val expense = totalExpense.sumOf { it.amount }
        incomeCardView.total.text = "+ ".plus(rupiah(income))
        expenseCardView.total.text = "- ".plus(rupiah(expense))
        totalmoney.text = (rupiah(income - expense))
    }

    private fun observeTransaction() = lifecycleScope.launchWhenStarted {
        transacationViewModel.uiState.collect { uiState ->
            when (uiState) {
                is ViewState.Loading -> {
                }
                is ViewState.Success -> {
                    showAllViews()
                    onTransactionLoaded(uiState.transaction)
                    onTotalTransactionLoaded(uiState.transaction)
                }
                is ViewState.Error -> {
                    binding.root.snack(
                        string = R.string.text_error
                    )
                }
                is ViewState.Empty -> {
                    hideAllViews()
                }
            }
        }
    }

    private fun showAllViews() = with(binding) {
        emptyStateLayout.hide()
        transactionRv.show()
    }

    private fun hideAllViews() = with(binding) {
        emptyStateLayout.show()
    }

    private fun onTransactionLoaded(list: List<TransactionEntity>) =
        transactionAdapter.differ.submitList(list)

    private fun initViews() = with(binding) {
        addTransaction.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment3_to_spendingFragment)
        }

//        mainDashboardScrollView.setOnScrollChangeListener(
//            NestedScrollView.OnScrollChangeListener { _, sX, sY, oX, oY ->
//                if (abs(sY - oY) > 10) {
//                    when {
//                        sY > oY -> btnAddTransaction.hide()
//                        oY > sY -> btnAddTransaction.show()
//                    }
//                }
//            }
//        )

        transactionAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("transaction", it)
            }
//            findNavController().navigate(
//                R.id.action_dashboardFragment_to_transactionDetailsFragment,
//                bundle
//            )
        }
    }





//    private fun goToSpend(){
//        binding.addTransaction.setOnClickListener(){
//            findNavController().navigate(R.id.action_homeFragment3_to_spendingFragment)
//        }
//    }
    private fun goToCalendar(){
        binding.calendar.setOnClickListener(){
            findNavController().navigate(R.id.action_homeFragment3_to_calendarFragment)
        }
    }
        private fun goToSetting (){
        binding.setting.setOnClickListener(){
            requireActivity().run {
                val intent = Intent(this , SettingActivity::class.java)
                startActivity(intent)
            }
        }
    }
}