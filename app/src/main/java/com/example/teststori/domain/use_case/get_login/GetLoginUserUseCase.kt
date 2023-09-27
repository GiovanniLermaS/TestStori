package com.example.teststori.domain.use_case.get_login

import coil.network.HttpException
import com.example.teststori.common.Resource
import com.example.teststori.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetLoginUserUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(
        email: String,
        password: String
    ): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val login = repository.getLogin(email, password)
            emit(Resource.Success(login))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}