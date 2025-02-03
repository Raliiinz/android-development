package ru.itis.androiddevelopment.util

import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger

class CoroutineManager {

    private var job: Job? = null
    private var coroutineScope: CoroutineScope? = null
    private var completedCount = AtomicInteger(0)
    private var totalCoroutines = 0

    suspend fun showToast(context: Context, message: String) {
        withContext(Dispatchers.Main) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    fun launchCoroutines(
        numberOfCoroutines: Int,
        launchMode: LaunchMode,
        threadPool: ThreadPool,
        onCoroutineCompleted: (Int) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        cancelCoroutines()

        totalCoroutines = numberOfCoroutines
        completedCount.set(0)
        coroutineScope = CoroutineScope(threadPool.dispatcher + SupervisorJob())
        job = coroutineScope?.launch {
            try {
                if (launchMode == LaunchMode.PARALLEL) {
                    repeat(numberOfCoroutines) {
                        launch {
                            performTask()
                        }
                    }
                } else {
                    repeat(numberOfCoroutines) {
                        launch {
                            performTask()
                        }.join()
                    }
                }

                onCoroutineCompleted(completedCount.get())
            } catch (e: CancellationException) {
                // Ignore cancellation exceptions
            } catch (e: Throwable) {
                onError(e)
            }
        }

    }

    fun cancelCoroutines(): Int {
        job?.cancel()
        println(completedCount)
        return totalCoroutines - completedCount.get()
    }

    private suspend fun performTask() {
        delay(2000)
        completedCount.incrementAndGet()
    }
}
