package tml.tools.apitest.modules.apirunner

import android.util.Log
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import tml.tools.apitest.domain.models.KeyValuePair

/**
 * Edit body text in Json Editor, then get saved text via LiveData, setup in [ApiRunnerFragment.setupLiveDataResultFromJsonEditor]
 */
class UsecaseEditBodyText(val host: ApiRunnerFragment, val bodyText:String) {
    fun start() {
        openJsonEditor()
    }

    private fun openJsonEditor() {
        val action = ApiRunnerFragmentDirections.actionApiRunnerToBodyEditor(bodyText)
        host.findNavController().navigate(action)
    }
}