package kr.disdong.sql.anti.pattern.domain.r2dbc.core.config

import io.r2dbc.spi.ConnectionFactory
import org.springframework.beans.factory.annotation.Configurable
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@Configuration
@EnableR2dbcRepositories(basePackages = ["kr.disdong.sql.anti.pattern.domain.r2dbc"])
class R2dbcConfig {
    @Bean
    fun r2dbcEntityTemplate(factory: ConnectionFactory): R2dbcEntityTemplate {
        return R2dbcEntityTemplate(factory)
    }
}