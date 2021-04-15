package com.learning.mobile.tasktimer

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
class Task(
    var name: String,
    var description: String,
    var sortOrder: Int
) : Parcelable {
    @IgnoredOnParcel
    var id: Long = 0
}