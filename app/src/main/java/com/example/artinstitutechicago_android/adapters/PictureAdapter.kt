package com.example.artinstitutechicago_android.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.artinstitutechicago_android.R
import com.example.artinstitutechicago_android.data.Picture
import com.example.artinstitutechicago_android.databinding.ItemPictureBinding
import com.squareup.picasso.Picasso

class PictureAdapter (
    var items: List<Picture>, val onClickListener: (Int) -> Unit
    ) : RecyclerView.Adapter<PictureViewHolder>() {

        // Cual es la vista para los elementos
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemPictureBinding.inflate(layoutInflater, parent, false)
            return PictureViewHolder(binding)
        }

    // Cuales son los datos para el elemento
        override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
            val item = items[position]
            holder.render(item)
            holder.itemView.setOnClickListener {
                onClickListener(position)
            }
        }

        // Cuantos elementos se quieren listar?
        override fun getItemCount(): Int {
            return items.size
        }

        fun updateItems(items: List<Picture>) {
            this.items = items
            notifyDataSetChanged()
        }
    }

    class PictureViewHolder(val binding: ItemPictureBinding) : RecyclerView.ViewHolder(binding.root) {

        fun render(picture: Picture) {
            binding.titleTextView.text = picture.title
            val shortDescription: String? = picture.shortDescription
            if(shortDescription == null)
                binding.shorDescriptionTextView.setText(R.string.detail_text_description)
            else
                binding.shorDescriptionTextView.text = picture.shortDescription


            Picasso.get().load(picture.getImageUrl())
//                .placeholder(R.drawable.bg_image_placeholder)
                .into(binding.imageView)
        }
}