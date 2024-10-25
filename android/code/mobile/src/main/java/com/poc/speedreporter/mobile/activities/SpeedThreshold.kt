package com.poc.speedreporter.mobile.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnTouchListener
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.poc.speedreporter.common.data.Speed
import com.poc.speedreporter.common.data.SpeedViolation
import com.poc.speedreporter.common.models.APIViewModule
import com.poc.speedreporter.common.utils.ErrorCode
import com.poc.speedreporter.common.utils.SkodaPocConstants
import com.poc.speedreporter.mobile.R
import kotlin.math.atan2


class SpeedThreshold : AppCompatActivity(), OnTouchListener, OnClickListener {
    private lateinit var mProgressBar : ProgressBar
    private lateinit var mTxtProgressVal : AppCompatTextView
    private lateinit var mTxtProgressUnit : AppCompatTextView
    private lateinit var mTxtSetSpeedVal : AppCompatTextView
    private lateinit var mTxtVehicleNumber : AppCompatTextView
    private lateinit var mBtnMap : AppCompatButton
    private lateinit var mBtnReset : AppCompatButton
    private lateinit var mAPIViewModule: APIViewModule
    private var i : Int = 0
    private var mAngle : Double= 1.0
    private val TAG = "MainActivity"
    private var centerX:Float = 0f
    private var centerY:Float = 0f

