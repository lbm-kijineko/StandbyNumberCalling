package jp.co.lbm.standbynumbercalling

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import java.io.IOException

abstract class MainActivity : AppCompatActivity(), MediaPlayer.OnCompletionListener {


//    lateinit var helper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        try {
//            helper = DatabaseHelper(this)
//            helper.createDatabase()
//        }
//        catch (e: IOException) {
//            throw Error("Unable to create database")
//        }

//        val builder: StringBuilder = StringBuilder()
//
//        val db: SQLiteDatabase = helper.writableDatabase

//        val cursor: Cursor = db.rawQuery("select * from calling_voice where VoicePatterNo = ?;", arrayOf("10"))
//
//        while(cursor.moveToNext()) {
//            builder.append(cursor.getInt(0))
//            builder.append(cursor.getInt(1))
//            builder.append(cursor.getString(2))
//            builder.append(cursor.getString(3))
//            builder.append(cursor.getString(4))
//            builder.append(cursor.getString(5))
//            builder.append(cursor.getString(6))
//            builder.append(cursor.getString(7))
//            builder.append(cursor.getString(8))
//        }

        val player: MediaPlayer = MediaPlayer.create(this, R.raw.com_jpnf_bth001_v01)
        player.isLooping = false

        val buttonVoiceTest = findViewById<Button>(R.id.voice_test_button)
        buttonVoiceTest.setOnClickListener {
            player.start()
        }

        player.setOnCompletionListener {
            player.stop()
            player.reset()
        }
    }

    private fun callingVoice(id: Int) {
        val mp: MediaPlayer


//        mp.start()
        mp.setOnCompletionListener {
            MediaPlayer.OnCompletionListener {

            }
        }

    }

    override fun onEnterAnimationComplete() {
        super.onEnterAnimationComplete()
    }


}