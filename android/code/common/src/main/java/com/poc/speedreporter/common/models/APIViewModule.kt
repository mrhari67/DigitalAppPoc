package com.poc.speedreporter.common.models

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.poc.speedreporter.common.ApiInstance
import com.poc.speedreporter.common.data.ErrorReport
import com.poc.speedreporter.common.data.Speed
import com.poc.speedreporter.common.data.SpeedViolation
import com.poc.speedreporter.common.utils.ErrorCode
import com.poc.speedreporter.common.utils.SkodaPocConstants

import kotlinx.coroutines.launch
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class APIViewModule(application: Application):AndroidViewModel(application) {

    private val TAG : String = "APIViewModule"
    private val mSharedPref : SharedPreferences = application.getSharedPreferences("SkodaPocPref", Context.MODE_PRIVATE)
    val mSpeedData : MutableLiveData<Speed> = MutableLiveData<Speed>()
    val mSpeedViolationListData : MutableLiveData<List<SpeedViolation>> = MutableLiveData<List<SpeedViolation>>()
    var mErrorReport:MutableLiveData<ErrorReport> = MutableLiveData<ErrorReport>()
    fun getSpeedThreshold() {
        viewModelScope.launch {
            try {
                val call:Call<Speed> = ApiInstance.getInstance().getSpeedThreshold(
                    getVehicleNumberPref()!!)

                call.enqueue(object: Callback<Speed> {
                     override fun onResponse(call: Call<Speed>, response: Response<Speed>) {
                        Log.d(TAG,"Response body : ${response.body()} ${response.isSuccessful}")
                        if (response.isSuccessful) {
                           //val speedLimit = response.body()?.speedLimit!!
                            mSpeedData.value = response.body()
                            Log.d(TAG, "speedLimit:${mSpeedData.value!!.speedLimit}")
                            if(mSpeedData.value!!.speedLimit != 0) {
                                mSharedPref.edit()
                                    .putInt(SkodaPocConstants.SPEED_THRESHOLD, mSpeedData.value!!.speedLimit)
                                    .apply()
                            }
                        } else {
                            Log.d(TAG,"response failed");
                            mErrorReport.value = ErrorReport(ErrorCode.ERROR_IN_GET_SPEED_THRESHOLD,"")
                        }
                    }

                    override fun onFailure(call: Call<Speed>, t: Throwable) {
                        Log.d(TAG,"speedLimit:${t.message}")
                        mErrorReport.value = ErrorReport(ErrorCode.ERROR_IN_GET_SPEED_THRESHOLD,t.message)
                    }
                })
            } catch (e: Exception) {
                Log.e(TAG,"Error in getSpeedThreshold: ${e.message}")
            }
        }
    }

    fun reportSpeedViolation(speedViolation: SpeedViolation) {
        viewModelScope.launch {
            try {
                Log.d(TAG,"speedViolation : $speedViolation")
                val call: Call<SpeedViolation> = ApiInstance.getInstance().reportSpeedViolation(speedViolation)
                call.enqueue(object: Callback<SpeedViolation> {

                    override fun onResponse(
                        call: Call<SpeedViolation>,
                        response: Response<SpeedViolation>
                    ) {
                        if (response.isSuccessful) {
                            Log.d(TAG,"response is isSuccessful")
                        } else {
                            Log.d(TAG,"response is failed")
                            mErrorReport.value = ErrorReport(ErrorCode.ERROR_IN_REPORT_SPEED_VIOLATION,"")
                        }
                    }

                    override fun onFailure(call: Call<SpeedViolation>, t: Throwable) {
                        Log.d(TAG,"Error in reportSpeedViolation:${t.message}")
                        mErrorReport.value = ErrorReport(ErrorCode.ERROR_IN_REPORT_SPEED_VIOLATION,t.message)
                    }
                })
            } catch (e: Exception) {
                // catch exception
                Log.e(TAG,"Error in reportSpeedViolation: ${e.message}")

            }
        }
    }

    fun setSpeedThreshold(speedThreshold:Speed ) {
        viewModelScope.launch {
            try {
                val call:Call<Speed> = ApiInstance.getInstance().setSpeedThreshold(speedThreshold)
                //Log.d(TAG,"Response body : ${speed.speedLimit} ${speed.vin}")

                call.enqueue(object: Callback<Speed> {
                    override fun onResponse(call: Call<Speed>, response: Response<Speed>) {
                        Log.d(TAG,"Response body : ${response.body()} ${response.isSuccessful}")
                        if (response.isSuccessful) {
                            val speed:Speed = response.body()!!
                            Log.d(TAG, "speedLimit:${speed.speedLimit}")
                            if(speed.speedLimit != 0) {
                                mSharedPref.edit()
                                    .putInt(SkodaPocConstants.SPEED_THRESHOLD, speed.speedLimit)
                                    .apply()
                            }
                            mSpeedData.value = response.body()
                        } else {
                            Log.d(TAG,"response failed");
                            mErrorReport.value = ErrorReport(ErrorCode.ERROR_IN_SET_SPEED_THRESHOLD,"")
                        }
                    }

                    override fun onFailure(call: Call<Speed>, t: Throwable) {
                        Log.d(TAG,"speedLimit:${t.message}")
                        mErrorReport.value = ErrorReport(ErrorCode.ERROR_IN_SET_SPEED_THRESHOLD,t.message)
                    }
                })
            } catch (e: Exception) {
                Log.e(TAG,"Error in getSpeedThreshold: ${e.message}")
            }
        }
    }

    fun getSpeedViolationList(vehicleNumber: String) {
        viewModelScope.launch {
            try {
                Log.d(TAG,"getSpeedViolation : $vehicleNumber")
                val call: Call<List<SpeedViolation>> = ApiInstance.getInstance().getSpeedViolation(vehicleNumber)
                call.enqueue(object: Callback<List<SpeedViolation>> {

                    override fun onResponse(
                        call: Call<List<SpeedViolation>>,
                        response: Response<List<SpeedViolation>>
                    ) {
                        if (response.isSuccessful) {
                            Log.d(TAG,"response is isSuccessful")
                            mSpeedViolationListData.value = response.body()!!
                            //val list:List<SpeedViolation> = response.body()!!
                            Log.d(TAG,"SpeedViolation list size:"+mSpeedViolationListData.value?.size)
                        } else {
                            Log.d(TAG,"response is failed");
                            mErrorReport.value = ErrorReport(ErrorCode.ERROR_IN_GET_SPEED_VIOLATION,"")
                        }
                    }

                    override fun onFailure(call: Call<List<SpeedViolation>>, t: Throwable) {
                        Log.d(TAG,"Error in getSpeedViolation:${t.message}")
                        mErrorReport.value = ErrorReport(ErrorCode.ERROR_IN_GET_SPEED_VIOLATION,t.message)
                    }
                })
            } catch (e: Exception) {
                // catch exception
                Log.e(TAG,"Error in getSpeedViolation: ${e.message}")

            }
        }
    }

    fun getSpeedThresholdPref() : Int {
        return mSharedPref.getInt(SkodaPocConstants.SPEED_THRESHOLD,SkodaPocConstants.DEFAULT_SPEED_THRESHOLD)
    }
    fun setVehicleNumberPref(vehicleNumber: String)  {
        return mSharedPref.edit().putString(SkodaPocConstants.VEHICLE_NUMBER_PREF,vehicleNumber).apply()
    }
    fun getVehicleNumberPref() : String? {
        return mSharedPref.getString(SkodaPocConstants.VEHICLE_NUMBER_PREF,SkodaPocConstants.VEHICLE_NUMBER)
    }
    fun getVehicleNumberNotEmptyPref() : String? {
        return mSharedPref.getString(SkodaPocConstants.VEHICLE_NUMBER_PREF,"")
    }
}