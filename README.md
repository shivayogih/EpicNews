
# 📱 Epic News App  

Epic News is a modern Android application that provides users with the latest news articles from various sources. The app is built using **Kotlin**, **MVVM Clean Architecture**, and **Jetpack Compose**, ensuring scalability, maintainability, and a smooth user experience.

---

## 📰 Project Description  
Epic News is designed to display the **latest news from the United States**, using the **newsapi.org** service.  

### 📌 **Features:**  
- ✅ Display a list of the **latest US top headlines**  
- ✅ Each news item includes **title, description, author, and an image**  
- ✅ Fetch news dynamically using **newsapi.org** API  
- ✅ Bookmark favorite articles for later reading  
- ✅ Dark & Light mode support  
- ✅ Offline support - Read previously loaded articles without the internet  
- ✅ Smooth UI with Jetpack Compose  

---

## 🛠 Tech Stack  
- **Kotlin** – Primary language for Android development  
- **MVVM Clean Architecture** – For maintainability and testability  
- **Jetpack Compose** – Modern UI toolkit for building beautiful UIs  
- **Retrofit 2** – For API calls  
- **Hilt (Dagger)** – Dependency Injection  
- **Coroutines & Flow** – For asynchronous operations  
- **Room Database** – Local caching for offline support  
- **Mockk & Mockito** – For unit testing  
- **JUnit** – Testing framework  

---

<img src="https://github.com/user-attachments/assets/c74400f9-6c57-4d74-ade3-0d209db0ce5a" width="300">

<img src="https://github.com/user-attachments/assets/87cc1551-7b6c-456c-9679-fa4953a99196" width="300">

<img src="https://github.com/user-attachments/assets/06a89204-a2a4-47e1-95de-53d178e6c6f2" width="300">





## 📂 Project Structure  


## 🔑 API Keys & Configuration  
The app fetches API keys and base URLs from a **`credentials.properties`** file. 
###  Add API Keys to `credentials.properties`**
Create a `credentials.properties` file in the root of your project and add:
```properties
API_KEY=<your_api_key_here>
BASE_URL1=https://newsapi.org/v2/
BASE_URL2=https://cn-news-info-api.herokuapp.com/




