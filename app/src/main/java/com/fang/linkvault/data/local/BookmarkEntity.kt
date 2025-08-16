import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "bookmarks")

data class BookmarkEntity (
    @PrimaryKey
    val url: String,
    val id: String,
    val title: String,
    val note: String?,
    val category: String,
    val tags: List<String>?,
    val userId: String,
    val createdAt: Date?,
    val updatedAt: Date?,
)