package com.example.teststori.domain.use_case.get_home

import coil.network.HttpException
import com.example.teststori.common.Resource
import com.example.teststori.domain.model.Balance
import com.example.teststori.domain.model.Movement
import com.example.teststori.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetHomeUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(): Flow<Resource<Balance?>> = flow {
        try {
            emit(Resource.Loading())
            val balance = repository.getBalance()
            emit(Resource.Success(balance))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }

    fun getMovements(): Flow<Resource<List<Movement?>?>> = flow {
        try {
            emit(Resource.Loading())
            val movements = repository.getMovements()
            emit(Resource.Success(movements))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}