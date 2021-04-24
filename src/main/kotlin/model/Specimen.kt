package model

/**
 * @param x x position expressed as offset from origin, in mm
 * @param y y position expressed as offset from origin, in mm
 * @param rotation rotation around desks center, in degrees
 */
data class Specimen(val x: Int, val y: Int, val rotation: Int)
