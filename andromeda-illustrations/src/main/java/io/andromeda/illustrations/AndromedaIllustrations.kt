package io.andromeda.illustrations

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

interface IllustrationResource {
    @Composable
    fun resource(): Painter
    fun resourceName(): String
}

@Suppress("unused")
enum class AndromedaIllustrations : IllustrationResource {
    FriendsChatting {
        @Composable
        override fun resource() = painterResource(R.drawable.andromeda_vector_friends_chatting)
        override fun resourceName(): String = "Friends Chatting"
    },
    ManVibing {
        @Composable
        override fun resource() = painterResource(R.drawable.andromeda_vector_man_vibing)
        override fun resourceName(): String = "Man Vibing"
    },
    WateringPlants {
        @Composable
        override fun resource() = painterResource(R.drawable.andromeda_vector_watering_plants)
        override fun resourceName(): String = "Watering Plants"
    },
    Workspace {
        @Composable
        override fun resource() = painterResource(R.drawable.andromeda_vector_workspace)
        override fun resourceName(): String = "Workspace"
    },
    WorkingPeople {
        @Composable
        override fun resource() = painterResource(R.drawable.andromeda_working_people)
        override fun resourceName(): String = "Working People"
    };
}
