package jp.co.lbm.standbynumbercalling

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class DatabaseHelper(private val mContext: Context) : SQLiteOpenHelper(mContext, DB_NAME, null, DATABASE_VERSION) {
    private val mDatabasePath: File = mContext.getDatabasePath(DB_NAME)

    /**
     * asset に格納したデータベースをコピーするための空のデータベースを作成する
     */
    @Throws(IOException::class)
    fun createDatabase() {
        val dbExist = checkDatabaseExists()
        if (dbExist) {
            // すでにデータベースは作成されている
        } else {
            // このメソッドを呼ぶことで、空のデータベースがアプリのデフォルトシステムパスに作られる
            val db = writableDatabase
            db.close()
            try {
                // asset に格納したデータベースをコピーする
                copyDatabaseFromAsset()
                val dbPath = mDatabasePath.absolutePath
                var checkDb: SQLiteDatabase? = null
                try {
                    checkDb = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE)
                } catch (_: SQLiteException) {
                }
                if (checkDb != null) {
                    checkDb.version = DATABASE_VERSION
                    checkDb.close()
                }
            } catch (e: IOException) {
                throw Error("Error copying database")
            }
        }
    }

    /**
     * 再コピーを防止するために、すでにデータベースがあるかどうか判定する
     *
     * @return 存在している場合 `true`
     */
    private fun checkDatabaseExists(): Boolean {
        val dbPath = mDatabasePath.absolutePath
        var checkDb: SQLiteDatabase? = null
        try {
            checkDb = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE)
        } catch (e: SQLiteException) {
            // データベースはまだ存在していない
        }
        if (checkDb == null) {
            // データベースはまだ存在していない
            return false
        }
        val oldVersion = checkDb.version
        val newVersion = DATABASE_VERSION
        if (oldVersion == newVersion) {
            // データベースは存在していて最新
            checkDb.close()
            return true
        }

        // データベースが存在していて最新ではないので削除
        val f = File(dbPath)
        f.delete()
        return false
    }

    /**
     * asset に格納したデーだベースをデフォルトのデータベースパスに作成したからのデータベースにコピーする
     */
    @Throws(IOException::class)
    private fun copyDatabaseFromAsset() {

        // asset 内のデータベースファイルにアクセス
        val mInput = mContext.assets.open(DB_NAME_ASSET)

        // デフォルトのデータベースパスに作成した空のDB
        val mOutput: OutputStream = FileOutputStream(mDatabasePath)

        // コピー
        val buffer = ByteArray(1024)
        var size: Int
        while (mInput.read(buffer).also { size = it } > 0) {
            mOutput.write(buffer, 0, size)
        }

        // Close the streams
        mOutput.flush()
        mOutput.close()
        mInput.close()
    }

    override fun onCreate(db: SQLiteDatabase) {}
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    companion object {
        private const val DB_NAME = "lcasx"
        private const val DB_NAME_ASSET = "lcasx.db"
        private const val DATABASE_VERSION = 1
    }
}