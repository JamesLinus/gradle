/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.api.internal.changedetection.resources;

import com.google.common.hash.HashCode;
import org.gradle.api.internal.changedetection.state.NormalizedFileSnapshotCollector;

public class DefaultNormalizedResource extends AbstractNormalizedResource {
    private final HashCode hash;

    public DefaultNormalizedResource(SnapshottableResource resource, NormalizedPath normalizedPath, HashCode hash) {
        super(resource, normalizedPath);
        this.hash = hash;
    }

    @Override
    protected HashCode getHashInternal(NormalizedFileSnapshotCollector collector) {
        return hash;
    }
}