package id.riverflows.moviicat.data.source.local.room

import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery


@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: FavoriteEntity): Long
    @Delete
    suspend fun delete(favorite: FavoriteEntity)
    @RawQuery(observedEntities = [FavoriteEntity::class])
    fun getFavoritePagedList(query: SupportSQLiteQuery): DataSource.Factory<Int, FavoriteEntity>
}