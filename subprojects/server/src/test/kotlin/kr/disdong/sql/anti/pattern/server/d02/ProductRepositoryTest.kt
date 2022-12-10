package kr.disdong.sql.anti.pattern.server.d02

import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.runBlocking
import kr.disdong.sql.anti.pattern.domain.r2dbc.d02.ProductRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class ProductRepositoryTest {

    @Autowired
    private lateinit var productRepository: ProductRepository

    @Test
    fun t1() = runBlocking {
        println(productRepository.findAll().awaitFirstOrNull())
    }
}