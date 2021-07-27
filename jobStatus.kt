import kotlinx.coroutines.*
import kotlin.system.*

fun main() {
    val coroutineScope = CoroutineScope(Dispatchers.Unconfined)

    val job1 = coroutineScope.launch {
        this.cancel()
    }
    val job2 = coroutineScope.launch { delay(1000L) }

    println("Job 1 state: ${job1.status()}")
    println("Job 2 state: ${job2.status()}")
    println("Parent job is active: ${coroutineScope.isActive}")
}

fun Job.status(): String = when {
    isCancelled -> "cancelled"
    isActive -> "Active"
    isCompleted -> "Complete"
    else -> "Nothing"
}
