import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import retrofit2.Converter

@Database(
entities = [BookmarkEntity::class],
    version = 1
)
@TypeConverters(Converter:: class)
abstract class LinkVaultDatabase: RoomDatabase(){
    abstract fun bookmarkDao():BookmarkDao
}
