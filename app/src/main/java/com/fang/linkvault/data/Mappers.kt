import com.fang.linkvault.data.dto.auth.UserDto
import com.fang.linkvault.data.dto.bookmark.BookmarkDto
import com.fang.linkvault.domain.model.Bookmark
import com.fang.linkvault.domain.model.User


fun UserDto.toDomain():User{
    return User(
        id = this.id,
        name=this.name,
        email = this.email,
        avatarUrl = this.avatar
    )
}
fun BookmarkDto.toDomain(): Bookmark{
    return Bookmark(
id = this.id!!,
        title = this.title,
        url = this.url,
        note = this.note,
        category = this.category,
        tags = this.tags,
        userId = this.userId,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}
fun Bookmark.toDto(): BookmarkDto{
    return BookmarkDto(
        id = this.id,
        title = this.title,
        url = this.url,
        note = this.note,
        category = this.category,
        tags = this.tags,
        userId = this.userId,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}