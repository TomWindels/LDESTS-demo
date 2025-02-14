package org.lib.ldests.rdf

class Query(
    val sparql: String,
    // FIXME: support for more than just `SELECT ? WHERE` might be handy, also doesn't support more complex queries
    val variables: Set<String> = if (sparql.startsWith("SELECT * WHERE")) {
        // everything matching the variable pattern is kept
        Regex(pattern = "\\?([a-zA-Z0-9]+)")
            .findAll(sparql)
            .map { it.groups[1]!!.value }
            .toSet()
    } else {
        // only keeping the ones mentioned in "SELECT `?xyz` WHERE"
        Regex(pattern = "SELECT\\s+((?:\\s*\\?[a-zA-Z0-9]+)+)\\s+WHERE")
            .find(sparql)!!
            .groups[1]!!
            .value
            .let {
                Regex(pattern = "\\?([a-zA-Z0-9]+)")
                    .findAll(it)
                    .map { it.groupValues[1] }
            }
            .toSet()
    }
)

// not nested, as it is a typealias in the JS target
expect class Binding {
    fun toPrettyString(): String
}

// externally declared, same reason as the one above
expect operator fun Binding.get(variable: String): Term?

/**
 * Querying the provider (directly); most efficient way of obtaining a stream of bindings instantly. Can return
 *  null if the provider is either invalid (e.g. existing but non-rdf file), or contains no resources (remote)
 */
expect suspend fun TripleProvider.query(query: Query, callback: (Binding) -> Unit)

