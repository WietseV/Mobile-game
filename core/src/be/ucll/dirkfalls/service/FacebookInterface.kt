package be.ucll.dirkfalls.service

import com.badlogic.gdx.Application

interface FacebookInterface {
    val app: Application
    fun share(score: Int)
}