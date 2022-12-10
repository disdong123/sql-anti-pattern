package kr.disdong.sql.anti.pattern.domain.r2dbc.d03

import io.r2dbc.spi.Connection
import io.r2dbc.spi.ConnectionFactory
import org.springframework.data.annotation.Id
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

@Table("comment")
data class Comment03(
    @Id
    var id: Long?,
    var author_id: Long,
    var bug_id: Long,
    var comment: String,
    // TODO Transient 가 없어도 에러가 발생하진 않습니다.
    //  즉, Entity 가 table 의 컬럼과 꼭 매칭되지 않아도 됩니다.
    //  만약 comment2 로 잘못적어도 쿼리는 수행됨. 단지 null 이면 에러가 발생한다.
    //  @Transient
    var treePath: List<TreePath03> = listOf()
)

interface CommentRepository: ReactiveCrudRepository<Comment03, Long> {

    /**
     * TODO
     *  treePath 가 정상적으로 들어오지 않습니다.
     *  즉, mapper 를 따로 정의하여 impl class 에서 쿼리 작성후 변환해줘야합니다.....
     *
     * @return
     */
    @Query("""
        select *
        from comment
        left join tree_path on comment.id = tree_path.ancestor
    """)
    fun findAllWithTreePath(): Flux<Comment03>
}

interface CommentRepositoryCustom {
}

class CommentRepositoryImpl(
    private final val connectionFactory: ConnectionFactory,
    private val connection: Connection
): CommentRepositoryCustom {
    private val r2dbcEntityTemplate = R2dbcEntityTemplate(connectionFactory)

    // override fun findAllWithTreePath2(): Flux<Comment03> {
    // }

}