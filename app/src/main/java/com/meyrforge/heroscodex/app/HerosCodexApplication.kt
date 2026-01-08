package com.meyrforge.heroscodex.app

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import com.meyrforge.heroscodex.core.work.TokenRegenWorker

@HiltAndroidApp
class HerosCodexApplication : Application(), Configuration.Provider {

	@Inject
	lateinit var workerFactory: HiltWorkerFactory

	override fun onCreate() {
		super.onCreate()

		// Schedule periodic token regeneration worker (runs every 15 minutes)
		val workRequest = PeriodicWorkRequestBuilder<TokenRegenWorker>(15, TimeUnit.MINUTES)
			.build()

		WorkManager.getInstance(this).enqueueUniquePeriodicWork(
			"token_regeneration",
			ExistingPeriodicWorkPolicy.KEEP,
			workRequest
		)
	}

	override val workManagerConfiguration: Configuration by lazy {
		Configuration.Builder()
			.setWorkerFactory(workerFactory)
			.build()
	}
}
