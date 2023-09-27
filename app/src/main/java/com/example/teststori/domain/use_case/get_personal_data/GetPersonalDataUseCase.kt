package com.example.teststori.domain.use_case.get_personal_data

import coil.network.HttpException
import com.example.teststori.common.Resource
import com.example.teststori.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetPersonalDataUseCase @Inject constructor(
    private val repository: Repository
) {

    operator fun invoke(
        email: String,
        password: String,
    ): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            val isSuccessRegister = repository.getRegistration(email, password)
            emit(Resource.Success(isSuccessRegister))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }

    operator fun invoke(
        nameLasName: String,
    ): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            val isSuccessUpdate = repository.updateUser(nameLasName)
            emit(Resource.Success(isSuccessUpdate))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}