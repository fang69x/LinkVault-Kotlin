import com.fang.linkvault.domain.repository.BookmarkRepository
import javax.inject.Inject

class UpdateBookmarksUseCase @Inject constructor(
    private val repository: BookmarkRepository
){

}