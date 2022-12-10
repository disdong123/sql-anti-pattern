package kr.disdong.sql.anti.pattern.domain.r2dbc.d03

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("bug")
class Bug03(
    @Id
    val id: Long,

    @Column("name")
    val name: String,
) {
}