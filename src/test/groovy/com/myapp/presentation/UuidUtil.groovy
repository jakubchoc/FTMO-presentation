package com.myapp.presentation

static UUID toUUID(String str) {
    return UUID.fromString(str)
}

static UUID toUUID(int num) {
    assert num >= 0

    return createUuidFromNumber(num.toLong())
}

private static UUID createUuidFromNumber(long number) {
    def uuidString = String.format("00000000-000-0000-0000-%012d", number)
    uuidString = replaceRange(uuidString, 9, 12, "000")
    return UUID.fromString(uuidString)
}

private static String replaceRange(String str, int start, int end, String replacement) {
    return str.substring(0, start) + replacement + str.substring(end)
}

