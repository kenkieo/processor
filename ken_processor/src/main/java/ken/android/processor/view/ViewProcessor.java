package ken.android.processor.view;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.tools.JavaFileObject;

import ken.android.processor.BaseProcessor;
import ken.android.processor.KenProcessor;
import ken.android.view.FindView;
import ken.android.view.ViewClick;

public class ViewProcessor extends BaseProcessor {
    
    private static final String RESOURCE_CLASS_NAME = "%s_BindViewProcess";
    private static final String METHOD_FIND_VIEW    = "findView";
    private static final String METHOD_ON_CLICK     = "onClickView";
    private static final String VIEW_CLASS          = "android.view.View";
    
    private HashMap<String, ViewInfo>     mViewInfoMap              = new HashMap<>();
    private HashMap<String, List<String>> mItemMap                  = new HashMap<>();
    private HashMap<String, List<String>> mItemFindViewFieldMap     = new HashMap<>();
    private HashMap<String, List<String>> mItemOnClickViewMethodMap = new HashMap<>();
    
    private ViewProcessorUtils mProcessorUtils = new ViewProcessorUtils();
    
    protected ViewProcessor() {
    }
    
    public void process(RoundEnvironment roundEnv, ProcessingEnvironment processingEnv) {
	   for (Element element : roundEnv.getElementsAnnotatedWith(FindView.class)) {
		  String   fieldName = element.getSimpleName().toString();
		  ViewInfo viewInfo  = getViewInfo(element);
		  buildItemClassList(mItemMap, viewInfo);
		  List<String> findViewMethodList = buildItemList(mItemFindViewFieldMap, viewInfo, METHOD_FIND_VIEW);
		  FindView     findView           = element.getAnnotation(FindView.class);
		  findViewMethodList.add(1, String.format("o.%s = v.findViewById(%s);\n", fieldName, findView.value()));
		  mProcessorUtils.attachView(viewInfo);
	   }
	   for (Element element : roundEnv.getElementsAnnotatedWith(ViewClick.class)) {
		  String   methodName = element.getSimpleName().toString();
		  ViewInfo viewInfo   = getViewInfo(element);
		  buildItemClassList(mItemMap, viewInfo);
		  List<String>                             onClickMethodList = buildItemList(mItemOnClickViewMethodMap, viewInfo, METHOD_ON_CLICK);
		  ViewClick                                viewClick         = element.getAnnotation(ViewClick.class);
		  com.sun.tools.javac.code.Type.MethodType paramsType        = (com.sun.tools.javac.code.Type.MethodType) element.asType();
		  String                                   paramsTypeString  = "";
		  if (!paramsType.argtypes.isEmpty()) {
			 paramsTypeString = String.format("(%s)view", paramsType.argtypes.get(0));
		  }
		  StringBuilder builder = new StringBuilder();
		  builder.append(String.format("v.findViewById(%s).setOnClickListener(new android.view.View.OnClickListener() {\n" +
				"@Override\n" +
				"public void onClick(android.view.View view) {\n" +
				"o.%s(%s);\n}\n}\n);", viewClick.value(), methodName, paramsTypeString));
		  onClickMethodList.add(onClickMethodList.size() - 1, builder.toString());
		  mProcessorUtils.attachView(viewInfo);
	   }
	   Set<String> keys = mItemMap.keySet();
	   for (String key : keys) {
		  List<String> stringList         = mItemMap.get(key);
		  ViewInfo     viewInfo           = getViewInfo(key);
		  List<String> findViewMethodList = buildItemList(mItemFindViewFieldMap, viewInfo, METHOD_FIND_VIEW);
		  if (findViewMethodList != null) {
			 stringList.addAll(stringList.size() - 1, findViewMethodList);
		  }
		  List<String> onClickMethodList = buildItemList(mItemOnClickViewMethodMap, viewInfo, METHOD_ON_CLICK);
		  if (onClickMethodList != null) {
			 stringList.addAll(stringList.size() - 1, onClickMethodList);
		  }
		  try { // write the file
			 JavaFileObject source = processingEnv.getFiler().createSourceFile(String.format("%s", viewInfo.resourcePath));
			 Writer         writer = source.openWriter();
			 writer.write(KenProcessor.join("\n", stringList));
			 writer.flush();
			 writer.close();
		  } catch (IOException e) {
			 // Note: calling e.printStackTrace() will print IO errors
			 // that occur from the file already existing after its first run, this is normal
		  }
	   }
	   mProcessorUtils.build(processingEnv);
	   
    }
    
    private List<String> buildItemClassList(HashMap<String, List<String>> map, ViewInfo viewInfo) {
	   List<String> stringList = map.get(viewInfo.classPath);
	   if (stringList == null) {
		  stringList = new ArrayList<>();
		  stringList.add(KenProcessor.getPackageString(viewInfo.packageName));
		  stringList.add(KenProcessor.getImportString(viewInfo.classPath));
		  stringList.add(KenProcessor.getImportString(VIEW_CLASS));
		  stringList.add(KenProcessor.getClassName(viewInfo.resourceName));
		  stringList.add(getConstructor(viewInfo.resourceName, viewInfo.className));
		  stringList.add("}");
		  map.put(viewInfo.classPath, stringList);
	   }
	   return stringList;
    }
    
    private List<String> buildItemList(HashMap<String, List<String>> map, ViewInfo viewInfo, String methodName) {
	   List<String> list = map.get(viewInfo.classPath);
	   if (list == null) {
		  list = new ArrayList<>();
		  list.add(String.format("private void %s(final %s o, View v){\n", methodName, viewInfo.className));
		  list.add("}\n");
		  map.put(viewInfo.classPath, list);
	   }
	   return list;
    }
    
    public static String getConstructor(String resourceName, String className) {
	   return String.format("public %s(final %s o,final View v){\n" +
			 "%s(o, v);\n" +
			 "%s(o, v);\n" +
			 "}\n", resourceName, className, METHOD_FIND_VIEW, METHOD_ON_CLICK);
    }
    
    public ViewInfo getViewInfo(Element element) {
	   Element clazz       = element.getEnclosingElement();
	   String  clazzString = clazz.toString();
	   return getViewInfo(clazzString);
    }
    
    public ViewInfo getViewInfo(String clazzString) {
	   ViewInfo viewInfo = mViewInfoMap.get(clazzString);
	   if (viewInfo == null) {
		  viewInfo = new ViewInfo();
		  String[] packageInfo = KenProcessor.getPackageInfo(clazzString);
		  viewInfo.packageName = packageInfo[0];
		  viewInfo.className = packageInfo[1];
		  viewInfo.classPath = clazzString;
		  viewInfo.resourceName = String.format(RESOURCE_CLASS_NAME, viewInfo.className);
		  viewInfo.resourcePath = String.format(RESOURCE_CLASS_NAME, viewInfo.classPath);
		  mViewInfoMap.put(clazzString, viewInfo);
	   }
	   return viewInfo;
    }
    
    public static ViewProcessor attach(RoundEnvironment roundEnv, ProcessingEnvironment processingEnv) {
	   ViewProcessor viewProcessor = new ViewProcessor();
	   viewProcessor.process(roundEnv, processingEnv);
	   return viewProcessor;
    }
    
    
}
