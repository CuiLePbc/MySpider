package com.example.demo.util.xiaowei

import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.Statement

class XiaoweiDbController private constructor(){
    companion object {
        val INSTANCE: XiaoweiDbController by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { XiaoweiDbController() }
    }


    fun createTables(): Int {
        var connection: Connection? = null
        var statement: Statement? = null
        try {
            Class.forName("org.sqlite.JDBC")
            connection = DriverManager.getConnection("jdbc:sqlite:/home/cuile/Desktop/scp/spider.db")

            statement = connection.createStatement()
            val createTableScps = "CREATE TABLE IF NOT EXISTS XIAOWEI (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "code TEXT NOT NULL," +
                    "type TEXT NOT NULL," +
                    "putin TEXT," +
                    "trade TEXT);"
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

    fun insert(xiaoweiItem: XiaoweiItem): Int {
        var connection: Connection? = null
        var statement: PreparedStatement? = null
        val insertSql = "INSERT INTO XIAOWEI (name, code, type, putin, trade) VALUES (?, ?, ?, ?, ?);"
        try {
            Class.forName("org.sqlite.JDBC")
            connection = DriverManager.getConnection("jdbc:sqlite:/home/cuile/Desktop/scp/spider.db")

            statement = connection.prepareStatement(insertSql)

            statement.setString(1, xiaoweiItem.name)
            statement.setString(2, xiaoweiItem.code)
            statement.setString(3, xiaoweiItem.type)
            statement.setString(4, xiaoweiItem.putin)
            statement.setString(5, xiaoweiItem.trade)

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
}