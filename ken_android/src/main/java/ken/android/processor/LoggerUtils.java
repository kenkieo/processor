package ken.android.processor;

import android.util.Log;
import android.view.View;


public class LoggerUtils {
    
    public static final String TAG = "Logger";
    
    public static boolean LOG_SWITCH = true;
    
    public static void v(Object text) {
	   if (LOG_SWITCH) {
		  v(TAG, text);
	   }
    }
    
    public static void v(Object tag, Object text) {
	   if (LOG_SWITCH) {
		  try {
			 Log.v(String.valueOf(tag), String.valueOf(text));
		  } catch (Exception e) {
			 e.printStackTrace();
		  }
	   }
    }
    
    public static void i(Object text) {
	   if (LOG_SWITCH) {
		  i(TAG, text);
	   }
    }
    
    public static void i(Object... objects) {
	   if (LOG_SWITCH) {
		  StringBuffer stringBuffer = new StringBuffer();
		  for (Object o : objects) {
			 if (o instanceof View) {
				View v = (View) o;
				o = o.getClass().getSimpleName();
			 } else if (o instanceof Class) {
				o = o.getClass().getSimpleName();
			 }
			 stringBuffer.append(String.valueOf(o)).append(" , ");
		  }
		  Log.i(TAG, stringBuffer.toString());
	   }
    }
    
    //    public static void i(String TAG, Object str) {
//	   if (LOG_SWITCH) {
//		  try {
////			 StackTraceElement[] traceElements = new Throwable().getStackTrace();
////			 for (StackTraceElement traceElement : traceElements) {
////				Log.i(TAG, traceElement.getMethodName() + "_" + traceElement.getClassName());
////			 }
//			 Log.i(String.valueOf(TAG), String.valueOf(str));
//		  } catch (Exception e) {
//			 e.printStackTrace();
//		  }
//	   }
//    }
//
    public static void d(Object text) {
	   if (LOG_SWITCH) {
		  d(TAG, text);
	   }
    }
    
    public static void d(Object TAG, Object str) {
	   if (LOG_SWITCH) {
		  try {
			 Log.d(String.valueOf(TAG), String.valueOf(str));
		  } catch (Exception e) {
			 e.printStackTrace();
		  }
	   }
    }
    
    public static void e(Object text) {
	   if (LOG_SWITCH) {
		  e(TAG, text);
	   }
    }
    
    public static void e(Object TAG, Object str) {
	   if (LOG_SWITCH) {
		  e(TAG, str, null);
	   }
    }
    
    public static void e(Object TAG, Object str, Throwable tr) {
	   if (LOG_SWITCH) {
		  try {
			 Log.e(String.valueOf(TAG), String.valueOf(str), tr);
		  } catch (Exception e) {
			 e.printStackTrace();
		  }
	   }
    }
    
    public static void w(Object text) {
	   if (LOG_SWITCH) {
		  w(TAG, text);
	   }
    }
    
    public static void w(Object TAG, Object str) {
	   if (LOG_SWITCH) {
		  w(TAG, str, null);
	   }
    }
    
    public static void w(Object TAG, Object str, Throwable tr) {
	   if (LOG_SWITCH) {
		  try {
			 Log.w(String.valueOf(TAG), String.valueOf(str), tr);
		  } catch (Exception e) {
			 e.printStackTrace();
		  }
	   }
    }
    
}