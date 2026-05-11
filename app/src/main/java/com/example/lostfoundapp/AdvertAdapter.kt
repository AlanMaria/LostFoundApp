package com.example.lostfoundapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView



class AdvertAdapter(
    private val context: Context,
    private val advertList: ArrayList<Advert>
) : RecyclerView.Adapter<AdvertAdapter.AdvertViewHolder>() {

    // stores the views inside one advert card.
    class AdvertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val advertImageView: ImageView = itemView.findViewById(R.id.imgItem)
        val advertTitleText: TextView = itemView.findViewById(R.id.tvTitle)
        val advertCategoryText: TextView = itemView.findViewById(R.id.tvCategory)
        val advertLocationText: TextView = itemView.findViewById(R.id.tvLocation)
        val advertTimestampText: TextView = itemView.findViewById(R.id.tvDateTime)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvertViewHolder {

        val advertItemView = LayoutInflater.from(context).inflate(
            R.layout.item_advert,
            parent,
            false
        )

        return AdvertViewHolder(advertItemView)
    }


    override fun onBindViewHolder(holder: AdvertViewHolder, position: Int) {

        val currentAdvert = advertList[position]

        // Display main advert information.
        holder.advertTitleText.text = "${currentAdvert.postType}: ${currentAdvert.name}"
        holder.advertCategoryText.text = "Category: ${currentAdvert.category}"
        holder.advertLocationText.text = "Location: ${currentAdvert.location}"
        holder.advertTimestampText.text = "Posted: ${currentAdvert.dateTime}"



        try {
            holder.advertImageView.setImageURI(Uri.parse(currentAdvert.imageUri))
        } catch (error: Exception) {
            holder.advertImageView.setImageResource(android.R.drawable.ic_menu_gallery)
        }


        holder.itemView.setOnClickListener {
            val detailIntent = Intent(context, AdvertDetailActivity::class.java)

            // Pass the selected advert ID to the detail screen.
            detailIntent.putExtra("advertId", currentAdvert.id)

            context.startActivity(detailIntent)
        }
    }

    // This tells RecyclerView how many advert cards are to be displayed.
    override fun getItemCount(): Int {
        return advertList.size
    }
}