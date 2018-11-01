package ken.android.processor;

public class PrintLog {
    
    public static void print(Object... objects) {
	   StringBuilder builder = new StringBuilder();
	   for (Object o : objects) {
		  builder.append(String.valueOf(o));
		  builder.append(" ");
	   }
	   System.out.println(builder.toString());
    }
}
