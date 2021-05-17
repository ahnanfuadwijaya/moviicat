package id.riverflows.moviicat.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery


@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite_table WHERE id == :id AND type == :type")
    suspend fun findByIdAndType(id: Long, type: String): FavoriteEntity?
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favorite: FavoriteEntity): Long
    @Delete
    suspend fun remove(favorite: FavoriteEntity): Int
    @RawQuery(observedEntities = [FavoriteEntity::class])
    fun getFavoritePaged(query: SupportSQLiteQuery): DataSource.Factory<Int, FavoriteEntity>
}