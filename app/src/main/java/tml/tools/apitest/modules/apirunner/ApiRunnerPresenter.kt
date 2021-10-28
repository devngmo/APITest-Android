@file:Suppress("PrivatePropertyName")

package tml.tools.apitest.modules.apirunner

import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.google.android.material.tabs.TabLayout
import tml.tools.apitest.R
import tml.tools.apitest.application.common.adapters.KVPListAdapter
import tml.tools.apitest.databinding.FragmentApirunnerBinding

class ApiRunnerPresenter(val host:ApiRunnerFragment) {


    private val requiredBinding : FragmentApirunnerBinding = host.binding
    private val HPFIsHeaders: Boolean
        get() = requiredBinding.hpfTabs.selectedTabPosition == 0
    private val HPFIsParams: Boolean
        get() = requiredBinding.hpfTabs.selectedTabPosition == 1
    private val HPFIsForm: Boolean
        get() = requiredBinding.hpfTabs.selectedTabPosition == 2

    private val requireVM = host.requiredRunVM

    fun bindingControlEvents() {
        setupHPFSection()
        setupTextPropertyEditing()

        host.binding.bodyTypePicker.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                UsecaseChangeBodyType(host).start()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        setupRunAndResultSection()
    }

    private fun setupRunAndResultSection() {
        host.binding.btnRun.setOnClickListener {
            UsecaseRunApi(host).start()
        }

        host.binding.resultTabs.addOnTabSelectedListener(  object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                host.requiredRunVM.resultMode = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun setupHPFSection() {
        host.hpfListAdapter = KVPListAdapter(host.requireContext(), requireVM.headers) { position ->
            UsecaseEditHeader(host, position).start()
        }
        requiredBinding.hpfList.adapter = host.hpfListAdapter
        requiredBinding.hpfList.layoutManager = LinearLayoutManager(host.requireContext())

        requiredBinding.arMethod.setOnClickListener {
            UsecaseChangeHTTPMethod(host).start()
        }
        requiredBinding.hpfAdd.setOnClickListener {
            when {
                HPFIsHeaders -> UsecaseAddHeader(host).start()
                HPFIsParams -> UsecaseAddParam(host).start()
                HPFIsForm -> UsecaseAddFormData(host).start()
            }
        }

        host.binding.hpfTabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                host.requiredRunVM.selectedListTabIndex = tab!!.position
                when {
                    HPFIsHeaders -> host.hpfListAdapter.update(requireVM.headers)
                    HPFIsParams -> host.hpfListAdapter.update(requireVM.params)
                    HPFIsForm -> host.hpfListAdapter.update(requireVM.form)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun setupTextPropertyEditing() {
        host.binding.arName.setOnClickListener {
            showDialogTextEdit( "Edit Name", requireVM.name) { text ->
                requireVM.name = text
            }
        }
        host.binding.arUrl.setOnClickListener {
            showDialogTextEdit( "Edit URL", requireVM.url) { text ->
                requireVM.url = text
            }
        }
        host.binding.bodyText.setOnClickListener {
            UsecaseEditBodyText(host, requireVM.bodyText).start()
        }
    }

    private fun showDialogTextEdit(title: String, value: String, onDone: (text:String) -> Unit) {
        MaterialDialog(host.requireContext()).show {
            title(text = title)
            input (maxLength = 255, prefill = value) {
                    _, text ->
                onDone("" + text)
            }
            positiveButton(R.string.save)
            negativeButton(R.string.cancel)
        }
    }

}