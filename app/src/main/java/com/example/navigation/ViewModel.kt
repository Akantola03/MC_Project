package com.example.navigation
/*
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import android.net.Uri
import kotlinx.coroutines.delay

class MyViewModel(
    private val dao: UserDao,
): ViewModel() {

    val user = dao.getUserName()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            null)




    fun saveUsername(name: String){
        viewModelScope.launch {
            val current = user.value
            dao.saveUser(UserEntity(
                id = 0,
                username = name,
                imageUri = current?.imageUri
                )
            )
        }
    }

    fun saveImageFromGallery(uri: Uri, context: Context) {
        viewModelScope.launch {
            val storedUri = ImageUtils.copyImageToStorage(context, uri)
            storedUri?.let{
                val current = user.value
                dao.saveUser(UserEntity(
                    id = 0,
                    username = current?.username?: "None",
                    imageUri = it.toString()
                )
                )
            }
        }
    }
}
*/
