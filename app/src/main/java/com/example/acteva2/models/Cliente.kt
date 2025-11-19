package com.example.acteva2.models

import android.os.Parcel
import android.os.Parcelable

data class Cliente(
    val id: Int = 0,
    val nombre: String,
    val email: String,
    val telefono: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombre)
        parcel.writeString(email)
        parcel.writeString(telefono)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Cliente> {
        override fun createFromParcel(parcel: Parcel): Cliente = Cliente(parcel)
        override fun newArray(size: Int): Array<Cliente?> = arrayOfNulls(size)
    }
}