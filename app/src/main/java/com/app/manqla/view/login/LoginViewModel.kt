package com.app.manqla.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.faucet.utils.BaseSchedulerProvider
import com.app.faucet.utils.BaseViewModel
import com.app.faucet.utils.ViewState
import com.app.manqla.network.response.login.LoginResponse
import com.app.manqla.repository.RemoteRepository
import retrofit2.HttpException
import javax.inject.Inject


class LoginViewModel @Inject
constructor(
    baseSchedulerProvider: BaseSchedulerProvider, private val repository: RemoteRepository
) : BaseViewModel(baseSchedulerProvider) {

    fun login(myMap: Map<String, String>): LiveData<ViewState<LoginResponse>> {

        val data = MutableLiveData<ViewState<LoginResponse>>()

        execute(
            loadingConsumer = {
                data.postValue(
                    ViewState.loading()
                )
            },
            successConsumer = { response ->
                response?.let {
                    data.postValue(
                        ViewState.success(it)
                    )
                }
            },
            throwableConsumer = { throwable ->
                data.postValue(
                    ViewState.error(handleError(throwable))
                )
            },
            useCase = repository.login(myMap)
        )

        return data
    }

    private fun handleError(throwable: Throwable): String {
        return if (throwable is HttpException) {
            when (throwable.code()) {
                400 -> {
                    "Wrong Username or Password"
                }
                403 -> {
                    "You should login"
                }
                500 -> {
                    "Something went wrong"
                }
                else -> {
                    throwable.message!!
                }
            }
        } else {
            throwable.message!!
        }
    }

}