package org.mvel2.tests.templates.tests.res;

import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.templates.TemplateRuntime;
import org.mvel2.templates.util.TemplateOutputStream;
import org.mvel2.templates.res.Node;

public class TestPluginNode extends Node {

  public void eval(TemplateRuntime runtime, TemplateOutputStream appender, Object ctx, VariableResolverFactory factory) {
    appender.append("THIS_IS_A_TEST");
  }

  public boolean demarcate(Node terminatingNode, char[] template) {
    return false;
  }
}
