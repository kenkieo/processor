package ken.android.processor;

import android.app.Application;

public class KenProcessorUtils {
    
    private static KenProcessorUtils mIns = null;
//    private        HashMap<Activity, List<Object>> mActivity2ProcessorObjectMap = new HashMap<>();
//    private        HashMap<Activity, Object>       mProcessorObjectMap          = new HashMap<>();
//    private        Activity                        mActivity                    = null;
    
    private KenProcessorUtils() {}
    
    public static KenProcessorUtils getIns() {
	   synchronized (KenProcessorUtils.class) {
		  if (mIns == null) { mIns = new KenProcessorUtils(); }
	   }
	   return mIns;
    }
    
    public void initApplication(Application application) {
//	   application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
//		  @Override
//		  public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
//			 mActivity = activity;
//		  }
//
//		  @Override
//		  public void onActivityStarted(Activity activity) {
//			 mActivity = activity;
//		  }
//
//		  @Override
//		  public void onActivityResumed(Activity activity) {
//			 mActivity = activity;
//		  }
//
//		  @Override
//		  public void onActivityPaused(Activity activity) {
//		  }
//
//		  @Override
//		  public void onActivityStopped(Activity activity) {
//		  }
//
//		  @Override
//		  public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
//		  }
//
//		  @Override
//		  public void onActivityDestroyed(Activity activity) {
//			 if (mActivity == activity) {
//				mActivity = null;
//			 }
//		  }
//	   });
    }
    
    public void bind(Object o) {
    }
    
}
