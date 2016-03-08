import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.SerializationHelper;

import java.util.Random;

/**
 * Created by Haihui on 5/3/2016.
 */
public class ClassifierEvaluator {

    /**
     * Use 10-fold cross-validation to evaluate the built classifier.
     * @param classifierFile The serialized classifier file.
     * @param arffFile The full path of the testing ARFF file.
     * @param classIndex The index of the class attribute.
     * @return The Evaluation object.
     * @throws Exception
     */
    public static Evaluation crossValidate(String classifierFile, String arffFile, int classIndex) throws Exception {

        //Load the instance.
        Instances instances = ArffFileManager.loadARFF(arffFile, classIndex);

        //Deserialize as the classifier.
        Classifier classifier = (Classifier) SerializationHelper.read(classifierFile);

        //Evaluate the classifier.
        Evaluation eval = new Evaluation(instances);
        eval.crossValidateModel(classifier, instances, 10, new Random(1));

        return eval;
    }


    /**
     * Use a test set to evaluate the classifier.
     * @param classifierFile The serialized classifier file.
     * @param arffFile The full path of the testing ARFF file.
     * @param classIndex The index of the class attribute.
     * @return The Evaluation object.
     * @throws Exception
     */
    public static Evaluation evaluateWithTestSet(String classifierFile, String arffFile, int classIndex) throws Exception {

        //Load the instance.
        Instances instances = ArffFileManager.loadARFF(arffFile, classIndex);

        //Deserialize as the classifier.
        Classifier classifier = (Classifier) SerializationHelper.read(classifierFile);

        //Evaluate the classifier.
        Evaluation eval = new Evaluation(instances);
        eval.evaluateModel(classifier, instances);

        return eval;
    }


}
