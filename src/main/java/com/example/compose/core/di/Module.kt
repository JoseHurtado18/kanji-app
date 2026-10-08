package com.example.compose.core.di

import android.content.Context
import com.example.compose.kanji.data.dao.ExampleWordDAO
import com.example.compose.kanji.data.dao.KanjiDAO
import com.example.compose.kanji.data.repository.KanjiRepositoryImpl
import com.example.compose.kanji.data.repository.KanjiStrokeRepositoryImpl
import com.example.compose.kanji.domain.repository.KanjiRepository
import com.example.compose.kanji.domain.repository.KanjiStrokeRepository
import com.example.compose.kanji.domain.usecase.KanjiUseCases
import com.example.compose.kanji.domain.usecase.kanji.AddExampleWordUseCase
import com.example.compose.kanji.domain.usecase.kanji.AddKanjiUseCase
import com.example.compose.kanji.domain.usecase.kanji.DeleteExampleWordUseCase
import com.example.compose.kanji.domain.usecase.kanji.DeleteKanjiUseCase
import com.example.compose.kanji.domain.usecase.kanji.GetAllKanjisUseCase
import com.example.compose.kanji.domain.usecase.kanji.GetKanjiUseCase
import com.example.compose.kanji.domain.usecase.kanji.GetStrokesKanjiUseCase
import com.example.compose.kanji.domain.usecase.kanji.UpdateKanjiUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KanjiModule {

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
            getStrokesKanji = GetStrokesKanjiUseCase(strokesrepository)
        )
    }
}
