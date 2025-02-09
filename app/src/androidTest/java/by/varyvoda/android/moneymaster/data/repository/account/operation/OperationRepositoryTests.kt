package by.varyvoda.android.moneymaster.data.repository.account.operation

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import by.varyvoda.android.moneymaster.config.di.appModule
import by.varyvoda.android.moneymaster.util.randomIncome
import by.varyvoda.android.moneymaster.util.randomString
import kotlinx.coroutines.flow.first
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.kodein.di.DI
import org.kodein.di.instance
import kotlinx.coroutines.runBlocking

@RunWith(AndroidJUnit4::class)
class OperationRepositoryTests {

    private lateinit var operationRepository: OperationRepository

    @Before
    fun initialize() {
        val di = DI {
            import(appModule(ApplicationProvider.getApplicationContext()))
        }
        val operationRepository: OperationRepository by di.instance()
        this.operationRepository = operationRepository
    }

    @Test
    fun testCRUD() = runBlocking {
        val income = randomIncome()

        operationRepository.insert(income)
        val storedIncome = operationRepository.getAll().first().single()
        assertEquals(income, storedIncome)

        val updatedIncome = income.copy(description = randomString())
        operationRepository.update(updatedIncome)
        val storedUpdatedIncome = operationRepository.getAll().first().single()
        assertEquals(updatedIncome, storedUpdatedIncome)

        operationRepository.delete(income)
        assertEquals(0, operationRepository.getAll().first().size)
    }
}