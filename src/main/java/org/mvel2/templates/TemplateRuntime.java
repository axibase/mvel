/**
 * MVEL 2.0
 * Copyright (C) 2007 The Codehaus
 * Mike Brock, Dhanji Prasanna, John Graham, Mark Proctor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mvel2.templates;

import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.integration.impl.ImmutableDefaultFactory;
import org.mvel2.integration.impl.MapVariableResolverFactory;
import org.mvel2.templates.res.Node;
import org.mvel2.templates.util.TemplateOutputStream;
import org.mvel2.templates.util.TemplateTools;
import org.mvel2.templates.util.io.StandardOutputStream;
import org.mvel2.templates.util.io.StringAppenderStream;
import org.mvel2.templates.util.io.StringBuilderStream;
import org.mvel2.util.ExecutionStack;
import org.mvel2.util.StringAppender;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import static org.mvel2.templates.TemplateCompiler.compileTemplate;

/**
 * This is the root of the template runtime, and contains various utility methods for executing templates.
 */
public class TemplateRuntime {
  private char[] template;
  private TemplateRegistry namedTemplateRegistry;
  private Node rootNode;
  private String baseDir;
  private ExecutionStack relPath;
  private ExecutableValuePostProcessor postProcessor = ExecutableValuePostProcessor.DEFAULT;


  public TemplateRuntime(char[] template, TemplateRegistry namedTemplateRegistry, Node rootNode, String baseDir) {
    this.template = template;
    this.namedTemplateRegistry = namedTemplateRegistry;
    this.rootNode = rootNode;
    this.baseDir = baseDir;
  }

  public static String eval(File file, Object ctx, VariableResolverFactory vars, TemplateRegistry registry) {
    return execute(compileTemplate(TemplateTools.readInFile(file)), ctx, vars, registry);
  }

  public static String eval(InputStream instream) {
    return eval(instream, null, new ImmutableDefaultFactory(), null);
  }

  public static String eval(InputStream instream, Object ctx) {
    return eval(instream, ctx, new ImmutableDefaultFactory(), null);
  }

  public static String eval(InputStream instream, Object ctx, VariableResolverFactory vars) {
    return eval(instream, ctx, vars);
  }

  public static String eval(InputStream instream, Object ctx, Map vars) {
    return eval(instream, ctx, new MapVariableResolverFactory(vars), null);
  }

  public static String eval(InputStream instream, Object ctx, Map vars, TemplateRegistry registry) {
    return execute(compileTemplate(TemplateTools.readStream(instream)), ctx, new MapVariableResolverFactory(vars), registry);
  }

  public static String eval(InputStream instream, Object ctx, VariableResolverFactory vars, TemplateRegistry registry) {
    return execute(compileTemplate(TemplateTools.readStream(instream)), ctx, vars, registry);
  }

  public static void eval(InputStream instream, Object ctx, VariableResolverFactory vars, TemplateRegistry register, OutputStream stream) {
    execute(compileTemplate(TemplateTools.readStream(instream)), ctx, vars, register, stream);
  }

  public static String eval(String template, Map vars) {
    return execute(compileTemplate(template), null, new MapVariableResolverFactory(vars));
  }

  public static void eval(String template, Map vars, OutputStream stream) {
    execute(compileTemplate(template), null, new MapVariableResolverFactory(vars), null, stream);
  }

  public static String eval(String template, Object ctx) {
    return execute(compileTemplate(template), ctx);
  }

  public static String eval(String template, Object ctx, Map vars) {
    return execute(compileTemplate(template), ctx, new MapVariableResolverFactory(vars));
  }

  public static void eval(String template, Object ctx, Map vars, OutputStream stream) {
    execute(compileTemplate(template), ctx, new MapVariableResolverFactory(vars), null, stream);
  }

  public static String eval(String template, Object ctx, VariableResolverFactory vars) {
    return execute(compileTemplate(template), ctx, vars);
  }

  public static void eval(String template, Object ctx, VariableResolverFactory vars, TemplateOutputStream stream) {
    execute(compileTemplate(template), ctx, vars, null, stream);
  }

  public static void eval(String template, Object ctx, VariableResolverFactory vars, OutputStream stream) {
    execute(compileTemplate(template), ctx, vars, null, stream);
  }

  public static String eval(String template, Map vars, TemplateRegistry registry) {
    return execute(compileTemplate(template), null, new MapVariableResolverFactory(vars), registry);
  }

  public static void eval(String template, Map vars, TemplateRegistry registry, TemplateOutputStream stream) {
    execute(compileTemplate(template), null, new MapVariableResolverFactory(vars), registry, stream);
  }

