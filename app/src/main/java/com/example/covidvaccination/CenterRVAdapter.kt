package com.example.covidvaccination

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

class CenterRVAdapter(private val centerList:List<CenterRVModal>):RecyclerView.Adapter<CenterRVAdapter.CenterRVViewHolder>(){
    class CenterRVViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val centerNameTV :TextView=itemView.findViewById(R.id.idTVCenterName)
        val centerAddressTV :TextView=itemView.findViewById(R.id.idTVCenterLocation)
        val centerTimingTV :TextView=itemView.findViewById(R.id.idTVCenterTimings)
        val VaccineNameTV :TextView=itemView.findViewById(R.id.idTVVaccineName)
        val VaccineFeesTV :TextView=itemView.findViewById(R.id.idTVVaccineFees)
        val ageLimitTV :TextView=itemView.findViewById(R.id.idTVAgeLimit)
        val avaliablityTV : TextView=itemView.findViewById(R.id.idTVAvaliablity)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CenterRVViewHolder {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.center_rv_item,parent,false)
        return CenterRVViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: CenterRVViewHolder, position: Int) {

        val center = centerList[position]
        holder.centerNameTV.text= center.centerName
        holder.centerAddressTV.text=center.centerAddress
        holder.centerTimingTV.text=("from: "+center.centerFromTime+" To : "+center.centerToTime)
        holder.VaccineNameTV.text = center.vaccineName
        holder.VaccineFeesTV.text = center.fee_type
        holder.ageLimitTV.text = ("Age Limit : "+center.ageLimit.toString())
        holder.avaliablityTV.text = ("Availability : "+center.availableCapacity.toString())

    }
    override fun getItemCount(): Int {
        return centerList.size
    }

}
