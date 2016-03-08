/**
 * Created by Haihui on 5/3/2016.
 */
import weka.classifiers.meta.AdaBoostM1;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class Tester {

    public static void main(String[] args) throws Exception {

//        FileConverter.convertJSONtoARFF("Files/json-testing.json",true);
//        FileConverter.convertCSVtoARFF("Files/training-set.csv");


//        Evaluation eval = ClassifierEvaluator.crossValidate("files/adaboost-smo.model","files/training-set-filtered.arff", 0);
//        Evaluation eval = ClassifierEvaluator.evaluateWithTestSet("files/adaboost-smo.model","files/training-set-filtered.arff", 0);
//        System.out.println(eval.toClassDetailsString());
//        System.out.println(eval.toSummaryString());


//        StringToWordVectorFilter.filter("files/training-set.arff", 0);
//        AdaBoostM1 cls = BoostClassifier.classifyBySMOBoosting("files/training-set-filtered.arff", 0);
        ClassPredictor.predict("files/unlabelled-data.arff", "files/adaboost-smo-classifier.model", "files/string-to-word-vector-filter.model");

    }
}