    private var mSpeedToDisplay : Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.speed_threshold)
        mAPIViewModule = ViewModelProvider(this).get<APIViewModule>(APIViewModule::class.java)
        mProgressBar = findViewById(R.id.progress_bar)
        mTxtProgressVal = findViewById(R.id.txt_progress_val)
        mTxtProgressUnit = findViewById(R.id.txt_progress_unit)
        mTxtSetSpeedVal = findViewById(R.id.txt_set_speed_val)
        mTxtVehicleNumber = findViewById(R.id.txt_vehicle_number)

        mTxtVehicleNumber.text = mAPIViewModule.getVehicleNumberPref()
        mTxtProgressUnit.text = getString(R.string.speed_unit)
        mTxtSetSpeedVal.text = ""+mAPIViewModule.getSpeedThresholdPref()


        mBtnMap = findViewById(R.id.btnMap)
        mBtnReset = findViewById(R.id.btnReset)
        mBtnMap.setOnClickListener(this)
        mBtnReset.setOnClickListener(this)

        mProgressBar.setOnTouchListener(this)

        //setProgressData(SkodaPocConstants.DEFAULT_SPEED_THRESHOLD)

        mAPIViewModule.mSpeedData.observe(this , Observer {
            Log.d(TAG , "Speed threshold set done")
            /*if(it.speedLimit == 0)
                Toast.makeText(this,getString(R.string.speed_unit),Toast.LENGTH_LONG)*/

            setProgressData(mAPIViewModule.getSpeedThresholdPref())
            mTxtSetSpeedVal.text = ""+mAPIViewModule.getSpeedThresholdPref()
        })

        mAPIViewModule.mSpeedViolationListData.observe(this, Observer {
            Log.d(TAG , "Speed violation List size: ${it.size}")
            if (it.isNotEmpty()) {
                val arrayList: ArrayList<SpeedViolation> = (it.toList()).listToArrayList()
                for(speedViolation in arrayList) {
                    Log.d(TAG,"speedViolation : ${speedViolation}")
                }
                val intent = Intent(this, MapView::class.java)
                intent.putParcelableArrayListExtra("violationList",arrayList)
                startActivity(intent)
            } else {
                Toast.makeText(this,getString(R.string.no_speed_violation),Toast.LENGTH_LONG).show()
            }

        })

        mAPIViewModule.mErrorReport.observe(this, Observer {
            when(it.errorCode) {
                ErrorCode.ERROR_IN_SET_SPEED_THRESHOLD -> {
                    displayMessage(it.errorMessage ?: ("" + getString(R.string.error_in_set_speed_threshold)))
                }
                ErrorCode.ERROR_IN_GET_SPEED_THRESHOLD -> {
                    displayMessage(it.errorMessage ?: ("" + getString(R.string.error_in_get_speed_threshold)))
                }
                ErrorCode.ERROR_IN_REPORT_SPEED_VIOLATION -> {
                    displayMessage(it.errorMessage ?: ("" + getString(R.string.error_in_set_speed_violation)))
                }
                ErrorCode.ERROR_IN_GET_SPEED_VIOLATION -> {
                    displayMessage(it.errorMessage ?: ("" + getString(R.string.error_in_get_speed_violation)))
                }
                ErrorCode.ERROR_FAIL -> {
                    displayMessage(it.errorMessage ?: ("" + getString(R.string.error_message)))
                }
            }
        })
        mAPIViewModule.getSpeedThreshold()
    }

    private fun displayMessage(message:String) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    companion object {
        fun <T> List<T>.listToArrayList(): ArrayList<T> {
            val array: ArrayList<T> = ArrayList()
            for (index in this) array.add(index)
            return array
        }
    }
    private fun setProgressData(defaultSpeed: Int) {
        //Change calculation from speed get progress
        val progressToDisplay = (100.0 * defaultSpeed)/SkodaPocConstants.SKODA_MAX_SPEED
        mProgressBar.progress = progressToDisplay.toInt()
        //val speedToDisplay = (SKODA_MAX_SPEED * progress)/100
        mTxtProgressVal.text = ""+defaultSpeed.toInt()

        mBtnReset.isEnabled = false
        mBtnReset.alpha = 0.5f

    }
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        centerX = /*mProgressBar.getX() +*/ mProgressBar.getWidth()  / 2f
        centerY = /*mProgressBar.getY() +*/ mProgressBar.getHeight() / 2f
        Log.d(TAG,"onTouch mProgressBar dimen: xy" + mProgressBar.getX() + " "+mProgressBar.getY()
                +" :pivot: "+mProgressBar.pivotX +" "+ mProgressBar.pivotY
                +" :wh: "+ mProgressBar.getWidth() +" "+ mProgressBar.getHeight()
        +" :: "+centerX+" "+centerY);

        when (event!!.action) {
            MotionEvent.ACTION_UP,MotionEvent.ACTION_MOVE ->{
                //Log.d(TAG,"Pointer count: ${event.pointerCount}")
                mAngle = getAngle(event.x,event.y)

                var progress = ((mAngle*100) / 360)
                Log.d(TAG,"progress : $progress ### ${progress.toInt()}");
                mProgressBar.progress = progress.toInt()

                mSpeedToDisplay = (SkodaPocConstants.SKODA_MAX_SPEED * progress)/100
                mTxtProgressVal.text = ""+mSpeedToDisplay.toInt()
            }
            MotionEvent.ACTION_DOWN -> {
                //Log.d(TAG,"ACTION_DOWN Pointer x:: $event.x y::${event.y}")
            }
        }
        Log.d(TAG,"mSpeedToDisplay: ${mSpeedToDisplay.toInt()} thrshoildSpeed: ${mAPIViewModule.getSpeedThresholdPref()}")
        if (mSpeedToDisplay.toInt() != mAPIViewModule.getSpeedThresholdPref()) {
            mBtnReset.isEnabled = true
            mBtnReset.alpha = 1.0f
        } else {
            mBtnReset.isEnabled = false
            mBtnReset.alpha = 0.5f
        }
        return true
    }

    private fun getAngle(touchX: Float, touchY: Float): Double {
        var tanX:Float = touchX - centerX
        var tanY:Float = centerY - touchY
        //var angle1 = Math.toDegrees(atan2(touchX - location.get(0), touchY - location.get(1))).toFloat()
        var angle1 = Math.toDegrees(atan2(tanY,tanX).toDouble())

        //var angle1 =((atan2(tanY,tanX).toDouble()) * 180 ) / Math.PI
        Log.d(TAG,"angle1 * : $angle1")
        if (angle1 < 0) {
            angle1 += 360f
        }
        Log.d(TAG,"angle1 : $angle1")
        return angle1
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btnMap -> {
                //Call the api and depending on result show MapView
                mAPIViewModule.getSpeedViolationList(mAPIViewModule.getVehicleNumberPref()!!)
//                val intent = Intent(this, MapView::class.java)
//                startActivity(intent)
            }
            R.id.btnReset -> {
                Log.d(TAG,"onClick btnReset")
                mBtnReset.isEnabled = false
                mBtnReset.alpha = 0.5f
                //Call API - depending api result will change threshold and btn status
                mAPIViewModule.setSpeedThreshold(Speed(mAPIViewModule.getVehicleNumberPref()!!,mSpeedToDisplay.toInt()))
            }
        }
    }
}