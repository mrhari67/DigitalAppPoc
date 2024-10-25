package com.poc.speedreporter.common.data

import android.os.Parcel
import android.os.Parcelable
import com.poc.speedreporter.common.utils.ErrorCode


data class Speed(var vin:String,var speedLimit: Int)

data class SpeedViolation(var vin: String, var speedLimit: Float, var violatedDate:String,
                          var latitude:String, var langitude:String, var speed_limit: Float) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readFloat(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readFloat(),
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(vin)
        parcel.writeFloat(speedLimit)
        parcel.writeString(violatedDate)
        parcel.writeString(latitude)
        parcel.writeString(langitude)
        parcel.writeFloat(speed_limit)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SpeedViolation> {
        override fun createFromParcel(parcel: Parcel): SpeedViolation {
            return SpeedViolation(parcel)
        }

        override fun newArray(size: Int): Array<SpeedViolation?> {
            return arrayOfNulls(size)
        }
    }
}

data class ErrorReport(var errorCode: ErrorCode=ErrorCode.ERROR_FAIL, var errorMessage: String? = "" )
