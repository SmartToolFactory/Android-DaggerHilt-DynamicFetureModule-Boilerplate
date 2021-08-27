package com.smarttoolfactory.projectboilerplate

import androidx.lifecycle.ViewModel
import com.smarttoolfactory.domain.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope

@HiltViewModel
class MainViewModel @Inject constructor(
    private val coroutineScope: CoroutineScope,
    private val useCase: UseCase
) : ViewModel()
