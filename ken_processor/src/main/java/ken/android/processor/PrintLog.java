package ken.android.processor;

public class PrintLog {
    
    public static void print(Object... objects) {
	   StringBuilder builder = new StringBuilder();
	   for (Object o : objects) {
		  if (o instanceof Integer ||
				o instanceof Boolean ||
				o instanceof String ||
				o instanceof Float ||
				o instanceof Double ||
				o instanceof Long) {
			 builder.append(o);
		  } else if (o instanceof StringBuilder || o instanceof StringBuffer) {
			 builder.append(String.valueOf(o));
		  } else if (o instanceof Class) {
			 Class cls = (Class) o;
			 builder.append(cls.getSimpleName());
		  } else {
			 builder.append(o.getClass().getSimpleName());
		  }
		  builder.append(" ");
	   }
	   System.out.println(builder.toString());
    }
}
