package tml.common

class PopupMessage (val title: String, val msg:String, val type: PopupTypes) {
    enum class PopupTypes {
        Error, Warning, Notification
    }

    companion object {
        fun Error(title: String, msg:String):PopupMessage {
            return PopupMessage(title, msg, PopupTypes.Error)
        }
        fun Warning(title: String, msg:String):PopupMessage {
            return PopupMessage(title, msg, PopupTypes.Warning)
        }
        fun Notification(title: String, msg:String):PopupMessage {
            return PopupMessage(title, msg, PopupTypes.Notification)
        }
    }
}