rootProject.name = "sql-anti-pattern"

include(
    ":subprojects:common",
    ":subprojects:core:domain-jpa",
    ":subprojects:core:domain-r2dbc",
    ":subprojects:server"
)