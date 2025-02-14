@file:JsModule("@comunica/query-sparql")
package org.lib.ldests.lib.rdf

import kotlin.js.Promise

@JsName("QueryEngine")
external class ComunicaQueryEngine {

    @JsName("queryBindings")
    fun query(query: String, options: dynamic) : Promise<ComunicaBindingStream>

}

external class ComunicaBindingStream {

    fun destroy(error: Error?)

    fun on(event: String, callback: (value: dynamic) -> Unit): ComunicaBindingStream

    fun read(): ComunicaBinding?

    fun once(event: String, callback: (data: dynamic) -> Unit)

}

external class ComunicaBinding {

    fun get(name: String): N3Term?

    @JsName("toString")
    fun toPrettyString(): String

}
