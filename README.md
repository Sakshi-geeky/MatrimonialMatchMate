# MatchMate - Matrimonial Android App

A clean, offline-first Android matrimonial application built with modern development principles and a clean UI.

## 🧭 Project Overview

This application fetches random users from an API ([https://randomuser.me](https://randomuser.me)), applies custom logic to calculate match scores, and stores them locally. Users can accept or decline suggestions. It handles flaky networks with retry support and stores statuses persistently offline.

---

## 🚀 Project Setup Instructions


1. **Open in Android Studio**

- Open the project from Android Studio.

2. **Build the project**

- Ensure Gradle is synced.
- Run on a device or emulator with internet access (first-time fetch).

3. **Dependencies** Ensure these dependencies are present in `build.gradle`:

```groovy
implementation "androidx.lifecycle:lifecycle-viewmodel-ktx"
implementation "androidx.lifecycle:lifecycle-livedata-ktx"
implementation "androidx.room:room-runtime"
kapt "androidx.room:room-compiler"
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
implementation 'com.github.bumptech.glide:glide:4.15.0'
kapt 'com.github.bumptech.glide:compiler:4.15.0'
```

---

## 📚 Library List & Justification

| Library              | Purpose                                             |
| -------------------- | --------------------------------------------------- |
| Retrofit + Gson      | API integration and JSON parsing                    |
| Room                 | Offline caching of users                            |
| Glide                | Efficient image loading from URLs                   |
| ViewModel + LiveData | UI state management with lifecycle awareness        |
| Coroutine            | Asynchronous operations in ViewModel and Repository |

---

## 🏛 Architecture - MVVM (Model View ViewModel)

- **Model:** `UserEntity`, `UserResponse`, DAO, and Repository classes
- **ViewModel:** `UserViewModel` handles user state and async calls
- **View:** `MainActivity`, `UserAdapter` renders the UI

Benefits:

- Separation of concerns
- Easy to test and scale
- Offline handling abstracted in Repository

---

## ✳️ Data Classes and Fields Justification

### UserEntity.kt

```kotlin
data class UserEntity(
  val id: Int,
  val name: String,
  val age: Int,
  val country: String,
  val email: String,
  val imageUrl: String,
  val education: String, // Added for user preferences
  val religion: String,  // Added to determine match compatibility
  val matchScore: Int,   // Calculated logic to determine ranking
  var status: String     // User status: accepted/declined/none
)
```

Justification:

- `education` and `religion` help simulate real-world filtering.
- `matchScore` helps sort and prioritize user display.
- `status` enables tracking of user's decision on a profile.

---

## 🧠 Match Score Logic

```kotlin
score = 100
score -= abs(28 - age) * 3
if country != "India" => -10
if religion != "Hindu" => -5
if education !in preferred => -5
```

### Example:

- Age = 25 → penalty = 9
- Country = USA → penalty = 10
- Religion = Christian → penalty = 5
- Education = BA → penalty = 5
- **Final Match Score = 100 - (9 + 10 + 5 + 5) = 71**

Purpose:

- Simulates real-world compatibility rules based on preferences.

---

## 📡 Offline & Error Handling Strategy

- 30% chance of simulated network failure (`IOException`).
- If API fails, local Room cache is returned instead.
- Room is used as single source of truth.
- ViewModel reloads LiveData after status update to reflect changes immediately.

---

## 🔄 Retry Strategy

- No explicit retry logic on API, but fallback to Room data ensures graceful degradation.
- `getUsers()` automatically updates UI even on failure using cached data.

---

## 🧪 Testing Considerations

- Repository is isolated from ViewModel (can be tested independently).
- Room supports in-memory DB testing.
- ViewModel can be tested using `LiveDataTestUtil`.

---

## 🔍 Reflection on Design Choices

- Used MVVM for clean separation and lifecycle-awareness.
- Avoided Hilt/Dagger for simplicity but can be added later for DI.
- Fallback on local cache ensures usability even in flaky network.
- Used simulated errors to test resilience.

---

## 🧠 Hypothetical Constraint Handling

| Constraint                    | Response                                                   |
| ----------------------------- | ---------------------------------------------------------- |
| No Internet at all            | Uses Room as fallback (already implemented)                |
| API rate limiting or slow     | Local DB prevents blocking UI on API failure               |
| Need to sort by match score   | Can easily sort userList before submitting to adapter      |
| Need to support pagination    | Retrofit and Room both support paging (can add Paging 3)   |
| Need to sync status to server | Add POST/PUT API calls in Repository to send status update |
| App restart loses changes     | Handled with Room; state is persistent                     |

---

## 📱 UI Highlights

- Shows name, age, country, education, religion
- Buttons to Accept / Decline user
- Accepted = green background
- Declined = red background
- Glide for profile picture loading

---

## 📦 Packages

```
com.example.matrimonial
├── local         # Room database, DAO, and UserEntity
├── model         # API response model
├── network       # Retrofit API service
├── repository    # Business logic and data sync
├── ui            # MainActivity, Adapter, and layout logic
└── viewmodel     # ViewModel & ViewModelFactory
```

---

## 📄 License

[MIT](https://choosealicense.com/licenses/mit/)

---

## 🤝 Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

---

## 📧 Contact

**Developer:** [SAKSHI CHAVAN]
