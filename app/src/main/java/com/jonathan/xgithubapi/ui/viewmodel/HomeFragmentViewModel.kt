package com.jonathan.xgithubapi.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joncasagrande.domain.usecase.GithubRepoUseCase
import com.jonathan.xgithubapi.ui.model.EventState
import com.jonathan.xgithubapi.ui.model.GithubUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val useCase: GithubRepoUseCase
) : ViewModel() {

    private val _eventData = MutableLiveData<EventState>()
    val eventData: LiveData<EventState> = _eventData

    fun loadRepos() {
        _eventData.value = EventState(showLoading = true)
        viewModelScope.launch {
            _eventData.value = when (val result = useCase.getRepos()) {
                is GithubRepoUseCase.Event.Success -> {
                    if (result.listDogs.isEmpty())
                        EventState(isEmpty = true)
                    else
                        EventState(listDogUi = result.listDogs.map {
                            GithubUi(
                                it.name,
                                it.image.orEmpty(),
                                        it.description,
                                0,
                                it.forks,
                                it.lastUpdated,
                                it.lang,
                                it.license,
                                it.ownerName
                            )
                        })
                }

                is GithubRepoUseCase.Event.Error -> EventState(showError = result.error)
            }
        }
    }
}