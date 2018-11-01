package ken.android.processor;

import com.google.auto.service.AutoService;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

import ken.android.json.JSONClass;
import ken.android.processor.json.JsonProcessor;
import ken.android.processor.view.ViewProcessor;
import ken.android.view.FindView;
import ken.android.view.ViewAnnotation;
import ken.android.view.ViewClick;

@SupportedAnnotationTypes({"ken.android.json.JSONClass", "ken.android.view.FindView", "ken.android.view.ViewAninotation"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@AutoService(Processor.class)
public class KenProcessor extends AbstractProcessor {
    
    private Messager      mMessager;
    private ViewProcessor mViewProcessor;
    private JsonProcessor mJsonProcessor;
    
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
	   super.init(processingEnv);
	   mMessager = processingEnv.getMessager();
    }
    
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
	   System.out.println("process >>>>>>>>>> ");
	   if (mViewProcessor == null) {
		  mViewProcessor = ViewProcessor.attach(roundEnv, processingEnv);
	   }
	   return true;
    }
    
    @Override
    public Set<String> getSupportedAnnotationTypes() {
	   Set<String> annotations = new LinkedHashSet<>();
	   annotations.add(JSONClass.class.getCanonicalName());
	   annotations.add(FindView.class.getCanonicalName());
	   annotations.add(ViewClick.class.getCanonicalName());
	   annotations.add(ViewAnnotation.class.getCanonicalName());
	   return annotations;
    }
    
    @Override
    public SourceVersion getSupportedSourceVersion() {
	   return super.getSupportedSourceVersion();
    }
    
    public static String[] getPackageInfo(String className) {
	   String[] strs  = new String[]{"", className};
	   int      start = className.lastIndexOf(".");
	   if (start >= 1) {
		  strs[0] = className.substring(0, start);
		  strs[1] = className.substring(start + 1);
	   }
	   return strs;
    }
    
    public static String getPackageString(String packageName) {
	   return String.format("package %s;\n", packageName);
    }
    
    public static String getImportString(String classString) {
	   return String.format("import %s;\n", classString);
    }
    
    public static String getClassName(String resourceName) {
	   return String.format("public class %s {\n", resourceName);
    }
    
    public static String join(CharSequence delimiter, Object[] tokens) {
	   StringBuilder sb        = new StringBuilder();
	   boolean       firstTime = true;
	   for (Object token : tokens) {
		  if (firstTime) {
			 firstTime = false;
		  } else {
			 sb.append(delimiter);
		  }
		  sb.append(token);
	   }
	   return sb.toString();
    }
    
    public static String join(CharSequence delimiter, Iterable tokens) {
	   StringBuilder sb = new StringBuilder();
	   Iterator<?>   it = tokens.iterator();
	   if (it.hasNext()) {
		  sb.append(it.next());
		  while (it.hasNext()) {
			 sb.append(delimiter);
			 sb.append(it.next());
		  }
	   }
	   return sb.toString();
    }
}
