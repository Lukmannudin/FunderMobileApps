package com.team.oleg.funder.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.team.oleg.funder.utils.ChatUtils
import org.jetbrains.anko.db.*

class FunderChatDatabaseHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FunderChatDB", null, 1) {
    companion object {
        private var instance: FunderChatDatabaseHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): FunderChatDatabaseHelper {
            if (instance == null) {
                instance = FunderChatDatabaseHelper(ctx.applicationContext)
            }
            return instance as FunderChatDatabaseHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(
            ChatUtils.TABLE_CHAT, true,
            ChatUtils.MESSAGE_ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            ChatUtils.CHAT_ID to TEXT,
            ChatUtils.SENDER to TEXT,
            ChatUtils.MESSAGE to TEXT,
            ChatUtils.MESSAGE_TIME to TEXT,
            ChatUtils.MESSAGE_STATUS to TEXT,
            ChatUtils.MESSAGE_READ to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(ChatUtils.TABLE_CHAT, true)
    }
}

val Context.database: FunderChatDatabaseHelper
    get() = FunderChatDatabaseHelper.getInstance(applicationContext)