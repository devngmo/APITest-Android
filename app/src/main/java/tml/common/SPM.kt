package tml.common

import tml.libs.cku.storages.SharedPreferencesMemory

class SPM {
    var shouldReloadApiList: Boolean
        get() = SharedPreferencesMemory.ins.get(Defs.SPM_SHOULD_RELOAD_API_LIST, "no") == "yes"
        set(updated) {
            if (updated)
                SharedPreferencesMemory.ins.set(Defs.SPM_SHOULD_RELOAD_API_LIST, "yes")
            else
                SharedPreferencesMemory.ins.set(Defs.SPM_SHOULD_RELOAD_API_LIST, "no")
        }
    companion object {
        val ins = SPM()
    }
}