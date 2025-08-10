package com.github.deianvn.eazyplayer.state.scene

import com.github.deianvn.compose.director.core.Scene


sealed class MainScene : Scene {

    object LoadingData : MainScene()

}
