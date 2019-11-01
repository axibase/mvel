package org.mvel2.templates;

public interface ExecutableValuePostProcessor {
    String process(Object value);

    ExecutableValuePostProcessor DEFAULT = new ExecutableValuePostProcessor() {
        @Override
        public String process(Object value) {
            return String.valueOf(value);
        }
    };
}
