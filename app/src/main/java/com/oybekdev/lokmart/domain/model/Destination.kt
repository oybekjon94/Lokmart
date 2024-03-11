package com.oybekdev.lokmart.domain.model

sealed class Destination {
    object Home:Destination()
    object Onboarding:Destination()
    object Auth:Destination()
}