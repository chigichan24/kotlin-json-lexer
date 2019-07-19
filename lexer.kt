fun string(str: String): Boolean {
    if (str.first() != '"') return false
    if (str.last() != '"') return false
    if (str.contains(":")) return false
    val body = str.substring(1, str.length - 1)
    printType(str, body, JSONType.STRING)
    return true
}

fun number(str: String): Boolean {
    str.forEach { if (it !in '0'..'9') return false }
    val number = str.toInt()
    printType(str, type = JSONType.NUMBER)
    return true
}

fun boolean(str: String): Boolean {
    if (!(str == "true" || str == "false")) return false
    printType(str, type = JSONType.BOOLEAN)
    return true
}

fun jsonNull(str: String): Boolean {
    if (str != "null") return false
    printType(str, type = JSONType.NULL)
    return true
}

fun elements(str: String) {
    var flg = false
    var count = 0
    for (i in str.length - 1 downTo 0) {
        if (str[i] == '}' || str[i] == ']') ++count
        if (str[i] == '{' || str[i] == '[') --count
        if (str[i] == ',' && count == 0) {
            flg = true
            val head = str.substring(0, i)
            elements(head)
            printType(",", type = JSONType.COMMA)
            val tail = str.substring(i + 1, str.length)
            value(tail)
            break
        }
    }
    if (!flg) {
        value(str)
    }
}

fun array(str: String): Boolean {
    if (str.length < 2) return false
    val head = str.first()
    val tail = str.last()
    if (head != '[') return false
    if (tail != ']') return false
    val elements = str.substring(1, str.length - 1)
    printType("[", type = JSONType.LEFT_ARRAY_BRACKET)
    if (elements.isNotEmpty()) elements(elements)
    printType("]", type = JSONType.RIGHT_ARRAY_RACKET)
    return true
}

fun value(str: String): Boolean {
    return string(str) or number(str) or boolean(str) or lex(str) or jsonNull(str) or array(str)
}

fun splitMap(str: String): Boolean {
    var flg = false
    str.forEachIndexed { index, c ->
        if (c == ':') {
            val head = str.substring(0, index)
            val tail = str.substring(index + 1, str.length)
            if (string(head)) {
                printType(":", type = JSONType.COLON)
                if (value(tail)) {
                    flg = true
                }
            }
        }
    }
    return flg
}

fun members(str: String) {
    var flg = false
    var count = 0
    for (i in str.length - 1 downTo 0) {
        if (str[i] == '}' || str[i] == ']') ++count
        if (str[i] == '{' || str[i] == '[') --count
        if (str[i] == ',' && count == 0) {
            val head = str.substring(0, i)
            members(head)
            printType(",", type = JSONType.COMMA)
            val tail = str.substring(i + 1, str.length)
            if (splitMap(tail)) {
                flg = true
                break
            }
        }
    }
    if (!flg) {
        splitMap(str)
    }
}

fun printType(rawData: String, data: String? = null, type: JSONType) {
    if (data == null) {
        println("[[ rawData->$rawData jsonType->${type.name} ]]")
    } else {
        println("[[ rawData->$rawData data->$data jsonType->${type.name} ]]")
    }

}

fun lex(str: String): Boolean {
    if (str.length < 2) return false
    val head = str.first()
    val tail = str.last()
    if (head != '{') return false
    if (tail != '}') return false
    val members = str.substring(1, str.length - 1)
    printType("{", type = JSONType.LEFT_BRACKET)
    if (members.isNotEmpty()) members(members)
    printType("}", type = JSONType.RIGHT_RACKET)
    return true
}

enum class JSONType {
    LEFT_BRACKET,
    RIGHT_RACKET,
    LEFT_ARRAY_BRACKET,
    RIGHT_ARRAY_RACKET,
    COMMA,
    COLON,
    STRING,
    NUMBER,
    OBJECT,
    ARRAY,
    BOOLEAN,
    NULL,
}

fun main(args: Array<String>) {
    val rawJsonString: String = """
        {"width": 20, "height": "long", "depth":null, "color":["RED", "BLUE", "PINK"], "filled": true}
    """.trimIndent().filter { it != ' ' }
    lex(rawJsonString)
}
