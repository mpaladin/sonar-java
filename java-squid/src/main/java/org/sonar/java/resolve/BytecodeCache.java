/*
 * SonarQube Java
 * Copyright (C) 2012 SonarSource
 * sonarqube@googlegroups.com
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
package org.sonar.java.resolve;

import com.google.common.base.Preconditions;
import org.objectweb.asm.ClassReader;

import javax.annotation.CheckForNull;
import java.util.HashMap;
import java.util.Map;

public class BytecodeCache {

  private static final String CACHEABLE_CLASSES = "java.";

  /**
   * Indexed by flat name.
   */
  private final Map<String, ClassReader> classReaders = new HashMap<>();
  private final Map<String, JavaSymbol.TypeJavaSymbol> classes = new HashMap<>();
  private final Map<String, JavaSymbol.PackageJavaSymbol> packages = new HashMap<>();

  Symbols symbols;

  @CheckForNull
  protected JavaSymbol.TypeJavaSymbol getClass(String name) {
    return classes.get(name);
  }

  protected void putClass(String name, JavaSymbol.TypeJavaSymbol typeJavaSymbol) {
    if (name.startsWith(CACHEABLE_CLASSES)) {
      Preconditions.checkState(!classes.containsKey(name));
      classes.put(name, typeJavaSymbol);
    }
  }

  @CheckForNull
  protected JavaSymbol.PackageJavaSymbol getPackage(String name) {
    return packages.get(name);
  }

  protected void putPackage(String name, JavaSymbol.PackageJavaSymbol packageJavaSymbol) {
    if ("java".equals(name) || name.startsWith(CACHEABLE_CLASSES)) {
      Preconditions.checkState(!packages.containsKey(name));
      packages.put(name, packageJavaSymbol);
    }
  }

  @CheckForNull
  protected ClassReader getClassReader(String name) {
    return classReaders.get(name);
  }

  protected void putClassReader(String name, ClassReader classReader) {
    Preconditions.checkState(!classReaders.containsKey(name));
    classReaders.put(name, classReader);
  }

}
