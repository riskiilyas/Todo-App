package com.keecoding.todoapp.data.util

import android.content.Context
import android.widget.Toast

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun String?.toAction(): Action {
    return when(this) {
        "ADD" -> {
            Action.ADD
        }

        "UPDATE" -> {
            Action.UPDATE
        }

        "DELETE" -> {
            Action.DELETE
        }

        "DELETE_ALL" -> {
            Action.DELETE_ALL
        }

        "UNDO" -> {
            Action.UNDO
        }

        else -> {
            Action.NO_ACTION
        }
    }
}