package kr.disdong.sql.anti.pattern.domain.r2dbc.d02

import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface ProductRepository: ReactiveCrudRepository<Product02, Long>, CustomProductRepository

interface CustomProductRepository {

}

/**
 *
 */
class CustomProductRepositoryImpl: CustomProductRepository {

}