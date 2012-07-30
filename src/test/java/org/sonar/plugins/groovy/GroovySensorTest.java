/*
 * Sonar Groovy Plugin
 * Copyright (C) 2010 SonarSource
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.plugins.groovy;

import org.junit.Test;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.resources.File;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.ProjectFileSystem;
import org.sonar.plugins.groovy.foundation.Groovy;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GroovySensorTest {

  private GroovySensor sensor = new GroovySensor(new Groovy());

  @Test
  public void test() {
    SensorContext context = mock(SensorContext.class);
    Project project = mock(Project.class);
    ProjectFileSystem pfs = mock(ProjectFileSystem.class);
    java.io.File sourceDir = new java.io.File("src/test/resources/org/sonar/plugins/groovy/gmetrics");
    List<java.io.File> sourceDirs = Arrays.asList(sourceDir);
    when(pfs.getSourceDirs()).thenReturn(sourceDirs);
    when(project.getFileSystem()).thenReturn(pfs);

    sensor.processDirectory(context, project, sourceDir);

    File sonarFile = File.fromIOFile(new java.io.File(sourceDir, "Greeting.groovy"), sourceDirs);
    verify(context).saveMeasure(sonarFile, CoreMetrics.FILES, 1.0);
    verify(context).saveMeasure(sonarFile, CoreMetrics.CLASSES, 2.0);
    verify(context).saveMeasure(sonarFile, CoreMetrics.FUNCTIONS, 1.0);
    verify(context).saveMeasure(sonarFile, CoreMetrics.COMPLEXITY, 2.0);
  }

}