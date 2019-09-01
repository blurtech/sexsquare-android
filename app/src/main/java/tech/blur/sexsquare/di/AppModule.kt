package tech.blur.sexsquare.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import tech.blur.sexsquare.ui.home.HomeViewModel

private var appModule = module {
    viewModel { HomeViewModel() }
}

val sexsquareApp = listOf(appModule)