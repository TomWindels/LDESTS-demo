@file:JsModule("stream/promises")
package org.lib.ldests.lib.node

import kotlin.js.Promise

internal external fun finished(
    stream: NodeStream,
    options: dynamic = definedExternally
): Promise<Unit>
