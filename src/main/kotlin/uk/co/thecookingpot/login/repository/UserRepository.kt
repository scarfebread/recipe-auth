package uk.co.thecookingpot.login.repository

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import javax.sql.DataSource

class UserRepository(private val dataSource: DataSource) {
    fun findByUsername(username: String): User? {
        Database.connect(dataSource)
        return transaction {
            Users.select { Users.username eq username }.singleOrNull()?.run {
                val result = this
                User().apply {
                    this.username = result[Users.username]
                    this.password = result[Users.password]
                    this.email = result[Users.email]
                }
            }
        }
    }

    fun save(user: User) {

    }

    object Users : IntIdTable() {
        val username: Column<String> = varchar("username", 50)
        val password: Column<String> = varchar("password", 50)
        val email: Column<String> = varchar("email", 50)
        override val primaryKey = PrimaryKey(id)
    }
}