  public static void eval(String template, Map vars, TemplateRegistry registry, OutputStream stream) {
    execute(compileTemplate(template), null, new MapVariableResolverFactory(vars), registry, stream);
  }

  public static String eval(String template, Object ctx, Map vars, TemplateRegistry registry) {
    return execute(compileTemplate(template), ctx, new MapVariableResolverFactory(vars), registry);
  }

  public static void eval(String template, Object ctx, Map vars, TemplateRegistry registry, OutputStream stream) {
    execute(compileTemplate(template), ctx, new MapVariableResolverFactory(vars), registry, stream);
  }

  public static String eval(String template, Object ctx, VariableResolverFactory vars, TemplateRegistry registry) {
    return execute(compileTemplate(template), ctx, vars, registry);
  }

  public static void eval(String template, Object ctx, VariableResolverFactory vars, TemplateRegistry registry, OutputStream stream) {
    execute(compileTemplate(template), ctx, vars, registry, stream);
  }

  public static void eval(String template, Object ctx, VariableResolverFactory vars, TemplateRegistry registry, TemplateOutputStream stream) {
    execute(compileTemplate(template), ctx, vars, registry, stream);
  }

  public static String execute(CompiledTemplate compiled) {
    return execute(compiled.getRoot(), compiled.getTemplate(), new StringAppender(), null, new ImmutableDefaultFactory(), null);
  }

  public static void execute(CompiledTemplate compiled, OutputStream stream) {
    execute(compiled.getRoot(), compiled.getTemplate(), new StandardOutputStream(stream), null, new ImmutableDefaultFactory(), null);
  }

  public static String execute(CompiledTemplate compiled, Object context) {
    return execute(compiled.getRoot(), compiled.getTemplate(), new StringAppender(), context, new ImmutableDefaultFactory(), null);
  }

  public static void execute(CompiledTemplate compiled, Object context, OutputStream stream) {
    execute(compiled.getRoot(), compiled.getTemplate(), new StandardOutputStream(stream), context, new ImmutableDefaultFactory(), null);
  }

  public static String execute(CompiledTemplate compiled, Map vars) {
    return execute(compiled.getRoot(), compiled.getTemplate(), new StringBuilder(), null, new MapVariableResolverFactory(vars), null);
  }

  public static void execute(CompiledTemplate compiled, Map vars, OutputStream stream) {
    execute(compiled.getRoot(), compiled.getTemplate(), new StandardOutputStream(stream), null, new MapVariableResolverFactory(vars), null);
  }

  public static String execute(CompiledTemplate compiled, Object context, Map vars) {
    return execute(compiled.getRoot(), compiled.getTemplate(), new StringBuilder(), context, new MapVariableResolverFactory(vars), null);
  }

  public static void execute(CompiledTemplate compiled, Object context, Map vars, OutputStream stream) {
    execute(compiled.getRoot(), compiled.getTemplate(), new StandardOutputStream(stream), context, new MapVariableResolverFactory(vars), null);
  }

  public static String execute(CompiledTemplate compiled, Object context, TemplateRegistry registry) {
    return execute(compiled.getRoot(), compiled.getTemplate(), new StringBuilder(), context, null, registry);
  }

  public static void execute(CompiledTemplate compiled, Object context, TemplateRegistry registry, OutputStream stream) {
    execute(compiled.getRoot(), compiled.getTemplate(), new StandardOutputStream(stream), context, null, registry);
  }

  public static String execute(CompiledTemplate compiled, Object context, Map vars, TemplateRegistry registry) {
    return execute(compiled.getRoot(), compiled.getTemplate(), new StringBuilder(), context, new MapVariableResolverFactory(vars), registry);
  }

  public static void execute(CompiledTemplate compiled, Object context, Map vars, TemplateRegistry registry, OutputStream stream) {
    execute(compiled.getRoot(), compiled.getTemplate(), new StandardOutputStream(stream), context, new MapVariableResolverFactory(vars), registry);
  }

  public static String execute(CompiledTemplate compiled, Object context, VariableResolverFactory factory) {
    return execute(compiled.getRoot(), compiled.getTemplate(), new StringBuilder(), context, factory, null);
  }

  public static String execute(CompiledTemplate compiled, Object context, VariableResolverFactory factory, TemplateRegistry registry) {
    return execute(compiled.getRoot(), compiled.getTemplate(), new StringBuilder(), context, factory, registry);
  }

