package ken.android.processor;

import android.view.View;

public class KenProcessorUtils {
    
    private static KenProcessorUtils mIns = null;
    
    private KenProcessorUtils() {}
    
    public static KenProcessorUtils getIns() {
	   synchronized (KenProcessorUtils.class) {
		  if (mIns == null) { mIns = new KenProcessorUtils(); }
	   }
	   return mIns;
    }
    
    public void bind(Object o, View v) {
    }
    
}
