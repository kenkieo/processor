package ken.android.processor.view;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.JavaFileObject;

import ken.android.processor.KenProcessor;
import ken.android.processor.KenProcessorUtils;

class ViewProcessorUtils {
    
    
    private List<String> mKenProcessorUtilsString = new ArrayList<>();
    private List<String> mImportList              = new ArrayList<>();
    private List<String> mBindList                = new ArrayList<>();
    String packageName = KenProcessorUtils.class.getPackage().getName();
    String className   = KenProcessorUtils.class.getSimpleName();
    
    protected ViewProcessorUtils() {
	   mKenProcessorUtilsString.add(KenProcessor.getPackageString(packageName));
	   mKenProcessorUtilsString.add(KenProcessor.getImportString(android.view.View.class.getName()));
	   mKenProcessorUtilsString.add(KenProcessor.getClassName(className));
	   mKenProcessorUtilsString.add(String.format("private static %s mIns = null;\n", className));
	   mKenProcessorUtilsString.add(String.format("private %s() {}\n", className));
	   mKenProcessorUtilsString.add(String.format("public static %s getIns() {\n", className));
	   mKenProcessorUtilsString.add(String.format("synchronized (%s.class) {\n", className));
	   mKenProcessorUtilsString.add(String.format(" if (mIns == null) { mIns = new %s(); }\n", className));
	   mKenProcessorUtilsString.add("}\n");
	   mKenProcessorUtilsString.add("return mIns;\n");
	   mKenProcessorUtilsString.add("}\n");
	   mKenProcessorUtilsString.add("public void bind(Object o, View v) {\n");
	   mKenProcessorUtilsString.add("}\n");
	   mKenProcessorUtilsString.add("}\n");
    }
    
    public void attachView(ViewInfo viewInfo) {
	   if (!mImportList.contains(viewInfo.resourcePath)) {
		  mImportList.add(KenProcessor.getImportString(viewInfo.classPath));
		  mImportList.add(KenProcessor.getImportString(viewInfo.resourcePath));
		  
		  mBindList.add(String.format("if (o instanceof %s){\n", viewInfo.className));
		  mBindList.add(String.format("new %s((%s)o,v);", viewInfo.resourceName, viewInfo.className));
		  mBindList.add("return;");
		  mBindList.add("}");
	   }
    }
    
    public void build(ProcessingEnvironment processingEnv) {
	   try { // write the file
		  mKenProcessorUtilsString.addAll(1, mImportList);
		  mKenProcessorUtilsString.addAll(mKenProcessorUtilsString.size() - 2, mBindList);
		  JavaFileObject source = processingEnv.getFiler().createSourceFile(String.format("%s", KenProcessorUtils.class.getName()));
		  Writer         writer = source.openWriter();
		  writer.write(KenProcessor.join("\n", mKenProcessorUtilsString));
		  writer.flush();
		  writer.close();
	   } catch (IOException e) {
		  e.printStackTrace();
	   }
    }
    
}