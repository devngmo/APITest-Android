@file:Suppress("PrivatePropertyName")

package tml.tools.apitest.modules.jsoneditor

import android.content.ClipboardManager
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.input.input
import com.afollestad.materialdialogs.list.listItems
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import tml.common.Defs
import tml.cuajet.fragments.FragmentD
import tml.cuajet.sys.SysUtils
import tml.libs.cku.data.StringUtils
import tml.libs.cku.io.EventHub
import tml.libs.cku.textutils.UltimateJsonParser
import tml.tools.apitest.R
import tml.tools.apitest.application.common.adapters.KVPListAdapter
import tml.tools.apitest.databinding.FragmentApirunnerBinding
import tml.tools.apitest.databinding.FragmentJsoneditorBinding
import tml.tools.apitest.domain.models.Api
import tml.tools.apitest.domain.models.KeyValuePair
import tml.tools.apitest.modules.apibrowser.ApiManager

class JsonEditorFragment : FragmentD() {
    private val args : JsonEditorFragmentArgs by navArgs()
    private var _binding: FragmentJsoneditorBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var vm : JsonEditorViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        EventHub.ins.post(Defs.EVTC_MAIN_ACTIVITY, Defs.EVT_HIDE_FAB, null)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_json_editor, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_parse_text) {
            parseUserTextToJson()
        }
        else if (item.itemId == R.id.action_save) {
            save()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun save() {
        findNavController().previousBackStackEntry?.savedStateHandle?.set("jsonText", binding.editModel!!.jsonStr)
        SysUtils.playNotificationSound(requireContext())
        Toast.makeText(requireContext(), "Json saved!", Toast.LENGTH_SHORT).show()
    }

    private fun parseUserTextToJson() {
        d("parse user text: " + binding.editModel!!.jsonStr)

        val lines = binding.editModel!!.jsonStr.split("\r\n")
        for (l in lines) {
            Log.d("-DBG-", StringUtils.replaceStartSpaces("-", l))
        }
        val parsed = UltimateJsonParser.parseRawText("" + binding.editModel!!.jsonStr, true, "\n", true)
        if (parsed != null) {
            binding.editModel!!.jsonStr = parsed
            d("parsed: $parsed")
        }
        else
            Toast.makeText(requireContext(), "Can not parse", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJsoneditorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.editModel = JsonEditorViewModel(args.jsonText)
    }

    private fun back() {
        val navController = findNavController()
        navController.navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}