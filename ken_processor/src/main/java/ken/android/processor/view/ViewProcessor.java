package ken.android.processor.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.lang.model.element.Element;

import ken.android.processor.BaseProcessor;
import ken.android.processor.KenProcessor;
import ken.android.processor.PrintLog;

public class ViewProcessor extends BaseProcessor {
    
    private HashMap<String, List<String>> mItemMap                  = new HashMap<>();
    private HashMap<String, List<String>> mItemFindViewMap          = new HashMap<>();
    private HashMap<String, List<String>> mItemOnClickViewMap       = new HashMap<>();
    private List<String>                  mProcessorUtilsListString = null;
    
    public void process(Element element) {
	   String       className   = element.getSimpleName().toString();
	   Element      clazz       = element.getEnclosingElement();
	   String       clazzString = clazz.toString();
	   List<String> stringList  = mItemMap.get(clazzString);
	   String       packageName = KenProcessor.getPackage(clazzString);
	   if (stringList == null) {
		  stringList = new ArrayList<>();
		  stringList.add(KenProcessor.getPackageString(packageName));
		  stringList.add(KenProcessor.getImportString(clazzString));
		  stringList.add(KenProcessor.getClassName(className));
		  stringList.add(getConstructor(className));
		  stringList.add("}");
	   }
//	   Elements.getPackageOf(Element).asType().toString();
//	   for (Element enclosedElement : element.getEnclosedElements()) {
//		  if (enclosedElement.getKind() == ElementKind.FIELD) {
//			 Set<Modifier> modifiers = enclosedElement.getModifiers();
//			 StringBuilder builder   = new StringBuilder();
//			 if (modifiers.contains(Modifier.PRIVATE)) {
//				builder.append("private").append(" ");
//			 } else if (modifiers.contains(Modifier.PROTECTED)) {
//				builder.append("protected").append(" ");
//			 } else if (modifiers.contains(Modifier.PUBLIC)) {
//				builder.append("public").append(" ");
//			 }
//			 if (modifiers.contains(Modifier.STATIC)) {
//				builder.append("static").append(" ");
//			 }
//			 if (modifiers.contains(Modifier.FINAL)) {
//				builder.append("final").append(" ");
//			 }
//			 builder.append(enclosedElement.asType()).append(" ");
//			 builder.append(enclosedElement.getSimpleName()).append(" ");
//			 PrintLog.print(builder);
//		  }
//	   }
	   PrintLog.print();
	   PrintLog.print();
//	   StringBuilder builder = new StringBuilder()
//			 .append("package com.chiclaim.processor.generated;\n\n")
//			 .append("public class GeneratedClass {\n\n") // open class
//			 .append("\tpublic String getMessage() {\n") // open method
//			 .append("\t\treturn \"");
//
//
//	   // for each javax.lang.model.element.Element annotated with the CustomAnnotation
//	   String objectType = element.getSimpleName().toString();
//	   // this is appending to the return statement
//	   builder.append(objectType).append(" says hello!\\n");
//
//	   builder.append("\";\n") // end return
//			 .append("\t}\n") // close method
//			 .append("}\n"); // close class
//
//	   try { // write the file
//		  JavaFileObject source = processingEnv.getFiler().createSourceFile("com.chiclaim.processor.generated.GeneratedClass");
//		  Writer         writer = source.openWriter();
//		  writer.write(builder.toString());
//		  writer.flush();
//		  writer.close();
//	   } catch (IOException e) {
//		  // Note: calling e.printStackTrace() will print IO errors
//		  // that occur from the file already existing after its first run, this is normal
//	   }
    }
    
    public ViewProcessor setProcessorUtilsListString(List<String> processorUtilsListString) {
	   mProcessorUtilsListString = processorUtilsListString;
	   return this;
    }
    
    
    public static String getConstructor(String className) {
	   return String.format("public class %s_BindProcess(%s o){" +
			 "findView(o)" +
			 "onClickView(o)" +
			 "}", className, className);
    }
}
