package com.smarttoolfactory.gallery.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.smarttoolfactory.core.viewstate.ViewState
import com.smarttoolfactory.domain.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope

@HiltViewModel
class FeatureViewModel @Inject constructor(
    private val coroutineScope: CoroutineScope,
    private val useCase: UseCase
) : ViewModel() {

    private val _dataState = MutableLiveData<ViewState<List<String>>>()
    val dataState: LiveData<ViewState<List<String>>>
        get() = _dataState
}

class FeatureViewModelFactory @Inject constructor(
    private val coroutineScope: CoroutineScope,
    private val useCase: UseCase,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass != FeatureViewModel::class.java) {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
        return FeatureViewModel(
            coroutineScope,
            useCase,
        ) as T
    }
}
