package com.mehrsoft.foody.data.repository

import com.mehrsoft.foody.data.remote.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject
import javax.inject.Scope


@ActivityRetainedScoped
class FoodRecipeRepo @Inject constructor(remoteDataSource: RemoteDataSource) {

val remote=remoteDataSource
}