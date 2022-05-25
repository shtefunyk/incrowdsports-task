package com.incrowdsports.task.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.incrowdsports.task.model.data.Fixture
import com.incrowdsports.task.model.repository.FixturesRepo
import com.incrowdsports.task.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FixtureListViewModel @Inject constructor(
    private val repo: FixturesRepo,
    private var ioScope: CoroutineScope
) : ViewModel() {

    val fixtureList = MutableStateFlow<Resource<List<Fixture>>>(Resource.Loading())

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        fixtureList.apply {
            exception.message?.let {
                value = Resource.Error(it)
            }
        }
    }

    internal suspend fun loadData() {
        viewModelScope.launch(ioScope.coroutineContext + exceptionHandler) {
            val data = repo.getFixturesList()
            fixtureList.value = Resource.Success(data)
        }
    }
}