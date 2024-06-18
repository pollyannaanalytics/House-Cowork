package com.polly.housecowork.ui.utils

sealed class DinosaurType {
    data object Egg: DinosaurType()
    data object EggOut: DinosaurType()
    data object Dinosaur: DinosaurType()
    data object DinosaurKing: DinosaurType()
}