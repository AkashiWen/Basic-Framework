package com.akashi.common.file.r.annotation

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class DBField(
    val value: String
)
