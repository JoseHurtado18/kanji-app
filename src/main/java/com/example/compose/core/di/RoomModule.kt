package com.example.compose.core.di

import android.content.Context
import androidx.room.Room
import com.example.compose.core.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.compose.data.dao.WordLearningStateDao

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val DATABASE_NAME = "database"

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            // Create word_table
            db.execSQL("""
                CREATE TABLE IF NOT EXISTS `word_table` (
                    `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    `kanji_word` TEXT NOT NULL,
                    `reading` TEXT NOT NULL,
                    `meaning` TEXT NOT NULL,
                    `sentence` TEXT,
                    `notes` TEXT
                )
            """.trimIndent())
            db.execSQL("CREATE INDEX IF NOT EXISTS `index_word_table_kanji_word` ON `word_table` (`kanji_word`)")

            // Create kanji_word_cross_ref
            db.execSQL("""
                CREATE TABLE IF NOT EXISTS `kanji_word_cross_ref` (
                    `kanjiId` INTEGER NOT NULL, 
                    `wordId` INTEGER NOT NULL, 
                    PRIMARY KEY(`kanjiId`, `wordId`),
                    FOREIGN KEY(`kanjiId`) REFERENCES `kanji_table`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE,
                    FOREIGN KEY(`wordId`) REFERENCES `word_table`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE
                )
            """.trimIndent())
            db.execSQL("CREATE INDEX IF NOT EXISTS `index_kanji_word_cross_ref_wordId` ON `kanji_word_cross_ref` (`wordId`)")

            // Create word_learning_state
            db.execSQL("""
                CREATE TABLE IF NOT EXISTS `word_learning_state` (
                    `wordId` INTEGER NOT NULL,
                    `status` TEXT NOT NULL,
                    `repetitions` INTEGER NOT NULL,
                    `intervalDays` INTEGER NOT NULL,
                    `easeFactor` REAL NOT NULL,
                    `lastReviewedAt` INTEGER,
                    `nextReviewAt` INTEGER,
                    PRIMARY KEY(`wordId`),
                    FOREIGN KEY(`wordId`) REFERENCES `word_table`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE
                )
            """.trimIndent())
            db.execSQL("CREATE INDEX IF NOT EXISTS `index_word_learning_state_nextReviewAt` ON `word_learning_state` (`nextReviewAt`)")

            // Migrate data from example_word_table to word_table & relationships
            // We'll migrate only if there's data and word_table is empty
            db.execSQL("""
                INSERT INTO `word_table` (`kanji_word`, `reading`, `meaning`)
                SELECT `word`, `reading`, `meaningEs` 
                FROM `example_word_table`
                WHERE `word` NOT IN (SELECT `kanji_word` FROM `word_table`)
            """.trimIndent())

            // Create relations
            db.execSQL("""
                INSERT INTO `kanji_word_cross_ref` (`kanjiId`, `wordId`)
                SELECT e.`kanjiId`, w.`id` 
                FROM `example_word_table` e
                JOIN `word_table` w ON e.`word` = w.`kanji_word`
            """.trimIndent())

            // Initialize word_learning_state for existing words
            db.execSQL("""
                INSERT INTO `word_learning_state` (`wordId`, `status`, `repetitions`, `intervalDays`, `easeFactor`, `lastReviewedAt`, `nextReviewAt`)
                SELECT `id`, 'NEW', 0, 0, 2.5, NULL, NULL 
                FROM `word_table`
                WHERE `id` NOT IN (SELECT `wordId` FROM `word_learning_state`)
            """.trimIndent())
        }
    }

    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE `word_table` ADD COLUMN `type_word` TEXT")
        }
    }

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()

    @Singleton
    @Provides
    fun provideKanjiDAO(db: AppDatabase) = db.getKanjiDAO()

    @Singleton
    @Provides
    fun provideExampleWordDAO(db: AppDatabase) = db.getExampleWordDAO()

    @Singleton
    @Provides
    fun provideWordDAO(db: AppDatabase) = db.getWordDAO()

    @Singleton
    @Provides
    fun provideWordLearningStateDao(db: AppDatabase): WordLearningStateDao = db.getWordLearningStateDao()
}