package com.example.teststori.domain.use_case.get_photo

import android.net.Uri
import coil.network.HttpException
import com.example.teststori.common.Resource
import com.example.teststori.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetPhotoUseCase @Inject constructor(
    private val repository: Repository
) {

    operator fun invoke(
        uri: Uri?
    ): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            val isSuccess = repository.updateUserUri(uri)
            emit(Resource.Success(isSuccess))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}