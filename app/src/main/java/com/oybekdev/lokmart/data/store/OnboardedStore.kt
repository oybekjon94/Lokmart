package com.oybekdev.lokmart.data.store

import javax.inject.Inject

class OnboardedStore @Inject constructor():BaseStore<Boolean>("onboarded",Boolean::class.java)