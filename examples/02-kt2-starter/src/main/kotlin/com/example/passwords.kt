package com.example

import at.favre.lib.crypto.bcrypt.BCrypt

fun hashPassword(raw: String): String =
    BCrypt.withDefaults().hashToString(12, raw.toCharArray())

fun checkPassword(raw: String, hash: String): Boolean =
    BCrypt.verifyer().verify(raw.toCharArray(), hash).verified
