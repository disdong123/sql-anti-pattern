package kr.disdong.sql.anti.pattern.server.test

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import kr.disdong.sql.anti.pattern.domain.r2dbc.d02.ProductRepository
import kr.disdong.sql.anti.pattern.domain.r2dbc.d03.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    private val productRepository: ProductRepository,
    private val commentRepository: CommentRepository,
    private val treePathRepository: TreePathRepository,
) {

    @GetMapping("test")
    suspend fun test() {

        // commentRepository.save(Comment03( null, 1, 1, "hello")).awaitSingle()
        // treePathRepository.save(TreePath03(null, 1, 1)).awaitSingle()

        println(commentRepository.findAllWithTreePath().asFlow().toList())
    }
}