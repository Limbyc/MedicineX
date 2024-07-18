package com.valance.medicine.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.valance.medicine.R
import com.valance.medicine.databinding.OrderFragmentBinding
import com.valance.medicine.ui.adapter.DoctorAdapter
import com.valance.medicine.ui.model.DoctorDisplayModel
import com.valance.medicine.ui.view.DoctorContract
import com.valance.medicine.ui.view.ProfessionContract
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrderFragment : Fragment(), ProfessionContract.View, DoctorContract.View
{

    private lateinit var binding: OrderFragmentBinding
    private lateinit var tabLayout: TabLayout
    private lateinit var tabLayout1: TabLayout

    @Inject lateinit var presenter: DoctorContract.Presenter
    @Inject lateinit var doctorAdapter: DoctorAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = OrderFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabLayout1 = binding.tabLayout1
        tabLayout = binding.tabLayout

        chooseCost()

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = doctorAdapter

        presenter.getDoctors()

    }

    override fun showData(data: List<Any?>) {
        for (profession in data) {
            val tab = tabLayout.newTab()
            val customTabView = createTabView(profession.toString()) //если не сработает
            tab.customView = customTabView
            tabLayout.addTab(tab)
        }
    }

    private fun createTabView(text: String): View {
        val customTabView = LayoutInflater.from(context).inflate(R.layout.profession_type, null) as TextView
        customTabView.text = text
        return customTabView
    }

    private fun chooseCost() {
        val costList = listOf("Бесплатно", "Платно")
        for (costLists in costList) {
            val tab = tabLayout1.newTab()
            val customTabView = createTabView(costLists)
            tab.customView = customTabView
            tab.tag = costLists
            tabLayout1.addTab(tab)
        }

        tabLayout1.addOnTabSelectedListener(createTabListener())
        tabLayout1.getTabAt(0)?.select()
    }

    private fun createTabListener(): TabLayout.OnTabSelectedListener {
        return object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                Log.d("OrderFragment", "onTabSelected called")
                val selectedCoffeeType: String? = tab.tag as? String
                selectedCoffeeType?.let { text ->
                    Log.d("OrderFragment", "Selected coffee type: $text")
                }
                updateTabAppearance(tab, R.color.order, R.drawable.tab_layout_item_cost)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                updateTabAppearance(tab, R.color.black, R.drawable.tab_layout_cost)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                onTabSelected(tab)
            }
        }
    }

    private fun updateTabAppearance(tab: TabLayout.Tab?, textColorResId: Int, backgroundDrawableResId: Int) {
        val customTabView = tab?.customView as? TextView
        customTabView?.setTextColor(ContextCompat.getColor(requireContext(), textColorResId))
        customTabView?.background =
            ContextCompat.getDrawable(requireContext(), backgroundDrawableResId)
    }

    override fun showDoctors(doctors: List<DoctorDisplayModel>) {
        Log.d("OrderFragment", "Number of doctors: ${doctors.size}")
        doctorAdapter.submitList(doctors)
    }


}
