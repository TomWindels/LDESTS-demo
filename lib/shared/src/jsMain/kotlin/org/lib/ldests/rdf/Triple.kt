package org.lib.ldests.rdf

import org.lib.ldests.lib.rdf.*

actual typealias Triple = N3Triple
actual typealias Term = N3Term
actual typealias NamedNodeTerm = N3NamedNode
actual typealias BlankNodeTerm = N3BlankNode
actual typealias LiteralTerm = N3Literal

actual fun String.asNamedNode() = createN3NamedNode(this)

actual fun String.asLiteral() = createN3Literal(this)

actual fun Int.asLiteral() = createN3Literal(this)

actual fun Long.asLiteral() = createN3Literal(this)

actual fun Float.asLiteral() = createN3Literal(this)

actual fun Double.asLiteral() = createN3Literal(this)

internal actual fun literal(content: String, type: NamedNodeTerm) = createN3Literal(content, type)
