/**
 * Created by Haihui on 5/3/2016.
 */

import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.functions.SMO;
import weka.core.Instances;
import weka.core.SerializationHelper;


public class BoostClassifier {

    /**
     * Build the classifier by AdaBoostM1 with SMO and serialize it.
     * @param filename The full path of the ARFF file.
     * @param classIndex The index of the class attribute.
     * @return The built classifier.
     * @throws Exception
     */
    public static AdaBoostM1 classifyBySMOBoosting(String filename, int classIndex) throws Exception {

        //Load the instance.
        Instances instances = ArffFileManager.loadARFF(filename, classIndex);

        //Set AdaBoostM1 classifier.
        AdaBoostM1 boostClassifier = new AdaBoostM1();
        SMO SMOClassifier = new SMO();
        boostClassifier.setClassifier(SMOClassifier);
        boostClassifier.setNumIterations(10);

        //Build the classifier.
        boostClassifier.buildClassifier(instances);

        //Serialize the classifier.
        SerializationHelper.write("files/adaboost-smo-classifier.model", boostClassifier);

        return boostClassifier;
    }


}
