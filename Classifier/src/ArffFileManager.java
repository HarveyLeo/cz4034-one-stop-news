/**
 * Created by Haihui on 5/3/2016.
 */

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import java.io.PrintWriter;
import java.io.FileWriter;


public class ArffFileManager {


    /**
     * Load the ARFF file into an Instances object.
     * @param filename The full path of the file to be saved.
     * @param classIndex The index of the class attribute.
     * @return The Instances object.
     * @throws Exception
     */
    public static Instances loadARFF (String filename, int classIndex) throws Exception {

        DataSource source = new DataSource(filename);
        Instances inputInstances = source.getDataSet();
        if (inputInstances.classIndex() == -1)
            inputInstances.setClassIndex(classIndex);
        return inputInstances;

    }


    /**
     * Save an Instances object into an ARFF file.
     * @param outputInstances The instances to be saved.
     * @param filename The full path of the file to be saved.
     * @throws Exception
     */
    public static void saveARFF(Instances outputInstances, String filename) throws Exception {
        PrintWriter writer = new PrintWriter(new FileWriter(filename));
        writer.print(outputInstances);
        writer.close();
    }
}
