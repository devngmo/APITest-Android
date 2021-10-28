package tml.tools.apitest.modules.apieditor

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import tml.common.Defs
import tml.libs.cku.io.EventHub
import tml.tools.apitest.R
import tml.tools.apitest.databinding.FragmentApieditorBinding
import tml.tools.apitest.domain.models.Api
import tml.tools.apitest.modules.apibrowser.ApiManager
import tml.tools.apitest.modules.apibrowser.BaseFragment

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ApiEditorFragment : BaseFragment() {
    private var _binding: FragmentApieditorBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        d("setup option menu, hide FAB onCreate")
        setHasOptionsMenu(true)
        EventHub.ins.post(Defs.EVTC_MAIN_ACTIVITY, Defs.EVT_HIDE_FAB, null)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        d("inflat menu api editor")
        inflater.inflate(R.menu.menu_api_editor, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_save) {
            doSave()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        d("onCreateView")
        _binding = FragmentApieditorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        d("onViewCreated")
        binding.httpMethodList = Defs.httpMethodList
        binding.api = ApiEditViewModel()
    }

    private fun doSave() {
        val editModel = binding.api
        if (editModel != null) {
            ApiManager.ins.apiRepo.save(Api.create(editModel))
            Toast.makeText(requireContext(), "API Saved: " + editModel.name, Toast.LENGTH_SHORT).show()
            back()
        }
        else {
            Log.w("-DBG-", "api is null")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}