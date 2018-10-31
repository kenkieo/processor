package ken.android.processor.json;

import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;

import ken.android.json.JSONClass;
import ken.android.processor.PrintLog;

public class JsonProcessor {
    public static void process(Element element, ProcessingEnvironment processingEnv) {
	   PrintLog.print(JSONClass.class, "FindView");
	   PrintLog.print();
	   PrintLog.print();
	   PrintLog.print(element.getSimpleName().toString());
	   Element clazz = element.getEnclosingElement();
	   System.out.println(clazz);
	   for (Element enclosedElement : element.getEnclosedElements()) {
		  if (enclosedElement.getKind() == ElementKind.FIELD) {
			 Set<Modifier> modifiers = enclosedElement.getModifiers();
			 StringBuilder builder   = new StringBuilder();
			 if (modifiers.contains(Modifier.PRIVATE)) {
				builder.append("private").append(" ");
			 } else if (modifiers.contains(Modifier.PROTECTED)) {
				builder.append("protected").append(" ");
			 } else if (modifiers.contains(Modifier.PUBLIC)) {
				builder.append("public").append(" ");
			 }
			 if (modifiers.contains(Modifier.STATIC)) {
				builder.append("static").append(" ");
			 }
			 if (modifiers.contains(Modifier.FINAL)) {
				builder.append("final").append(" ");
			 }
			 builder.append(enclosedElement.asType()).append(" ");
			 builder.append(enclosedElement.getSimpleName()).append(" ");
			 PrintLog.print(builder);
		  }
	   }
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
}
