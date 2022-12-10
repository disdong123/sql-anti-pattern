package kr.disdong.sql.anti.pattern.domain.r2dbc.d02

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("product")
class Product02(
    @Id
    val id: Long,

    @Column("name")
    val name: String,

    @Column("account_id")
    val accountId: Long,

    @Transient
    val contacts: List<Contact02>
)
