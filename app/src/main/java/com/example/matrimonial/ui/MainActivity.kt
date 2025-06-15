package com.example.matrimony.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.matrimonial.databinding.ActivityMainBinding
import com.example.matrimonial.local.UserDatabase
import com.example.matrimonial.network.RetrofitInstance
import com.example.matrimonial.repository.UserRepository
import com.example.matrimonial.ui.UserAdapter
import com.example.matrimonial.viewmodel.UserViewModel
import com.example.matrimonial.viewmodel.UserViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = UserDatabase.getDatabase(this).userDao()
        val repository = UserRepository(RetrofitInstance.api, dao)
        val factory = UserViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]

        setupRecyclerView()
        viewModel.fetchUsers()

        viewModel.users.observe(this) {
            adapter.submitList(it)
        }

    }


    private fun setupRecyclerView() {
        adapter = UserAdapter(viewModel)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }
}
