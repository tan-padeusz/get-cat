package pl.tanpadeusz.getcat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.tanpadeusz.getcat.service.CatService
import pl.tanpadeusz.getcat.service.ImageResponse
import timber.log.Timber

class CatViewModel : ViewModel() {
    private val _imageResponse = MutableLiveData<ImageResponse?>()
    val imageResponse = this._imageResponse as LiveData<ImageResponse?>

    fun requestImage() {
        this.viewModelScope.launch {
            Timber.d("requesting new cat")
            try {
                val response = CatService.getImage()
                Timber.d("is response successful : ${response.isSuccessful} | code ${response.code()} | message : ${response.message()}")
                val body = response.body()
                this@CatViewModel._imageResponse.value = if (body.isNullOrEmpty()) null else body[0]
            } catch (ex: Exception) {
                Timber.e("unexpected error occurred : ${ex.message}")
            }
        }
    }
}