  public static String execute(CompiledTemplate compiled, Object context, VariableResolverFactory factory, String baseDir) {
    return execute(compiled.getRoot(), compiled.getTemplate(), new StringBuilder(), context, factory, null, baseDir);
  }

  public static String execute(CompiledTemplate compiled, Object context, VariableResolverFactory factory, TemplateRegistry registry, String baseDir) {
    return execute(compiled.getRoot(), compiled.getTemplate(), new StringBuilder(), context, factory, registry, baseDir);
  }

  public static void execute(CompiledTemplate compiled, Object context, VariableResolverFactory factory, OutputStream stream) {
    execute(compiled.getRoot(), compiled.getTemplate(), new StandardOutputStream(stream), context, factory, null);
  }

  public static void execute(CompiledTemplate compiled, Object context, VariableResolverFactory factory, OutputStream stream, String baseDir) {
    execute(compiled.getRoot(), compiled.getTemplate(), new StandardOutputStream(stream), context, factory, null, baseDir);
  }

  public static String execute(CompiledTemplate compiled, Object context, VariableResolverFactory factory, TemplateRegistry registry, OutputStream stream) {
    return execute(compiled.getRoot(), compiled.getTemplate(), new StandardOutputStream(stream), context, factory, registry);
  }


  public static String execute(CompiledTemplate compiled, Object context, VariableResolverFactory factory, TemplateRegistry registry, TemplateOutputStream stream) {
    return execute(compiled.getRoot(), compiled.getTemplate(), stream, context, factory, registry);
  }

  public static String execute(CompiledTemplate compiled, Object context, VariableResolverFactory factory, TemplateRegistry registry, TemplateOutputStream stream, String basedir) {
    return execute(compiled.getRoot(), compiled.getTemplate(), stream, context, factory, registry, basedir);
  }

  public static String execute(Node root, char[] template,
                               StringAppender appender, Object context,
                               VariableResolverFactory factory, TemplateRegistry registry) {

    return new TemplateRuntime(template, registry, root, ".").execute(appender, context, factory);
  }

  public String execute(StringBuilder appender, Object context, VariableResolverFactory factory) {
    return execute(new StringBuilderStream(appender), context, factory);
  }


  public static String execute(Node root, char[] template,
                               StringBuilder appender, Object context,
                               VariableResolverFactory factory, TemplateRegistry registry) {

    return new TemplateRuntime(template, registry, root, ".").execute(appender, context, factory);
  }

  public static String execute(Node root, char[] template,
                               StringBuilder appender, Object context,
                               VariableResolverFactory factory, TemplateRegistry registry, String baseDir) {

    return new TemplateRuntime(template, registry, root, baseDir).execute(appender, context, factory);
  }

  public static String execute(Node root, char[] template,
                               TemplateOutputStream appender, Object context,
                               VariableResolverFactory factory, TemplateRegistry registry) {

    return new TemplateRuntime(template, registry, root, ".").execute(appender, context, factory);
  }

  public static String execute(Node root, char[] template,
                               TemplateOutputStream appender, Object context,
                               VariableResolverFactory factory, TemplateRegistry registry, String baseDir) {

    return new TemplateRuntime(template, registry, root, baseDir).execute(appender, context, factory);
  }


  public String execute(StringAppender appender, Object context, VariableResolverFactory factory) {
    return execute(new StringAppenderStream(appender), context, factory);
  }

  public String execute(TemplateOutputStream stream, Object context, VariableResolverFactory factory) {
    execute(rootNode, stream, context, factory);
    return stream.toString();
  }

  public void execute(Node startNode, TemplateOutputStream stream, Object context, VariableResolverFactory factory) {
    Node node = startNode;
    while (node != null) {
      node.eval(this, stream, context, factory);
      node = node.getNextExecutableNode(context, factory);
    }
  }

  public Node getRootNode() {
    return rootNode;
  }

  public void setRootNode(Node rootNode) {
    this.rootNode = rootNode;
  }

  public char[] getTemplate() {
    return template;
  }

  public void setTemplate(char[] template) {
    this.template = template;
  }

  public TemplateRegistry getNamedTemplateRegistry() {
    return namedTemplateRegistry;
  }

  public void setNamedTemplateRegistry(TemplateRegistry namedTemplateRegistry) {
    this.namedTemplateRegistry = namedTemplateRegistry;
  }

  public ExecutionStack getRelPath() {
    if (relPath == null) {
      relPath = new ExecutionStack();
      relPath.push(baseDir);
    }
    return relPath;
  }

  public ExecutableValuePostProcessor getPostProcessor() {
    return postProcessor;
  }
}
