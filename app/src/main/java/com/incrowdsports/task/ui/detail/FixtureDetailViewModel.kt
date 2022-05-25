package com.incrowdsports.task.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.incrowdsports.task.model.data.DetailInfo
import com.incrowdsports.task.model.repository.FixtureDetailRepo
import com.incrowdsports.task.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FixtureDetailViewModel @Inject constructor(
    private val repo: FixtureDetailRepo,
    private var ioScope: CoroutineScope
) : ViewModel() {

    val fixture = MutableStateFlow<Resource<DetailInfo>>(Resource.Loading())

    internal suspend fun loadDetailInfo(id: String) {
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            fixture.apply {
                exception.message?.let {
                    value = Resource.Error(it)
                }
            }
        }
        viewModelScope.launch(ioScope.coroutineContext + exceptionHandler) {
            val data = repo.getFixtureDetail(id)
            fixture.value = Resource.Success(data)
        }
    }
}