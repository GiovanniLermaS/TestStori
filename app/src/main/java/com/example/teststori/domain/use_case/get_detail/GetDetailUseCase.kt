package com.example.teststori.domain.use_case.get_detail

import coil.network.HttpException
import com.example.teststori.common.Resource
import com.example.teststori.domain.model.Detail
import com.example.teststori.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetDetailUseCase @Inject constructor(
    private val repository: Repository
) {

    operator fun invoke(id: String): Flow<Resource<List<Detail?>?>> = flow {
        try {
            emit(Resource.Loading())
            val detail = repository.getDetail(id)
            emit(Resource.Success(detail))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}