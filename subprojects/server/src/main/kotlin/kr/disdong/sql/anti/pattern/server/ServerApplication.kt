package kr.disdong.sql.anti.pattern.server

import kotlinx.coroutines.reactive.awaitSingle
import kr.disdong.sql.anti.pattern.domain.r2dbc.d02.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication(scanBasePackages = ["kr"])
class ServerApplication

fun main(args: Array<String>) {
    runApplication<ServerApplication>(*args)
}