package tech.blur.sexsquare.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import tech.blur.sexsquare.ui.map.MapViewModel

private var appModule = module {
    viewModel { MapViewModel() }
}

val sexsquareApp = listOf(appModule)