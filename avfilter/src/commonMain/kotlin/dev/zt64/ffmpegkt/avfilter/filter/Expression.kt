package dev.zt64.ffmpegkt.avfilter.filter

public typealias Expression = String

public fun Expression(value: String): Expression = value

public operator fun Expression.plus(other: Expression): Expression = "$this+$other"