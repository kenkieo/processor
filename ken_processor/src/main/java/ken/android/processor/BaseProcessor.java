package ken.android.processor;

import javax.annotation.processing.ProcessingEnvironment;

public class BaseProcessor {
    
    private ProcessingEnvironment mProcessingEnvironment;
    
    public void setProcessingEnvironment(ProcessingEnvironment processingEnvironment) {
	   mProcessingEnvironment = processingEnvironment;
    }
}
