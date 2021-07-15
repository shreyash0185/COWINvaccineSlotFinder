package com.example.covidvaccination

import android.app.DatePickerDialog
import android.app.DownloadManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var searchButton:Button
    lateinit var pinCodeEdt : EditText
    lateinit var centerRv :RecyclerView
    lateinit var loadingPB :ProgressBar
    lateinit var centerList:List<CenterRVModal>
    lateinit var centerRVAdapter: CenterRVAdapter

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchButton = findViewById(R.id.idBtnSearch)
        pinCodeEdt = findViewById(R.id.idEdtPinCode)
        centerRv = findViewById(R.id.idRVcenters)
        loadingPB = findViewById(R.id.idPBLoading)
        centerList = ArrayList<CenterRVModal>()

        searchButton.setOnClickListener {
            val pinCode = pinCodeEdt.text.toString()
            if(pinCode.length!=6){
                Toast.makeText(this,"Please Enter a Valid PinCode",Toast.LENGTH_SHORT).show()
            }else{
                (centerList as ArrayList<CenterRVModal>).clear()
                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)

                val dpd = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year,month, dayOfMonth ->
                    loadingPB.setVisibility(View.VISIBLE)
                    val dateStr: String = """$dayOfMonth-${month+1}-$year"""
                    getAppointmentDetails(pinCode,dateStr)
                },
                    year,
                    month,
                    day
                )
                dpd.show()


            }
        }

    }

    private fun getAppointmentDetails(pinCode:String,date:String){
        val url = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByPin?pincode="+pinCode+"&date="+date

        val queue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(Request.Method.GET,url,null,{
            response ->
            Log.e("TAG", "SUCCESS RESPONSE IS $response")
            // we are setting the visibility of progress bar as gone.
            loadingPB.setVisibility(View.GONE)
            try {
                val centerArray = response.getJSONArray("centers")
                if(centerArray.length().equals(0)){

                    Toast.makeText(this,"No Vaccination Center Avaliable",Toast.LENGTH_SHORT).show()
                }


                for(i in 0 until centerArray.length()){
                        val centerObj = centerArray.getJSONObject(i)

                        val centerName: String = centerObj.getString("name")
                        val centerAddress: String = centerObj.getString("address")
                        val centerFromTime: String = centerObj.getString("from")
                        val centerToTime: String = centerObj.getString("to")
                        val fee_type: String = centerObj.getString("fee_type")


                        val sessionObj = centerObj.getJSONArray("sessions").getJSONObject(0)
                        val ageLimit: Int = sessionObj.getInt("min_age_limit")
                        val vaccineName: String = sessionObj.getString("vaccine")
                        val avaliableCapacity: Int = sessionObj.getInt("available_capacity")

                        val center = CenterRVModal(
                                centerName,
                                centerAddress,
                                centerFromTime,
                                centerToTime,
                                fee_type,
                                ageLimit,
                                vaccineName,
                                avaliableCapacity
                        )
                        centerList = centerList+center
                    }

                    centerRVAdapter = CenterRVAdapter(centerList)
                    centerRv.layoutManager = LinearLayoutManager(this)
                    centerRv.adapter = centerRVAdapter
                    centerRVAdapter.notifyDataSetChanged()







            }catch (e:JSONException){
                e.printStackTrace()
            }


        },
            {
                error ->
                Log.e("TAG", "RESPONSE IS $error")
                Toast.makeText(this,"Fail to get data",Toast.LENGTH_SHORT).show()

            })
        queue.add(request)

    }
}