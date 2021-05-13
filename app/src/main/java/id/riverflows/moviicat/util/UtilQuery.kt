package id.riverflows.moviicat.util

import androidx.sqlite.db.SimpleSQLiteQuery

object UtilQuery {
    const val GET_ALL_FAVORITE_LIST = "SELECT * FROM favorite_table ORDER BY created_at DESC"
    fun buildQuery(query: String): SimpleSQLiteQuery = SimpleSQLiteQuery(query)
}