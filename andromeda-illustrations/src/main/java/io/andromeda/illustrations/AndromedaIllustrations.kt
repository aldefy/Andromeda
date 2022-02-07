package io.andromeda.illustrations

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

@Suppress("unused")
public object AndromedaIllustrations {
    public val FriendsChatting: Painter
        @Composable
        get() = painterResource(R.drawable.andromeda_vector_friends_chatting)
    public val ManVibing: Painter
        @Composable
        get() = painterResource(R.drawable.andromeda_vector_man_vibing)
    public val WateringPlants: Painter
        @Composable
        get() = painterResource(R.drawable.andromeda_vector_watering_plants)
    public val Workspace: Painter
        @Composable
        get() = painterResource(R.drawable.andromeda_vector_workspace)
    public val WorkingPeople: Painter
        @Composable
        get() = painterResource(R.drawable.andromeda_working_people)
}
