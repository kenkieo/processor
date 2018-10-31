package ken.android.processor;

import com.google.auto.service.AutoService;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import ken.android.json.JSONClass;
import ken.android.processor.json.JsonProcessor;
import ken.android.processor.view.ViewProcessor;
import ken.android.view.FindView;
import ken.android.view.ViewAnnotation;
import ken.android.view.ViewClick;

@SupportedAnnotationTypes({"ken.android.json.JSONClass", "ken.android.view.FindView", "ken.android.view.ViewAninotation",})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@AutoService(Processor.class)
public class KenProcessor extends AbstractProcessor {
    
    private Messager mMessager;
    private List<String> mKenProcessorUtilsString = new ArrayList<>();
    private ViewProcessor mViewProcessor;
    private JsonProcessor mJsonProcessor;
    
    public KenProcessor() {
	   super();
	   mViewProcessor = new ViewProcessor().setProcessorUtilsListString(mKenProcessorUtilsString);
	   mViewProcessor.setProcessingEnvironment(processingEnv);
	   mJsonProcessor = new JsonProcessor();
    }
    
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
	   super.init(processingEnv);
	   mMessager = processingEnv.getMessager();
    }
    
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
	   System.out.println("process >>>>>>>>>> ");
	   if (mKenProcessorUtilsString.isEmpty()) {
		  mKenProcessorUtilsString.add("package ken.android.processor;");
		  mKenProcessorUtilsString.add("import android.app.Application;");
		  mKenProcessorUtilsString.add("public class KenProcessorUtils {");
		  mKenProcessorUtilsString.add("private static KenProcessorUtils mIns = null;");
		  mKenProcessorUtilsString.add("private KenProcessorUtils() {}");
		  mKenProcessorUtilsString.add("    public static KenProcessorUtils getIns() {");
		  mKenProcessorUtilsString.add("synchronized (KenProcessorUtils.class) {");
		  mKenProcessorUtilsString.add(" if (mIns == null) { mIns = new KenProcessorUtils(); }");
		  mKenProcessorUtilsString.add("}");
		  mKenProcessorUtilsString.add("return mIns;");
		  mKenProcessorUtilsString.add("}");
		  mKenProcessorUtilsString.add("public void initApplication(Application application) {}");
		  mKenProcessorUtilsString.add("public void bind(Object o) {");
		  mKenProcessorUtilsString.add("}");
	   }
	   ViewProcessor.getConstructor()
	   for (Element element : roundEnv.getElementsAnnotatedWith(FindView.class)) {
		  mViewProcessor.process(element);
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
    
    public static String getPackage(String className) {
	   int start = className.lastIndexOf(".");
	   if (start >= 1) {
		  return className.substring(0, start);
	   }
	   return "";
    }
    
    public static String getPackageString(String packageName) {
	   return String.format("package %s;", packageName);
    }
    
    public static String getImportString(String classString) {
	   return String.format("import %s;", classString);
    }
    
    public static String getClassName(String className) {
	   return String.format("public class %s_BindProcess extends %s {", className, className);
    }
    
    
}
