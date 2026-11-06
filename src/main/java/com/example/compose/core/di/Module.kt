package com.example.compose.core.di

import android.content.Context
import com.example.compose.data.dao.ExampleWordDAO
import com.example.compose.data.dao.KanjiDAO
import com.example.compose.data.dao.WordDAO
import com.example.compose.data.dao.WordLearningStateDao
import com.example.compose.data.repository.DictionaryRepositoryImpl
import com.example.compose.data.repository.KanjiRepositoryImpl
import com.example.compose.data.repository.KanjiStrokeRepositoryImpl
import com.example.compose.data.repository.WordRepositoryImpl
import com.example.compose.domain.repository.DictionaryRepository
import com.example.compose.domain.repository.KanjiRepository
import com.example.compose.domain.repository.KanjiStrokeRepository
import com.example.compose.domain.repository.WordRepository
import com.example.compose.domain.usecase.KanjiUseCases
import com.example.compose.domain.usecase.WordUseCases
import com.example.compose.domain.usecase.kanji.AddExampleWordUseCase
import com.example.compose.domain.usecase.kanji.AddKanjiUseCase
import com.example.compose.domain.usecase.kanji.DeleteExampleWordUseCase
import com.example.compose.domain.usecase.kanji.DeleteKanjiUseCase
import com.example.compose.domain.usecase.kanji.GetAllKanjisUseCase
import com.example.compose.domain.usecase.kanji.GetKanjiUseCase
import com.example.compose.domain.usecase.kanji.GetStrokesKanjiUseCase
import com.example.compose.domain.usecase.kanji.ObserveKanjiCountUseCase
import com.example.compose.domain.usecase.kanji.ObserveWordsForKanjiUseCase
import com.example.compose.domain.usecase.kanji.UpdateKanjiUseCase
import com.example.compose.domain.usecase.word.AddWordUseCase
import com.example.compose.domain.usecase.word.GetAllKanjisForWordUseCase
import com.example.compose.domain.usecase.word.GetAllWordsUseCase
import com.example.compose.domain.usecase.word.GetWordUseCase
import com.example.compose.domain.usecase.word.UpdateWordUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KanjiModule {

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class RepositoryModule {
        @Binds
        abstract fun bindDictionaryRepository(
            impl: DictionaryRepositoryImpl
        ): DictionaryRepository
    }
    @Provides
    @Singleton
    fun provideKanjiRepository(
        kanjiDao: KanjiDAO,
        exampleWordDao: ExampleWordDAO
    ): KanjiRepository {
        return KanjiRepositoryImpl(kanjiDao, exampleWordDao)
    }

    @Provides
    @Singleton
    fun provideWordRepository(
        wordDao: WordDAO,
        learningStateDao: WordLearningStateDao
    ):  WordRepository{
        return WordRepositoryImpl(wordDao, learningStateDao)
    }

    @Provides
    @Singleton
    fun provideKanjiStrokeRepository(
        @ApplicationContext context: Context
    ): KanjiStrokeRepository{
        return KanjiStrokeRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideKanjiUseCases(
        repository: KanjiRepository,
        strokesrepository: KanjiStrokeRepository
    ): KanjiUseCases {
        return KanjiUseCases(
            addKanji = AddKanjiUseCase(repository),
            getKanji = GetKanjiUseCase(repository),
            getAllKanjis = GetAllKanjisUseCase(repository),
            updateKanji = UpdateKanjiUseCase(repository),
            deleteKanji = DeleteKanjiUseCase(repository),
            addExampleWord = AddExampleWordUseCase(repository),
            deleteExampleWord = DeleteExampleWordUseCase(repository),
            getStrokesKanji = GetStrokesKanjiUseCase(strokesrepository),
            observeKanjiCount = ObserveKanjiCountUseCase(repository),
            observeWordsForKanji = ObserveWordsForKanjiUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideWordUseCases(
        repository: WordRepository
    ): WordUseCases {
        return WordUseCases(
            addWordUseCase = AddWordUseCase(repository),
            getAllWordsUseCase = GetAllWordsUseCase(repository),
            getWordUseCase = GetWordUseCase(repository),
            getAllKanjisForWordUseCase = GetAllKanjisForWordUseCase(repository),
            updateWordUseCase = UpdateWordUseCase(repository)
        )
    }
}
