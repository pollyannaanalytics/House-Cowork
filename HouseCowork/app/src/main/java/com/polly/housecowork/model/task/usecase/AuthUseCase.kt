package com.polly.housecowork.model.task.usecase

import com.polly.housecowork.prefs.PrefsLicense
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val license: PrefsLicense,

) {
}