package io.syhids.mgj18.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.ashley.systems.SortedIteratingSystem

abstract class DebugEntitySystem : EntitySystem() {
    final override fun update(deltaTime: Float) {
        val time = System.currentTimeMillis()
        onUpdate(deltaTime)
        val timeTaken = System.currentTimeMillis() - time
        printTime(timeTaken)
    }


    abstract fun onUpdate(deltaTime: Float)
}

private fun Any.printTime(timeTaken: Long) {
    if (timeTaken > 3)
        println("System ${this.javaClass.simpleName} took $timeTaken ms")
}

abstract class DebugSortedIteratingSystem(
    family: Family?,
    comparator: (e1: Entity, e2: Entity) -> Int
) : SortedIteratingSystem(family, comparator) {
    open override fun update(deltaTime: Float) {
        val time = System.currentTimeMillis()
        super.update(deltaTime)
        val timeTaken = System.currentTimeMillis() - time
        printTime(timeTaken)
    }
}

abstract class DebugIteratingSystem(family: Family?) : IteratingSystem(family) {
    open override fun update(deltaTime: Float) {
        val time = System.currentTimeMillis()
        super.update(deltaTime)
        val timeTaken = System.currentTimeMillis() - time
        printTime(timeTaken)
    }
}