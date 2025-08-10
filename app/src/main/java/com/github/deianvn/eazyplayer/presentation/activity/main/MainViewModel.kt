package com.github.deianvn.eazyplayer.presentation.activity.main

import androidx.lifecycle.viewModelScope
import com.github.deianvn.compose.director.core.EmptyDecor
import com.github.deianvn.compose.director.core.EmptyPlot
import com.github.deianvn.compose.director.core.Stage
import com.github.deianvn.compose.director.core.Status
import com.github.deianvn.compose.director.ui.StageViewModel
import com.github.deianvn.eazyplayer.state.scene.MainScene
import com.github.deianvn.eazyplayer.state.scene.MainScene.LoadingData
import kotlinx.coroutines.launch


class MainViewModel() : StageViewModel<MainScene, EmptyPlot, EmptyDecor>(
    Stage(
        status = Status.WORKING,
        scene = LoadingData,
        plot = EmptyPlot(),
        decor = EmptyDecor()
    )
) {

    init {

    }

    fun loadLibrary() {
        publish {
            it.next(
                isSequence = false,
                status = Status.WORKING
            )
        }

        viewModelScope.launch {

        }
    }

}
