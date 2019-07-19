# kotlin-json-lexer
Json lexer implemented by Kotlin

## example
run `lex()` as below
```
val rawJsonString: String = """
        {"width": 20, "height": "long", "depth":null, "color":["RED", "BLUE", "PINK"], "filled": true}
    """.trimIndent().filter { it != ' ' }
    lex(rawJsonString)
```

we can get the result of lex
```
[[ rawData->{ jsonType->LEFT_BRACKET ]]
[[ rawData->"width" data->width jsonType->STRING ]]
[[ rawData->: jsonType->COLON ]]
[[ rawData->20 jsonType->NUMBER ]]
[[ rawData->, jsonType->COMMA ]]
[[ rawData->"height" data->height jsonType->STRING ]]
[[ rawData->: jsonType->COLON ]]
[[ rawData->"long" data->long jsonType->STRING ]]
[[ rawData->, jsonType->COMMA ]]
[[ rawData->"depth" data->depth jsonType->STRING ]]
[[ rawData->: jsonType->COLON ]]
[[ rawData->null jsonType->NULL ]]
[[ rawData->, jsonType->COMMA ]]
[[ rawData->"color" data->color jsonType->STRING ]]
[[ rawData->: jsonType->COLON ]]
[[ rawData->[ jsonType->LEFT_ARRAY_BRACKET ]]
[[ rawData->"RED" data->RED jsonType->STRING ]]
[[ rawData->, jsonType->COMMA ]]
[[ rawData->"BLUE" data->BLUE jsonType->STRING ]]
[[ rawData->, jsonType->COMMA ]]
[[ rawData->"PINK" data->PINK jsonType->STRING ]]
[[ rawData->] jsonType->RIGHT_ARRAY_RACKET ]]
[[ rawData->, jsonType->COMMA ]]
[[ rawData->"filled" data->filled jsonType->STRING ]]
[[ rawData->: jsonType->COLON ]]
[[ rawData->true jsonType->BOOLEAN ]]
[[ rawData->} jsonType->RIGHT_RACKET ]]
```
