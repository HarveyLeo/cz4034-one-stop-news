import org.apache.commons.io.FilenameUtils;
import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.SerializationHelper;

/**
 * Created by Haihui on 5/3/2016.
 */
public class ClassPredictor {

    /**
     * Predict instances in the ARFF unlabelled file.
     * @param unlabelledFile The ARFF unlabelled file.
     * @param classifierFile The pre-built classifier file.
     * @param filterFile The pre-built filter file.
     * @throws Exception
     */
    public static void predict(String unlabelledFile, String classifierFile, String filterFile) throws Exception {

        //Load unlabelled data.
        Instances unlabelled = ArffFileManager.loadARFF(unlabelledFile, 0);

        //Create copy.
        Instances labelled = new Instances(unlabelled);

        //Deserialize the classifier and the filter.
        Classifier classifier = (Classifier) SerializationHelper.read(classifierFile);

        Instances filteredUnlabelled = StringToWordVectorFilter.filter(unlabelledFile, 0, filterFile);

        // label instances
        for (int i = 0; i < filteredUnlabelled.numInstances(); i++) {
            double clsLabel = classifier.classifyInstance(filteredUnlabelled.instance(i));
            labelled.instance(i).setClassValue(clsLabel);
        }

        // save labeled data
        ArffFileManager.saveARFF(labelled, FilenameUtils.getPath(unlabelledFile) + "labelled-data.arff");

    }

}
