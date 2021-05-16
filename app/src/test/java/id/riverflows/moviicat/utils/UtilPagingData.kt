package id.riverflows.moviicat.utils

import androidx.paging.PagingData
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@Suppress("UNCHECKED_CAST")
object UtilPagingData {

    fun <T : Any> mockPagingData(list: List<T>): PagingData<T> {
        val pagingData = mock(PagingData::class.java) as PagingData<T>
        `when`(pagingData).then { invocation ->
            val index = invocation.arguments.first() as Int
            list[index]
        }
        return pagingData
    }
}