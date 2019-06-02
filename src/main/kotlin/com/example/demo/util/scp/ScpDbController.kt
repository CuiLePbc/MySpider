package com.example.demo.util.scp

import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.Statement

class ScpDbController private constructor(){
    companion object {
        val INSTANCE: ScpDbController by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { ScpDbController() }
    }

    fun createTables(): Int {
        var connection: Connection? = null
        var statement: Statement? = null
        try {
            Class.forName("org.sqlite.JDBC")
            connection = DriverManager.getConnection("jdbc:sqlite:/home/cuile/Desktop/scp/spider.db")

            statement = connection.createStatement()
            val createTableScps = "CREATE TABLE IF NOT EXISTS SCPS (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "title TEXT NOT NULL," +
                    "img TEXT," +
                    "content TEXT);"
            val result = statement.executeUpdate(createTableScps)
            statement.close()
            connection.close()

            return result

        } catch (e: Exception) {
            error("${e.javaClass.name}:${e.message}")
        } finally {
            statement?.close()
            connection?.close()
        }
    }

    fun insert(scpItem: ScpItem): Int {
        var connection: Connection? = null
        var statement: PreparedStatement? = null
        val insertSql = "INSERT INTO SCPS (title, img, content) VALUES (?, ?, ?);"
        try {
            Class.forName("org.sqlite.JDBC")
            connection = DriverManager.getConnection("jdbc:sqlite:/home/cuile/Desktop/scp/spider.db")

            statement = connection.prepareStatement(insertSql)
//            val insertScpSql = "INSERT INTO SCPS (title, img, content) " +
//                    "VALUES ('${scpItem.title}', '${scpItem.img}', '${scpItem.content}');"
//            val result = statement.executeUpdate(insertScpSql)
//            val result = statement.executeUpdate("INSERT INTO SCPS (title, img, content) VALUES (?, ?, ?);", arrayOf(scpItem.title, scpItem.img, scpItem.content))

            statement.setString(1, scpItem.title)
            statement.setString(2, scpItem.img)
            statement.setString(3, scpItem.content)

            val result = statement.executeUpdate()

            statement.close()
            connection.close()

            return result
        } catch (e: Exception) {
            error("${e.javaClass.name}:${e.message}")
        } finally {
            statement?.close()
            connection?.close()
        }
    }

    fun selectList(): List<ScpItem> {

        return emptyList()
    }

    fun selectContent(title: String) : String {

        return ""
    }

}