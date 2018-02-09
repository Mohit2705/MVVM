package com.example.msharma.dashlite.util

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.runners.BlockJUnit4ClassRunner
import org.junit.runners.model.InitializationError
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class RxJavaTestRunner @Throws(InitializationError::class)
constructor(testClass: Class<*>) : BlockJUnit4ClassRunner(testClass) {

    private val immediate = object : Scheduler() {
        override fun scheduleDirect(run: Runnable,
                                    delay: Long, unit: TimeUnit): Disposable {
            return super.scheduleDirect(run, 0, unit)
        }

        override fun createWorker(): Scheduler.Worker {
            return ExecutorScheduler.ExecutorWorker(
                    Executor { it.run() })
        }
    }
    fun setUp() {
        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
    }


    init {
        RxAndroidPlugins.reset()
        setUp()
    }
}