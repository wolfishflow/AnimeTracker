package com.animetracker

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Hilt Requires a custom application class, that has an annotation @HiltAndroidApp (same situation with Dagger)
@HiltAndroidApp
class AnimeTrackerApplication : Application()
