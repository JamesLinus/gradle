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

package org.gradle.api.internal.changedetection.rules;

import com.google.common.collect.ImmutableMap;
import org.gradle.api.internal.TaskInternal;
import org.gradle.api.internal.changedetection.state.DefaultValueSnapshot;
import org.gradle.api.internal.changedetection.state.TaskExecution;
import org.gradle.api.internal.changedetection.state.ValueSnapshot;
import org.gradle.util.ChangeListener;
import org.gradle.util.DiffUtil;

import java.util.List;
import java.util.Map;

class InputPropertiesTaskStateChanges extends SimpleTaskStateChanges {
    private final ImmutableMap<String, ValueSnapshot> properties;
    private final TaskExecution previousExecution;
    private final TaskInternal task;

    public InputPropertiesTaskStateChanges(TaskExecution previousExecution, TaskExecution currentExecution, TaskInternal task) {
        ImmutableMap.Builder<String, ValueSnapshot> builder = ImmutableMap.builder();
        for (Map.Entry<String, Object> entry : task.getInputs().getProperties().entrySet()) {
            builder.put(entry.getKey(), new DefaultValueSnapshot(entry.getValue()));
        }
        properties = builder.build();
        currentExecution.setInputProperties(properties);
        this.previousExecution = previousExecution;
        this.task = task;
    }

    @Override
    protected void addAllChanges(final List<TaskStateChange> changes) {
        DiffUtil.diff(properties, previousExecution.getInputProperties(), new ChangeListener<Map.Entry<String, ValueSnapshot>>() {
            public void added(Map.Entry<String, ValueSnapshot> element) {
                changes.add(new DescriptiveChange("Input property '%s' has been added for %s", element.getKey(), task));
            }

            public void removed(Map.Entry<String, ValueSnapshot> element) {
                changes.add(new DescriptiveChange("Input property '%s' has been removed for %s", element.getKey(), task));
            }

            public void changed(Map.Entry<String, ValueSnapshot> element) {
                changes.add(new DescriptiveChange("Value of input property '%s' has changed for %s", element.getKey(), task));
            }
        });
    }
}
