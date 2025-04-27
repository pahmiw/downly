package id.downly.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.downly.domain.model.SocialAutolink
import id.downly.domain.repository.Repository
import id.downly.utils.Either
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @Author Ahmad Pahmi Created on April 2025
 */
@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _autoLink = MutableLiveData<Either<SocialAutolink>>()
    val autolink: LiveData<Either<SocialAutolink>> get() = _autoLink

    fun getSocialAutoLink(url: String) = viewModelScope.launch(Dispatchers.IO) {
        _autoLink.postValue(repository.getSocialAutoLink(url))
    }
}