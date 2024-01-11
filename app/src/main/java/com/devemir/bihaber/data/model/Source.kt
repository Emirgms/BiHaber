package com.devemir.bihaber.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


@Parcelize
data class Source(
    @SerializedName("id")
    val id:@RawValue Any?,
    @SerializedName("name")
    val name:String?
):Parcelable
