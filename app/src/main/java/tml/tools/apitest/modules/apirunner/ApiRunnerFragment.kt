@file:Suppress("PrivatePropertyName")

package tml.tools.apitest.modules.apirunner

import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import tml.common.Defs
import tml.common.SmartDbgDefs
import tml.libs.cku.io.EventHub
import tml.libs.cku.smartdbg.WorkflowAssert
import tml.tools.apitest.R
import tml.tools.apitest.application.common.adapters.KVPListAdapter
import tml.tools.apitest.databinding.FragmentApirunnerBinding
import tml.tools.apitest.modules.apibrowser.ApiManager
import tml.tools.apitest.modules.apibrowser.BaseFragment

class ApiRunnerFragment : BaseFragment() {
    private val args : ApiRunnerFragmentArgs by navArgs()
    private var _binding: FragmentApirunnerBinding? = null
    internal val binding : FragmentApirunnerBinding
        get() = _binding!!
    val requiredRunVM : ApiRunnerViewModel
        get() = binding.runVM!!

    internal lateinit var hpfListAdapter: KVPListAdapter
    val requireHeaderListViewAdapter : KVPListAdapter
        get() = hpfListAdapter
    val requireFormDataListViewAdapter  : KVPListAdapter
        get() = hpfListAdapter
    val requireParamsListViewAdapter : KVPListAdapter
        get() =  hpfListAdapter

    lateinit var presenter: ApiRunnerPresenter
    lateinit var serializer: ApiRunSerializer



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        EventHub.ins.post(Defs.EVTC_MAIN_ACTIVITY, Defs.EVT_HIDE_FAB, null)
        serializer = ApiRunSerializer(this)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_api_runner, menu)
        menu.getItem(1).setIcon(R.drawable.ic_baseline_transform_24_black)
        menu.getItem(2).setIcon(R.drawable.ic_baseline_content_copy_24_black)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_save) {
            serializer.doSave()
        }
        else if (item.itemId == R.id.action_save_as) {
            serializer.saveAs()
            GlobalRefs.shouldReloadApiList = true
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentApirunnerBinding.inflate(inflater, container, false)
        WorkflowAssert.pass(SmartDbgDefs.API_RUNNER_BINDING_CREATED)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        WorkflowAssert.expectPasses(arrayOf(SmartDbgDefs.API_RUNNER_BINDING_CREATED))
        val api = ApiManager.ins.apiRepo.getApiByID(args.apiId)
        if (api != null) {
            binding.runVM = ApiRunnerViewModel(api)
            setupLiveDataResultFromJsonEditor()
            presenter = ApiRunnerPresenter(this)
            presenter.bindingControlEvents()
        }
        else {
            back()
        }
    }
    private fun setupLiveDataResultFromJsonEditor() {
        findNavController().currentBackStackEntry?.savedStateHandle?.
        getLiveData<String>("jsonText")?.observe(viewLifecycleOwner) { result ->
            binding.runVM!!.bodyText = result
            BaseVM.shouldAskUserConfirmBeforeBack = true
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}