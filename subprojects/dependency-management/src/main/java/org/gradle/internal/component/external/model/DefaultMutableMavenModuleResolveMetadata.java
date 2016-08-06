/*
 * Copyright 2016 the original author or authors.
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

package org.gradle.internal.component.external.model;

import org.gradle.api.Nullable;
import org.gradle.api.artifacts.component.ModuleComponentIdentifier;
import org.gradle.internal.component.external.descriptor.ModuleDescriptorState;
import org.gradle.internal.component.external.descriptor.MutableModuleDescriptorState;
import org.gradle.internal.component.model.IvyArtifactName;

import java.util.Set;

import static org.gradle.internal.component.external.model.DefaultMavenModuleResolveMetadata.JAR_PACKAGINGS;
import static org.gradle.internal.component.external.model.DefaultMavenModuleResolveMetadata.POM_PACKAGING;

public class DefaultMutableMavenModuleResolveMetadata extends AbstractMutableModuleComponentResolveMetadata implements MutableMavenModuleResolveMetadata {
    private final String packaging;
    private final boolean relocated;
    private String snapshotTimestamp;

    public DefaultMutableMavenModuleResolveMetadata(ModuleComponentIdentifier componentIdentifier, Set<IvyArtifactName> artifacts) {
        this(componentIdentifier, MutableModuleDescriptorState.createModuleDescriptor(componentIdentifier, artifacts), "jar", false);
    }

    public DefaultMutableMavenModuleResolveMetadata(ModuleDescriptorState moduleDescriptor, String packaging, boolean relocated) {
        this(moduleDescriptor.getComponentIdentifier(), moduleDescriptor, packaging, relocated);
    }

    public DefaultMutableMavenModuleResolveMetadata(ModuleComponentIdentifier componentIdentifier, ModuleDescriptorState descriptor, String packaging, boolean relocated) {
        super(componentIdentifier, descriptor);
        this.packaging = packaging;
        this.relocated = relocated;
    }

    DefaultMutableMavenModuleResolveMetadata(MavenModuleResolveMetadata metadata) {
        super(metadata);
        this.packaging = metadata.getPackaging();
        this.relocated = metadata.isRelocated();
        this.snapshotTimestamp = metadata.getSnapshotTimestamp();
    }

    @Override
    public MavenModuleResolveMetadata asImmutable() {
        return new DefaultMavenModuleResolveMetadata(this);
    }

    @Nullable
    @Override
    public String getSnapshotTimestamp() {
        return snapshotTimestamp;
    }

    @Override
    public void setSnapshotTimestamp(@Nullable String snapshotTimestamp) {
        this.snapshotTimestamp = snapshotTimestamp;
    }

    @Override
    public boolean isRelocated() {
        return relocated;
    }

    @Override
    public String getPackaging() {
        return packaging;
    }

    public boolean isPomPackaging() {
        return POM_PACKAGING.equals(packaging);
    }

    public boolean isKnownJarPackaging() {
        return JAR_PACKAGINGS.contains(packaging);
    }

}
