package kr.disdong.sql.anti.pattern.domain.r2dbc.d03

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.reactive.ReactiveCrudRepository

@Table("tree_path")
data class TreePath03(
    @Id
    var id: Long?,
    var ancestor: Long,
    var descendant: Long,
)

interface TreePathRepository: ReactiveCrudRepository<TreePath03, Long>