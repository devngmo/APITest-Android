package tml.tools.apitest.modules.apibrowser

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import tml.common.Dlg
import tml.common.SPM
import tml.cuajet.app.LogcatStreamer
import tml.cuajet.fragments.FragmentD
import tml.libs.cku.smartdbg.WorkflowAssert
import tml.tools.apitest.modules.basefragment.BaseViewModel
import tml.tools.apitest.modules.basefragment.ConfirmTask

/**
 * A Base [Fragment] for other Fragments.
 */
abstract class BaseFragment : FragmentD() {
    @Suppress("PropertyName")
    val GlobalRefs = SPM.ins
    @Suppress("PropertyName")
    lateinit var _vm : BaseViewModel

    @Suppress("PrivatePropertyName")
    val BaseVM : BaseViewModel
        get() = _vm

    open fun initViewModel() {
        _vm = ViewModelProvider(this).get(BaseViewModel::class.java)
    }

    fun back() {
        val navController = findNavController()
        navController.navigateUp()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val WA = WorkflowAssert.onProcessStart(this.javaClass.simpleName + ".onViewCreated", LogcatStreamer())
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handleBackKey()
            }
        })

        initViewModel()
        WA.expected(_vm != null, "ViewModel ${_vm.javaClass.simpleName} was not initialized")

        BaseVM.confirmTaskLD.observe(viewLifecycleOwner, {
            Dlg.showConfirmPopup(requireContext(), it.title, it.msg, it.onResult)
        })

        setupUI()

        WA.end()
    }

    protected open fun handleBackKey() {
        askUserConfirmOnBack()
    }

    open fun setupUI() {

    }

    fun showPopupMsg(title:String, msg:String) {
        BaseVM.confirmTaskLD.value = ConfirmTask(title, msg) {
            // ignore confirmation
        }
    }

    fun showConfirmDialog(title:String, msg:String, onDone:(confirmed:Boolean) ->Unit) {
        BaseVM.confirmTaskLD.value = ConfirmTask(title, msg, onDone)
    }

    private fun askUserConfirmOnBack() {
        if (BaseVM.shouldAskUserConfirmBeforeBack)
        {
            BaseVM.confirmTaskLD.value = createBackConfirmTask()
        }
        else {
            back()
        }
    }

    open fun createBackConfirmTask(): ConfirmTask {
        return ConfirmTask("Back to previous Screen?", "All changes will be LOST, you should SAVE your work BEFORE going BACL to previous screen! Are you sure?") { confirmed ->
            if (confirmed) back()
        }
    }
}