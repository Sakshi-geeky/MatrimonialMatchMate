/*
package com.example.matrimonial.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.matrimonial.databinding.ItemUserBinding
import com.example.matrimonial.local.UserEntity
import com.example.matrimonial.viewmodel.UserViewModel

class UserAdapter(private val viewModel: UserViewModel) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var userList: List<UserEntity> = emptyList()

    fun submitList(users: List<UserEntity>) {
        userList = users
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int = userList.size

    inner class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserEntity) {
            // Set name
            binding.tvName.text = user.name

            // Display "age, city" format
            val ageCityText = "${user.age}, ${user.country}"
            binding.tvAgeCity.text = ageCityText

            // Show match score
            binding.tvMatchScore.text = "Match: ${user.matchScore}%"

            // Load image
            Glide.with(binding.imgUser.context)
                .load(user.imageUrl)
                .into(binding.imgUser)

            // Set Accept/Decline states
            val isSelectable = user.status == "none"
            binding.imgAccept.isEnabled = isSelectable
            binding.imgDecline.isEnabled = isSelectable

            // Accept
            binding.imgAccept.setOnClickListener {
                viewModel.updateStatus(user.id, "accepted")
            }

            // Decline
            binding.imgDecline.setOnClickListener {
                viewModel.updateStatus(user.id, "declined")
            }
        }
    }
}
*/

package com.example.matrimonial.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.matrimonial.databinding.ItemUserBinding
import com.example.matrimonial.local.UserEntity
import com.example.matrimonial.viewmodel.UserViewModel

class UserAdapter(private val viewModel: UserViewModel) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {



    private var userList: List<UserEntity> = emptyList()

    fun submitList(users: List<UserEntity>) {
        userList = users
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int = userList.size

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserEntity) = with(binding) {
            tvName.text = user.name
            tvAge.text = "${user.age} yrs"
            tvCountry.text = user.country
            tvMatchScore.text = "Match Score: ${user.matchScore}%"
            tvEducation.text = "Education: ${user.education}"
            tvReligion.text = "Religion: ${user.religion}"

            Glide.with(imgUser.context)
                .load(user.imageUrl)
                .into(imgUser)

            // Enable buttons only if not yet accepted/declined
            val canRespond = user.status == "none"
            imgAccept.isEnabled = canRespond
            imgDecline.isEnabled = canRespond

            viewModel.getStatus(user.id) { status ->
                if (status != null && status != "none") {
                    tvStatus.text = "Status: $status"
                } else {
                    tvStatus.text = "" // or tvStatus.visibility = View.GONE
                }
            }

            // Set background color based on status
            when (user.status) {
                "accepted" -> root.setBackgroundColor(root.context.getColor(android.R.color.holo_green_light))
                "declined" -> root.setBackgroundColor(root.context.getColor(android.R.color.holo_red_light))
                else -> root.setBackgroundColor(root.context.getColor(android.R.color.white))
            }

            imgAccept.setOnClickListener {
                if (canRespond) {
                    viewModel.updateStatus(user.id, "Accepted")
                    tvStatus.text = "Accepted"
                    user.status = "Accepted"
                    notifyItemChanged(adapterPosition)
                }
            }

            imgDecline.setOnClickListener {
                if (canRespond) {
                    viewModel.updateStatus(user.id, "Declined")
                    tvStatus.text = "Declined"
                    user.status = "Declined"
                    notifyItemChanged(adapterPosition)
                }
            }
        }
    }
}

