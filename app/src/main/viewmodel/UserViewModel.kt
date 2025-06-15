package com.example.matrimonial.viewmodel
import androidx.lifecycle.*
import com.example.matrimonial.local.UserEntity
import com.example.matrimonial.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository): ViewModel() {

    private val _users = MutableLiveData<List<UserEntity>>()
    val users: LiveData<List<UserEntity>> = _users

    fun fetchUsers() {
        viewModelScope.launch {
            _users.value = repository.getUsers()
        }
    }

    fun updateStatus(id: Int, status: String) {
        viewModelScope.launch {
            repository.updateUserStatus(id, status)
            fetchUsers()
        }
    }

    fun getStatus(userId: Int, callback: (String?) -> Unit) {
        viewModelScope.launch {
            val status = repository.getUserStatus(userId)
            callback(status)
        }
    }
    
}

class UserViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
