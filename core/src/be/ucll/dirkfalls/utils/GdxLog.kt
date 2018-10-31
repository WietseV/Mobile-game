package be.ucll.dirkfalls.utils

import com.badlogic.gdx.utils.Logger
// gaat gebruikt worden om onze entities te volgen en te gebruiken.
//fun <T: Any> logger(clazz: Class<T>): Logger = Logger(clazz.simpleName, Logger.DEBUG)

inline fun <reified T: Any> logger(): Logger = Logger(T::class.java.simpleName, Logger.DEBUG)