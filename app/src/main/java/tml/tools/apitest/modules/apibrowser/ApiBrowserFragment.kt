package tml.tools.apitest.modules.apibrowser

import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import tml.common.Defs
import tml.common.PopupMessage
import tml.common.SPM
import tml.libs.cku.io.EventHub
import tml.libs.cku.io.EventHubListener
import tml.libs.cku.storages.SharedPreferencesMemory
import tml.tools.apitest.R
import tml.tools.apitest.application.ApiTestApp
import tml.tools.apitest.databinding.FragmentApibrowserBinding
import tml.tools.apitest.modules.basefragment.BaseViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ApiBrowserFragment : BaseFragment() {
    private var _binding: FragmentApibrowserBinding? = null
    private lateinit var listAdapter: ApiListAdapter
    private val binding
        get() = _binding!!

    @Suppress("PrivatePropertyName")
    private val BrowserVM :ApiBrowserViewModel
        get() = _vm as ApiBrowserViewModel

    override fun initViewModel() {
        _vm = ViewModelProvider(this).get(ApiBrowserViewModel::class.java)
    }

    var lastBackKeyTime: Long = 0
    override fun handleBackKey() {
        val d = System.currentTimeMillis() - lastBackKeyTime
        if (d < 1000)
            requireActivity().finish()
        else {
            lastBackKeyTime = System.currentTimeMillis()
            Toast.makeText(requireContext(), "Press BACK again to exit.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        EventHub.ins.post(Defs.EVTC_MAIN_ACTIVITY, Defs.EVT_SHOW_FAB, null)
        if (GlobalRefs.shouldReloadApiList) {
            GlobalRefs.shouldReloadApiList = false
            BrowserVM.filter(requireContext())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentApibrowserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUI() {
        listAdapter = ApiListAdapter(requireContext(), BrowserVM.filteredApiList) { position ->
            val action = ApiBrowserFragmentDirections.actionApiBrowserToApiRunner(listAdapter.items[position].id)
            findNavController().navigate(action)
        }
        binding.lvApis.layoutManager = LinearLayoutManager(requireContext())
        binding.lvApis.adapter = listAdapter

        BrowserVM.filteredApiListLD.observe(viewLifecycleOwner, {
            //_binding!!.items = it
            listAdapter.items = it
            listAdapter.notifyDataSetChanged()
        })
        BrowserVM.filter(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}