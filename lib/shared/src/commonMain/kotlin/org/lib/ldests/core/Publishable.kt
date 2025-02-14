package org.lib.ldests.core

import org.lib.ldests.rdf.RDFBuilder
import org.lib.ldests.util.warn

abstract class Publishable(
    // own, relative, path after the host (e.g. `stream/`, `stream/fragment/resource`
    override val name: String
): RDFBuilder.Element {

    internal var buffer: PublishBuffer? = null
        private set

    enum class PublisherAttachResult {
        SUCCESS, FAILURE, NEW
    }

    internal open suspend fun onPublisherAttached(publisher: Publisher): PublisherAttachResult {
        // no compat checking by default
        return PublisherAttachResult.SUCCESS
    }

    internal suspend fun onCreate(publisher: Publisher) {
        publisher.publish(name) { onCreate(publisher) }
    }

    protected open fun RDFBuilder.onCreate(publisher: Publisher) {
        /* nothing to do by default */
    }

    // FIXME: this path MAY BE ignored in favour of `name` in some Builder contexts(?)! Can lead to bugs
    //  when using the `uri` generated from this context when the path is modified (e.g. .meta changes to
    //  a non-publishable but Builder.Element type)
    protected fun publish(path: String = name, block: RDFBuilder.() -> Unit) {
        buffer
            ?.emit(path = path, data = block)
            ?: run {
                warn("A publishable type experienced spillage (no buffer attached)!")
            }
    }

    fun attach(buffer: PublishBuffer) {
        this.buffer = buffer
        buffer.onAttached(this)
    }

}