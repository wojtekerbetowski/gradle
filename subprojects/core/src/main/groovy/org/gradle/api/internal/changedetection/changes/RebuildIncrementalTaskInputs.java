/*
 * Copyright 2013 the original author or authors.
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

package org.gradle.api.internal.changedetection.changes;

import org.gradle.api.Action;
import org.gradle.api.Task;
import org.gradle.api.tasks.incremental.InputFile;

import java.io.File;

public class RebuildIncrementalTaskInputs extends StatefulIncrementalTaskInputs {
    private final Task task;

    public RebuildIncrementalTaskInputs(Task task) {
        this.task = task;
    }

    public boolean isIncremental() {
        return false;
    }

    public void doOutOfDate(Action<? super InputFile> outOfDateAction) {
        for (File file : task.getInputs().getFiles()) {
            outOfDateAction.execute(new RebuildInputFile(file));
        }
    }

    public void doRemoved(Action<? super InputFile> removedAction) {
    }

    private static class RebuildInputFile implements InputFile {
        private final File file;

        private RebuildInputFile(File file) {
            this.file = file;
        }

        public File getFile() {
            return file;
        }

        public boolean isAdded() {
            return false;
        }

        public boolean isModified() {
            return false;
        }

        public boolean isRemoved() {
            return false;
        }
    }
}
