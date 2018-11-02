package be.ucll.dirkfalls.common

/**
 * @author goran on 29/10/2017.
 */
class SampleInfo(val clazz: Class<out SampleBase>) {
    val name: String = clazz.simpleName
}