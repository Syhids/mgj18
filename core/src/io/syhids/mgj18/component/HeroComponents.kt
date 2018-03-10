package io.syhids.mgj18.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity

class SoulComponent(var state: State = State.NoSoul) : Component {
    sealed class State {
        object NoSoul : State()
        object SelectingSoul : State()
        data class ControllingSoul(var entity: Entity) : State()
    }
}