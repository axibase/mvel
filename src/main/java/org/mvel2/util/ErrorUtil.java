package org.mvel2.util;

import org.mvel2.CompileException;
import org.mvel2.ErrorDetail;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Mike Brock .
 */
public class ErrorUtil {
  private static final Logger LOG = Logger.getLogger(ErrorUtil.class.getName());

  private static String createSubstring(char[] arr, int index) {
    return new String(arr, index, arr.length - index);
  }

  public static CompileException rewriteIfNeeded(CompileException caught, char[] outer, int outerCursor) {
    if (outer != caught.getExpr()) {
      if (caught.getExpr().length <= caught.getCursor()) {
        caught.setCursor(caught.getExpr().length - 1);
      }

      try {
        String innerExpr = createSubstring(caught.getExpr(), caught.getCursor());
        String outerStr = new String(outer);

        int newCursor = outerStr.indexOf(innerExpr);
        if (newCursor != -1) {
          caught.setCursor(newCursor);
          caught.setExpr(outer);
        }
      }
      catch (Throwable t) {
        LOG.log(Level.WARNING, "", t);
      }
    }
    return caught;
  }

  public static ErrorDetail rewriteIfNeeded(ErrorDetail detail, char[] outer, int outerCursor) {
    if (outer != detail.getExpr()) {
      String innerExpr = new String(detail.getExpr()).substring(detail.getCursor());
      detail.setExpr(outer);

      int newCursor = outerCursor;
      newCursor += new String(outer).substring(outerCursor).indexOf(innerExpr);

      detail.setCursor(newCursor);
    }
    return detail;
  }
}
