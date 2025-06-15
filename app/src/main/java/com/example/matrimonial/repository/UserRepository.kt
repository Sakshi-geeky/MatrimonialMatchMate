package com.example.matrimonial.repository

import com.example.matrimonial.local.UserDao
import com.example.matrimonial.local.UserEntity
import com.example.matrimonial.network.ApiService
import java.io.IOException

class UserRepository(private val api: ApiService, private val dao: UserDao) {


    suspend fun getUsers(): List<UserEntity> {
        return try {
            val shouldFail = (0..9).random() < 3 // 30% chance of failure
            if (shouldFail) throw IOException("Simulated network failure")

            val response = api.getUsers()
            if (response.isSuccessful) {
                val users = response.body()?.results?.map {
                    val education = listOf("B.Tech", "MCA", "MBA", "BA", "B.Com").random()
                    val religion = listOf("Hindu", "Muslim", "Christian", "Sikh").random()
                    val matchScore = calculateMatchScore(it.dob.age, it.location.country, religion, education)

                    UserEntity(
                        name = "${it.name.first} ${it.name.last}",
                        age = it.dob.age,
                        country = it.location.country,
                        email = it.email,
                        imageUrl = it.picture.large,
                        education = education,
                        religion = religion,
                        matchScore = matchScore,
                        status = "none"
                    )
                } ?: emptyList()

                dao.clearAll()
                dao.insertUsers(users)
                users
            } else {
                dao.getUsers()
            }
        } catch (e: Exception) {
            dao.getUsers() // return local data if API fails
        }
    }

    private fun calculateMatchScore(age: Int, country: String, religion: String, education: String): Int {
        var score = 100
        score -= kotlin.math.abs(28 - age) * 3
        if (country != "India") {
            score -= 10
        }
        if (religion != "Hindu") {
            score -= 5
        }
        val preferredEducation = listOf("MCA", "MBA", "B.Tech")
        if (!preferredEducation.contains(education)) {
            score -= 5
        }
        return score.coerceIn(0, 100)
    }

    suspend fun updateUserStatus(id: Int, status: String) {
        dao.updateStatus(id, status)
    }

    suspend fun getUserStatus(userId: Int): String? {
        return dao.getStatusById(userId)
    }
}
